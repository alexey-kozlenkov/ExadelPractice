CREATE TABLE IF NOT EXISTS "USER"
(
  id       BIGSERIAL PRIMARY KEY,
  login    CHARACTER VARYING(64) UNIQUE NOT NULL,
  password CHARACTER VARYING(32)        NOT NULL DEFAULT 'pass',
  name     TEXT                         NOT NULL,
  email    TEXT,
  role     CHARACTER VARYING(32)        NOT NULL
);
CREATE INDEX id_index ON "USER" (id);

CREATE TABLE IF NOT EXISTS "STUDENT"
(
  role  CHARACTER VARYING(32) NOT NULL DEFAULT 'stud',
  state TEXT not null,
  hire_date DATE,
  university TEXT NOT NULL,
  faculty TEXT NOT NULL,
  course INT NOT NULL,
  s_group INT NOT NULL,
  hours_number INT NOT NULL,
  billable DATE,
  role_current_project TEXT NOT NULL,
  techs_current_project TEXT NOT NULL,
  english_level TEXT NOT NULL,
  CONSTRAINT student_pkey PRIMARY KEY (id)
)
  INHERITS ("USER");

CREATE TABLE IF NOT EXISTS "EMPLOYEE"
(
  CONSTRAINT employee_pkey PRIMARY KEY (id)
)
  INHERITS ("USER");

-- ///////////////// Skills ////////////////////////

CREATE TABLE IF NOT EXISTS "SKILL_TYPE"
(
  id   BIGSERIAL PRIMARY KEY,
  name TEXT unique
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
  student_id  BIGINT NOT NULL,
  employee_id BIGINT NOT NULL,
  feedback    TEXT   NOT NULL,
  prof_competence TEXT NOT NULL,
  attitude_to_work TEXT NOT NULL,
  collective_relations TEXT NOT NULL,
  professional_progress TEXT NOT NULL,
  need_more_hours BOOLEAN NOT NULL,
  real_project BOOLEAN NOT NULL,
  date DATE NOT NULL,
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
  DELETE FROM "STUDENT_LOG" WHERE student_id = OLD.id;
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER t_iu_student
AFTER INSERT OR UPDATE ON "STUDENT" FOR EACH ROW EXECUTE PROCEDURE add_to_log();
CREATE TRIGGER t_d_student
before DELETE ON "STUDENT" FOR EACH ROW EXECUTE PROCEDURE remove_from_log();

