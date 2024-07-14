-- PostgreSQL dump for the catering database
-- This script is adapted from a MySQL dump for use in PostgreSQL environments.

-- Create the database and switch to it
CREATE DATABASE catering WITH ENCODING 'UTF8';
\c catering;

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
DROP TABLE IF EXISTS "sheets" CASCADE;
DROP TABLE IF EXISTS "jobs" CASCADE;
DROP TABLE IF EXISTS "cooks" CASCADE;
DROP TABLE IF EXISTS "shifts" CASCADE;
DROP TABLE IF EXISTS "shift_cook" CASCADE;
DROP TABLE IF EXISTS "preparations" CASCADE;
DROP TABLE IF EXISTS "recipes" CASCADE;
DROP TABLE IF EXISTS "duties" CASCADE;

-- Table structure for table 'boards'
DROP TABLE IF EXISTS "boards";
CREATE TABLE "boards" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(128)
);

INSERT INTO "boards" VALUES
    (1, 'Convegno Agile Community'),
    (2, 'Compleanno di Manuela'),
    (3, 'Fiera del Sedano Rapa');

-- Table structure for table 'events'
DROP TABLE IF EXISTS "events";
CREATE TABLE "events" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(128),
    "date_start" DATE,
    "date_end" DATE,
    "expected_participants" INTEGER,
    "organizer_id" INTEGER NOT NULL,
    "board" INTEGER,
     CONSTRAINT fk_board_id FOREIGN KEY ("board") REFERENCES "boards"("id")
);

-- Dumping data for table 'events'
INSERT INTO "events" VALUES
    (1, 'Convegno Agile Community', '2020-09-25', '2020-09-25', 100, 2, 1),
    (2, 'Compleanno di Manuela', '2020-08-13', '2020-08-13', 25, 2, 2),
    (3, 'Fiera del Sedano Rapa', '2020-10-02', '2020-10-04', 400, 1, 3);

-- Table structure for table 'menufeatures'
DROP TABLE IF EXISTS "menufeatures";
CREATE TABLE "menufeatures" (
    "menu_id" INTEGER NOT NULL,
    "name" VARCHAR(128) NOT NULL,
    "value" BOOLEAN DEFAULT FALSE
);

-- Dumping data for table 'menufeatures'
INSERT INTO "menufeatures" VALUES
    (80, 'Richiede cuoco', FALSE), (80, 'Buffet', FALSE),
    (80, 'Richiede cucina', FALSE), (80, 'Finger food', FALSE),
    (80, 'Piatti caldi', FALSE), (82, 'Richiede cuoco', FALSE),
    (82, 'Buffet', FALSE), (82, 'Richiede cucina', FALSE),
    (82, 'Finger food', FALSE), (82, 'Piatti caldi', FALSE),
    (86, 'Richiede cuoco', FALSE), (86, 'Buffet', FALSE),
    (86, 'Richiede cucina', FALSE), (86, 'Finger food', FALSE),
    (86, 'Piatti caldi', FALSE);

-- Table structure for table 'menuitems'
DROP TABLE IF EXISTS "menuitems";
CREATE TABLE "menuitems" (
    "id" SERIAL PRIMARY KEY,
    "menu_id" INTEGER NOT NULL,
    "section_id" INTEGER,
    "description" TEXT,
    "recipe_id" INTEGER NOT NULL,
    "position" INTEGER
);

-- Dumping data for table 'menuitems'
INSERT INTO "menuitems" VALUES
    (96, 80, 0, 'Croissant vuoti', 9, 0),
    (97, 80, 0, 'Croissant alla marmellata', 9, 1),
    (98, 80, 0, 'Pane al cioccolato mignon', 10, 2),
    (99, 80, 0, 'Panini al latte con prosciutto crudo', 12, 4),
    (100, 80, 0, 'Panini al latte con prosciutto cotto', 12, 5),
    (101, 80, 0, 'Panini al latte con formaggio spalmabile alle erbe', 12, 6),
    (102, 80, 0, 'Girelle all''uvetta mignon', 11, 3),
    (103, 82, 0, 'Biscotti', 13, 1),
    (104, 82, 0, 'Lingue di gatto', 14, 2),
    (105, 82, 0, 'Bigné alla crema', 15, 3),
    (106, 82, 0, 'Bigné al caffè', 15, 4),
    (107, 82, 0, 'Pizzette', 16, 5),
    (108, 82, 0, 'Croissant al prosciutto crudo mignon', 9, 6),
    (109, 82, 0, 'Tramezzini tonno e carciofini mignon', 17, 7),
    (112, 86, 41, 'Vitello tonnato', 1, 0),
    (113, 86, 41, 'Carpaccio di spada', 2, 1),
    (114, 86, 41, 'Alici marinate', 3, 2),
    (115, 86, 42, 'Penne alla messinese', 5, 0),
    (116, 86, 42, 'Risotto alla zucca', 20, 1),
    (117, 86, 43, 'Salmone al forno', 8, 0),
    (118, 86, 44, 'Sorbetto al limone', 18, 0),
    (119, 86, 44, 'Torta Saint Honoré', 19, 1);

