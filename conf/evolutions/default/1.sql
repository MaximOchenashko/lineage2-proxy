# --- !Ups

SET SCHEMA 'lineage-proxy';

CREATE TABLE users (
  id UUID PRIMARY KEY NOT NULL,
  create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  name CHARACTER VARYING(255) NOT NULL,
  api_token CHARACTER VARYING(255) NOT NULL,
  telegram INT,
  email CHARACTER VARYING(255) NOT NULL,
  enabled BOOLEAN NOT NULL,
  password CHARACTER VARYING(255) NOT NULL,
  role INTEGER NOT NULL
);
CREATE UNIQUE INDEX uk_users_email ON users USING BTREE (email);

INSERT INTO "lineage-proxy"."users"("id", "create_date", "name",
        "api_token", "telegram", "email", "enabled", "password", "role")
        VALUES ('8eccaf86-36a7-47c6-a5a5-5c7a7650e1a4', now(), 'test', 'c2238fae-7ebc-4341-8faf-3dab62570171',
        null, 'test@test.com', true, 'test123', 0);
# --- !Downs

DROP TABLE users CASCADE;
DROP INDEX IF EXISTS uk_users_email;