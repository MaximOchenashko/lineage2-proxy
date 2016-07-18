# --- !Ups

SET SCHEMA 'lineage-proxy';

CREATE TABLE broker_notifications (
  id UUID PRIMARY KEY NOT NULL,
  create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  update_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  user_id UUID NOT NULL,
  --item info
  enabled BOOLEAN NOT NULL,
  item_id INT NOT NULL,
  price BIGINT,
  count BIGINT,
  enchant INT,
  attribute_type INT,
  attribute_value INT
);

ALTER TABLE broker_notifications ADD FOREIGN KEY (user_id) REFERENCES users (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

# --- !Downs

DROP TABLE broker_notifications CASCADE;