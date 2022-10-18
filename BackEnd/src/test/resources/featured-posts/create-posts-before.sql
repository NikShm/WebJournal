DELETE FROM post;
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

INSERT INTO post (id, author_id, title, foreword, content, likes, is_approved, published_at, created_at, updated_at)
VALUES
    (1, 1, 'Sesame Apricot Tofu', 'Cooking', 'Cooking', 6, true, '2022-10-16 19:10:25', '2022-10-01 19:10:25', '2022-10-01 19:10:25'),
    (2, 1, 'Awesome Broccoli Cheese Soup', 'Cooking', 'Cooking', 3, true, '2022-10-16 19:10:25', '2022-10-01 19:10:25', '2022-10-01 19:10:25'),
    (3, 3, 'Java Stream API', 'Programming', 'Programming', 5, true, '2022-09-02 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (4, 3, 'Custom Exceptions', 'Programming', 'Programming', 4, true, '2022-10-14 19:10:25', '2022-10-01 19:10:25', '2022-10-01 19:10:25'),
    (5, 6, 'Logo Design', 'UI', 'UI', 5, true, '2022-10-13 19:10:25', '2022-10-01 19:10:25', '2022-10-01 19:10:25'),
    (6, 7, 'Pro tips on alignment', 'UI', 'UI', 1, true, '2022-10-17 19:10:25', '2022-10-01 19:10:25', '2022-10-01 19:10:25'),
    (7, 4, 'The HSB Color System', 'UI', 'UI', 2, true, '2022-10-16 19:10:25', '2022-10-01 19:10:25', '2022-10-01 19:10:25'),
    (8, 6, 'The Reframe Technique', 'UI', 'UI', 2, true, '2022-10-16 19:10:25', '2022-10-01 19:10:25', '2022-10-01 19:10:25');