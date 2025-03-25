```sql

CREATE TABLE Osoba (
    pesel VARCHAR(100) PRIMARY KEY,
    imie VARCHAR(100) NOT NULL,
    nazwisko VARCHAR(100) NOT NULL
);

CREATE TABLE Mieszkanie (
    id_mieszkanie SERIAL PRIMARY KEY, 
    adres VARCHAR(100) NOT NULL,
    metraz FLOAT NOT NULL,
    pesel_wlasciciela VARCHAR(100) NOT NULL REFERENCES Osoba(pesel)
);

CREATE TABLE GdzieMieszka (
    pesel VARCHAR(100) NOT NULL REFERENCES Osoba(pesel), 
    id_mieszkanie INT NOT NULL REFERENCES Mieszkanie(id_mieszkanie),
    CONSTRAINT gdzie_mieszka_id PRIMARY KEY (pesel, id_mieszkanie) 
);


```