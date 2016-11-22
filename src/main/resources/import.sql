-- test user
insert into authority (id, authority) values (nextval('authority_sequence'), 'ROLE_USER');
insert into users (id, email, password, enabled) values (nextval('user_sequence'), 'user@test.hu', '$2a$10$JgzUSFgxLodg.R4xmZXvB.sRmJ1LtHXuVPOndRec13K13hykLyqqW', true);
insert into user_auth (user_id, auth_id) values (currval('user_sequence'), currval('authority_sequence'));

-- admin user
insert into users (id, email, password, enabled) values (nextval('user_sequence'), 'admin@test.hu', '$2a$10$cEan6k0hSBWgarnhZiXRs.vtMCyu2TGheF7i6j5IErkORfHDIjp6G', true);
insert into user_auth (user_id, auth_id) values (currval('user_sequence'), currval('authority_sequence'));
insert into authority (id, authority) values (nextval('authority_sequence'), 'ROLE_ADMIN');
insert into user_auth (user_id, auth_id) values (currval('user_sequence'), currval('authority_sequence'));