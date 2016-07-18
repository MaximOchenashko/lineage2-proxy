# --- !Ups

SET SCHEMA 'lineage-proxy';

CREATE TABLE servers (
  id UUID PRIMARY KEY NOT NULL,
  create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  name CHARACTER VARYING(255) NOT NULL,
  identifier CHARACTER VARYING(255) NOT NULL
);

CREATE TABLE players (
  id UUID PRIMARY KEY NOT NULL,
  server_id UUID NOT NULL,
  object_id INT NOT NULL,
  create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_seen TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  name CHARACTER VARYING(255) NOT NULL,
  title CHARACTER VARYING(255),
  race INT NOT NULL,
  sex INT NOT NULL,
  base_class INT NOT NULL,
  clan_id INT,
  is_noble BOOLEAN NOT NULL,
  is_hero BOOLEAN NOT NULL,
  paper_dolls CHARACTER VARYING(500) NOT NULL,
  x INT NOT NULL,
  y INT NOT NULL,
  z INT NOT NULL
);
CREATE UNIQUE INDEX players_sever_id_name_unique_key ON "lineage-proxy".players (name, server_id);

CREATE TABLE subclasses (
  id UUID PRIMARY KEY NOT NULL,
  create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_seen TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  player_id UUID NOT NULL,
  class_id INT NOT NULL
);

CREATE TABLE items_on_sell (
  id UUID PRIMARY KEY NOT NULL,
  create_date TIMESTAMP  WITHOUT TIME ZONE NOT NULL,
  seller_id UUID NOT NULL,
  is_package BOOLEAN NOT NULL,
  item_id INT NOT NULL,
  item_object_id INT NOT NULL,
  name VARCHAR(255),
  count BIGINT NOT NULL,
  enchant INT NOT NULL,
  attackElementId INT NOT NULL, 
  attackElementValue INT NOT NULL,
  defenseFire INT NOT NULL, 
  defenseWater INT NOT NULL, 
  defenceWind INT NOT NULL,
  defenceEarth INT NOT NULL, 
  defenceHoly INT NOT NULL,
  defenceUnholy INT NOT NULL,
  owners_price BIGINT NOT NULL,
  store_price BIGINT NOT NULL,
  actual BOOLEAN NOT NULL
);

CREATE TABLE items_on_buy (
  id UUID PRIMARY KEY NOT NULL,
  create_date TIMESTAMP  WITHOUT TIME ZONE NOT NULL,
  customer_id UUID NOT NULL,
  item_id INT NOT NULL,
  item_object_id INT NOT NULL,
  name VARCHAR(255),
  count BIGINT NOT NULL,
  customer_price BIGINT NOT NULL,
  store_price BIGINT NOT NULL,
  actual BOOLEAN NOT NULL
);

ALTER TABLE items_on_sell ADD FOREIGN KEY (seller_id) REFERENCES players (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE items_on_sell ADD FOREIGN KEY (item_id) REFERENCES item_templates (item_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE items_on_buy ADD FOREIGN KEY (customer_id) REFERENCES players (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE items_on_buy ADD FOREIGN KEY (item_id) REFERENCES item_templates (item_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE subclasses ADD FOREIGN KEY (player_id) REFERENCES players (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE players ADD FOREIGN KEY (server_id) REFERENCES servers (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

INSERT INTO servers(id, create_date, name, identifier) VALUES ('ed5f4e9e-993e-40bb-a49b-91fd6f7b204d', now(), 'RPG Club x7', 'RPG Club x7');
# --- !Downs

DROP TABLE players CASCADE;
DROP TABLE items_on_sell CASCADE ;
DROP TABLE items_on_buy CASCADE ;
DROP TABLE subclasses CASCADE ;

DROP INDEX IF EXISTS players_sever_id_object_id_unique_key;