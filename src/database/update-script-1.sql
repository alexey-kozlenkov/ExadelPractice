--update number columns - set default values (=0)

ALTER TABLE "SKILL_SET" ALTER COLUMN level SET DEFAULT 0;
ALTER TABLE "STUDENT" ALTER COLUMN course SET DEFAULT 0;
ALTER TABLE "STUDENT" ALTER COLUMN s_group SET DEFAULT 0;
ALTER TABLE "STUDENT" ALTER COLUMN working_hours SET DEFAULT 0;