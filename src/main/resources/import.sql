--- authorities
insert into authority (id, authority) values (nextval('authority_sequence'), 'CREATE_USER');
insert into authority (id, authority) values (nextval('authority_sequence'), 'MODIFY_USER');
insert into authority (id, authority) values (nextval('authority_sequence'), 'CREATE_PROJECT');
insert into authority (id, authority) values (nextval('authority_sequence'), 'CREATE_ISSUE');
insert into authority (id, authority) values (nextval('authority_sequence'), 'MODIFY_ISSUE');
insert into authority (id, authority) values (nextval('authority_sequence'), 'COMMENT_ISSUE');
insert into authority (id, authority) values (nextval('authority_sequence'), 'DELETE_ISSUE');
insert into authority (id, authority) values (nextval('authority_sequence'), 'LOGIN');

--- roles
insert into role(id, role_name) values (nextval('role_sequence'), 'viewer');
insert into role(id, role_name) values (nextval('role_sequence'), 'reporter');
insert into role(id, role_name) values (nextval('role_sequence'), 'developer');
insert into role(id, role_name) values (nextval('role_sequence'), 'admin');

--- viewer role authorities: empty
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='viewer'), (select ID from AUTHORITY where AUTHORITY='LOGIN'));

--- developer role authorities
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='reporter'), (select ID from AUTHORITY where AUTHORITY='CREATE_ISSUE'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='reporter'), (select ID from AUTHORITY where AUTHORITY='MODIFY_ISSUE'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='reporter'), (select ID from AUTHORITY where AUTHORITY='COMMENT_ISSUE'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='reporter'), (select ID from AUTHORITY where AUTHORITY='LOGIN'));

--- developer role authorities
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='developer'), (select ID from AUTHORITY where AUTHORITY='CREATE_ISSUE'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='developer'), (select ID from AUTHORITY where AUTHORITY='MODIFY_ISSUE'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='developer'), (select ID from AUTHORITY where AUTHORITY='COMMENT_ISSUE'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='developer'), (select ID from AUTHORITY where AUTHORITY='LOGIN'));

--- admin role authorities
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='admin'), (select ID from AUTHORITY where AUTHORITY='CREATE_USER'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='admin'), (select ID from AUTHORITY where AUTHORITY='MODIFY_USER'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='admin'), (select ID from AUTHORITY where AUTHORITY='CREATE_PROJECT'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='admin'), (select ID from AUTHORITY where AUTHORITY='CREATE_ISSUE'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='admin'), (select ID from AUTHORITY where AUTHORITY='MODIFY_ISSUE'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='admin'), (select ID from AUTHORITY where AUTHORITY='COMMENT_ISSUE'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='admin'), (select ID from AUTHORITY where AUTHORITY='DELETE_ISSUE'));
insert into role_auth(role_id, auth_id) values ((select ID FROM ROLE WHERE ROLE_NAME='admin'), (select ID from AUTHORITY where AUTHORITY='LOGIN'));

--- viewer: viewer@teszt.hu, password
insert into users (id, email, password, enabled, role_id) values (nextval('user_sequence'), 'viewer@teszt.hu', '$2a$10$JDzMlUVX6cKFP5R1rBs55eveavaxCu0z6w/gHjkINSeUvOsIgERES', true, (SELECT ID FROM ROLE WHERE ROLE_NAME='viewer'));

--- reporter: reporter@teszt.hu, password
insert into users (id, email, password, enabled, role_id) values (nextval('user_sequence'), 'reporter@teszt.hu', '$2a$10$JDzMlUVX6cKFP5R1rBs55eveavaxCu0z6w/gHjkINSeUvOsIgERES', true, (SELECT ID FROM ROLE WHERE ROLE_NAME='reporter'));

--- developer@teszt.hu, password
insert into users (id, email, password, enabled, role_id) values (nextval('user_sequence'), 'developer@teszt.hu', '$2a$10$JDzMlUVX6cKFP5R1rBs55eveavaxCu0z6w/gHjkINSeUvOsIgERES', true, (SELECT ID FROM ROLE WHERE ROLE_NAME='developer'));

--- admin@teszt.hu, password
insert into users (id, email, password, enabled, role_id) values (nextval('user_sequence'), 'admin@teszt.hu', '$2a$10$JDzMlUVX6cKFP5R1rBs55eveavaxCu0z6w/gHjkINSeUvOsIgERES', true, (SELECT ID FROM ROLE WHERE ROLE_NAME='admin'));

--- project + issue
insert into project(id, name) values (nextval('project_sequence'), 'Teszt projekt 1');
insert into issue(id, name, description, reproduction_steps, version, type, status, priority, severity, creation_time_stamp, assignee_id, project_id) values (nextval('issue_sequence'), 'Teszt issue 1', 'ASD', 'ASD', '1.0.0', 0, 0, 0, 0, current_timestamp, (SELECT id FROM users WHERE email='developer@teszt.hu'), currval('project_sequence'));
insert into issue(id, name, description, reproduction_steps, version, type, status, priority, severity, creation_time_stamp, assignee_id, project_id) values (nextval('issue_sequence'), 'Teszt issue 2', 'ASD2', 'ASD2', '1.0.0', 1, 3, 3, 3, current_timestamp, (SELECT id FROM users WHERE email='reporter@teszt.hu'), currval('project_sequence'));
insert into users_projects(users_id, projects_id) values ((SELECT id FROM users WHERE email='developer@teszt.hu'), currval('project_sequence'));
insert into users_projects(users_id, projects_id) values ((SELECT id FROM users WHERE email='admin@teszt.hu'), currval('project_sequence'));

insert into project(id, name) values (nextval('project_sequence'), 'Teszt projekt 2');
insert into issue(id, name, description, reproduction_steps, version, type, status, priority, severity, creation_time_stamp, assignee_id, project_id) values (nextval('issue_sequence'), 'TP2I1', 'Teszt leírás', 'Repro', '1.0.0', 0, 0, 3, 3, current_timestamp, (SELECT id FROM users WHERE email='admin@teszt.hu'), currval('project_sequence'));
insert into users_projects(users_id, projects_id) values ((SELECT id FROM users WHERE email='admin@teszt.hu'), currval('project_sequence'));
insert into users_projects(users_id, projects_id) values ((SELECT id FROM users WHERE email='viewer@teszt.hu'), currval('project_sequence'));