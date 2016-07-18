# --- !Ups

SET SCHEMA 'lineage-proxy';

CREATE TABLE npcs_spawns (
  id UUID PRIMARY KEY NOT NULL,
  create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  update_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  token_id UUID NOT NULL,
  --watcher info
  watcher_name VARCHAR(255) NOT NULL,
  watcher_x INT NOT NULL,
  watcher_y INT NOT NULL,
  watcher_z INT NOT NULL,
  --npc block
  npc_object_id INT NOT NULL,
  npc_id INT NOT NULL,
  npc_level INT NOT NULL,
  npc_name VARCHAR(255) NOT NULL,
  npc_is_dead BOOLEAN NOT NULL,
  npc_x INT NOT NULL,
  npc_y INT NOT NULL,
  npc_z INT NOT NULL
);
CREATE UNIQUE INDEX npcs_spawns_obj_id_token_unique_key ON npcs_spawns (npc_object_id, token_id);

CREATE TABLE dropped_items (
  id UUID PRIMARY KEY NOT NULL,
  create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  token_id UUID NOT NULL,
  -- item info
  dropper_id INT NOT NULL,
  item_object_id INT NOT NULL,
  item_id INT NOT NULL,
  item_x INT NOT NULL,
  item_y INT NOT NULL,
  item_z INT NOT NULL,
  stackable BOOLEAN NOT NULL,
  count BIGINT NOT NULL
);
CREATE UNIQUE INDEX dropped_items_unique_key ON dropped_items (dropper_id, token_id, item_object_id);

# --- !Downs

DROP TABLE npcs_spawns CASCADE;
DROP TABLE dropped_items CASCADE;

DROP INDEX IF EXISTS npcs_spawns_obj_id_token_unique_key;
DROP INDEX IF EXISTS dropped_items_unique_key;