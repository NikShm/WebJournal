CREATE OR REPLACE FUNCTION like_add()
RETURNS TRIGGER
AS
$$
BEGIN
    update post set likes = (select count(*) from "like" where "like".post_id = post.id) where new.post_id = id;
    return new;
END
$$
LANGUAGE 'plpgsql';

DROP TRIGGER IF EXISTS like_update_trigger ON "like";

CREATE TRIGGER like_update_trigger
    AFTER INSERT
    ON "like"
    FOR EACH ROW
    EXECUTE PROCEDURE like_add();

CREATE OR REPLACE FUNCTION like_delete()
RETURNS TRIGGER
AS
$$
BEGIN
    update post set likes = (select count(*) from "like" where post_id = id) where old.post_id = id;
    return new;
END
$$
LANGUAGE 'plpgsql';

DROP TRIGGER IF EXISTS like_delete_trigger ON "like";

CREATE TRIGGER like_delete_trigger
    AFTER delete
    ON "like"
    FOR EACH ROW
    EXECUTE PROCEDURE like_delete();