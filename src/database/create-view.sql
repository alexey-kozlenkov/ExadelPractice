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