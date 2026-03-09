CREATE TABLE companies (
                           id          BIGSERIAL     PRIMARY KEY,
                           name        VARCHAR(255)  NOT NULL,
                           industry    VARCHAR(100),
                           website     VARCHAR(255),
                           notes       TEXT,
                           created_at  TIMESTAMP     NOT NULL DEFAULT now()
);