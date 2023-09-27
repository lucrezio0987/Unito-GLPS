--------------- DDL ---------------

BEGIN;

DROP TABLE IF EXISTS Affiliate CASCADE;
DROP TABLE IF EXISTS Canale CASCADE;
DROP TABLE IF EXISTS Categoria CASCADE;
DROP TABLE IF EXISTS Messaggio_Privato CASCADE;
DROP TABLE IF EXISTS Clip CASCADE;
DROP TABLE IF EXISTS Donazione CASCADE;
DROP TABLE IF EXISTS Follow CASCADE;
DROP TABLE IF EXISTS Live_In_Diretta CASCADE;
DROP TABLE IF EXISTS Live_Passate CASCADE;
DROP TABLE IF EXISTS Live_Programmate CASCADE;
DROP TABLE IF EXISTS Messaggio CASCADE;
DROP TABLE IF EXISTS Multimedia CASCADE;
DROP TABLE IF EXISTS Offerta CASCADE;
DROP TABLE IF EXISTS Privilegi CASCADE;
DROP TABLE IF EXISTS Registrato CASCADE;
DROP TABLE IF EXISTS Social CASCADE;
DROP TABLE IF EXISTS Spettatore CASCADE;
DROP TABLE IF EXISTS Subscription CASCADE;
DROP TABLE IF EXISTS Tag_LiveInDiretta CASCADE;
DROP TABLE IF EXISTS Tag_Live_Passate CASCADE;
DROP TABLE IF EXISTS Tag_Video CASCADE;
DROP TABLE IF EXISTS Tag CASCADE;
DROP TABLE IF EXISTS Utente CASCADE; -- Da Rimuovere
DROP TABLE IF EXISTS Video CASCADE;

CREATE TABLE Canale (
    URL VARCHAR(255),
    URL_Trailer VARCHAR(255),
    Descrizione TEXT,
    Numero_di_Live INTEGER not NULL,
    Numero_di_Video INTEGER not NULL,
    Numero_di_Clip INTEGER not NULL,
    URL_Img_di_Copertina VARCHAR(255),
    URL_Img_Profilo VARCHAR(255),
    URL_Ultima_Live VARCHAR(255),
    Numero_di_Follow INTEGER not NULL,
    CONSTRAINT canale_pk PRIMARY KEY (URL)
);

CREATE TABLE Categoria (
    Nome VARCHAR(32) not NULL,
    URL_img VARCHAR(255) not NULL,
	CONSTRAINT Categoria_PK PRIMARY KEY(Nome)
);

CREATE TABLE Live_Programmate (
    Canale VARCHAR(255),
    Data DATE,
    Ora_Inizio TIME,
    Ora_Fine TIME not NULL,
    Titolo VARCHAR(255) not NULL,
    Categoria VARCHAR(255) not NULL,
	CONSTRAINT live_programmate_canale_PK PRIMARY KEY (Canale, Data, Ora_Inizio),
    CONSTRAINT live_programmate_canale_fk FOREIGN KEY (Canale) REFERENCES Canale(URL)  on delete cascade,
    CONSTRAINT live_programmate_categoria_fk FOREIGN KEY (Categoria) REFERENCES Categoria(Nome) on delete cascade,
    CONSTRAINT check_ora CHECK (Ora_Inizio < Ora_Fine)
);

CREATE TABLE Social (
    URL_Account VARCHAR(255),
    Nome VARCHAR(255) not NULL,
    Canale VARCHAR(255) not NULL,
	CONSTRAINT social_canale_pk PRIMARY KEY (URL_Account),
    CONSTRAINT social_canale_fk FOREIGN KEY (Canale) REFERENCES Canale(URL) on delete cascade
);

CREATE TABLE Multimedia (
    URL VARCHAR(255),
    URL_Img_Copertina VARCHAR(255) not NULL,
    Canale VARCHAR(255) not NULL,
    Categoria VARCHAR(32) not NULL,
	CONSTRAINT multimedia_canale_pk PRIMARY KEY (URL),
    CONSTRAINT multimedia_canale_fk FOREIGN KEY (Canale) REFERENCES Canale(URL) on delete cascade,
    CONSTRAINT multimedia_categoria_fk FOREIGN KEY (Categoria) REFERENCES Categoria(Nome) on delete cascade
);

CREATE TABLE Tag (
    Nome VARCHAR(255),
    CONSTRAINT tag_pk PRIMARY KEY (Nome)
);

CREATE TABLE Live_Passate (
    URL VARCHAR(255),
    Titolo VARCHAR(255) not NULL,
    Data DATE not NULL,
    Ora_Inizio TIME not NULL,
    Ora_Fine TIME not NULL,
    Durata INTEGER not NULL,
    Numero_Spettatori INTEGER not NULL,
    Media_Spettatori INTEGER not NULL,
	CONSTRAINT live_passate_pk PRIMARY KEY (URL),
	CONSTRAINT live_passate_fk FOREIGN KEY (URL) REFERENCES Multimedia(URL) on delete cascade,
    CONSTRAINT check_ora CHECK (Ora_Inizio < Ora_Fine),
    CONSTRAINT check_data CHECK (Data <= current_date)
);

CREATE TABLE Video (
    URL VARCHAR(255),
    Titolo VARCHAR(255),
    Data DATE not NULL,
    Durata INTEGER not NULL,
    Numero_Visualizzazioni INTEGER not NULL,
    Fonte VARCHAR(255) not NULL,
	CONSTRAINT video_multimedia_pk PRIMARY KEY (URL),
    CONSTRAINT video_multimedia_fk FOREIGN KEY (Fonte) REFERENCES Live_Passate(URL) on delete cascade,
    CONSTRAINT video_url_fk FOREIGN KEY (URL) REFERENCES Multimedia(URL) on delete cascade,
	CONSTRAINT unique_video_fonte UNIQUE (Fonte),
    CONSTRAINT check_data CHECK (Data <= current_date)
);

CREATE TABLE Clip (
    URL VARCHAR(255) ,
    Titolo VARCHAR(255) not NULL,
    Data DATE not NULL,
    Durata INTEGER not NULL,
    Numero_Visualizzazioni INTEGER not NULL,
	CONSTRAINT clip_pk PRIMARY KEY (URL),
    CONSTRAINT clip_multimedia_fk FOREIGN KEY (URL) REFERENCES Multimedia(URL) on delete cascade,
    CONSTRAINT check_data CHECK (Data <= current_date)
);

