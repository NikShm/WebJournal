DELETE FROM follow;
DELETE FROM "user";
DELETE FROM "role";

INSERT INTO "role"(id, role)
VALUES (1, 'ADMIN'),(2, 'USER');

INSERT INTO "user" (id, login, password, email, birth_date, bio, role_id, profile_picture_path)
VALUES
    (1, 'yulianabilak', 'qwerty', 'bilak.yuliana@chnu.edu.ua', '2003-01-02', '...', 2,''),
    (2, 'yulianabila', 'qwerty', 'bila.yuliana@chnu.edu.ua', '2003-01-02', '...', 2,''),
    (3, 'yulianabil', 'qwerty', 'bil.yuliana@chnu.edu.ua', '2003-01-02', '...', 2,''),
    (4, 'yulianabi', 'qwerty', 'bi.yuliana@chnu.edu.ua', '2003-01-02', '...', 2,''),
    (5, 'yulianab', 'qwerty', 'b.yuliana@chnu.edu.ua', '2003-01-02', '...', 2,''),
    (6, 'yuliana', 'qwerty', 'yuliana@chnu.edu.ua', '2003-01-02', '...', 2,''),
    (7, 'yulian', 'qwerty', 'yulian@chnu.edu.ua', '2003-01-02', '...', 2,'');

INSERT INTO follow (id, user_id, following_user_id)
VALUES
    (1, 1, 2), (2, 1, 3), (3, 1, 4), (4, 1, 5), (5, 1, 6), (6, 1, 7),
    (7, 3, 1), (8, 3, 2), (9, 3, 4), (10, 3, 5), (11, 3, 6),
    (12, 4, 1), (13, 4, 2), (14, 4, 3), (15, 4, 6),
    (16, 2, 7), (17, 2, 4), (18, 2, 3),
    (19, 5, 1), (20, 5, 3),
    (21, 6, 4);