--database name: webjournal
DROP TABLE IF EXISTS post_tag, tag, follow, "like", "comment", post, "user", "role";

CREATE TABLE "role"
(
    id SERIAL PRIMARY KEY,
    role VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE "user"
(
    id SERIAL PRIMARY KEY,
    login VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL,
    email VARCHAR(256) UNIQUE,
    birth_date DATE NOT NULL,
    bio VARCHAR(150),
    role_id INTEGER REFERENCES "role"(id) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE follow
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES "user"(id) NOT NULL,
    follower_user_id INTEGER REFERENCES "user"(id) NOT NULL
);

CREATE TABLE post
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES "user"(id) NOT NULL,
    title VARCHAR(128) NOT NULL,
    foreword VARCHAR(150) NOT NULL,
    content TEXT NOT NULL,
    likes INTEGER NOT NULL,
    is_approved BOOLEAN NOT NULL,
    published_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE "like"
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES "user"(id) NOT NULL,
    post_id INTEGER REFERENCES post(id) NOT NULL
);

CREATE TABLE "comment"
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES "user"(id) NOT NULL,
    post_id INTEGER REFERENCES post(id) NOT NULL,
    text VARCHAR(500) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE tag
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE post_tag
(
    id SERIAL PRIMARY KEY,
    post_id INTEGER REFERENCES post(id) NOT NULL,
    tag_id INTEGER REFERENCES tag(id) NOT NULL
);