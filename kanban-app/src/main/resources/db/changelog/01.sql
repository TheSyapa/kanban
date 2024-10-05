-- Create table for Dev
CREATE TABLE dev (
                     id SERIAL PRIMARY KEY,
                     name VARCHAR(255) NOT NULL,
                     email VARCHAR(255) NOT NULL,
                     password VARCHAR(255) NOT NULL
);

-- Create table for Role
CREATE TABLE role (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL
);

-- Create table for Dev-Role many-to-many relationship
CREATE TABLE dev_role (
                          dev_id INTEGER NOT NULL,
                          role_id INTEGER NOT NULL,
                          PRIMARY KEY (dev_id, role_id),
                          FOREIGN KEY (dev_id) REFERENCES dev(id),
                          FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Create table for Card
CREATE TABLE card (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      type VARCHAR(255) NOT NULL,
                      author_id INTEGER NOT NULL,
                      worker_id INTEGER,
                      tester_id INTEGER,
                      time_created TIMESTAMP NOT NULL,
                      time_taken TIMESTAMP,
                      time_deadline TIMESTAMP,
                      time_fact_end_time TIMESTAMP,
                      status VARCHAR(255) NOT NULL,
                      direction VARCHAR(255) NOT NULL,
                      color VARCHAR(255) NOT NULL,
                      description VARCHAR(255) NOT NULL,
                      FOREIGN KEY (author_id) REFERENCES dev(id),
                      FOREIGN KEY (worker_id) REFERENCES dev(id),
                      FOREIGN KEY (tester_id) REFERENCES dev(id)
);

-- Create table for Comment
CREATE TABLE comment (
                         id SERIAL  PRIMARY KEY,
                         card_id INTEGER NOT NULL,
                         author_id INTEGER NOT NULL,
                         message VARCHAR(255) NOT NULL,
                         time_created TIMESTAMP NOT NULL,
                         FOREIGN KEY (card_id) REFERENCES card(id),
                         FOREIGN KEY (author_id) REFERENCES dev(id)
);

-- Create table for Project
CREATE TABLE project (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL
);

-- Create table for Project-Dev many-to-many relationship
CREATE TABLE project_dev (
                             project_id INTEGER NOT NULL,
                             dev_id INTEGER NOT NULL,
                             PRIMARY KEY (project_id, dev_id),
                             FOREIGN KEY (project_id) REFERENCES project(id),
                             FOREIGN KEY (dev_id) REFERENCES dev(id)
);

-- Create table for Project-Card one-to-many relationship
CREATE TABLE project_card (
                              project_id INTEGER NOT NULL,
                              card_id INTEGER NOT NULL,
                              PRIMARY KEY (project_id, card_id),
                              FOREIGN KEY (project_id) REFERENCES project(id),
                              FOREIGN KEY (card_id) REFERENCES card(id)
);
