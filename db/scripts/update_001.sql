CREATE TABLE account(
    id       SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    email    VARCHAR NOT NULL UNIQUE,
    phone    VARCHAR NOT NULL UNIQUE
);

CREATE TABLE ticket(
    id         SERIAL PRIMARY KEY,
    session_id INT NOT NULL CHECK (session_id > 0 and session_id < 4),
    row        INT NOT NULL CHECK (row > 0 and row < 4),
    cell       INT NOT NULL CHECK (cell > 0 and cell < 4),
    account_id INT NOT NULL REFERENCES account (id),
    CONSTRAINT ticket_all_key UNIQUE(session_id, row, cell)
);