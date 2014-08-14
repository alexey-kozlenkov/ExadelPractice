CREATE OR REPLACE VIEW "SKILL_VIEW" AS
  SELECT
    "SKILL_TYPE".id as id,
    "SKILL_TYPE".name,
    "SKILL_SET".user_id,
    level
  FROM "SKILL_SET"
    INNER JOIN "SKILL_TYPE" ON "SKILL_SET".skill_type_id = "SKILL_TYPE".id;