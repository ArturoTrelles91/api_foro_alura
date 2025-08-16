-- db/migration/V2__topics_unique_title_message.sql
ALTER TABLE topics
  ADD COLUMN title_message_hash BINARY(32)
    GENERATED ALWAYS AS (UNHEX(SHA2(CONCAT(title, '\0', message), 256))) STORED,
  ADD UNIQUE INDEX ux_topics_title_message_hash (title_message_hash);