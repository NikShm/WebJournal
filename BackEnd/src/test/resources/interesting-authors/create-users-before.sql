DELETE FROM follow;
DELETE FROM "user";
DELETE FROM "role";

INSERT INTO "role"(id, role)
VALUES (1, 'ADMIN'),(2, 'AUTHOR');

INSERT INTO "user" (id, login, password, email, birth_date, bio, role_id)
VALUES
    (1, 'yulianabilak', 'qwerty', 'bilak.yuliana@chnu.edu.ua', '2003-01-02', '...', 2),
    (2, 'yulianabila', 'qwerty', 'bila.yuliana@chnu.edu.ua', '2003-01-02', '...', 2),
    (3, 'yulianabil', 'qwerty', 'bil.yuliana@chnu.edu.ua', '2003-01-02', '...', 2),
    (4, 'yulianabi', 'qwerty', 'bi.yuliana@chnu.edu.ua', '2003-01-02', '...', 2),
    (5, 'yulianab', 'qwerty', 'b.yuliana@chnu.edu.ua', '2003-01-02', '...', 2),
    (6, 'yuliana', 'qwerty', 'yuliana@chnu.edu.ua', '2003-01-02', '...', 2),
    (7, 'yulian', 'qwerty', 'yulian@chnu.edu.ua', '2003-01-02', '...', 2);

INSERT INTO follow (user_id, following_user_id)
VALUES
    (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7),
    (3, 1), (3, 2), (3, 4), (3, 5), (3, 6),
    (4, 1), (4, 2), (4, 3), (4, 6),
    (2, 7), (2, 4), (2, 3),
    (5, 1), (5, 3),
    (6, 4);