CREATE TABLE users (
                      id SERIAL PRIMARY KEY,
                      username VARCHAR(255) NOT NULL CHECK (LENGTH(password) >= 3),
                      password VARCHAR(255) NOT NULL CHECK (LENGTH(password) >= 5)
);