ALTER TABLE post
DROP CONSTRAINT post_author_id_fkey,
ADD CONSTRAINT post_author_id_fkey
FOREIGN KEY (author_id) REFERENCES "user"(id) ON DELETE CASCADE;

ALTER TABLE "comment"
DROP CONSTRAINT comment_user_id_fkey,
ADD CONSTRAINT comment_user_id_fkey
FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE;

ALTER TABLE follow
DROP CONSTRAINT follow_following_user_id_fkey,
ADD CONSTRAINT follow_following_user_id_fkey
FOREIGN KEY (following_user_id) REFERENCES "user"(id) ON DELETE CASCADE;

ALTER TABLE follow
DROP CONSTRAINT follow_user_id_fkey,
ADD CONSTRAINT follow_user_id_fkey
FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE;

ALTER TABLE "like"
DROP CONSTRAINT like_user_id_fkey,
ADD CONSTRAINT like_user_id_fkey
FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE;

ALTER TABLE "like"
DROP CONSTRAINT like_post_id_fkey,
ADD CONSTRAINT like_post_id_fkey
FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE;

ALTER TABLE mail_token
DROP CONSTRAINT mail_token_user_id_fkey,
ADD CONSTRAINT mail_token_user_id_fkey
FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE;

ALTER TABLE refresh_token
DROP CONSTRAINT refresh_token_user_id_fkey,
ADD CONSTRAINT refresh_token_user_id_fkey
FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE;

ALTER TABLE post_tag
DROP CONSTRAINT post_tag_post_id_fkey,
ADD CONSTRAINT post_tag_post_id_fkey
FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE;