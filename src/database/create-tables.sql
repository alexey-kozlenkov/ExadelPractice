CREATE TABLE IF NOT EXISTS "STUDENT"
(
  id                    BIGSERIAL NOT NULL  PRIMARY KEY,
  name                  TEXT      NOT NULL,
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
  id                      BIGSERIAL NOT NULL,
  student_id              BIGINT    NOT NULL,
  employee_id             BIGINT    NOT NULL,
  content                 TEXT      NOT NULL,
  professional_competence TEXT      NOT NULL,
  attitude_to_work        TEXT      NOT NULL,
  collective_relations    TEXT      NOT NULL,
  professional_progress   TEXT      NOT NULL,
  need_more_hours         BOOLEAN   NOT NULL,
  is_on_project           BOOLEAN   NOT NULL,
  feedback_date           DATE      NOT NULL,
  FOREIGN KEY (student_id) REFERENCES "STUDENT" (id),
  FOREIGN KEY (employee_id) REFERENCES "EMPLOYEE" (id)
);

--/////////////// Log //////////////////////////////////////


CREATE TABLE IF NOT EXISTS "STUDENT_LOG" (
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


CREATE TRIGGER t_iu_student
AFTER INSERT OR UPDATE ON "STUDENT" FOR EACH ROW EXECUTE PROCEDURE add_to_log();
CREATE TRIGGER t_d_student
BEFORE DELETE ON "STUDENT" FOR EACH ROW EXECUTE PROCEDURE remove_from_log();


CREATE TABLE IF NOT EXISTS "DOCUMENT" (
  student_id      BIGINT NOT NULL,
  doctype         TEXT   NOT NULL,
  issue_date      DATE   NOT NULL,
  expiration_date DATE,
  info            TEXT
);

INSERT INTO "USER" (login, role, student_id) VALUES
  ('1test_login', 'test_role', 3);

SELECT
  'id'
FROM "USER"
WHERE student_id = 3;


INSERT INTO "STUDENT" (name, email, state, hire_date, university, faculty, course, s_group, working_hours, role_current_project, techs_current_project, english_level)
VALUES ('test_name', 'test_email', 'test_state', '2013-06-01', 'test_uni', 'test_fac', 0, 0, 0,
        'test_role_curr_proj', 'test_role_techs', 'test_eng');

INSERT INTO "EMPLOYEE" (name, email) VALUES ('name', 'email');
DELETE FROM "EMPLOYEE" WHERE id=8;