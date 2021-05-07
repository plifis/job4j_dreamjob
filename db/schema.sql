CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT
);
CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created DATE,
    cityId  INTEGER REFERENCES city(id)
);
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT UNIQUE,
    password TEXT
);

CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name TEXT
);