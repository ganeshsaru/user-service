--liquibase formatted sql

--changeset ganesh:1
CREATE TABLE user_account(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(254) NOT NULL,
    address VARCHAR(255) NOT NULL
);

--rollback DROP TABLE user_account