CREATE TABLE Tag_Live_Passate (
    URL VARCHAR(255),
    Tag VARCHAR(255),
	CONSTRAINT tag_live_passate_pk PRIMARY KEY (URL, Tag),
    CONSTRAINT tag_live_passate_url_fk FOREIGN KEY (URL) REFERENCES Live_Passate(URL) on delete cascade,
    CONSTRAINT tag_nome_fk2 FOREIGN KEY (Tag) REFERENCES Tag(Nome) on delete cascade
);

CREATE TABLE Tag_Video (
    URL VARCHAR(255),
    Tag VARCHAR(255),
	CONSTRAINT tag_video_pk PRIMARY KEY (URL, Tag),
    CONSTRAINT tag_video_url_fk FOREIGN KEY (URL) REFERENCES Video(URL) on delete cascade,
    CONSTRAINT tag_nome_fk3 FOREIGN KEY (Tag) REFERENCES Tag(Nome) on delete cascade
);

CREATE TABLE Privilegi (
    Tipo VARCHAR(32),
	CONSTRAINT privilegi_pk PRIMARY KEY (Tipo)
);

CREATE TABLE Registrato (
    Username VARCHAR(32),
    Password VARCHAR(255) not NULL,
    Nome VARCHAR(255) not NULL,
    Cognome VARCHAR(255) not NULL,
    Data_di_nascita DATE not NULL,
    Email VARCHAR(255) not NULL,
    Cellulare VARCHAR(255),
    Minuti_Trasmessi INTEGER not NULL,
    Media_Spettatori_Simultanei INTEGER not NULL,
    Portafoglio_di_Bit INTEGER not NULL,
    URL_Canale VARCHAR(255) not NULL,
	CONSTRAINT registrato_utente_pk PRIMARY KEY (Username),
    CONSTRAINT registrato_url_canale_fk FOREIGN KEY (URL_Canale) REFERENCES Canale(URL) on delete cascade,
	CONSTRAINT unique_registrato_url_canale UNIQUE (URL_Canale),
    CONSTRAINT check_data CHECK (Data_di_nascita <= current_date)
);

CREATE TABLE Affiliate (
    Username VARCHAR(32),
    Sottoscrizioni INTEGER not NULL,
    Bit_Ricevuti INTEGER not NULL,
	CONSTRAINT affiliate_pk PRIMARY KEY (Username),
    CONSTRAINT affiliate_username_fk FOREIGN KEY (Username) REFERENCES Registrato(Username) on delete cascade,
	CONSTRAINT unique_affiliate_username UNIQUE (Username)
);

CREATE TABLE Messaggio_Privato (
    Mittente VARCHAR(32),
    Data DATE ,
    Ora TIME,
    Messaggio TEXT not NULL,
    Destinatario VARCHAR(32) not NULL,
	CONSTRAINT chat_privata_pk PRIMARY KEY (Mittente, Data, Ora),
    CONSTRAINT messaggio_privato_mittente_fk FOREIGN KEY (Mittente) REFERENCES Registrato(Username) on delete cascade,
    CONSTRAINT messaggio_privato_destinatario_fk FOREIGN KEY (Destinatario) REFERENCES Registrato(Username) on delete cascade,
    CONSTRAINT check_data CHECK (Data <= current_date)
);

CREATE TABLE Donazione (
    Registrato VARCHAR(32),
    Affiliate VARCHAR(32),
    Data DATE,
    Ora TIME,
    Valore FLOAT not NULL,
    CONSTRAINT donazione_pk PRIMARY KEY (Registrato, Affiliate, Data, Ora),
    CONSTRAINT donazione_registrato_fk FOREIGN KEY (Registrato) REFERENCES Registrato(Username) on delete cascade,
    CONSTRAINT donazione_affiliate_fk FOREIGN KEY (Affiliate) REFERENCES Affiliate(Username) on delete cascade,
    CONSTRAINT check_data CHECK (Data <= current_date)
);

CREATE TABLE Follow (
    Registrato VARCHAR(32),
    Canale VARCHAR(255),
    CONSTRAINT follow_registrato_pk PRIMARY KEY (Registrato, Canale),
    CONSTRAINT follow_registrato_fk FOREIGN KEY (Registrato) REFERENCES Registrato(Username) on delete cascade,
    CONSTRAINT follow_canale_fk FOREIGN KEY (Canale) REFERENCES Canale(URL) on delete cascade
);

CREATE TABLE Live_In_Diretta (
    URL VARCHAR(255),
    Titolo VARCHAR(255) not NULL,
    Numero_Spettatori INTEGER not NULL,
    Ora_Inizio TIME not NULL,
    URL_Img_Copertina VARCHAR(255) not NULL,
    Streamer VARCHAR(32) not NULL,
    CONSTRAINT live_in_diretta_streamer_pk PRIMARY KEY (URL),
    CONSTRAINT live_in_diretta_url_fk FOREIGN KEY (URL) REFERENCES Multimedia(URL) on delete cascade,
    CONSTRAINT live_in_diretta_streamer_fk FOREIGN KEY (Streamer) REFERENCES Registrato(Username) on delete cascade,
    CONSTRAINT unique_live_in_diretta_streamer UNIQUE (Streamer)
);

CREATE TABLE Messaggio (
    Live VARCHAR(255),
    Username VARCHAR(32),
    Orario TIME,
    Testo TEXT not NULL,
	CONSTRAINT messaggio_live_pk PRIMARY KEY (Username, Orario),
    CONSTRAINT messaggio_live_fk FOREIGN KEY (Live) REFERENCES Live_In_Diretta(URL) on delete cascade,
    CONSTRAINT messaggio_username_fk FOREIGN KEY (Username) REFERENCES Registrato(Username) on delete cascade
);

