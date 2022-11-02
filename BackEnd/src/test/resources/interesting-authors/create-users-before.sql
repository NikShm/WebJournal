DELETE FROM follow;
DELETE FROM "user";
DELETE FROM "role";

INSERT INTO "role"(id, role)
VALUES (1, 'ADMIN'),(2, 'AUTHOR');

INSERT INTO "user" (id, login, password, email, account_verified, bio, role_id)
VALUES
    (1, 'yulianabilak', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'bilak.yuliana@chnu.edu.ua', true, '...', 2),
    (2, 'yulianabila', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'bila.yuliana@chnu.edu.ua', true, '...', 2),
    (3, 'yulianabil', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'bil.yuliana@chnu.edu.ua', true, '...', 2),
    (4, 'yulianabi', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'bi.yuliana@chnu.edu.ua', true, '...', 2),
    (5, 'yulianab', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'b.yuliana@chnu.edu.ua', true, '...', 2),
    (6, 'yuliana', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'yuliana@chnu.edu.ua', true, '...', 2),
    (7, 'yulian', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'yulian@chnu.edu.ua', true, '...', 2);

INSERT INTO follow (user_id, following_user_id)
VALUES
    (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7),
    (3, 1), (3, 2), (3, 4), (3, 5), (3, 6),
    (4, 1), (4, 2), (4, 3), (4, 6),
    (2, 7), (2, 4), (2, 3),
    (5, 1), (5, 3),
    (6, 4);