CREATE EXTENSION pg_trgm;

CREATE OR REPLACE FUNCTION find_student_by_name (desired_name text)
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
                 FROM "STUDENT_VIEW") LOOP
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
