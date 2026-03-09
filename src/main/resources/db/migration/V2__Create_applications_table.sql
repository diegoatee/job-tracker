CREATE TABLE applications (
                              id           BIGSERIAL    PRIMARY KEY,
                              company_id   BIGINT       NOT NULL REFERENCES companies(id),
                              job_title    VARCHAR(255) NOT NULL,
                              job_url      VARCHAR(255),
                              status       VARCHAR(50)  NOT NULL DEFAULT 'WISHLIST',
                              applied_date DATE,
                              notes        TEXT,
                              created_at   TIMESTAMP    NOT NULL DEFAULT now()
);