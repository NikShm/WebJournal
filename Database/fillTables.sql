INSERT INTO languages(id, language) VALUES (1, 'english');

INSERT INTO "role" (id, role)
VALUES (1, 'ADMIN'), (2, 'AUTHOR'), (3, 'MODERATOR');

INSERT INTO "user" (id, login, password, email, birth_date, bio, role_id, profile_picture_path)
VALUES
    (1, 'yulianabilak', 'qwerty', 'bilak.yuliana@chnu.edu.ua', '2003-02-01', '...', 2,''),
    (2, 'yulianabila', 'qwerty', 'bila.yuliana@chnu.edu.ua', '2003-02-01', '...', 2,''),
    (3, 'yulianabil', 'qwerty', 'bil.yuliana@chnu.edu.ua', '2003-02-01', '...', 2,''),
    (4, 'yulianabi', 'qwerty', 'bi.yuliana@chnu.edu.ua', '2003-02-01', '...', 2,''),
    (5, 'yulianab', 'qwerty', 'b.yuliana@chnu.edu.ua', '2003-02-01', '...', 2,''),
    (6, 'yuliana', 'qwerty', 'yuliana@chnu.edu.ua', '2003-02-01', '...', 2,''),
    (7, 'yulian', 'qwerty', 'yulian@chnu.edu.ua', '2003-02-01', '...', 2,'');

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

INSERT INTO tag(name)
VALUES ('teg');

INSERT INTO post_tag(post_id, tag_id)
VALUES (2, 1);