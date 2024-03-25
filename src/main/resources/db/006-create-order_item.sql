--liquibase formatted sql

--changeset liquibase:6

CREATE TABLE IF NOT EXISTS order_item
(
    id SERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id),
    product_id BIGINT REFERENCES product(id),
    quantity INT,
    total_price NUMERIC,
    created TIMESTAMP,
    updated TIMESTAMP
);
