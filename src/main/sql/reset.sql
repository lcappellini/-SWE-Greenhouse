-- Drop existing tables
DROP TABLE IF EXISTS "Posizionamento" CASCADE ;
DROP TABLE IF EXISTS "Posizione" CASCADE ;
DROP TABLE IF EXISTS "Spazio" CASCADE ;
DROP TABLE IF EXISTS "Cliente" CASCADE ;
DROP TABLE IF EXISTS "Ordine" CASCADE ;
DROP TABLE IF EXISTS "Pianta" CASCADE ;
DROP TABLE IF EXISTS "Operatore" CASCADE;
DROP TABLE IF EXISTS "Ambiente" CASCADE;


-- Recreate tables based on schema
CREATE TABLE IF NOT EXISTS "Pianta" (
    tipo VARCHAR(50) PRIMARY KEY,
    descrizione varchar(200)
);

CREATE TABLE IF NOT EXISTS "Ordine" (
    id SERIAL PRIMARY KEY,
    cliente VARCHAR(50),
    dataConsegna VARCHAR(50),
    tipoPianta VARCHAR(50),
    quantità INT,
    descrizione varchar(100),
    totale decimal(6,2),
    stato VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS "Cliente" (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) ,
    cognome VARCHAR(50),
    email VARCHAR(50),
    password varchar(50)
);

CREATE TABLE IF NOT EXISTS "Operatore"(
    id SERIAL PRIMARY KEY
);



CREATE TABLE IF NOT EXISTS "Ambiente" (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descrizione VARCHAR(200),
    nSpaziMax INT NOT NULL
);

CREATE TABLE IF NOT EXISTS "Spazio" (
    id SERIAL PRIMARY KEY,
    ambiente_id INT,
    nPosizioniMax INT NOT NULL,
    FOREIGN KEY (ambiente_id) REFERENCES "Ambiente"(id)
);

CREATE TABLE IF NOT EXISTS "Posizione" (
    id SERIAL PRIMARY KEY,
    assegnata BOOLEAN,
    spazio_id INT,
    FOREIGN KEY (spazio_id) REFERENCES  "Spazio"(id)
);
CREATE TABLE IF NOT EXISTS "Posizionamento" (
    id SERIAL PRIMARY KEY,
    pianta VARCHAR(50),
    posizione INT,
    ordine INT,
    operatore int,
    FOREIGN KEY (pianta) REFERENCES "Pianta"(tipo),
    FOREIGN KEY (posizione) REFERENCES  "Posizione"(id),                                            FOREIGN KEY (ordine) REFERENCES  "Ordine"(id)
);