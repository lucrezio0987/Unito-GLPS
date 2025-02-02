-- PostgreSQL dump for the catering database
-- This script is adapted from a MySQL dump for use in PostgreSQL environments.

-- Create the database and switch to it

DROP TABLE IF EXISTS "events" CASCADE;
DROP TABLE IF EXISTS "menufeatures" CASCADE;
DROP TABLE IF EXISTS "menuitems" CASCADE;
DROP TABLE IF EXISTS "menusections" CASCADE;
DROP TABLE IF EXISTS "menus" CASCADE;
DROP TABLE IF EXISTS "recipes" CASCADE;
DROP TABLE IF EXISTS "roles" CASCADE;
DROP TABLE IF EXISTS "services" CASCADE;
DROP TABLE IF EXISTS "userroles" CASCADE;
DROP TABLE IF EXISTS "users" CASCADE;

DROP TABLE IF EXISTS "fogli" CASCADE;
DROP TABLE IF EXISTS "compiti" CASCADE;
DROP TABLE IF EXISTS "cuochi" CASCADE;
DROP TABLE IF EXISTS "turni" CASCADE;
DROP TABLE IF EXISTS "mansioni" CASCADE;
DROP TABLE IF EXISTS "preparazioni" CASCADE;

DROP TABLE IF EXISTS "compito_cuoco" CASCADE;
DROP TABLE IF EXISTS "turno_cuoco" CASCADE;


-- Tabella Events
CREATE TABLE Events (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(128),
                        date_start DATE,
                        date_end DATE,
                        expected_participants INT,
                        organizer_id INT NOT NULL
);

INSERT INTO Events VALUES
                       (1, 'Convegno Agile Community', '2020-09-25', '2020-09-25', 100, 2),
                       (2, 'Compleanno di Manuela', '2020-08-13', '2020-08-13', 25, 2),
                       (3, 'Fiera del Sedano Rapa', '2020-10-02', '2020-10-04', 400, 1);

-- Tabella MenuFeatures
CREATE TABLE MenuFeatures (
                              menu_id INT NOT NULL,
                              name VARCHAR(128) NOT NULL DEFAULT '',
                              value BOOLEAN DEFAULT FALSE
);

INSERT INTO MenuFeatures VALUES
                             (80, 'Richiede cuoco', FALSE), (80, 'Buffet', FALSE),
                             (80, 'Richiede cucina', FALSE), (80, 'Finger food', FALSE),
                             (80, 'Piatti caldi', FALSE), (82, 'Richiede cuoco', FALSE),
                             (82, 'Buffet', FALSE), (82, 'Richiede cucina', FALSE),
                             (82, 'Finger food', FALSE), (82, 'Piatti caldi', FALSE),
                             (86, 'Richiede cuoco', FALSE), (86, 'Buffet', FALSE),
                             (86, 'Richiede cucina', FALSE), (86, 'Finger food', FALSE),
                             (86, 'Piatti caldi', FALSE);

-- Tabella MenuItems
CREATE TABLE MenuItems (
                           id SERIAL PRIMARY KEY,
                           menu_id INT NOT NULL,
                           section_id INT,
                           description TEXT,
                           recipe_id INT NOT NULL,
                           position INT
);

INSERT INTO MenuItems VALUES
                          (96, 80, 0, 'Croissant vuoti', 9, 0),
                          (97, 80, 0, 'Croissant alla marmellata', 9, 1),
                          (98, 80, 0, 'Pane al cioccolato mignon', 10, 2),
                          (99, 80, 0, 'Panini al latte con prosciutto crudo', 12, 4),
                          (100, 80, 0, 'Panini al latte con prosciutto cotto', 12, 5);

-- Tabella MenuSections
CREATE TABLE MenuSections (
                              id SERIAL PRIMARY KEY,
                              menu_id INT NOT NULL,
                              name TEXT,
                              position INT
);

INSERT INTO MenuSections VALUES
                             (41, 86, 'Antipasti', 0),
                             (42, 86, 'Primi', 1),
                             (43, 86, 'Secondi', 2),
                             (44, 86, 'Dessert', 3);

-- Tabella Menus
CREATE TABLE Menus (
                       id SERIAL PRIMARY KEY,
                       name TEXT,
                       owner_id INT,
                       published BOOLEAN DEFAULT FALSE
);

INSERT INTO Menus VALUES
                      (80, 'Coffee break mattutino', 2, TRUE),
                      (82, 'Coffee break pomeridiano', 2, TRUE),
                      (86, 'Cena di compleanno pesce', 3, TRUE);

CREATE TABLE "mansioni" (
                            "id" SERIAL PRIMARY KEY,
                            "name" TEXT,
                            "description" TEXT,
                            "time" INTEGER,
                            "quantity" INTEGER
);

