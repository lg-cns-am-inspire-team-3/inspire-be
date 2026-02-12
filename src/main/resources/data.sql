INSERT INTO users (id, email, name, contact, address, role, status, salary)
VALUES(1, 'admin@gmail.com', 'admin', '010-1234-5678', null, 'ADMIN', 'ACTIVE', null);
INSERT INTO users (id, email, name, contact, address, role, status, salary)
VALUES(2, 'user1@gmail.com', 'username1', '010-1234-5679', 'Seoul', 'USER', 'ACTIVE', 10000);
INSERT INTO users (id, email, name, contact, address, role, status, salary)
VALUES(3, 'user2@gmail.com', 'username2', '010-1234-5680', 'Busan', 'USER', 'ACTIVE', 15000);
INSERT INTO users (id, email, name, contact, address, role, status, salary)
VALUES(4, 'user3@gmail.com', 'username3', '010-1234-5681', 'Daejeon', 'USER', 'ACTIVE', 10000);
INSERT INTO users (id, email, name, contact, address, role, status, salary)
VALUES(5, 'user4@gmail.com', 'username4', '010-1234-5682', 'Seoul', 'USER', 'ACTIVE', 12000);
INSERT INTO users (id, email, name, contact, address, role, status, salary)
VALUES(6, 'user5@gmail.com', 'username5', '010-1234-5683', 'Daegu', 'USER', 'ACTIVE', 20000);
INSERT INTO users (id, email, name, contact, address, role, status, salary)
VALUES(7, 'user6@gmail.com', 'username6', '010-1234-5684', 'Busan', 'USER', 'ACTIVE', 18000);
INSERT INTO users (id, name, contact, address, status, salary, role, email, created_at, updated_at)
VALUES (8, 'user7', '010-1111-0001', '서울시 강남구', 'SUSPENDED', NULL, 'USER', 'test1@example.com', NOW(), NOW());
INSERT INTO users (id, name, contact, address, status, salary, role, email, created_at, updated_at)
VALUES (9,  'user8', '010-1111-0002', '서울시 서초구', 'SUSPENDED', NULL, 'USER', 'test2@example.com', NOW(), NOW());
INSERT INTO users (id, name, contact, address, status, salary, role, email, created_at, updated_at)
VALUES (10, 'user9', '010-1111-0003', '경기도 성남시', 'SUSPENDED', NULL, 'USER', 'test3@example.com', NOW(), NOW());
INSERT INTO users (id, name, contact, address, status, salary, role, email, created_at, updated_at)
VALUES (11, 'user10', '010-1111-0004', '인천시 부평구', 'SUSPENDED', NULL, 'USER', 'test4@example.com', NOW(), NOW());
INSERT INTO users (id, name, contact, address, status, salary, role, email, created_at, updated_at)
VALUES (12, 'user11', '010-1111-0005', '서울시 송파구', 'SUSPENDED', NULL, 'USER', 'test5@example.com', NOW(), NOW());
INSERT INTO users (id, name, contact, address, status, salary, role, email, created_at, updated_at)
VALUES (13, 'user12', '010-1111-0006', '경기도 수원시', 'SUSPENDED', NULL, 'USER', 'test6@example.com', NOW(), NOW());

-- admin, admin
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (1, 'admin', '$2a$10$BFiDSx3nvDj5hO7PzDyMu.jZNhX9EW7OY2uiEOSQS2wsIWL.Nxenm');
-- user1, password1
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (2, 'user1', '$2a$10$c7ehw3EdeX23BSskF3P.KeuKpIy1St3du56kBqfA6CAOAHMmYWR6m');
-- user2, password2
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (3, 'user2', '$2a$10$squ5TdD6Or61ZdRXRv7Wse0Uimx/u.JXaVske76uT4uO9NUZ0CW0O');
-- user3, password3
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (4, 'user3', '$2a$10$rZ46PqxO7shAp7IBvefNKO/8KYjVulWk.7c3E3lzLoiTzB5.6mGSO');
-- user4, password4
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (5, 'user4', '$2a$10$JqNX7mxaubqcjjd5WJ6fBuvBV4aqnzUb4Tv3ii7o5q65gp/Z1D1bm');
-- user5, password5
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (6, 'user5', '$2a$10$u8j126kZ9uXvnTBQaBti3u79.jBetkfEUJ7ZWMATuJ4OZZTPZzvV2');
-- user6, password6
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (7, 'user6', '$2a$10$Hs/uH8vOJzEqB7.83YyXM.B6AZYN8pjgP2hWWRB2IF4S0kWJLyKhm');
-- user7, password7
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (8, 'user7', '$2a$10$8fgdBdAmR.fHwjF6S2O57udH6smabzQhL/l9dQy8ydJ8XQQsRIkw2');
-- user8, password8
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (9, 'user8', '$2a$10$wMWoAMHJjH5mzQlHgGtwbOg/Qets6iGGCw0Rw98UgZJd.i78gvdpa');
-- user9, password9
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (10, 'user9', '$2a$10$26WO6rzcjRUaXOB.ir9plO9O5tchBiYSyanN/043HhR4reXR8NXwW');
-- user10, password10
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (11, 'user10', '$2a$10$6AjHrVnnzjOevllSfoBsbePHC1xAMZ.P6WZ9y64eXozqANzBBmTVG');
-- user11, password11
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (12, 'user11', '$2a$10$VIHWowSBOBqYOo9Z/rRiouchC3zvoQAts5fEDUOpXZIRNMdsKiuYO');
-- user12, password12
INSERT INTO user_credentials (user_id, login_id, password_hash)
VALUES (13, 'user12', '$2a$10$P/Xh9Muzr60JaZmV.EtzDOvRlyBqCR8BQ7Usy1h0RMwumdVwac2ha');

INSERT INTO attendances (user_id, work_date, check_in, check_out, work_minute)
VALUES(2, '2020-12-23', '2020-12-23 07:30:00', '2020-12-23 18:30:00', 660);
INSERT INTO attendances (user_id, work_date, check_in, check_out, work_minute)
VALUES(3, '2020-12-24', '2020-12-24 07:30:00', '2020-12-24 18:30:00', 660);
INSERT INTO attendances (user_id, work_date, check_in, check_out, work_minute)
VALUES(4, '2020-12-25', '2020-12-25 07:30:00', '2020-12-25 18:30:00', 660);
INSERT INTO attendances (user_id, work_date, check_in, check_out, work_minute)
VALUES(5, '2020-12-26', '2020-12-26 07:30:00', '2020-12-26 18:30:00', 660);
INSERT INTO attendances (user_id, work_date, check_in, check_out, work_minute)
VALUES(6, '2020-12-27', '2020-12-27 07:30:00', '2020-12-27 18:30:00', 660);
INSERT INTO attendances (user_id, work_date, check_in, check_out, work_minute)
VALUES(7, '2020-12-28', '2020-12-28 07:30:00', '2020-12-28 18:30:00', 660);