-- Table structure for table 'menusections'
DROP TABLE IF EXISTS "menusections";
CREATE TABLE "menusections" (
    "id" SERIAL PRIMARY KEY,
    "menu_id" INTEGER NOT NULL,
    "name" TEXT,
    "position" INTEGER
);

-- Dumping data for table 'menusections'
INSERT INTO "menusections" VALUES
    (41, 86, 'Antipasti', 0),
    (42, 86, 'Primi', 1),
    (43, 86, 'Secondi', 2),
    (44, 86, 'Dessert', 3),
    (45, 87, 'Antipasti', 0);

-- Table structure for table 'menus'
DROP TABLE IF EXISTS "menus";
CREATE TABLE "menus" (
    "id" SERIAL PRIMARY KEY,
    "name" TEXT,
    "owner_id" INTEGER,
    "published" BOOLEAN DEFAULT FALSE
);

-- Dumping data for table 'menus'
INSERT INTO "menus" VALUES
    (80, 'Coffee break mattutino', 2, TRUE),
    (82, 'Coffee break pomeridiano', 2, TRUE),
    (86, 'Cena di compleanno pesce', 3, TRUE);

-- Table structure for table 'recipes'
CREATE TABLE "duties" (
    "id" SERIAL PRIMARY KEY,
    "name" TEXT,
    "description" TEXT,
    "difficult" INTEGER,
    "importance" INTEGER,
    "time" INTEGER,
    "quantity" INTEGER,
    "portions" INTEGER,
    "type" TEXT CHECK (type IN ('recipe', 'preparation'))
);

CREATE TABLE "recipes" (
    "id" SERIAL PRIMARY KEY,
    "duty_id" INTEGER REFERENCES "duties"("id")
);

CREATE TABLE "preparations" (
    "id" SERIAL PRIMARY KEY,
    "duty_id" INTEGER REFERENCES "duties"("id")
);

