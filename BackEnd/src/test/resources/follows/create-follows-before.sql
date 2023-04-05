DELETE FROM follow;

INSERT INTO follow (user_id, following_user_id)
VALUES
    (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7),
    (3, 1), (3, 2), (3, 4), (3, 5), (3, 6),
    (4, 1), (4, 2), (4, 3), (4, 6),
    (2, 7), (2, 4), (2, 3),
    (5, 1), (5, 3),
    (6, 4);