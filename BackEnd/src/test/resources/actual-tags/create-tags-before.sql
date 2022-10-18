DELETE FROM post_tag;
DELETE FROM tag;
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
    (1, 1, 'Sesame Apricot Tofu', 'Cooking', 'Cooking', 6, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (2, 1, 'Awesome Broccoli Cheese Soup', 'Cooking', 'Cooking', 3, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (3, 1, 'Making food at home', 'Cooking', 'Cooking', 4, true, '2022-08-02 19:10:25', '2022-08-01 19:10:25', '2022-08-01 19:10:25'),
    (4, 1, 'Easy Peach Cobbler', 'Cooking', 'Cooking', 2, true, '2022-08-30 19:10:25', '2022-08-21 19:10:25', '2022-08-21 19:10:25'),
    (5, 3, 'Java Stream API', 'Programming', 'Programming', 5, true, '2022-10-13 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (6, 3, 'Custom Exceptions', 'Programming', 'Programming', 4, true, '2022-10-14 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (7, 5, 'Spring Boot vs ASP.NET Core', 'Programming', 'Programming', 3, true, '2022-10-15 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (8, 2, 'Guide for Pairing Fonts', 'UI', 'UI', 7, true, '2022-10-15 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (9, 6, 'Logo Design', 'UI', 'UI', 2, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (10, 7, 'Pro tips on alignment', 'UI', 'UI', 1, true, '2022-10-17 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (11, 4, 'The HSB Color System', 'UI', 'UI', 2, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (12, 6, 'The Reframe Technique', 'UI', 'UI', 2, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25');

INSERT INTO tag (id, name, created_at)
VALUES
    (1, 'ui', '2022-10-15 19:10:25'),
    (2, 'design', '2022-10-16 19:10:25'),
    (3, 'fonts', '2022-10-15 19:10:25'),
    (4, 'java', '2022-10-13 19:10:25'),
    (5, 'spring boot', '2022-10-15 19:10:25'),
    (6, 'cooking', '2022-10-16 19:10:25');

INSERT INTO post_tag (post_id, tag_id)
VALUES
    (8, 1), (9, 1), (10, 1), (11, 1), (12, 1),
    (5, 4), (6, 4), (7, 4),
    (1, 6), (2, 6),
    (3, 6), (4, 6),
    (9, 2),
    (8, 3),
    (7, 5);