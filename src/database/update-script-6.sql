DROP FUNCTION find_student_by_name(TEXT);

DROP VIEW "STUDENT_VIEW";

ALTER TABLE "STUDENT" DROP COLUMN english_level;

ALTER TABLE "STUDENT" ADD COLUMN english_level INT;

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

ALTER TABLE public."STUDENT" RENAME COLUMN course_when_start_woking TO course_when_start_working;
ALTER TABLE public."USER" ALTER COLUMN password SET DEFAULT '11111';