CREATE TABLE Offerta (
    Affiliate VARCHAR(32),
    Tipo VARCHAR(32),
	CONSTRAINT offerta_affiliate_PK PRIMARY KEY (Affiliate, Tipo),
    CONSTRAINT offerta_affiliate_fk FOREIGN KEY (Affiliate) REFERENCES Affiliate(Username) on delete cascade,
    CONSTRAINT offerta_tipo_fk FOREIGN KEY (Tipo) REFERENCES Privilegi(Tipo) on delete cascade
);

CREATE TABLE Subscription (
    Registrato VARCHAR(32),
    Affiliate VARCHAR(32),
	CONSTRAINT subscription_registrato_pk PRIMARY KEY (Registrato, Affiliate),
    CONSTRAINT subscription_registrato_fk FOREIGN KEY (Registrato) REFERENCES Registrato(Username) on delete cascade,
    CONSTRAINT subscription_affiliate_fk FOREIGN KEY (Affiliate) REFERENCES Affiliate(Username) on delete cascade
);

CREATE TABLE Tag_LiveInDiretta (
    URL VARCHAR(255),
    Tag VARCHAR(255),
    CONSTRAINT tag_live_in_diretta_pk PRIMARY KEY (URL, Tag),
    CONSTRAINT tag_live_in_diretta_url_fk FOREIGN KEY (URL) REFERENCES Live_In_Diretta(URL) on delete cascade,
    CONSTRAINT tag_live_in_diretta_tag_fk FOREIGN KEY (Tag) REFERENCES Tag(Nome) on delete cascade
);

CREATE TABLE Spettatore (
    Registrato VARCHAR(32),
    Live_In_Diretta VARCHAR(255) not NULL,
    CONSTRAINT spettatore_pk PRIMARY KEY (Registrato),
    CONSTRAINT spettatore_utente_fk FOREIGN KEY (Registrato) REFERENCES Registrato(Username) on delete cascade,
    CONSTRAINT fk_Live_In_Diretta_Utente FOREIGN KEY (Live_In_Diretta) REFERENCES Live_In_Diretta (URL) on delete cascade
);


COMMIT;

--------------- FUNZIONE ---------------

DROP FUNCTION IF EXISTS delete_from_canale;
DROP TRIGGER IF EXISTS delete_canale ON Registrato;

CREATE FUNCTION delete_from_canale()
RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM Canale WHERE URL = OLD.URL_Canale;
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_canale
AFTER DELETE ON Registrato
FOR EACH ROW
EXECUTE FUNCTION delete_from_canale();

--------------- DML ---------------

