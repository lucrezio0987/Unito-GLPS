-- PostgreSQL dump for the catering database
-- This script is adapted from a MySQL dump for use in PostgreSQL environments.

-- Create the database and switch to it
CREATE DATABASE catering WITH ENCODING 'UTF8';
\\c catering;

-- Table structure for table 'Events'
DROP TABLE IF EXISTS "Events";
CREATE TABLE "Events" (
  "id" SERIAL PRIMARY KEY,
  "name" VARCHAR(128),
  "date_start" DATE,
  "date_end" DATE,
  "expected_participants" INTEGER,
  "organizer_id" INTEGER NOT NULL
);

-- Dumping data for table 'Events'
INSERT INTO "Events" VALUES
  (1, 'Convegno Agile Community', '2020-09-25', '2020-09-25', 100, 2),
  (2, 'Compleanno di Manuela', '2020-08-13', '2020-08-13', 25, 2),
  (3, 'Fiera del Sedano Rapa', '2020-10-02', '2020-10-04', 400, 1);

-- Table structure for table 'MenuFeatures'
DROP TABLE IF EXISTS "MenuFeatures";
CREATE TABLE "MenuFeatures" (
  "menu_id" INTEGER NOT NULL,
  "name" VARCHAR(128) NOT NULL,
  "value" BOOLEAN DEFAULT FALSE
);

-- Dumping data for table 'MenuFeatures'
INSERT INTO "MenuFeatures" VALUES
  (80, 'Richiede cuoco', FALSE), (80, 'Buffet', FALSE),
  (80, 'Richiede cucina', FALSE), (80, 'Finger food', FALSE),
  (80, 'Piatti caldi', FALSE), (82, 'Richiede cuoco', FALSE),
  (82, 'Buffet', FALSE), (82, 'Richiede cucina', FALSE),
  (82, 'Finger food', FALSE), (82, 'Piatti caldi', FALSE),
  (86, 'Richiede cuoco', FALSE), (86, 'Buffet', FALSE),
  (86, 'Richiede cucina', FALSE), (86, 'Finger food', FALSE),
  (86, 'Piatti caldi', FALSE);

-- Table structure for table 'MenuItems'
DROP TABLE IF EXISTS "MenuItems";
CREATE TABLE "MenuItems" (
  "id" SERIAL PRIMARY KEY,
  "menu_id" INTEGER NOT NULL,
  "section_id" INTEGER,
  "description" TEXT,
  "recipe_id" INTEGER NOT NULL,
  "position" INTEGER
);

-- Dumping data for table 'MenuItems'
INSERT INTO "MenuItems" VALUES
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

-- Table structure for table 'MenuSections'
DROP TABLE IF EXISTS "MenuSections";
CREATE TABLE "MenuSections" (
  "id" SERIAL PRIMARY KEY,
  "menu_id" INTEGER NOT NULL,
  "name" TEXT,
  "position" INTEGER
);

-- Dumping data for table 'MenuSections'
INSERT INTO "MenuSections" VALUES
  (41, 86, 'Antipasti', 0),
  (42, 86, 'Primi', 1),
  (43, 86, 'Secondi', 2),
  (44, 86, 'Dessert', 3),
  (45, 87, 'Antipasti', 0);

-- Table structure for table 'Menus'
DROP TABLE IF EXISTS "Menus";
CREATE TABLE "Menus" (
  "id" SERIAL PRIMARY KEY,
  "title" TEXT,
  "owner_id" INTEGER,
  "published" BOOLEAN DEFAULT FALSE
);

-- Dumping data for table 'Menus'
INSERT INTO "Menus" VALUES
  (80, 'Coffee break mattutino', 2, TRUE),
  (82, 'Coffee break pomeridiano', 2, TRUE),
  (86, 'Cena di compleanno pesce', 3, TRUE);

-- Table structure for table 'Recipes'
DROP TABLE IF EXISTS "Recipes";
CREATE TABLE "Recipes" (
  "id" SERIAL PRIMARY KEY,
  "name" TEXT
);

-- Dumping data for table 'Recipes'
INSERT INTO "Recipes" VALUES
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

-- Table structure for table 'Roles'
DROP TABLE IF EXISTS "Roles";
CREATE TABLE "Roles" (
  "id" CHAR(1) PRIMARY KEY,
  "role" VARCHAR(128) NOT NULL DEFAULT 'servizio'
);

-- Dumping data for table 'Roles'
INSERT INTO "Roles" VALUES
  ('c', 'cuoco'),
  ('h', 'chef'),
  ('o', 'organizzatore'),
  ('s', 'servizio');

-- Table structure for table 'Services'
DROP TABLE IF EXISTS "Services";
CREATE TABLE "Services" (
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

-- Dumping data for table 'Services'
INSERT INTO "Services" VALUES
  (1, 2, 'Cena', 86, 0, '2020-08-13', '20:00:00', '23:30:00', 25),
  (2, 1, 'Coffee break mattino', 0, 80, '2020-09-25', '10:30:00', '11:30:00', 100),
  (3, 1, 'Colazione di lavoro', 0, 0, '2020-09-25', '13:00:00', '14:00:00', 80),
  (4, 1, 'Coffee break pomeriggio', 0, 82, '2020-09-25', '16:00:00', '16:30:00', 100),
  (5, 1, 'Cena sociale', 0, 0, '2020-09-25', '20:00:00', '22:30:00', 40),
  (6, 3, 'Pranzo giorno 1', 0, 0, '2020-10-02', '12:00:00', '15:00:00', 200),
  (7, 3, 'Pranzo giorno 2', 0, 0, '2020-10-03', '12:00:00', '15:00:00', 300),
  (8, 3, 'Pranzo giorno 3', 0, 0, '2020-10-04', '12:00:00', '15:00:00', 400);

-- Table structure for table 'UserRoles'
DROP TABLE IF EXISTS "UserRoles";
CREATE TABLE "UserRoles" (
  "user_id" INTEGER NOT NULL,
  "role_id" CHAR(1) NOT NULL
);

-- Dumping data for table 'UserRoles'
INSERT INTO "UserRoles" VALUES
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

-- Table structure for table 'Users'
DROP TABLE IF EXISTS "Users";
CREATE TABLE "Users" (
  "id" SERIAL PRIMARY KEY,
  "username" VARCHAR(128) NOT NULL
);

-- Dumping data for table 'Users'
INSERT INTO "Users" VALUES
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
"""

# Save the completed PostgreSQL script to a file
path = "/mnt/data/PostgreSQL_Catering_Dump.sql"
with open(path, "w") as file:
    file.write(pgsql_script_content)

path &#8203;``【oaicite:0】``&#8203;
