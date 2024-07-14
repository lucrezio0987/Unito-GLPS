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
DROP TABLE IF EXISTS "sheet" CASCADE;
DROP TABLE IF EXISTS "jobs" CASCADE;
DROP TABLE IF EXISTS "cooks" CASCADE;
DROP TABLE IF EXISTS "shifts" CASCADE;
DROP TABLE IF EXISTS "shift_cook" CASCADE;

-- Table structure for table 'board'
DROP TABLE IF EXISTS "board";
CREATE TABLE "board" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(128)
);

INSERT INTO "board" VALUES
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
     CONSTRAINT fk_board_id FOREIGN KEY ("board") REFERENCES "board"("id")
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
    "title" TEXT,
    "owner_id" INTEGER,
    "published" BOOLEAN DEFAULT FALSE
);

-- Dumping data for table 'menus'
INSERT INTO "menus" VALUES
    (80, 'Coffee break mattutino', 2, TRUE),
    (82, 'Coffee break pomeridiano', 2, TRUE),
    (86, 'Cena di compleanno pesce', 3, TRUE);

-- Table structure for table 'recipes'
DROP TABLE IF EXISTS "recipes";
CREATE TABLE "recipes" (
    "id" SERIAL PRIMARY KEY,
    "name" TEXT
);

-- Dumping data for table 'recipes'
INSERT INTO "recipes" VALUES
    (1, 'Vitello tonnato'),
    (2, 'Carpaccio di spada'),
    (3, 'Alici marinate'),
    (4, 'Insalata di riso'),
    (5, 'Penne al sugo di baccalà'),
    (6, 'Pappa al pomodoro'),
    (7, 'Hamburger con bacon e cipolla caramellata'),
    (8, 'Salmone al forno'),
    (9, 'Croissant'),
    (10, 'Pane al cioccolato'),
    (11, 'Girelle all''uvetta'),
    (12, 'Panini al latte'),
    (13, 'Biscotti di pasta frolla'),
    (14, 'Lingue di gatto'),
    (15, 'Bigné farciti'),
    (16, 'Pizzette'),
    (17, 'Tramezzini'),
    (18, 'Sorbetto al limone'),
    (19, 'Torta Saint Honoré'),
    (20, 'Risotto alla zucca');

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
DROP TABLE IF EXISTS "userroles";
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
DROP TABLE IF EXISTS "users";
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

-- Table structure for table 'sheet'
DROP TABLE IF EXISTS "sheet";
CREATE TABLE "sheet" (
    sheet_id SERIAL PRIMARY KEY,
    service INTEGER,
    CONSTRAINT fk_service_id FOREIGN KEY (service) REFERENCES services(id)
);

INSERT INTO "sheet" VALUES
    (1, 3),
    (2, 2),
    (3, 6),
    (4, 5),
    (5, 1);

-- Table structure for table 'jobs'
DROP TABLE IF EXISTS "jobs";
CREATE TABLE "jobs" (
    "id" SERIAL PRIMARY KEY,
    "title" VARCHAR(128),
    "time" INTEGER,
    "portions" INTEGER,
    "prepare" BOOLEAN DEFAULT TRUE,
    "completed" BOOLEAN DEFAULT TRUE,
    "service_id" INTEGER,
    CONSTRAINT fk_service_id FOREIGN KEY (service_id) REFERENCES services(id)
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
    CONSTRAINT fk_board_id FOREIGN KEY ("board") REFERENCES "board"("id")
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

