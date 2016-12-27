DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

ALTER SEQUENCE global_seq RESTART WITH 100000;
INSERT INTO meals (dateTime, user_id, description, calories)
VALUES ('2012-06-04 15:00:00',100000, 'ужин', 100), ('2015-06-04 10:00:00',100000, 'завтрак', 100);