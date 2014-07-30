CREATE TABLE IF NOT EXISTS "USER"
(
  id       BIGSERIAL PRIMARY KEY,
  name     TEXT                  NOT NULL,
  email    TEXT                  NOT NULL,
  login    CHARACTER VARYING(64) NOT NULL UNIQUE,
  password CHARACTER VARYING(32) NOT NULL DEFAULT 'pass',
  role     CHARACTER VARYING(32) NOT NULL
);
CREATE INDEX id_index ON "USER" (id);

CREATE TABLE IF NOT EXISTS "STUDENT"
(
  id                    BIGINT PRIMARY KEY,
  state                 TEXT,
  hire_date             DATE,
  university            TEXT,
  faculty               TEXT,
  course                INT DEFAULT 0,
  s_group               INT DEFAULT 0,
  graduation_date       DATE,
  working_hours         INT DEFAULT 0,
  billable              DATE,
  role_current_project  TEXT,
  techs_current_project TEXT,
  english_level         TEXT,
  FOREIGN KEY (id) REFERENCES "USER" (id)
);

CREATE TABLE IF NOT EXISTS "EMPLOYEE"
(
  id BIGINT PRIMARY KEY,
  FOREIGN KEY (id) REFERENCES "USER" (id)
);


-- ///////////////// Skills ////////////////////////

CREATE TABLE IF NOT EXISTS "SKILL_TYPE"
(
  id   BIGSERIAL PRIMARY KEY,
  name TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS "SKILL_SET"
(
  id            BIGSERIAL PRIMARY KEY,
  skill_type_id BIGINT NOT NULL,
  user_id       BIGINT NOT NULL,
  level         INT DEFAULT 0,
  info          TEXT,
  UNIQUE (skill_type_id, user_id),
  FOREIGN KEY (user_id) REFERENCES "USER" (id),
  FOREIGN KEY (skill_type_id) REFERENCES "SKILL_TYPE" (id)
);
-- ////////////// Feedback ///////////////////////////////////
CREATE TABLE IF NOT EXISTS "FEEDBACK" (
  id                      BIGSERIAL PRIMARY KEY,
  student_id              BIGINT,
  employee_id             BIGINT,
  content                 TEXT,
  professional_competence TEXT,
  attitude_to_work        TEXT,
  collective_relations    TEXT,
  professional_progress   TEXT,
  need_more_hours         BOOLEAN,
  is_on_project           BOOLEAN,
  feedback_date           DATE,
  FOREIGN KEY (student_id) REFERENCES "STUDENT" (id),
  FOREIGN KEY (employee_id) REFERENCES "EMPLOYEE" (id)
);

--/////////////// Log //////////////////////////////////////


CREATE TABLE IF NOT EXISTS "STUDENT_LOG" (
  id          BIGSERIAL PRIMARY KEY,
  student_id  BIGINT NOT NULL,
  state       TEXT   NOT NULL,
  modify_date DATE   NOT NULL,
  FOREIGN KEY (student_id) REFERENCES "STUDENT" (id)
);

--DROP TRIGGER t_students ON "STUDENT";
--DROP TRIGGER t2_students ON "STUDENT";

CREATE OR REPLACE FUNCTION add_to_log()
  RETURNS TRIGGER AS $$
DECLARE
  state TEXT;
  id    BIGINT;
BEGIN

  IF TG_OP = 'INSERT'
  THEN
    id = NEW.id;
    state = NEW.state;
    INSERT INTO "STUDENT_LOG" (student_id, state, modify_date) VALUES (id, state, NOW());
    RETURN NEW;
  ELSIF TG_OP = 'UPDATE'
    THEN
      IF NEW.state = OLD.state
      THEN
        RETURN NEW;
      END IF;
      id = NEW.id;
      state = NEW.state;
      INSERT INTO "STUDENT_LOG" (student_id, state, modify_date) VALUES (id, state, NOW());
      RETURN NEW;
  END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION remove_from_log()
  RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM "STUDENT_LOG"
  WHERE student_id = OLD.id;
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION remove_student_from_user()
  RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM "USER"
  WHERE id = OLD.id;
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION remove_employee_from_user()
  RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM "USER"
  WHERE id = OLD.id;
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER t_iu_student
AFTER INSERT OR UPDATE ON "STUDENT" FOR EACH ROW EXECUTE PROCEDURE add_to_log();
CREATE TRIGGER t_d_student
BEFORE DELETE ON "STUDENT" FOR EACH ROW EXECUTE PROCEDURE remove_from_log();
CREATE TRIGGER t_d_student_clear_user
AFTER DELETE ON "STUDENT" FOR EACH ROW EXECUTE PROCEDURE remove_student_from_user();
CREATE TRIGGER t_d_employee_clear_user
AFTER DELETE ON "EMPLOYEE" FOR EACH ROW EXECUTE PROCEDURE remove_employee_from_user();

CREATE TABLE IF NOT EXISTS "DOCUMENT" (
  id              BIGSERIAL PRIMARY KEY,
  student_id      BIGINT NOT NULL,
  doctype         TEXT   NOT NULL,
  issue_date      DATE   NOT NULL,
  expiration_date DATE,
  info            TEXT
);