-- Dumping data for table 'duties' (generalized table)
INSERT INTO "duties" ("name", "description", "difficult", "importance", "time", "quantity", "portions", "type")
VALUES
    ('Vitello tonnato', 'Piatto tradizionale italiano di vitello freddo a fette coperto con una salsa cremosa simile alla maionese aromatizzata al tonno.', 3, 4, 45, 4, 4, 'recipe'),
    ('Carpaccio di spada', 'Pesce spada crudo a fette sottili, condito con olio d oliva, limone e rucola.', 2, 3, 15, 1, 2, 'recipe'),
    ('Alici marinate', 'Alici marinate servite come antipasto.', 1, 3, 60, 1, 4, 'recipe'),
    ('Insalata di riso', 'Insalata di riso fredda con verdure e a volte frutti di mare.', 1, 2, 30, 4, 6, 'recipe'),
    ('Penne al sugo di baccalà', 'Pasta con sugo di baccalà.', 3, 4, 50, 4, 4, 'recipe'),
    ('Pappa al pomodoro', 'Zuppa toscana di pane e pomodoro.', 2, 3, 40, 4, 4, 'recipe'),
    ('Hamburger con bacon e cipolla caramellata', 'Hamburger con bacon e cipolla caramellata.', 3, 5, 30, 2, 2, 'recipe'),
    ('Salmone al forno', 'Salmone al forno.', 2, 4, 25, 2, 2, 'recipe'),
    ('Croissant', 'Pasta sfoglia francese burrosa e croccante.', 5, 4, 180, 12, 12, 'preparation'),
    ('Pane al cioccolato', 'Pane al cioccolato.', 3, 5, 120, 8, 8, 'preparation'),
    ('Girelle all uvetta', 'Rotoli all uvetta.', 3, 4, 90, 10, 10, 'preparation'),
    ('Panini al latte', 'Panini al latte.', 3, 3, 70, 10, 10, 'preparation'),
    ('Biscotti di pasta frolla', 'Biscotti di pasta frolla.', 2, 3, 45, 20, 20, 'preparation'),
    ('Lingue di gatto', 'Biscotti sottili e croccanti.', 1, 4, 35, 30, 30, 'preparation'),
    ('Bigné farciti', 'Bignè ripieni di crema.', 3, 4, 60, 12, 12, 'preparation'),
    ('Pizzette', 'Piccole pizze.', 3, 3, 60, 15, 15, 'preparation'),
    ('Tramezzini', 'Tramezzini italiani.', 2, 1, 20, 10, 10, 'preparation'),
    ('Sorbetto al limone', 'Sorbetto al limone.', 1, 3, 20, 4, 4, 'preparation'),
    ('Torta Saint Honoré', 'Classica torta francese intitolata al patrono dei panettieri.', 5, 5, 120, 8, 8, 'preparation'),
    ('Risotto alla zucca', 'Risotto alla zucca.', 4, 5, 40, 4, 4, 'recipe'),
    ('Crema pasticcera', 'Crema per farcire i bignè.', 3, 4, 30, 1, 1, 'preparation'),
    ('Ragù alla bolognese', 'Sugo di carne per le lasagne.', 4, 3, 120, 1, 1, 'preparation'),
    ('Salsa tonnata', 'Salsa cremosa al tonno per il vitello tonnato.', 2, 2, 20, 1, 1, 'preparation'),
    ('Salsa di acciughe', 'Salsa di acciughe per le alici marinate.', 1, 3, 15, 1, 1, 'preparation'),
    ('Maionese', 'Maionese per gli hamburger.', 2, 3, 10, 1, 1, 'preparation'),
    ('Salsa al limone', 'Salsa al limone per il salmone al forno.', 2, 4, 15, 1, 1, 'preparation'),
    ('Besciamella', 'Salsa bianca per lasagne e cannelloni.', 3, 3, 20, 1, 1, 'preparation'),
    ('Pesto alla genovese', 'Salsa di basilico, pinoli, aglio e formaggio.', 2, 4, 15, 1, 1, 'preparation'),
    ('Salsa al cioccolato', 'Salsa di cioccolato per decorare dolci e gelati.', 1, 2, 10, 1, 1, 'preparation');
;


-- Inserting data for table 'recipes'
INSERT INTO "recipes" ("id", "duty_id")
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 20);

-- Inserting data for table 'preparations'
INSERT INTO "preparations" ("id", "duty_id")
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


-- Table structure for table 'roles'
DROP TABLE IF EXISTS "roles";
CREATE TABLE "roles" (
    "id" CHAR(1) PRIMARY KEY,
    "role" VARCHAR(128) NOT NULL DEFAULT 'servizio'
);

-- Dumping data for table 'roles'
INSERT INTO "roles" VALUES
    ('c', 'cuoco'),
    ('h', 'chef'),
    ('o', 'organizzatore'),
    ('s', 'servizio');

-- Table structure for table 'services'
DROP TABLE IF EXISTS "services";
CREATE TABLE "services" (
    "id" SERIAL PRIMARY KEY,
    "event_id" INTEGER NOT NULL,
    "name" VARCHAR(128),
    "proposed_menu_id" INTEGER NOT NULL DEFAULT 0,
    "approved_menu_id" INTEGER DEFAULT 0,
    "service_date" DATE,
    "time_start" TIME,
    "time_end" TIME,
    "expected_participants" INTEGER
);

-- Dumping data for table 'services'
INSERT INTO "services" VALUES
    (1, 2, 'Cena', 86, 0, '2020-08-13', '20:00:00', '23:30:00', 25),
    (2, 1, 'Coffee break mattino', 0, 80, '2020-09-25', '10:30:00', '11:30:00', 100),
    (3, 1, 'Colazione di lavoro', 0, 0, '2020-09-25', '13:00:00', '14:00:00', 80),
    (4, 1, 'Coffee break pomeriggio', 0, 82, '2020-09-25', '16:00:00', '16:30:00', 100),
    (5, 1, 'Cena sociale', 0, 0, '2020-09-25', '20:00:00', '22:30:00', 40),
    (6, 3, 'Pranzo giorno 1', 0, 0, '2020-10-02', '12:00:00', '15:00:00', 200),
    (7, 3, 'Pranzo giorno 2', 0, 0, '2020-10-03', '12:00:00', '15:00:00', 300),
    (8, 3, 'Pranzo giorno 3', 0, 0, '2020-10-04', '12:00:00', '15:00:00', 400);

