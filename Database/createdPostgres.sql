create database WebJournal;

DROP TABLE IF EXISTS  post, users,post_tag,tag, comments;

CREATE TYPE userRole AS ENUM ('USER', 'MODERATOR', 'ADMIN');

CREATE TABLE post
(
    id SERIAL PRIMARY KEY,
    photo_name,
    id_author NUMERIC NOT NULL,
    name VARCHAR(128) NOT NULL,
    description TEXT,
    createdAt timestamp default now(),
    update timestamp default now()
);

CREATE TABLE tag
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    photo_name_horizontal,
    photo_name_vertical,
    description TEXT,
    createdAt timestamp default now(),
    update timestamp default now()
);

CREATE TABLE post_tag
(
    postId INTEGER REFERENCES book(id) NOT NULL,
    tegId INTEGER REFERENCES author(id) NOT NULL
);

CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(128) NOT NULL,
    photo_name,
    role userRole NOT NULL,
    password VARCHAR(32) NOT NULL,
    createdAt timestamp default now()
);

CREATE TABLE comments
(
    id SERIAL PRIMARY KEY,
    id_user NUMERIC NOT NULL,
    id_post NUMERIC NOT NULL,
    description TEXT,
    createdAt timestamp default now(),
    update timestamp default now()
);


