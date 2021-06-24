CREATE SCHEMA IF NOT EXISTS student_record;

SET search_path TO student_record;

CREATE TABLE IF NOT EXISTS status(
    id                  NUMERIC(19, 0) PRIMARY KEY,
    name                VARCHAR(32) NOT NULL UNIQUE
);
CREATE SEQUENCE IF NOT EXISTS status_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS student_record.user (
    id                  NUMERIC(19, 0) PRIMARY KEY,
    login               VARCHAR(32) NOT NULL UNIQUE,
    first_name          VARCHAR(64) NOT NULL,
    last_name           VARCHAR(64) NOT NULL,
    image               BYTEA,
    status_id           NUMERIC(19, 0) NOT NULL REFERENCES status (id)
);
CREATE SEQUENCE IF NOT EXISTS user_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS technology_group (
    id                  NUMERIC(19, 0) PRIMARY KEY,
    name                VARCHAR(32) NOT NULL UNIQUE
);
CREATE SEQUENCE IF NOT EXISTS technology_group_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS stage (
    id                  NUMERIC(19, 0) PRIMARY KEY,
    name                VARCHAR(64) NOT NULL,
    description         VARCHAR(1024),
    year                INTEGER NOT NULL,
    technology_group_id NUMERIC(19, 0) NOT NULL REFERENCES technology_group (id)
);
CREATE SEQUENCE IF NOT EXISTS stage_seq INCREMENT BY 10 MINVALUE 1 MAXVALUE 999999999999999999 CACHE 10 NO CYCLE;

CREATE TABLE IF NOT EXISTS user_stage (
    user_id             NUMERIC(19, 0) NOT NULL REFERENCES student_record.user (id),
    stage_id            NUMERIC(19, 0) NOT NULL REFERENCES stage (id),
    mark                FLOAT,
    reason              VARCHAR(512)
);