INSERT INTO Canale(URL, URL_Trailer, Descrizione, Numero_di_Live, Numero_di_Video, Numero_di_Clip, URL_Img_di_Copertina, URL_Img_Profilo, URL_Ultima_Live, Numero_di_Follow) VALUES
    ('twitch.com/esportsgamingchannel', 'twitch.com/esportsgamingchannel/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/esportsgamingchannel/img_copertina.jpg', 'twitch.com/esportsgamingchannel/img_profilo.jpg', NULL, 2),
    ('twitch.com/artandmusicstreaming', 'twitch.com/artandmusicstreaming/trailer.mp4', 'descrizione di prova', 7, 7, 4, 'twitch.com/artandmusicstreaming/img_copertina.jpg', 'twitch.com/artandmusicstreaming/img_profilo.jpg', NULL, 22),
    ('twitch.com/lifestylevloggers', 'twitch.com/lifestylevloggers/trailer.mp4', 'descrizione di prova', 3, 2, 2, 'twitch.com/lifestylevloggers/img_copertina.jpg', 'twitch.com/lifestylevloggers/img_profilo.jpg', 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-11.mp4', 1),
    ('twitch.com/gamingnewsupdates', 'twitch.com/gamingnewsupdates/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/gamingnewsupdates/img_copertina.jpg', 'twitch.com/gamingnewsupdates/img_profilo.img', 'twitch.com/gamingnewsupdates/ww48nt8yg5nytc89n34ycm824t14c3myy23v5t2957yt', 0),
    ('twitch.com/cookingandrecipeslive', 'twitch.com/cookingandrecipeslive/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/cookingandrecipeslive/img_copertina.jpg', 'twitch.com/cookingandrecipeslive/img_profilo.jpg', NULL, 0),
    ('twitch.com/ShadowLurker', 'twitch.com/ShadowLurker/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/ShadowLurker/img_copertina.jpg', 'twitch.com/ShadowLurker/img_profilo.jpg', NULL, 0),
    ('twitch.com/MysticEnigma', 'twitch.com/MysticEnigma/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/MysticEnigma/img_copertina.jpg', 'twitch.com/MysticEnigma/img_profilo.jpg', NULL, 0),
    ('twitch.com/CrimsonPhoenix', 'twitch.com/CrimsonPhoenix/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/CrimsonPhoenix/img_copertina.jpg', 'twitch.com/CrimsonPhoenix/img_profilo.jpg', NULL, 0),
    ('twitch.com/DreamWeaverX', 'twitch.com/DreamWeaverX/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/DreamWeaverX/img_copertina.jpg', 'twitch.com/DreamWeaverX/img_profilo.jpg', NULL, 0),
    ('twitch.com/NightFuryX', 'twitch.com/NightFuryX/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/NightFuryX/img_copertina.jpg', 'twitch.com/NightFuryX/img_profilo.jpg', NULL, 0),
    ('twitch.com/ArcaneNinja', 'twitch.com/ArcaneNinja/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/ArcaneNinja/img_copertina.jpg', 'twitch.com/ArcaneNinja/img_profilo.jpg', NULL, 0),
    ('twitch.com/ElementalGoddess', 'twitch.com/ElementalGoddess/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/ElementalGoddess/img_copertina.jpg', 'twitch.com/ElementalGoddess/img_profilo.jpg', NULL, 0),
    ('twitch.com/DragonSlayerX', 'twitch.com/DragonSlayerX/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/DragonSlayerX/img_copertina.jpg', 'twitch.com/DragonSlayerX/img_profilo.jpg', NULL, 0),
    ('twitch.com/EnchantedWarrior', 'twitch.com/EnchantedWarrior/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/EnchantedWarrior/img_copertina.jpg', 'twitch.com/EnchantedWarrior/img_profilo.jpg', NULL, 0),
    ('twitch.com/SorceressQueen', 'twitch.com/SorceressQueen/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/SorceressQueen/img_copertina.jpg', 'twitch.com/SorceressQueen/img_profilo.jpg', NULL, 0),
    ('twitch.com/EternalFlameX', 'twitch.com/EternalFlameX/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/EternalFlameX/img_copertina.jpg', 'twitch.com/EternalFlameX/img_profilo.jpg', NULL, 0),
    ('twitch.com/CelestialSiren', 'twitch.com/CelestialSiren/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/CelestialSiren/img_copertina.jpg', 'twitch.com/CelestialSiren/img_profilo.jpg', NULL, 0),
    ('twitch.com/GalaxyGiver', 'twitch.com/GalaxyGiver/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/GalaxyGiver/img_copertina.jpg', 'twitch.com/GalaxyGiver/img_profilo.jpg', NULL, 0),
    ('twitch.com/MysticSeekerX', 'twitch.com/MysticSeekerX/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/MysticSeekerX/img_copertina.jpg', 'twitch.com/MysticSeekerX/img_profilo.jpg', NULL, 0),
    ('twitch.com/SunlightSavior', 'twitch.com/SunlightSavior/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/SunlightSavior/img_copertina.jpg', 'twitch.com/SunlightSavior/img_profilo.jpg', NULL, 0),
    ('twitch.com/EmeraldEmbraceX', 'twitch.com/EmeraldEmbraceX/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/EmeraldEmbraceX/img_copertina.jpg', 'twitch.com/EmeraldEmbraceX/img_profilo.jpg', NULL, 0),
    ('twitch.com/PhoenixRising', 'twitch.com/PhoenixRising/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/PhoenixRising/img_copertina.jpg', 'twitch.com/PhoenixRising/img_profilo.jpg', NULL, 0),
    ('twitch.com/MysticalMermaid', 'twitch.com/MysticalMermaid/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/MysticalMermaid/img_copertina.jpg', 'twitch.com/MysticalMermaid/img_profilo.jpg', NULL, 0),
    ('twitch.com/CosmicCrafterX', 'twitch.com/CosmicCrafterX/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/CosmicCrafterX/img_copertina.jpg', 'twitch.com/CosmicCrafterX/img_profilo.jpg', NULL, 0),
    ('twitch.com/GlowingGuard', 'twitch.com/GlowingGuard/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/GlowingGuard/img_copertina.jpg', 'twitch.com/GlowingGuard/img_profilo.jpg', NULL, 0);

INSERT INTO Categoria (Nome, URL_img) VALUES
    ('Minecraft', 'twitch.com/categorie/Minecraft.jpg'),
    ('Fortnite', 'twitch.com/categorie/Fortnite.jpg'),
    ('Call_of_Duty', 'twitch.com/categorie/Call_of_Duty.jpg'),
    ('Assassins_Creed', 'twitch.com/categorie/Assassins_Creed.jpg'),
    ('Grand_Theft_Auto', 'twitch.com/categorie/Grand_Theft_Auto.jpg'),
    ('The_Legend_of_Zelda', 'twitch.com/categorie/The_Legend_of_Zelda.jpg'),
    ('Super_Mario_Bros', 'twitch.com/categorie/Super_Mario_Bros.jpg'),
    ('Pokemon', 'twitch.com/categorie/Pokemon.jpg'),
    ('Overwatch', 'twitch.com/categorie/Overwatch.jpg'),
    ('League_of_Legends', 'twitch.com/categorie/League_of_Legends.jpg'),
    ('Just_Chat', 'twitch.com/categorie/JustChat.jpg');


INSERT INTO Live_Programmate (Canale, Data, Ora_Inizio, Ora_Fine, Titolo, Categoria) VALUES
    ('twitch.com/esportsgamingchannel', '2020-01-03', '20:30:00', '23:00:00', 'che bello strimmare su twitch!!', 'Call_of_Duty'),
    ('twitch.com/esportsgamingchannel', '2020-03-01', '14:00:00', '20:00:00', 'che emozione strimmare su twitch!!', 'Grand_Theft_Auto'),
    ('twitch.com/lifestylevloggers', '2020-05-10', '12:30:00', '17:00:00', 'che goduria strimmare su twitch!!', 'Fortnite'),
    ('twitch.com/gamingnewsupdates', '2020-01-02', '12:30:00', '17:00:00', 'che giubilo strimmare su twitch!!', 'Minecraft'),
    ('twitch.com/gamingnewsupdates', '2020-01-03', '15:30:00', '18:00:00', 'che brutto strimmare su twitch!!', 'Minecraft'),
    ('twitch.com/gamingnewsupdates', '2020-01-04', '15:30:00', '18:00:00', 'che sofferenza strimmare su twitch!!', 'Minecraft'),
    ('twitch.com/cookingandrecipeslive', '2020-01-10', '10:00:00', '11:00:00', 'che  strimmare su twitch!!', 'Pokemon');

INSERT INTO Social (URL_Account, Nome, Canale) VALUES
    ('www.facebook.com/esportsgamingchannel', 'facebook' ,'twitch.com/esportsgamingchannel'),
    ('www.instagram.com/esportsgamingchannel', 'instagram' ,'twitch.com/esportsgamingchannel'),
    ('www.tiktok.com/esportsgamingchannel', 'tiktok' ,'twitch.com/esportsgamingchannel'),
    ('www.twitter.com/esportsgamingchannel', 'twitter' ,'twitch.com/esportsgamingchannel'),
    ('www.instagram.com/artandmusicstreaming', 'instagram' ,'twitch.com/artandmusicstreaming'),
    ('www.facebook.com/lifestylevloggers', 'facebook' ,'twitch.com/lifestylevloggers'),
    ('www.instagram.com/lifestylevloggers', 'instagram' ,'twitch.com/lifestylevloggers'),
    ('www.instagram.com/gamingnewsupdates', 'instagram' ,'twitch.com/gamingnewsupdates'),
    ('www.tiktok.com/gamingnewsupdates', 'tiktok' ,'twitch.com/gamingnewsupdates');


INSERT INTO Multimedia (URL, URL_Img_Copertina, Canale, Categoria) VALUES
    -- Live_in_Diretta
    ('twitch.com/artandmusicstreaming/diretta.html', 'twitch.com/artandmusicstreaming/diretta.jpg', 'twitch.com/artandmusicstreaming', 'Fortnite'),
    -- Live_Passate
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-10.mp4', 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-10.jpg', 'twitch.com/artandmusicstreaming', 'Grand_Theft_Auto'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-11.mp4', 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-11.jpg', 'twitch.com/artandmusicstreaming', 'Minecraft'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-12.mp4', 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-12.jpg', 'twitch.com/artandmusicstreaming', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-13.mp4', 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-13.jpg', 'twitch.com/artandmusicstreaming', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-14.mp4', 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-14.jpg', 'twitch.com/artandmusicstreaming', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-15.mp4', 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-15.jpg', 'twitch.com/artandmusicstreaming', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-16.mp4', 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-16.jpg', 'twitch.com/artandmusicstreaming', 'Minecraft'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-02.mp4', 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-02.jpg', 'twitch.com/lifestylevloggers', 'Call_of_Duty'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-10.mp4', 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-10.jpg', 'twitch.com/lifestylevloggers', 'Fortnite'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-11.mp4', 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-11.jpg', 'twitch.com/lifestylevloggers', 'Minecraft'),
    -- Video
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-10.mp4', 'twitch.com/artandmusicstreaming/video/video_live_del_2019-05-10.jpg', 'twitch.com/artandmusicstreaming', 'Grand_Theft_Auto'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-11.mp4', 'twitch.com/artandmusicstreaming/video/video_live_del_2019-05-11.jpg', 'twitch.com/artandmusicstreaming', 'Minecraft'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-12.mp4', 'twitch.com/artandmusicstreaming/video/video_live_del_2019-05-12.jpg', 'twitch.com/artandmusicstreaming', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-13.mp4', 'twitch.com/artandmusicstreaming/video/video_live_del_2019-05-13.jpg', 'twitch.com/artandmusicstreaming', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-14.mp4', 'twitch.com/artandmusicstreaming/video/video_live_del_2019-05-14.jpg', 'twitch.com/artandmusicstreaming', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-15.mp4', 'twitch.com/artandmusicstreaming/video/video_live_del_2019-05-15.jpg', 'twitch.com/artandmusicstreaming', 'Minecraft'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-16.mp4', 'twitch.com/artandmusicstreaming/video/video_live_del_2019-05-16.jpg', 'twitch.com/artandmusicstreaming', 'Minecraft'),
    ('twitch.com/lifestylevloggers/video/video_live_del_2019-05-02.mp4', 'twitch.com/lifestylevloggers/video/video_live_del_2019-05-02.jpg', 'twitch.com/lifestylevloggers', 'Call_of_Duty'),
    ('twitch.com/lifestylevloggers/video/video_live_del_2019-05-10.mp4', 'twitch.com/lifestylevloggers/video/video_live_del_2019-05-10.jpg', 'twitch.com/lifestylevloggers', 'Fortnite'),
    --Clip
    ('twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-10.mp4', 'twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-10.jpg', 'twitch.com/artandmusicstreaming', 'Grand_Theft_Auto'),
    ('twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-12.mp4', 'twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-12.jpg', 'twitch.com/artandmusicstreaming', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-14.mp4', 'twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-14.jpg', 'twitch.com/artandmusicstreaming', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-15.mp4', 'twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-15.jpg', 'twitch.com/artandmusicstreaming', 'Minecraft'),
    ('twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-16.mp4', 'twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-16.jpg', 'twitch.com/artandmusicstreaming', 'Minecraft'),
    ('twitch.com/lifestylevloggers/clip/clip_01_live_del_2019-05-02.mp4', 'twitch.com/lifestylevloggers/clip/clip_01_live_del_2019-05-02.jpg', 'twitch.com/lifestylevloggers', 'Call_of_Duty'),
    ('twitch.com/lifestylevloggers/clip/clip_01_live_del_2019-05-10.mp4', 'twitch.com/lifestylevloggers/clip/clip_01_live_del_2019-05-10.jpg', 'twitch.com/lifestylevloggers', 'Fortnite');