INSERT INTO "mansioni" ("name", "description", "time", "quantity")
VALUES
    ('Vitello tonnato', 'Piatto tradizionale italiano di vitello freddo a fette coperto con una salsa cremosa simile alla maionese aromatizzata al tonno.', 3, 4),
    ('Carpaccio di spada', 'Pesce spada crudo a fette sottili, condito con olio d oliva, limone e rucola.', 2, 3),
    ('Alici marinate', 'Alici marinate servite come antipasto.', 1, 3),
    ('Insalata di riso', 'Insalata di riso fredda con verdure e a volte frutti di mare.', 1, 2),
    ('Penne al sugo di baccalà', 'Pasta con sugo di baccalà.', 3, 4),
    ('Pappa al pomodoro', 'Zuppa toscana di pane e pomodoro.', 2, 3),
    ('Hamburger con bacon e cipolla caramellata', 'Hamburger con bacon e cipolla caramellata.', 3, 5),
    ('Salmone al forno', 'Salmone al forno.', 2, 4),
    ('Croissant', 'Pasta sfoglia francese burrosa e croccante.', 5, 4),
    ('Pane al cioccolato', 'Pane al cioccolato.', 3, 5),
    ('Girelle all uvetta', 'Rotoli all uvetta.', 3, 4),
    ('Panini al latte', 'Panini al latte.', 3, 3),
    ('Biscotti di pasta frolla', 'Biscotti di pasta frolla.', 2, 3),
    ('Lingue di gatto', 'Biscotti sottili e croccanti.', 1, 4),
    ('Bigné farciti', 'Bignè ripieni di crema.', 3, 4),
    ('Pizzette', 'Piccole pizze.', 3, 3),
    ('Tramezzini', 'Tramezzini italiani.', 2, 1),
    ('Sorbetto al limone', 'Sorbetto al limone.', 1, 3),
    ('Torta Saint Honoré', 'Classica torta francese intitolata al patrono dei panettieri.', 5, 5),
    ('Risotto alla zucca', 'Risotto alla zucca.', 4, 5),
    ('Crema pasticcera', 'Crema per farcire i bignè.', 3, 4),
    ('Ragù alla bolognese', 'Sugo di carne per le lasagne.', 4, 3),
    ('Salsa tonnata', 'Salsa cremosa al tonno per il vitello tonnato.', 2, 2),
    ('Salsa di acciughe', 'Salsa di acciughe per le alici marinate.', 1, 3),
    ('Maionese', 'Maionese per gli hamburger.', 2, 3),
    ('Salsa al limone', 'Salsa al limone per il salmone al forno.', 2, 4),
    ('Besciamella', 'Salsa bianca per lasagne e cannelloni.', 3, 3),
    ('Pesto alla genovese', 'Salsa di basilico, pinoli, aglio e formaggio.', 2, 3),
    ('Salsa al cioccolato', 'Salsa di cioccolato per decorare dolci e gelati.', 1, 3);
;

-- Tabella Recipes
CREATE TABLE Recipes (
                         id SERIAL PRIMARY KEY,
                         name TEXT,
                         "mansione_id" INTEGER REFERENCES "mansioni"("id")
);

INSERT INTO "recipes" ("id", "mansione_id") VALUES
                        (1, 1),
                        (2, 2),
                        (3, 3),
                        (4, 4),
                        (5, 5),
                        (6, 6),
                        (7, 7),
                        (8, 8),
                        (9, 20);

CREATE TABLE "preparazioni" (
        "id" SERIAL PRIMARY KEY,
        "mansione_id" INTEGER REFERENCES "mansioni"("id")
);

INSERT INTO "preparazioni" ("id", "mansione_id")
VALUES
    (1, 9),
    (2, 10),
    (3, 11),
    (4, 12),
    (5, 13),
    (6, 14),
    (7, 15),
    (8, 16),
    (9, 17),
    (10, 18),
    (11, 19);

-- Tabella Roles
CREATE TABLE Roles (
                       id CHAR(1) PRIMARY KEY,
                       role VARCHAR(128) NOT NULL DEFAULT 'servizio'
);

INSERT INTO Roles VALUES
                      ('c', 'cuoco'), ('h', 'chef'), ('o', 'organizzatore'), ('s', 'servizio');

-- Tabella Services
CREATE TABLE Services (
                          id SERIAL PRIMARY KEY,
                          event_id INT NOT NULL,
                          name VARCHAR(128),
                          proposed_menu_id INT NOT NULL DEFAULT 0,
                          approved_menu_id INT DEFAULT 0,
                          service_date DATE,
                          time_start TIME,
                          time_end TIME,
                          expected_participants INT
);

