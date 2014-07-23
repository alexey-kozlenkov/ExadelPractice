CREATE TABLE IF NOT EXISTS "STUDENT"
(
  id                    BIGSERIAL PRIMARY KEY,
  name                  TEXT NOT NULL,
  email                 TEXT,
  state                 TEXT,
  hire_date             DATE,
  university            TEXT,
  faculty               TEXT,
  course                INT,
  s_group               INT,
  graduation_date       DATE,
  working_hours         INT,
  billable              DATE,
  role_current_project  TEXT,
  techs_current_project TEXT,
  english_level         TEXT
);

CREATE TABLE IF NOT EXISTS "EMPLOYEE"
(
  id    BIGSERIAL NOT NULL PRIMARY KEY,
  name  TEXT      NOT NULL,
  email TEXT
);

CREATE TABLE IF NOT EXISTS "USER"
(
  id          BIGSERIAL PRIMARY KEY,
  student_id  BIGINT,
  employee_id BIGINT,
  login       CHARACTER VARYING(64) NOT NULL,
  password    CHARACTER VARYING(32) NOT NULL DEFAULT 'pass',
  role        CHARACTER VARYING(32) NOT NULL,
  CONSTRAINT user_login_unique UNIQUE (login),
  FOREIGN KEY (student_id) REFERENCES "STUDENT" (id),
  FOREIGN KEY (employee_id) REFERENCES "EMPLOYEE" (id)
);
CREATE INDEX id_index ON "USER" (id);

-- ///////////////// Skills ////////////////////////

CREATE TABLE IF NOT EXISTS "SKILL_TYPE"
(
  id   BIGSERIAL PRIMARY KEY,
  name TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS "SKILL_SET"
(
  skill_type_id BIGINT NOT NULL,
  user_id       BIGINT NOT NULL,
  level         INTEGER,
  info          TEXT,
  UNIQUE (skill_type_id, user_id),
  FOREIGN KEY (user_id) REFERENCES "USER" (id),
  FOREIGN KEY (skill_type_id) REFERENCES "SKILL_TYPE" (id)
);
-- ////////////// Feedback ///////////////////////////////////
CREATE TABLE IF NOT EXISTS "FEEDBACK" (
  id                      BIGSERIAL PRIMARY KEY,
  student_id              BIGINT  NOT NULL,
  employee_id             BIGINT  NOT NULL,
  content                 TEXT    NOT NULL,
  professional_competence TEXT    NOT NULL,
  attitude_to_work        TEXT    NOT NULL,
  collective_relations    TEXT    NOT NULL,
  professional_progress   TEXT    NOT NULL,
  need_more_hours         BOOLEAN NOT NULL,
  is_on_project           BOOLEAN NOT NULL,
  feedback_date           DATE    NOT NULL,
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

CREATE OR REPLACE FUNCTION remove_from_log_and_update_user()
  RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM "STUDENT_LOG"
  WHERE student_id = OLD.id;

  UPDATE "USER"
  SET student_id = NULL
  WHERE student_id = OLD.id;

  DELETE FROM "USER"
  WHERE student_id IS NULL AND employee_id IS NULL ;
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_user_table()
  RETURNS TRIGGER AS $$
BEGIN
  UPDATE "USER"
  SET employee_id = NULL
  WHERE employee_id = OLD.id;

  DELETE FROM "USER"
  WHERE student_id IS NULL AND employee_id IS NULL;
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER t_iu_student
AFTER INSERT OR UPDATE ON "STUDENT" FOR EACH ROW EXECUTE PROCEDURE add_to_log();
CREATE TRIGGER t_d_student
BEFORE DELETE ON "STUDENT" FOR EACH ROW EXECUTE PROCEDURE remove_from_log_and_update_user();
CREATE TRIGGER t_d_employee
BEFORE DELETE ON "EMPLOYEE" FOR EACH ROW EXECUTE PROCEDURE update_user_table();