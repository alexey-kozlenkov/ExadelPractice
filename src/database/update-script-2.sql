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

ALTER TABLE "USER" ADD COLUMN "skype" TEXT;
ALTER TABLE "USER" ADD COLUMN "telephone" TEXT;
ALTER TABLE "STUDENT" ADD COLUMN "term_marks" TEXT;