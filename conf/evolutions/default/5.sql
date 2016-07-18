# --- !Ups

SET SCHEMA 'lineage-proxy';

CREATE TABLE item_templates (
  id                  UUID    NOT NULL,
  item_id             INTEGER NOT NULL,
  name                VARCHAR(255) NOT NULL,
  description         VARCHAR(500),
  item_type           INTEGER,
  slot_bit_type       INTEGER,
  armor_type          INTEGER,
  etcitem_type        INTEGER,
  recipe_id           INTEGER,
  weight              INTEGER,
  price               BIGINT,
  default_price       BIGINT,
  material_type       INTEGER,
  crystal_type        INTEGER,
  crystal_count       INTEGER,
  is_trade            BOOLEAN,
  is_drop             BOOLEAN,
  is_destruct         BOOLEAN,
  is_private_store    BOOLEAN,
  keep_type           INTEGER,
  physical_damage     INTEGER,
  weapon_type         INTEGER,
  magical_damage      INTEGER,
  magic_weapon        BOOLEAN,
  elemental_enable    BOOLEAN,
  is_olympiad_can_use BOOLEAN,
  can_move            BOOLEAN,
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX item_templates_item_id_unique ON item_templates (item_id);

ALTER TABLE items_on_sell ADD FOREIGN KEY (item_id) REFERENCES item_templates (item_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE items_on_buy ADD FOREIGN KEY (item_id) REFERENCES item_templates (item_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;


# --- !Downs

DROP TABLE item_templates CASCADE;