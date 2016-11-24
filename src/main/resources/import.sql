--- authorities
insert into authority (id, authority) values (nextval('authority_sequence'), 'ADD_NEW_USER');
insert into authority (id, authority) values (nextval('authority_sequence'), 'CREATE_NEW_PROJECT');

--- roles
insert into role(id, role_name) values (nextval('role_sequence'), 'DEVELOPER');
insert into role(id, role_name) values (nextval('role_sequence'), 'ADMIN');
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='DEVELOPER'), (select ID from AUTHORITY where AUTHORITY='CREATE_NEW_PROJECT'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='ADMIN'), (select ID from AUTHORITY where AUTHORITY='ADD_NEW_USER'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='ADMIN'), (select ID from AUTHORITY where AUTHORITY='CREATE_NEW_PROJECT'));

--- developer@asd.com asdasdasd
insert into users (id, email, password, enabled) values (nextval('user_sequence'), 'developer@asd.com', '$2a$10$2SKf9tcOgm4ZCMVancksI.42goBX7W3ckqDNh31wZ6hltQLH9LILu', true);
insert into user_role(user_id, role_id) values (currval('user_sequence'), (SELECT ID FROM ROLE WHERE ROLE_NAME='DEVELOPER'));

--- admin@asd.com asdasdasd
insert into users (id, email, password, enabled) values (nextval('user_sequence'), 'admin@asd.com', '$2a$10$2SKf9tcOgm4ZCMVancksI.42goBX7W3ckqDNh31wZ6hltQLH9LILu', true);
insert into user_role(user_id, role_id) values (currval('user_sequence'), (SELECT ID FROM ROLE WHERE ROLE_NAME='ADMIN'));

--- project + issue
insert into project(id, name) values (nextval('project_sequence'), 'Teszt projekt 1');
insert into issue(id, name, description, reproduction_steps, version, type, status, priority, severity, creation_time_stamp, assignee_id, project_id) values (nextval('issue_sequence'), 'Teszt issue 1', 'ASD', 'ASD', '1.0.0', 0, 0, 0, 0, current_timestamp, (SELECT id FROM users WHERE email='developer@asd.com'), currval('project_sequence'));
insert into issue(id, name, description, reproduction_steps, version, type, status, priority, severity, creation_time_stamp, assignee_id, project_id) values (nextval('issue_sequence'), 'Teszt issue 2', 'ASD2', 'ASD2', '1.0.0', 1, 3, 3, 3, current_timestamp, (SELECT id FROM users WHERE email='admin@asd.com'), currval('project_sequence'));
insert into users_projects(users_id, projects_id) values ((SELECT id FROM users WHERE email='developer@asd.com'), currval('project_sequence'));
insert into users_projects(users_id, projects_id) values ((SELECT id FROM users WHERE email='admin@asd.com'), currval('project_sequence'));

insert into project(id, name) values (nextval('project_sequence'), 'Teszt projekt 2');
insert into issue(id, name, description, reproduction_steps, version, type, status, priority, severity, creation_time_stamp, assignee_id, project_id) values (nextval('issue_sequence'), 'TP2I1', 'Teszt leírás', 'Repro', '1.0.0', 0, 0, 3, 3, current_timestamp, (SELECT id FROM users WHERE email='admin@asd.com'), currval('project_sequence'));
insert into users_projects(users_id, projects_id) values ((SELECT id FROM users WHERE email='admin@asd.com'), currval('project_sequence'));
