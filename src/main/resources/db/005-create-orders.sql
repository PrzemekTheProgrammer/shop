--liquibase formatted sql

--changeset liquibase:5

CREATE TABLE IF NOT EXISTS orders
(
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    total_price NUMERIC,
    created TIMESTAMP,
    updated TIMESTAMP
);