INSERT INTO Tag (Nome) VALUES
    ('Minecraft'),
    ('Fortnite'),
    ('Call_of_Duty'),
    ('Assassins_Creed'),
    ('Grand_Theft_Auto'),
    ('The_Legend_of_Zelda'),
    ('Super_Mario_Bros'),
    ('Pokemon'),
    ('Overwatch'),
    ('League_of_Legends'),
    ('Just_Chat'),
    ('italiano'),
    ('inglese'),
    ('francese'),
    ('russo'),
    ('tedesco'),
    ('accesso_anticipato'),
    ('drop_attivi');

INSERT INTO Live_Passate (URL, Titolo, Data, Ora_Inizio, Ora_Fine, Durata, Numero_Spettatori, Media_Spettatori) VALUES
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-10.mp4', 'live_del_2019-05-10', '2019-05-10', '17:00:00', '19:30:00', 150 , 200, 100),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-11.mp4', 'live_del_2019-05-11', '2019-05-11', '17:00:00', '19:30:00', 150 , 200, 100),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-12.mp4', 'live_del_2019-05-12', '2019-05-12', '17:00:00', '19:30:00', 150 , 200, 100),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-13.mp4', 'live_del_2019-05-13', '2019-05-13', '17:00:00', '19:30:00', 150 , 200, 100),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-14.mp4', 'live_del_2019-05-14', '2019-05-14', '17:00:00', '19:30:00', 150 , 200, 100),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-15.mp4', 'live_del_2019-05-15', '2019-05-15', '17:00:00', '19:30:00', 150 , 200, 100),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-16.mp4', 'live_del_2019-05-16', '2019-05-16', '17:00:00', '19:30:00', 150 , 200, 100),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-02.mp4', 'live_del_2019-05-02', '2019-05-02', '17:00:00', '19:30:00', 150 , 200, 100),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-10.mp4', 'live_del_2019-05-10', '2019-05-10', '17:00:00', '19:30:00', 150 , 200, 100),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-11.mp4', 'live_del_2019-05-11', '2019-05-11', '17:00:00', '19:30:00', 150 , 200, 100);

