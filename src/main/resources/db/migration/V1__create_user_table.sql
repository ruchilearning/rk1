CREATE TABLE IF NOT EXISTS user_tbl (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email_id VARCHAR(255)
    );

INSERT INTO user_tbl (first_name, last_name, email_id)
VALUES ('John', 'Doe', 'johndoe@example.com');

INSERT INTO user_tbl (first_name, last_name, email_id)
VALUES ('Jane', 'Doe', 'janedoe@example.com');