INSERT INTO "services" VALUES
                           (1, 2, 'Cena', 86, 0, '2020-08-13', '20:00:00', '23:30:00', 25),
                           (2, 1, 'Coffee break mattino', 0, 80, '2020-09-25', '10:30:00', '11:30:00', 100),
                           (3, 1, 'Colazione di lavoro', 0, 0, '2020-09-25', '13:00:00', '14:00:00', 80),
                           (4, 1, 'Coffee break pomeriggio', 0, 82, '2020-09-25', '16:00:00', '16:30:00', 100),
                           (5, 1, 'Cena sociale', 0, 0, '2020-09-25', '20:00:00', '22:30:00', 40),
                           (6, 3, 'Pranzo giorno 1', 0, 0, '2020-10-02', '12:00:00', '15:00:00', 200),
                           (7, 3, 'Pranzo giorno 2', 0, 0, '2020-10-03', '12:00:00', '15:00:00', 300),
                           (8, 3, 'Pranzo giorno 3', 0, 0, '2020-10-04', '12:00:00', '15:00:00', 400);

-- Tabella UserRoles
CREATE TABLE UserRoles (
                           user_id INT NOT NULL,
                           role_id CHAR(1) NOT NULL DEFAULT 's'
);

-- Dumping data for table 'userroles'
INSERT INTO "userroles" VALUES
                            (1, 'o'),
                            (2, 'o'),
                            (2, 'h'),
                            (3, 'h'),
                            (4, 'h'),
                            (4, 'c'),
                            (5, 'c'),
                            (6, 'c'),
                            (7, 'c'),
                            (8, 's'),
                            (9, 's'),
                            (10, 's'),
                            (7, 's');
-- Tabella Users
CREATE TABLE Users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(128) NOT NULL DEFAULT ''
);

INSERT INTO "users" VALUES
                        (1, 'Carlin'),
                        (2, 'Lidia'),
                        (3, 'Tony'),
                        (4, 'Marinella'),
                        (5, 'Guido'),
                        (6, 'Antonietta'),
                        (7, 'Paola'),
                        (8, 'Silvia'),
                        (9, 'Marco'),
                        (10, 'Piergiorgio');


CREATE TABLE  "fogli" (
                          id SERIAL PRIMARY KEY,
                          service INTEGER,
                          owner_id INTEGER,
                          CONSTRAINT fk_service_id FOREIGN KEY (service) REFERENCES services(id),
                          CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES users(id)
);
CREATE TABLE  "turni" (
                          "id" SERIAL PRIMARY KEY,
                          "date" DATE,
                          "time" TIME,
                          "duration" FLOAT
);
INSERT INTO "turni" VALUES
                        (1, '2021-07-14', '08:00:00', 2.0),
                        (2, '2021-07-14', '14:00:00', 2.0),
                        (3, '2021-07-14', '20:00:00', 2.0),
                        (4, '2021-07-14', '02:00:00', 2.0),
                        (5, '2021-07-14', '00:00:00', 2.0);

CREATE TABLE "compiti" (
                           "id" SERIAL PRIMARY KEY,
                           "time" INTEGER,
                           "portions" INTEGER,
                           "prepare" BOOLEAN DEFAULT TRUE,
                           "completed" BOOLEAN DEFAULT TRUE,
                           "mansione_id" INTEGER,
                           "turno_id" INTEGER,
                           "foglio_id" INTEGER,
                           CONSTRAINT fk_duty_id FOREIGN KEY (mansione_id) REFERENCES mansioni(id),
                           CONSTRAINT fk_sheet_id FOREIGN KEY (turno_id) REFERENCES turni(id),
                           CONSTRAINT fk_shift_id FOREIGN KEY (foglio_id) REFERENCES fogli(id)
);
CREATE TABLE "cuochi" (
                          "id" SERIAL PRIMARY KEY,
                          "name" VARCHAR(128)
);
INSERT INTO "cuochi" VALUES
                        (1, 'Mario Verde'),
                        (2, 'Luigi Bianchi'),
                        (3, 'Guido Verdi'),
                        (4, 'Anna Neri'),
                        (5, 'Paola Gialli');


CREATE TABLE "compito_cuoco" (
                                 "compito_id" SERIAL NOT NULL,
                                 "cuoco_id"  SERIAL NOT NULL,
                                 PRIMARY KEY ("compito_id", "cuoco_id"),
                                 CONSTRAINT fk_job FOREIGN KEY ("compito_id") REFERENCES "compiti" ("id"),
                                 CONSTRAINT fk_cook FOREIGN KEY ("cuoco_id") REFERENCES "cuochi" ("id")
);

CREATE TABLE "turno_cuoco" (
                               "turno_id" SERIAL NOT NULL,
                               "cuoco_id"  SERIAL NOT NULL,
                               PRIMARY KEY ("turno_id", "cuoco_id"),
                               CONSTRAINT fk_shift FOREIGN KEY ("turno_id") REFERENCES "turni" ("id"),
                               CONSTRAINT fk_cook FOREIGN KEY ("cuoco_id") REFERENCES "cuochi" ("id")
);

INSERT INTO "turno_cuoco" VALUES
                             (1, 1),
                             (2, 2),
                             (3, 3),
                             (4, 4),
                             (5, 5);