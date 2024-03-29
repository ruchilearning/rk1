CREATE TABLE IF NOT EXISTS user_tbl
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email_id   VARCHAR(255),
    active     BOOLEAN DEFAULT FALSE
);

INSERT INTO user_tbl (first_name, last_name, email_id, active)
VALUES ('John', 'Doe', 'johndoe@example.com', true);

INSERT INTO user_tbl (first_name, last_name, email_id, active)
VALUES ('Jane', 'Doe', 'janedoe@example.com', true);


CREATE TABLE IF NOT EXISTS details_tbl
(
    uuid      VARCHAR(36) PRIMARY KEY,
    details   VARCHAR(255) NOT NULL,
    active    BOOLEAN DEFAULT FALSE
);

INSERT INTO details_tbl (uuid, details, active)
VALUES ('1', 'John Doe', true);

INSERT INTO details_tbl (uuid, details, active)
VALUES ('2', 'Jane Doe', true);