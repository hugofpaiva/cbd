CREATE TABLE users(
   username text,
   name text,
   email text PRIMARY KEY,
   registration_date timestamp,
   );

CREATE TABLE videos(
   id uuid PRIMARY KEY,
   name text,
   uploader_username text,
   description text,
   tags set<text>,
   followers_usernames set<text>,
   upload_date timestamp,
   ) WITH CLUSTERING ORDER BY (uploader_username ASC, upload_date DESC);

CREATE TABLE comments_video(
   id uuid PRIMARY KEY,
   video_name text,
   reviewer_username text,
   comment text,
   creation_date timestamp,
   ) WITH CLUSTERING ORDER BY (video_name ASC, creation_date DESC);

CREATE TABLE comments_reviewer(
   id uuid PRIMARY KEY,
   video_name text,
   reviewer_username text,
   comment text,
   creation_date timestamp,
   ) WITH CLUSTERING ORDER BY (reviewer_username ASC, creation_date DESC);

CREATE TABLE events(
   id uuid PRIMARY KEY,
   video_id uuid,
   user_username text ,
   creation_date timestamp,
   type int,
   time int,
   );

CREATE TABLE rating(
   id uuid,
   video_id uuid,
   rating int,
   PRIMARY KEY (id, video_id)
   );

CREATE INDEX video_uploader ON videos (uploader_username);
CREATE INDEX video_date ON videos (upload_date);

CREATE INDEX comment_reviewer ON comments_reviewer (reviewer_username);
CREATE INDEX comment_date_reviewer ON comments_reviewer (creation_date;

CREATE INDEX comment_video ON comments_video (video_name);
CREATE INDEX comment_date_video ON comments_video (creation_date)