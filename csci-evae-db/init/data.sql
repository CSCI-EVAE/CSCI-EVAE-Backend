INSERT INTO  users (id, created_at, created_by, email_address, email, first_name, last_name, password, updated_at, updated_by, username) VALUES (1, '2024-02-10 15:29:52.225000', null, 'mohamed@gmail.com', null, 'med', 'med', '$2a$10$3IffsR0.w9ZsNzcBZ4KDEuECPD2cIUbiXATfZzz3w6C.UNzhS0Kz6', '2024-02-10 15:29:52.225000', null, 'medenseignant');
INSERT INTO  users (id, created_at, created_by, email_address, email, first_name, last_name, password, updated_at, updated_by, username) VALUES (2, '2024-02-10 17:01:36.963000', null, 'mohamed@gmail.com', null, 'med', 'med', '$2a$10$4M3lxESq/xGGLhWiZWyMj.wqNK6OAokq3ax0PSl/9ByQ8EyXOsmNe', '2024-02-10 17:01:36.963000', null, 'medadmin');
INSERT INTO  users (id, created_at, created_by, email_address, email, first_name, last_name, password, updated_at, updated_by, username) VALUES (3, '2024-02-10 17:02:09.765000', null, 'mohamed@gmail.com', null, 'med', 'med', '$2a$10$ZWpvG71XfqhxNqAMjfQUo.pwdnSCmR.JAuCagAHEiVPxfSykJSFzG', '2024-02-10 17:02:09.765000', null, 'medetudiant');
INSERT INTO  roles (id, name) VALUES (1, 'ENSEIGNANT');
INSERT INTO  roles (id, name) VALUES (2, 'ADMIN');
INSERT INTO  roles (id, name) VALUES (3, 'ETUDIANT');
INSERT INTO  user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO  user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO  user_roles (user_id, role_id) VALUES (3, 3);