DELETE FROM post;

INSERT INTO post (id, author_id, title, foreword, content, likes, is_approved, published_at, created_at, updated_at)
VALUES
    (1, 1, 'Sesame Apricot Tofu', 'Cooking', 'Cooking', 6, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (2, 1, 'Awesome Broccoli Cheese Soup', 'Cooking', 'Cooking', 3, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (3, 1, 'Making food at home', 'Cooking', 'Cooking', 3, true, '2022-08-02 19:10:25', '2022-08-01 19:10:25', '2022-08-01 19:10:25'),
    (4, 1, 'Easy Peach Cobbler', 'Cooking', 'Cooking', 2, true, '2022-08-30 19:10:25', '2022-08-21 19:10:25', '2022-08-21 19:10:25'),
    (5, 3, 'Java Stream API', 'Programming', 'Programming', 5, true, '2022-10-13 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (6, 3, 'Custom Exceptions', 'Programming', 'Programming', 4, true, '2022-10-14 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (7, 5, 'Spring Boot vs ASP.NET Core', 'Programming', 'Programming', 3, true, '2022-10-15 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (8, 2, 'Guide for Pairing Fonts', 'UI', 'UI', 7, true, '2022-10-15 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (9, 6, 'Logo Design', 'UI', 'UI', 2, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (10, 7, 'Pro tips on alignment', 'UI', 'UI', 1, true, '2022-10-17 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (11, 4, 'The HSB Color System', 'UI', 'UI', 2, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25'),
    (12, 6, 'The Reframe Technique', 'UI', 'UI', 2, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25');