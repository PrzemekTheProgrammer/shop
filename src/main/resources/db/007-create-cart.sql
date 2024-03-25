--liquibase formatted sql

--changeset liquibase:7

CREATE TABLE IF NOT EXISTS cart
(
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    total_price NUMERIC,
    created TIMESTAMP,
    updated TIMESTAMP
);