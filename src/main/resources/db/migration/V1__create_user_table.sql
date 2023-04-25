CREATE TABLE IF NOT EXISTS "user" (
    id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email_id VARCHAR(255) NOT NULL
    );

INSERT INTO "user" (id, first_name, last_name, email_id)
VALUES ('1', 'John', 'Doe', 'johndoe@example.com');

INSERT INTO "user" (id, first_name, last_name, email_id)
VALUES ('2', 'Jane', 'Doe', 'janedoe@example.com');