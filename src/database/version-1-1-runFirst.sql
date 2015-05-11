CREATE TABLE IF NOT EXISTS "USER"
(
	id        BIGSERIAL PRIMARY KEY,
	name      TEXT                  NOT NULL,
	email     TEXT,
	login     CHARACTER VARYING(64) NOT NULL UNIQUE,
	password  CHARACTER VARYING(32) NOT NULL DEFAULT 'pass',
	role      CHARACTER VARYING(32) NOT NULL,
	skype     TEXT,
	telephone TEXT,
	birthdate DATE
);
CREATE INDEX id_index ON "USER" (id);

CREATE TABLE IF NOT EXISTS "STUDENT"
(
	id                              BIGINT PRIMARY KEY,
	state                           TEXT,
	hire_date                       DATE,
	university                      BIGINT,
	faculty                         BIGINT,
	course                          INT DEFAULT 0,
	s_group                         INT DEFAULT 0,
	graduation_date                 INT DEFAULT 0,
	working_hours                   INT DEFAULT 0,
	billable                        DATE,
	role_current_project            TEXT,
	techs_current_project           TEXT,
	english_level                   INT,
	term_marks                      TEXT,
	current_project                 TEXT,
	team_lead_current_project       BIGINT,
	project_manager_current_project BIGINT,
	training_before_working         BOOL,
	course_when_start_working       INT,
	speciality                      TEXT,
	wishes_hours_number             INT,
	trainings_exadel                TEXT,
	FOREIGN KEY (id) REFERENCES "USER" (id),
	FOREIGN KEY (team_lead_current_project) REFERENCES "EMPLOYEE" (id),
	FOREIGN KEY (project_manager_current_project) REFERENCES "EMPLOYEE" (id)
);
ALTER TABLE "STUDENT" ADD CONSTRAINT "university_fk" FOREIGN KEY (university) REFERENCES "UNIVERSITY" (id);
ALTER TABLE "STUDENT" ADD CONSTRAINT "faculty_fk" FOREIGN KEY (faculty) REFERENCES "FACULTY" (id);

CREATE TABLE IF NOT EXISTS "EMPLOYEE"
(
	id BIGINT PRIMARY KEY,
	FOREIGN KEY (id) REFERENCES "USER" (id)
);

CREATE TABLE IF NOT EXISTS "CURATORING"
(
	id           BIGSERIAL PRIMARY KEY,
	student_id   BIGINT NOT NULL,
	employee_id  BIGINT NOT NULL,
	project_name TEXT,
	start_date   DATE,
	end_date     DATE,
	FOREIGN KEY (student_id) REFERENCES "STUDENT" (id),
	FOREIGN KEY (employee_id) REFERENCES "EMPLOYEE" (id)
);

CREATE OR REPLACE VIEW "STUDENT_VIEW" AS
	SELECT
		"USER".id,
		"USER".name,
		hire_date,
		"UNIVERSITY".name AS university,
		"FACULTY".name    AS faculty,
		course,
		s_group,
		graduation_date,
		working_hours,
		billable,
		role_current_project,
		techs_current_project,
		english_level
	FROM "USER"
		INNER JOIN "STUDENT" ON "USER".id = "STUDENT".id
		LEFT JOIN "UNIVERSITY" ON "STUDENT".university = "UNIVERSITY".id
		LEFT JOIN "FACULTY" ON "STUDENT".faculty = "FACULTY".id;

CREATE OR REPLACE VIEW "EMPLOYEE_VIEW" AS
	SELECT
		"USER".id,
		"USER".name,
		"USER".email,
		"USER".skype,
		"USER".role,
		"USER".telephone
	FROM "USER"
		INNER JOIN "EMPLOYEE" ON "USER".id = "EMPLOYEE".id;

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

CREATE OR REPLACE VIEW "SKILL_VIEW" AS
	SELECT
		"SKILL_TYPE".id AS id,
		"SKILL_TYPE".name,
		"SKILL_SET".user_id,
		level
	FROM "SKILL_SET"
		INNER JOIN "SKILL_TYPE" ON "SKILL_SET".skill_type_id = "SKILL_TYPE".id;

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
	is_on_project           TEXT,
	feedback_date           DATE,
	FOREIGN KEY (student_id) REFERENCES "STUDENT" (id),
	FOREIGN KEY (employee_id) REFERENCES "EMPLOYEE" (id)
);

--///////////////////////////////////////////////////////////
CREATE TABLE IF NOT EXISTS "UNIVERSITY"
(
	id   BIGSERIAL PRIMARY KEY,
	name TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS "FACULTY"
(
	id            BIGSERIAL PRIMARY KEY,
	university_id BIGINT,
	name          TEXT UNIQUE,
	FOREIGN KEY (university_id) REFERENCES "UNIVERSITY" (id)
);


--/////////////// Log //////////////////////////////////////

CREATE TABLE IF NOT EXISTS "STUDENT_LOG" (
	id          BIGSERIAL PRIMARY KEY,
	student_id  BIGINT NOT NULL,
	state       TEXT   NOT NULL,
	modify_date DATE   NOT NULL,
	FOREIGN KEY (student_id) REFERENCES "STUDENT" (id)
);


CREATE TABLE IF NOT EXISTS "DOCUMENT" (
	id              BIGSERIAL PRIMARY KEY,
	student_id      BIGINT NOT NULL,
	doctype         TEXT   NOT NULL,
	issue_date      DATE   NOT NULL,
	expiration_date DATE,
	info            TEXT
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


CREATE OR REPLACE FUNCTION find_student_by_name(desired_name TEXT)
	RETURNS SETOF "STUDENT_VIEW"
AS
	$BODY$
	DECLARE
		line                 "STUDENT_VIEW"%ROWTYPE;
		max_similarity_value REAL;
	BEGIN
		max_similarity_value := 0;
		FOR line IN (SELECT
			             *
		             FROM "STUDENT_VIEW" ORDER BY similarity(name, $1)  DESC) LOOP
			IF  desired_name =''
			THEN RETURN NEXT line;
			ELSE
				IF similarity(line.name, $1) > max_similarity_value
				THEN
					max_similarity_value = similarity(line.name, $1);
					RETURN NEXT line;
				ELSE
					IF 4 * similarity(line.name, $1) > max_similarity_value
					THEN
						RETURN NEXT line;
					END IF;
				END IF;
			END IF;
		END LOOP;
	END;
  $BODY$
LANGUAGE plpgsql VOLATILE;