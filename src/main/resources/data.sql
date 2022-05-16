CREATE TABLE IF NOT EXISTS task (
   task_id INT NOT NULL,
   description VARCHAR(50) NOT NULL,
   status CHAR(1) NOT NULL,
   creation DATE,
   due DATE,
   done DATE
);

INSERT INTO task (task_id, description, status, creation, due, done)
VALUES (1, 'Task description 01', '1', '2022-05-12 22:34:55', '2022-05-11 01:27:34', '2022-05-12 21:31:53');

INSERT INTO task (task_id, description, status, creation, due, done)
VALUES (2, 'Task description 02', '1', '2022-05-12 22:34:55', '2022-05-11 01:27:34', '2022-05-12 21:31:53');

INSERT INTO task (task_id, description, status, creation, due, done)
VALUES (3, 'Task description 03', '1', '2022-05-12 22:34:55', '2022-05-11 01:27:34', '2022-05-12 21:31:53');

