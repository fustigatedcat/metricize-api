USE metricize;

ALTER TABLE Customer CHANGE COLUMN customer_key customer_key VARCHAR(512) NOT NULL DEFAULT '';
ALTER TABLE Agent CHANGE COLUMN agent_key agent_key VARCHAR(512) NOT NULL DEFAULT '';

ALTER TABLE Customer ADD UNIQUE customer_key_idx (customer_key);
ALTER TABLE Agent ADD UNIQUE agent_key_idx (agent_key);