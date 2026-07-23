

CREATE TABLE Marque (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50)
);

CREATE TABLE Modele (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50),
    idMarque INT,
    referencee VARCHAR(50),
    FOREIGN KEY (idMarque) REFERENCES Marque(id)
);

CREATE TABLE Ordinateur (
    id SERIAL PRIMARY KEY,
    idModele INT,
    ram INT,
    processuer VARCHAR(50),
    disqueDur INT,
    FOREIGN KEY (idModele) REFERENCES Modele(id)
);
CREATE TABLE Utilisateur (
    id SERIAL PRIMARY KEY,
    login VARCHAR(50),
    pwd VARCHAR(50),
    role VARCHAR(20)
);

CREATE TABLE Problem_ordinateur (
    id SERIAL PRIMARY KEY,
    nom_problem VARCHAR(255)
);

CREATE TABLE Etat (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50)
);

CREATE TABLE Ordinateur_etat (
    id SERIAL PRIMARY KEY,
    idEtat INT,
    idOrdinateur INT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observation VARCHAR(255),
    id_problem_ordinateur INT,
    FOREIGN KEY (id_problem_ordinateur) REFERENCES Problem_ordinateur(id),
    FOREIGN KEY (idEtat) REFERENCES Etat(id),
    FOREIGN KEY (idOrdinateur) REFERENCES Ordinateur(id)
);
