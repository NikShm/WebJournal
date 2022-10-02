INSERT INTO role(id, role)
VALUES(1, 'ADMIN'),(2, 'USER');

INSERT INTO languages(id, language) VALUES (1, 'english');

INSERT INTO post(author_id, title, foreword, content, published_at, likes, is_approved)
values(1, 'Bad', 'dsf', 'A 1.5 mile wide swath of winds gusting to around 95 mph created **tornado-like** damage along Kentucky Highway 259 in Edmons
on County. The winds, extending 3/4 of a mile north and south of Bee Spring, destroyed or heavily damaged several small outbuildings, tore
part of the roof off of one home, uprooted and snapped the trunks of numerous trees, and snapped around a dozen power poles. Several othe
r homes sustained roof damage, and wind-driven hail shredded vinyl siding on a number of buildings.', '2022-09-20T18:21:40.532623', 23, true);
INSERT INTO post(author_id, title, foreword, content, published_at, likes, is_approved)
values(1, 'Bad', 'dsf', 'Good', '2022-09-20T18:21:40.532623', 23, true);

SELECT *
FROM post
WHERE  post.ts_content @@ phraseto_tsquery('english','roof damaging')
ORDER BY author_id
limit 10 offset 0;
SELECT cfgname FROM pg_ts_config;
show default_text_search_config;
set default_text_search_config = 'pg_catalog.[ukrainian]';

SELECT *
FROM post
JOIN "like" on post.id = post_id
WHERE  post.ts_content @@ phraseto_tsquery('english','roof damaging')
;

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

INSERT INTO follow (id, user_id, following_user_id)
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

insert into  "like"(user_id, post_id)
VALUES
       (1,2),
       (1,1),
       (2,2);
insert into  "like"(user_id, post_id)
VALUES
    (4,2);

delete from "like" where post_id = 2 and user_id = 2;

insert into tag(name)
VALUES ('teg');
insert into post_tag(post_id, tag_id)
VALUES (2,1);