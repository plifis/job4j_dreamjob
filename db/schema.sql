CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT
);
CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created DATE
);
CREATE TABLE user (
    id SERIAL PRIMARY KEY;
    name TEXT;
    email TEXT;
    password TEXT
);