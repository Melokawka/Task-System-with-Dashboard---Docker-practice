INSERT INTO persons (name, email) VALUES ('Alice Johnson', 'alice@example.com');
INSERT INTO persons (name, email) VALUES ('Bob Smith', 'bob@example.com');
INSERT INTO persons (name, email) VALUES ('Charlie Lee', 'charlie@example.com');

INSERT INTO tasks (project_id, title, description, status, due_date)
VALUES (1, 'Design database schema', 'Create tables for tasks and assignments', 'OPEN', '2025-09-30');

INSERT INTO tasks (project_id, title, description, status, due_date)
VALUES (1, 'Implement REST API', 'Build CRUD endpoints for Task entity', 'IN_PROGRESS', '2025-10-05');

INSERT INTO task_assignments (task_id, person_id, assigned_date)
VALUES (1, 1, CURRENT_DATE);

INSERT INTO task_assignments (task_id, person_id, assigned_date)
VALUES (2, 2, CURRENT_DATE);