-- Table structure for table 'userroles'
CREATE TABLE "userroles" (
    "user_id" INTEGER NOT NULL,
    "role_id" CHAR(1) NOT NULL
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

-- Table structure for table 'users'
CREATE TABLE "users" (
    "id" SERIAL PRIMARY KEY,
    "username" VARCHAR(128) NOT NULL
);

-- Dumping data for table 'users'
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

-- Table structure for table 'sheets'
CREATE TABLE "sheets" (
    id SERIAL PRIMARY KEY,
    service INTEGER,
    owner_id INTEGER,
    CONSTRAINT fk_service_id FOREIGN KEY (service) REFERENCES services(id),
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES users(id)
);

INSERT INTO "sheets" VALUES
    (1, 3, 2),
    (2, 2, 3),
    (3, 6, 4),
    (4, 5, 4),
    (5, 1, 2);

-- Table structure for table 'jobs'
DROP TABLE IF EXISTS "jobs";
CREATE TABLE "jobs" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(128),
    "time" INTEGER,
    "portions" INTEGER,
    "prepare" BOOLEAN DEFAULT TRUE,
    "completed" BOOLEAN DEFAULT TRUE,
    "duty_id" INTEGER,
    "sheet_id" INTEGER,
    CONSTRAINT fk_duty_id FOREIGN KEY (duty_id) REFERENCES duties(id),
    CONSTRAINT fk_sheet_id FOREIGN KEY (sheet_id) REFERENCES sheets(id)
);

INSERT INTO "jobs" VALUES
    (1, 'Preparare antipasti', 60, 100, TRUE, FALSE, 1),
    (2, 'Cucinare primi', 90, 80, TRUE, FALSE, 2),
    (3, 'Preparare secondi', 120, 60, TRUE, FALSE, 3),
    (4, 'Cucinare dolci', 45, 50, TRUE, FALSE, 4),
    (5, 'Servire al tavolo', 30, 200, TRUE, FALSE, 5);

-- Table structure for table 'cooks'
DROP TABLE IF EXISTS  "cooks";
CREATE TABLE "cooks" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(128)
);

INSERT INTO "cooks" VALUES
    (1, 'Mario Rossi'),
    (2, 'Luigi Bianchi'),
    (3, 'Giuseppe Verdi'),
    (4, 'Anna Neri'),
    (5, 'Paola Gialli');

-- Table structure for table 'shifts'
DROP TABLE IF EXISTS "shifts";
CREATE TABLE "shifts" (
    "id" SERIAL PRIMARY KEY,
    "description" VARCHAR(128),
    "date" DATE,
    "time" TIME,
    "duration" INT,
    "expire" DATE,
    "lock" BOOLEAN DEFAULT FALSE,
    "board" INTEGER,
    CONSTRAINT fk_board_id FOREIGN KEY ("board") REFERENCES "boards"("id")
);

INSERT INTO "shifts" VALUES
    (1, 'Turno di mattina', '2021-07-14', '08:00:00', 480, '2021-07-13', FALSE, 1),
    (2, 'Turno di pomeriggio', '2021-07-14', '14:00:00', 480, '2021-07-13', FALSE, 1),
    (3, 'Turno di sera', '2021-07-14', '20:00:00', 480, '2021-07-13', FALSE, 1),
    (4, 'Turno di notte', '2021-07-14', '02:00:00', 480, '2021-07-13', FALSE, 2),
    (5, 'Turno di riposo', '2021-07-14', '00:00:00', 0, '2021-07-13', TRUE, 2);

-- Table structure for table 'job-cook'
DROP TABLE IF EXISTS "job-cook";
CREATE TABLE "job-cook" (
    "job_id" SERIAL NOT NULL,
    "cook_id"  SERIAL NOT NULL,
    PRIMARY KEY ("job_id", "cook_id"),
    CONSTRAINT fk_job FOREIGN KEY ("job_id") REFERENCES "jobs" ("id"),
    CONSTRAINT fk_cook FOREIGN KEY ("cook_id") REFERENCES "cooks" ("id")
);

-- Table structure for table 'shift-cook'
DROP TABLE IF EXISTS "shift-cook";
CREATE TABLE "shift-cook" (
    "shift_id" SERIAL NOT NULL,
    "cook_id"  SERIAL NOT NULL,
    PRIMARY KEY ("shift_id", "cook_id"),
    CONSTRAINT fk_shift FOREIGN KEY ("shift_id") REFERENCES "shifts" ("id"),
    CONSTRAINT fk_cook FOREIGN KEY ("cook_id") REFERENCES "cooks" ("id")
);

INSERT INTO "shift-cook" VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

