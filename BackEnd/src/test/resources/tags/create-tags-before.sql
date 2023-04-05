DELETE FROM post_tag;
DELETE FROM tag;

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