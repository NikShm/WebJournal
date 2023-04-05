DELETE FROM post;

INSERT INTO post (id, author_id, title, foreword, content, likes, is_approved, published_at, created_at, updated_at)
VALUES
    (1, 1, 'Sesame Apricot Tofu', 'Cooking', 'Cooking', 6, true, '2022-10-16 19:10:25', '2022-09-01 19:10:25', '2022-09-01 19:10:25');