INSERT INTO Video (URL,Titolo, Data, Durata, Numero_Visualizzazioni, Fonte) VALUES
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-10.mp4', 'video_live_del_2019-05-10', '2019-05-10', 150 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-10.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-11.mp4', 'video_live_del_2019-05-11', '2019-05-11', 150 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-11.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-12.mp4', 'video_live_del_2019-05-12', '2019-05-12', 150 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-12.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-13.mp4', 'video_live_del_2019-05-13', '2019-05-13', 150 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-13.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-14.mp4', 'video_live_del_2019-05-14', '2019-05-14', 150 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-14.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-15.mp4', 'video_live_del_2019-05-15', '2019-05-15', 150 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-15.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-16.mp4', 'video_live_del_2019-05-16', '2019-05-16', 150 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-16.mp4'),
    ('twitch.com/lifestylevloggers/video/video_live_del_2019-05-02.mp4', 'video_live_del_2019-05-02', '2019-05-02', 150 , 100, 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-02.mp4'),
    ('twitch.com/lifestylevloggers/video/video_live_del_2019-05-10.mp4', 'video_live_del_2019-05-10', '2019-05-10', 150 , 100, 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-10.mp4');

INSERT INTO Clip (URL, Titolo, Data, Durata, Numero_Visualizzazioni) VALUES
    ('twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-10.mp4','clip_01_live_del_2019-05-10','2019-05-10', 20 , 90),
    ('twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-12.mp4','clip_01_live_del_2019-05-12','2019-05-12', 20 , 80),
    ('twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-14.mp4','clip_01_live_del_2019-05-14','2019-05-14', 20 , 70),
    ('twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-15.mp4','clip_01_live_del_2019-05-15','2019-05-15', 20 , 70),
    ('twitch.com/artandmusicstreaming/clip/clip_01_live_del_2019-05-16.mp4','clip_01_live_del_2019-05-16','2019-05-16', 20 , 30),
    ('twitch.com/lifestylevloggers/clip/clip_01_live_del_2019-05-02.mp4','clip_01_live_del_2019-05-02','2019-05-02', 20 , 10),
    ('twitch.com/lifestylevloggers/clip/clip_01_live_del_2019-05-10.mp4','clip_01_live_del_2019-05-10','2019-05-10', 20 , 30);

INSERT INTO Tag_Live_Passate (URL, Tag) VALUES
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-10.mp4', 'Grand_Theft_Auto'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-11.mp4', 'Minecraft'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-12.mp4', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-13.mp4', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-14.mp4', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-14.mp4', 'drop_attivi'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-15.mp4', 'Minecraft'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-16.mp4', 'drop_attivi'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-16.mp4', 'Minecraft'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-02.mp4', 'Call_of_Duty'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-02.mp4', 'italiano'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-02.mp4', 'drop_attivi'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-10.mp4', 'Fortnite'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-11.mp4', 'Minecraft');

INSERT INTO Tag_Video (URL, Tag) VALUES
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-10.mp4', 'Grand_Theft_Auto'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-11.mp4', 'Minecraft'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-12.mp4', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-13.mp4', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-14.mp4', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-14.mp4', 'drop_attivi'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-15.mp4', 'Minecraft'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-16.mp4', 'drop_attivi'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-16.mp4', 'Minecraft'),
    ('twitch.com/lifestylevloggers/video/video_live_del_2019-05-02.mp4', 'Call_of_Duty'),
    ('twitch.com/lifestylevloggers/video/video_live_del_2019-05-02.mp4', 'italiano'),
    ('twitch.com/lifestylevloggers/video/video_live_del_2019-05-02.mp4', 'drop_attivi'),
    ('twitch.com/lifestylevloggers/video/video_live_del_2019-05-10.mp4', 'Fortnite');

INSERT INTO Privilegi (Tipo) VALUES
    ('no_publicita'),
    ('emoticons'),
    ('icona_canale'),
    ('chat_solo_abbonati'),
    ('co_op');

INSERT INTO Registrato (Username, Password, Nome, Cognome, Data_di_nascita, Email, Cellulare, Minuti_Trasmessi, Media_Spettatori_Simultanei, Portafoglio_di_Bit, URL_Canale) VALUES
    ('esportsgamingchannel', '7b5f9d2r', 'Mario', 'Rossi', '2000-02-05', 'esportsgamingchannel@gmail.com', '+393774441550', 0, 200, 0, 'twitch.com/esportsgamingchannel'),
    ('artandmusicstreaming', 't8m4j6q1', 'Luca', 'Verdi', '2000-01-05', 'artandmusicstreaming@gmail.com', '+393774441551', 1050, 200, 30, 'twitch.com/artandmusicstreaming'),
    ('lifestylevloggers', 'f5h2r6s7', 'Veronica', 'Neri', '2001-10-07', 'lifestylevloggers@gmail.com', '+393774441552', 0, 200, 5, 'twitch.com/lifestylevloggers'),
    ('gamingnewsupdates', 'b9k1j4p7', 'Guido', 'Lavespa', '2003-12-20', 'gamingnewsupdates@gmail.com', '+393774441553', 450, 200, 25, 'twitch.com/gamingnewsupdates'),
    ('cookingandrecipeslive', 's6p2t8b1', 'Gustavo', 'Lapizza', '2003-03-24', 'cookingandrecipeslive@gmail.com', '+393774441554', 0, 200, 95, 'twitch.com/cookingandrecipeslive'),
    ('ShadowLurker', '2d6f7s8a', 'Maria', 'Rossi', '2003-03-24', 'ShadowLurker@gmail.com', '+393774441754', 0, 0, 0, 'twitch.com/ShadowLurker'),
    ('MysticEnigma', 'c9h1j7m5', 'Paolo', 'Bianchi', '2003-03-24', 'MysticEnigma@gmail.com', '+393774441654', 0, 0, 0, 'twitch.com/MysticEnigma'),
    ('CrimsonPhoenix', 'w3e4r6u8', 'Chiara', 'Neri', '2003-03-24', 'CrimsonPhoenix@gmail.com', '+393574441554', 0, 0, 0, 'twitch.com/CrimsonPhoenix'),
    ('DreamWeaverX', '5t6y2u4i', 'Marco', 'Verdi', '2003-03-24', 'DreamWeaverX@gmail.com', '+393774431554', 0, 0, 0, 'twitch.com/DreamWeaverX'),
    ('NightFuryX', '1h3j5b7n', 'Federica', 'Rosso', '2003-03-24', 'NightFuryX@gmail.com', '+393774441552', 0, 0, 0, 'twitch.com/NightFuryX'),
    ('ArcaneNinja', 'e4r9t2u5', 'Simone', 'Grigio', '2003-03-24', 'ArcaneNinja@gmail.com', '+393774441154', 0, 0, 0, 'twitch.com/ArcaneNinja'),
    ('ElementalGoddess', '8f7d2s1g', 'Martina', 'Viola', '2003-03-24', 'ElementalGoddess@gmail.com', '+393334441554', 0, 0, 0, 'twitch.com/ElementalGoddess'),
    ('DragonSlayerX', 'b6n1m2t7', 'Luigi', 'Gialli', '2003-03-24', 'DragonSlayerX@gmail.com', '+393734441554', 0, 0, 0, 'twitch.com/DragonSlayerX'),
    ('EnchantedWarrior', 'q4e6r2t8', 'Laura', 'Marrone', '2003-03-24', 'EnchantedWarrior@gmail.com', '+393771441554', 0, 0, 0, 'twitch.com/EnchantedWarrior'),
    ('SorceressQueen', '3y7t2h9k', 'Giovanni', 'Celeste', '2003-03-24', 'SorceressQueen@gmail.com', '+393774441114', 0, 0, 0, 'twitch.com/SorceressQueen'),
    ('EternalFlameX', 'p6k8j5h2', 'Giulia', 'Arancione', '2003-03-24', 'EternalFlameX@gmail.com', '+393774441556', 0, 0, 0, 'twitch.com/EternalFlameX'),
    ('CelestialSiren', 'r9w2e1q7', 'Davide', 'Oro', '2003-03-24', 'CelestialSiren@gmail.com', '+393775541554', 0, 0, 0, 'twitch.com/CelestialSiren'),
    ('GalaxyGiver', '6y7u4t1h', 'Eleonora', 'Turchese', '2003-03-24', 'GalaxyGiver@gmail.com', '+393774381554', 0, 0, 0, 'twitch.com/GalaxyGiver'),
    ('MysticSeekerX', 's8d7f2g5', 'Andrea', 'Blu', '2003-03-24', 'MysticSeekerX@gmail.com', '+393732441554', 0, 0, 0, 'twitch.com/MysticSeekerX'),
    ('SunlightSavior', 'j4h2k7n5', 'Sofia', 'Nero', '2003-03-24', 'SunlightSavior@gmail.com', '+393774888554', 0, 0, 0, 'twitch.com/SunlightSavior'),
    ('EmeraldEmbraceX', '2p6l4k9j', 'Mattia', 'Rubino', '2003-03-24', 'EmeraldEmbraceX@gmail.com', '+393774412554', 0, 0, 0, 'twitch.com/EmeraldEmbraceX'),
    ('PhoenixRising', '8d6s7q4f', 'Elisa', 'Fucsia', '2003-03-24', 'PhoenixRising@gmail.com', '+393774441524', 0, 0, 0, 'twitch.com/PhoenixRising'),
    ('MysticalMermaid', 'u1i5o8p2', 'Roberto', 'Beige', '2003-03-24', 'MysticalMermaid@gmail.com', '+393774441222', 0, 0, 0, 'twitch.com/MysticalMermaid'),
    ('CosmicCrafterX', 'm7n8b4t1', 'Valentina', 'Azzurro', '2003-03-24', 'CosmicCrafterX@gmail.com', '+393774441332', 0, 0, 0, 'twitch.com/CosmicCrafterX'),
    ('GlowingGuard', '2h4j6k8l', 'Luca', 'Rame', '2003-03-24', 'GlowingGuard@gmail.com', '+393771141556', 0, 0, 0, 'twitch.com/GlowingGuard');


INSERT INTO Affiliate (Username, Sottoscrizioni, Bit_Ricevuti) VALUES
    ('artandmusicstreaming', 3, 20);

INSERT INTO Messaggio_Privato (Mittente, Data, Ora, Messaggio, Destinatario) VALUES
    ('esportsgamingchannel', '2019-06-02', '17:00:01', 'hai visto la live di oggi?', 'artandmusicstreaming'),
    ('artandmusicstreaming', '2019-06-02', '17:02:00', 'no, non mi piace questo sito', 'esportsgamingchannel'),
    ('esportsgamingchannel', '2019-06-02', '17:03:00', 'gia, è abbastanza losco', 'artandmusicstreaming'),
    ('artandmusicstreaming', '2019-06-02', '17:04:00', 'però il mio papà ha detto che il database è ben fatto', 'esportsgamingchannel'),
    ('esportsgamingchannel', '2019-06-02', '17:09:00', 'hey, ciao, cosa ne pensi di questo sito tu invece?', 'cookingandrecipeslive'),
    ('cookingandrecipeslive', '2019-06-02', '17:10:05', 'ciaoo, paicerè Gustavo, vuoi uscire con me?', 'lifestylevloggers'),
    ('cookingandrecipeslive', '2019-06-02', '17:10:30', 'vabbe dai, mi rispondi quando leggi... non ti metto fretta', 'lifestylevloggers'),
    ('lifestylevloggers', '2019-06-02', '20:00:20', 'ma certo tesoro, piacere Martino', 'cookingandrecipeslive');

INSERT INTO Donazione (Registrato, Affiliate, Data, Ora, Valore) VALUES
    ('cookingandrecipeslive', 'artandmusicstreaming', '2019-05-14', '17:30:10', 5),
    ('cookingandrecipeslive', 'artandmusicstreaming', '2019-05-14', '17:40:10', 5),
    ('cookingandrecipeslive', 'artandmusicstreaming', '2019-05-14', '17:50:10', 10);

INSERT INTO Follow (Registrato, Canale) VALUES
    ('cookingandrecipeslive', 'twitch.com/artandmusicstreaming'),
    ('cookingandrecipeslive', 'twitch.com/lifestylevloggers'),
    ('cookingandrecipeslive', 'twitch.com/esportsgamingchannel'),
    ('artandmusicstreaming', 'twitch.com/esportsgamingchannel'),
    ('esportsgamingchannel', 'twitch.com/artandmusicstreaming'),

    ('ShadowLurker', 'twitch.com/artandmusicstreaming'), 
    ('MysticEnigma', 'twitch.com/artandmusicstreaming'),
    ('CrimsonPhoenix', 'twitch.com/artandmusicstreaming'),
    ('DreamWeaverX', 'twitch.com/artandmusicstreaming'),
    ('NightFuryX', 'twitch.com/artandmusicstreaming'),
    ('ArcaneNinja', 'twitch.com/artandmusicstreaming'),
    ('ElementalGoddess', 'twitch.com/artandmusicstreaming'),
    ('DragonSlayerX', 'twitch.com/artandmusicstreaming'),
    ('EnchantedWarrior', 'twitch.com/artandmusicstreaming'),
    ('SorceressQueen', 'twitch.com/artandmusicstreaming'),
    ('EternalFlameX', 'twitch.com/artandmusicstreaming'),
    ('CelestialSiren', 'twitch.com/artandmusicstreaming'),
    ('GalaxyGiver', 'twitch.com/artandmusicstreaming'),
    ('MysticSeekerX', 'twitch.com/artandmusicstreaming'),
    ('SunlightSavior', 'twitch.com/artandmusicstreaming'),
    ('EmeraldEmbraceX', 'twitch.com/artandmusicstreaming'),
    ('PhoenixRising', 'twitch.com/artandmusicstreaming'),
    ('MysticalMermaid', 'twitch.com/artandmusicstreaming'),
    ('CosmicCrafterX', 'twitch.com/artandmusicstreaming'),
    ('GlowingGuard', 'twitch.com/artandmusicstreaming');


INSERT INTO Live_In_Diretta (URL, Titolo, Numero_Spettatori, Ora_Inizio, URL_Img_Copertina, Streamer) VALUES
    ('twitch.com/artandmusicstreaming/diretta.html', 'live_su_fortnite', 30, '17:00:00', 'twitch.com/artandmusicstreaming/diretta.jpg', 'artandmusicstreaming');

INSERT INTO Messaggio (Live, Username, Orario, Testo) VALUES
    ('twitch.com/artandmusicstreaming/diretta.html', 'cookingandrecipeslive', '17:00:30', 'ciaooo, che bello essere qui!!'),
    ('twitch.com/artandmusicstreaming/diretta.html', 'cookingandrecipeslive', '17:01:00', 'qualcuno mi dice come si chiama questo gioco?'),
    ('twitch.com/artandmusicstreaming/diretta.html', 'esportsgamingchannel', '17:01:30', 'trovati un lavoro vero');

INSERT INTO Offerta (Affiliate, Tipo) VALUES
    ('artandmusicstreaming', 'no_publicita'),
    ('artandmusicstreaming', 'emoticons'),
    ('artandmusicstreaming', 'icona_canale');

INSERT INTO Subscription (Registrato, Affiliate) VALUES
    ('esportsgamingchannel', 'artandmusicstreaming'),
    ('cookingandrecipeslive', 'artandmusicstreaming');

INSERT INTO Tag_LiveInDiretta (URL, Tag) VALUES
    ('twitch.com/artandmusicstreaming/diretta.html', 'Fortnite'),
    ('twitch.com/artandmusicstreaming/diretta.html', 'drop_attivi');

INSERT INTO Spettatore (Registrato, Live_In_Diretta) VALUES
    ('cookingandrecipeslive', 'twitch.com/artandmusicstreaming/diretta.html'),
    ('esportsgamingchannel', 'twitch.com/artandmusicstreaming/diretta.html'),
    ('CosmicCrafterX', 'twitch.com/artandmusicstreaming/diretta.html');

--------------- QUERY DI ESEMPIO ---------------


--Mostrare l'username e il numero di follower degli spettatori della live in diretta effettuata da 'artandmusicstreaming'
select Registrato.Username, Canale.Numero_di_follow
from Registrato join Canale on Registrato.URL_canale = Canale.URL
join Spettatore on Registrato.Username = Spettatore.Registrato
join Live_In_Diretta on Spettatore.Live_in_diretta = Live_In_Diretta.URL
where Live_in_diretta.Streamer = 'artandmusicstreaming';


--Update di una chiave primaria(Modifica il valore dell'attributo URL_Account della tabella Social associata al Registrato 'esportsgamingchannel')
update Social
	set URL_Account = 'www.facebook.com/esportsgamingchannel_official'
	where Nome = 'facebook' and Canale in (
	select Canale.URL from Canale join Registrato on Canale.URL = Registrato. URL_Canale
	where Registrato.Username = 'esportsgamingchannel');


--Modificare la categoria(chiave esterna) di una live programmata
	update Live_programmate
	set Categoria = 'Just_Chat'
	where Categoria = 'Fortnite';


--Contare il numero di live di un determinato registrato
select Registrato.Username, count(*)
from Registrato join Canale on Registrato.URL_Canale = Canale.URL
join Multimedia on Canale.URL = Multimedia.Canale join Live_Passate on Multimedia.URL = Live_Passate.URL
where Registrato.Username = 'artandmusicstreaming'
group by Registrato.Username;
--    |
--RIDONDANZA
--    |
select Numero_di_live
from Canale join Registrato on URL = URL_Canale
where Username = 'artandmusicstreaming';


--Mostrare la classifica dei registrati più seguiti
select Registrato.Username, Canale.Numero_di_Follow
from Registrato join Canale on Registrato.URL_Canale = Canale.URL
where Canale.Numero_di_Follow > 0
order by Canale.Numero_di_follow desc;
--    |
--RIDONDANZA
--    |
select Registrato.Username, count(*) as NumeroFollower
from Follow join Canale on Follow.Canale = Canale.URL join Registrato on Canale.URL = Registrato.URL_Canale
group by Registrato.Username
order by NumeroFollower desc;


--Eliminare un registrato affiliate per verificare la cancellazione a cascata:
--	Registrato -> Affiliate,
--	Registrato -> Canale -> Multimedia -> Live_In_Diretta -> Spettatore
delete from Registrato where Username = 'artandmusicstreaming';


