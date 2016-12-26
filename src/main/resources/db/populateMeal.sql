DELETE FROM meals;
ALTER SEQUENCE global_seqm RESTART WITH 100000;

INSERT INTO meals (dateTime, user_id, description, calories)
VALUES ('2012-06-04 15:00:00',100000, 'ужин', 100), ('2015-06-04 10:00:00',100000, 'завтрак', 100);