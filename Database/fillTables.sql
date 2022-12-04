INSERT INTO languages(id, language) VALUES (1, 'english');

INSERT INTO "role" (id, role)
VALUES (1, 'ADMIN'), (2, 'AUTHOR'), (3, 'MODERATOR');

INSERT INTO "user" (login, password, email, account_verified, bio, role_id)
VALUES
    ( 'yulianabilak', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'bilak.yuliana@chnu.edu.ua', true, 'If you can change something, change it. If you can’t, don’t waste time thinking about it', 2),
    ( 'yulianabila', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'bila.yuliana@chnu.edu.ua', true, 'Your kindness warms my heart more than the brightest flame ever could', 2),
    ( 'yulianabil', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'bil.yuliana@chnu.edu.ua', true, 'You should know that all power comes at a price. For every bit of power you gain', 2),
    ( 'yulianabi', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'bi.yuliana@chnu.edu.ua', true, 'I need your actions', 2),
    ( 'yulianab', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'b.yuliana@chnu.edu.ua', true, 'Reality is the stillness buried deep beneath the illusion', 2),
    ( 'yuliana', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'yuliana@chnu.edu.ua', true, 'You should know that all power comes at a price.', 2),
    ( 'yulian', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'yulian@chnu.edu.ua', true, 'Yesterday’s experiences make me stronger today', 2),
    ( 'yulia', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'yulia@chnu.edu.ua', true, 'Osmanthus wine tastes the same as I remember... ', 2),
    ( 'yuli', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'yuli.yuliana@chnu.edu.ua', true, 'Inactivity serves no purpose whatsoever', 2),
    ( 'yu', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'yu.yuliana@chnu.edu.ua', true, 'But where are those who share the memory?', 2),
    ( 'y', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'y.yuliana@chnu.edu.ua', true, 'For every bit of power you gain, so too do you gain more responsibility?', 2),
    ( 'yul', '$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu', 'yul.yuliana@chnu.edu.ua', true, 'Every journey has its final day. Don’t rush!', 2),
    ( 'yuliana-admin', '$2a$10$KrX9CF.o2qiBc5aDYJVn4.PHDQm5NQhno0nnEtM0jeHBIsmRqEMPS', 'bilakyuliana6996@gmail.com', true, 'Yesterday’s experiences make me stronger today', 1);

INSERT INTO follow (user_id, following_user_id)
VALUES
    (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7),
    (3, 1), (3, 2), (3, 4), (3, 5), (3, 6),
    (4, 1), (4, 2), (4, 3), (4, 6),
    (2, 7), (2, 4), (2, 3),
    (5, 1), (5, 3),
    (6, 4);

INSERT INTO post (id, author_id, title, foreword, content, likes, is_approved, published_at, created_at, updated_at)
VALUES
    (1, 1, 'A', 'Travel', 'A 1.5 mile wide swath of winds gusting to around 95 mph created **tornado-like** damage along Kentucky Highway 259 in Edmons
on County. The winds, extending 3/4 of a mile north and south of Bee Spring, destroyed or heavily damaged several small outbuildings, tore
part of the roof off of one home, uprooted and snapped the trunks of numerous trees, and snapped around a dozen power poles. Several othe
r homes sustained roof damage, and wind-driven hail shredded vinyl siding on a number of buildings.', 0, true, '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (2, 2, 'B', 'Cooking', 'Cooking', 0, true, '2022-09-02 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (3, 3, 'C', 'Programming', 'Programming', 0, true, '2022-09-03 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (4, 4, 'D', 'News', 'News', 0, true, '2022-09-04 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (5, 5, 'E', 'Sport', 'Sport', 0, true, '2022-09-05 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (6, 6, 'F', 'Music', 'A 1.5 mile wide swath of winds gusting to around 95 mph created **tornado-like** damage along Kentucky Highway 259 in Edmons
on County. The winds, extending 3/4 of a mile north and south of Bee Spring, destroyed or heavily damaged several small outbuildings, tore
part of the roof off of one home, uprooted and snapped the trunks of numerous trees, and snapped around a dozen power poles. Several othe
r homes sustained roof damage, and wind-driven hail shredded vinyl siding on a number of buildings.', 0, true, '2022-09-06 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07'),
    (7, 7, 'G', 'Gaming', 'Gaming', 0, true, '2022-09-07 19:10:25-07', '2022-09-01 19:10:25-07', '2022-09-01 19:10:25-07');

INSERT INTO "like"(user_id, post_id)
VALUES
    (1, 2),
    (1, 3),
    (3, 2);

INSERT INTO tag(id, name)
VALUES (1, 'Cooking'),
       (2, 'Television'),
       (3, 'Modernism'),
       (4, 'Programming'),
       (5, 'Genshin Impact'),
       (6, 'Summer outfit'),
       (7, 'Fashion week'),
       (8, 'Elizabet II'),
       (9, 'Fanfics'),
       (10, 'JavaScript'),
       (11, 'Back to school'),
       (12, 'Depression'),
       (13, 'Coronavirus'),
       (14, 'Iphone 16'),
       (15, 'Conspiracy theory'),
       (16, 'Vaccine'),
       (17, 'Terrorism'),
       (18, 'K-pop'),
       (19, 'Grunge'),
       (20, 'Mozart');

INSERT INTO post_tag(post_id, tag_id)
VALUES
       (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 7),
       (3, 8),
       (3, 9),
       (4, 10),
       (4, 11),
       (4, 12),
       (5, 13),
       (5, 14),
       (5, 15),
       (6, 16),
       (6, 17),
       (6, 18),
       (7, 19),
       (7, 20);