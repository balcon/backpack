CREATE SEQUENCE IF NOT EXISTS main_seq
    INCREMENT BY 1
    START WITH 1000;

CREATE TABLE IF NOT EXISTS users
(
    id    SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    name  VARCHAR(255)        NOT NULL,
    role  VARCHAR(16)         NOT NULL
);

CREATE TABLE IF NOT EXISTS backpack
(
    id       SERIAL PRIMARY KEY,
    owner_id INTEGER REFERENCES users (id) ON DELETE CASCADE,
    name     VARCHAR(255) NOT NULL
);

-- CREATE TABLE IF NOT EXISTS type
-- (
--     id             SERIAL PRIMARY KEY,
--     parent_type_id INTEGER REFERENCES type (id),
--     name           VARCHAR(255)
-- );
--
CREATE TABLE IF NOT EXISTS equipment
(
    id           SERIAL PRIMARY KEY,
    owner_id     INTEGER REFERENCES users (id) ON DELETE CASCADE,
--     type_id      INTEGER REFERENCES type (id),
    name         VARCHAR(255) NOT NULL,
    manufacturer VARCHAR(255),
    weight       INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS backpack_equipment
(
    backpack_id  INTEGER REFERENCES backpack (id) ON DELETE CASCADE,
    equipment_id INTEGER REFERENCES equipment (id) ON DELETE CASCADE
);

-- CREATE TABLE IF NOT EXISTS property
-- (
--     equipment_id   INTEGER REFERENCES equipment (id),
--     name           VARCHAR(255) NOT NULL UNIQUE,
--     property_value VARCHAR(255),
--     PRIMARY KEY (equipment_id, name)
-- );
