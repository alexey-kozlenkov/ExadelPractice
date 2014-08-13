CREATE TABLE IF NOT EXISTS "UNIVERSITY"
(
  id BIGSERIAL PRIMARY KEY,
  name TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS "FACULTY"
(
  id BIGSERIAL PRIMARY KEY,
  university_id BIGINT,
  name TEXT UNIQUE,
  FOREIGN KEY (university_id) REFERENCES "UNIVERSITY" (id)
);

DROP FUNCTION find_student_by_name(TEXT);

DROP VIEW "STUDENT_VIEW";

ALTER TABLE "STUDENT" DROP COLUMN faculty;
ALTER TABLE "STUDENT" DROP COLUMN university;

ALTER TABLE "STUDENT" ADD COLUMN university BIGINT;
ALTER TABLE "STUDENT" ADD CONSTRAINT "university_fk" FOREIGN KEY (university) REFERENCES "UNIVERSITY" (id);
ALTER TABLE "STUDENT" ADD COLUMN faculty BIGINT;
ALTER TABLE "STUDENT" ADD CONSTRAINT "faculty_fk" FOREIGN KEY (faculty) REFERENCES "FACULTY" (id);

CREATE OR REPLACE VIEW "STUDENT_VIEW" AS
  SELECT
    "USER".id,
    "USER".name,
    hire_date,
    "UNIVERSITY".name AS university,
    "FACULTY".name AS faculty,
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
