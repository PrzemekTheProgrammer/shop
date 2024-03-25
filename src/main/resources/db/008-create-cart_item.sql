--liquibase formatted sql

--changeset liquibase:8

CREATE TABLE IF NOT EXISTS cart_item
(
    cart_id BIGINT REFERENCES cart(id),
    product_id BIGINT REFERENCES product(id),
    PRIMARY KEY ("cart_id", "product_id"),
    quantity INT,
    total_price NUMERIC,
    cart_index INT
);