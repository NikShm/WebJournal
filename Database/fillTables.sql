INSERT INTO "role" (id, role)
VALUES (1, 'ADMIN'), (2, 'AUTHOR'), (3, 'MODERATOR');

INSERT INTO "user" (id, login, password, email, birth_date, bio, role_id)
VALUES
    (1, 'yulianabilak', 'qwerty', 'bilak.yuliana@chnu.edu.ua', '2003-02-01', '...', 2),
    (2, 'yulianabila', 'qwerty', 'bila.yuliana@chnu.edu.ua', '2003-02-01', '...', 2),
    (3, 'yulianabil', 'qwerty', 'bil.yuliana@chnu.edu.ua', '2003-02-01', '...', 2),
    (4, 'yulianabi', 'qwerty', 'bi.yuliana@chnu.edu.ua', '2003-02-01', '...', 2),
    (5, 'yulianab', 'qwerty', 'b.yuliana@chnu.edu.ua', '2003-02-01', '...', 2),
    (6, 'yuliana', 'qwerty', 'yuliana@chnu.edu.ua', '2003-02-01', '...', 2),
    (7, 'yulian', 'qwerty', 'yulian@chnu.edu.ua', '2003-02-01', '...', 2);

INSERT INTO follow (id, user_id, follower_user_id)
VALUES
    (1, 1, 2), (2, 1, 3), (3, 1, 4), (4, 1, 5), (5, 1, 6), (6, 1, 7),
    (7, 3, 1), (8, 3, 2), (9, 3, 4), (10, 3, 5), (11, 3, 6),
    (12, 4, 1), (13, 4, 2), (14, 4, 3), (15, 4, 6),
    (16, 2, 7), (17, 2, 4), (18, 2, 3),
    (19, 5, 1), (20, 5, 3),
    (21, 6, 4);

INSERT INTO post (id, user_id, title, foreword, content, likes, is_approved, published_at, created_at, updated_at)
VALUES
    (1, 1, 'A', 'Travel', 'Travel', 7, true, '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (2, 2, 'B', 'Cooking', 'Cooking', 6, true, '2022-09-02 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (3, 3, 'C', 'Programming', 'Programming', 5, true, '2022-09-03 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (4, 4, 'D', 'News', 'News', 4, true, '2022-09-04 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (5, 5, 'E', 'Sport', 'Sport', 3, true, '2022-09-05 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (6, 6, 'F', 'Music', 'Music', 2, true, '2022-09-06 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (7, 7, 'G', 'Gaming', 'Gaming', 1, true, '2022-09-07 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07');