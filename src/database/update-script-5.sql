DROP FUNCTION find_student_by_name(TEXT);

DROP VIEW "STUDENT_VIEW";

ALTER TABLE "STUDENT" DROP COLUMN graduation_date;

ALTER TABLE "STUDENT" ADD COLUMN "graduation_date" INT;

CREATE OR REPLACE VIEW "STUDENT_VIEW" AS
  SELECT
    "USER".id,
    "USER".name,
    hire_date,
    university,
    faculty,
    course,
    s_group,
    graduation_date,
    working_hours,
    billable,
    role_current_project,
    techs_current_project,
    english_level
  FROM "USER"
    INNER JOIN "STUDENT" ON "USER".id = "STUDENT".id;

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

ALTER TABLE "STUDENT" ADD COLUMN training_before_working BOOLEAN;
ALTER TABLE "STUDENT" ADD COLUMN course_when_start_woking INT;
ALTER TABLE "STUDENT" ADD COLUMN speciality TEXT;
ALTER TABLE "STUDENT" ADD COLUMN wishes_hours_number INT;
ALTER TABLE "STUDENT" ADD COLUMN trainings_exadel TEXT;