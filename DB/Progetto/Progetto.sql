BEGIN TRANSACTION;
DROP TABLE IF EXISTS Canale CASCADE;
DROP TABLE IF EXISTS Affiliate CASCADE;
DROP TABLE IF EXISTS Categoria CASCADE;
DROP TABLE IF EXISTS Chat_Privata CASCADE;
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
DROP TABLE IF EXISTS Tipo CASCADE;
DROP TABLE IF EXISTS Registrato CASCADE;
DROP TABLE IF EXISTS Social CASCADE;
DROP TABLE IF EXISTS Subscription CASCADE;
DROP TABLE IF EXISTS Tag_Live_Passate CASCADE;
DROP TABLE IF EXISTS Tag_Video CASCADE;
DROP TABLE IF EXISTS Tag CASCADE;
DROP TABLE IF EXISTS Utente CASCADE;
DROP TABLE IF EXISTS Video CASCADE;
CREATE TABLE Canale (
    URL VARCHAR(255),
    URL_Trailer VARCHAR(255),
    Descrizione TEXT,
    Numero_di_Live INTEGER,
    Numero_di_Video INTEGER,
    Numero_di_Clip INTEGER,
    URL_Img_di_Copertina VARCHAR(255),
    URL_Img_Profilo VARCHAR(255),
    URL_Ultima_Live VARCHAR(255),
	CONSTRAINT canale_pk PRIMARY KEY (URL)
);
CREATE TABLE Categoria (
    Nome VARCHAR(20),
    URL_img VARCHAR(255),
	CONSTRAINT Categoria_PK PRIMARY KEY(Nome)
);
CREATE TABLE Live_Programmate (
    Canale VARCHAR(255),
    Data DATE,
    Ora_Inizio TIME,
    Ora_Fine TIME,
    Titolo VARCHAR(255),
    Categoria VARCHAR(255),
	CONSTRAINT live_programmate_canale_PK PRIMARY KEY (Canale, Data, Ora_Inizio),
    CONSTRAINT live_programmate_canale_fk FOREIGN KEY (Canale) REFERENCES Canale(URL)  on delete cascade,
    CONSTRAINT live_programmate_categoria_fk FOREIGN KEY (Categoria) REFERENCES Categoria(Nome) on delete cascade
);
CREATE TABLE Social (
    URL_Account VARCHAR(255),
    Nome VARCHAR(255),
    Canale VARCHAR(255),
	CONSTRAINT social_canale_pk PRIMARY KEY (URL_Account),
    CONSTRAINT social_canale_fk FOREIGN KEY (Canale) REFERENCES Canale(URL) on delete cascade
);
CREATE TABLE Multimedia (
    URL VARCHAR(255),
    URL_Img_Copertina VARCHAR(255),
    Canale VARCHAR(255),
    Categoria VARCHAR(20),
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
    Titolo VARCHAR(255),
    Data DATE,
    Ora_Inizio TIME,
    Ora_Fine TIME,
    Durata INTEGER,
    Numero_Spettatori INTEGER,
    Media_Spettatori INTEGER,
	CONSTRAINT live_passate_pk PRIMARY KEY (URL),
	CONSTRAINT live_passate_fk FOREIGN KEY (URL) REFERENCES Multimedia(URL) on delete cascade
);
CREATE TABLE Video (
    URL VARCHAR(255),
    Titolo VARCHAR(255),
    Data DATE,
    Durata INTEGER,
    Numero_Visualizzazioni INTEGER,
    Fonte VARCHAR(255),
	CONSTRAINT video_multimedia_pk PRIMARY KEY (URL),
    CONSTRAINT video_multimedia_fk FOREIGN KEY (Fonte) REFERENCES Live_Passate(URL) on delete cascade,
    CONSTRAINT video_url_fk FOREIGN KEY (URL) REFERENCES Multimedia(URL) on delete cascade,
	CONSTRAINT unique_video_fonte UNIQUE (Fonte)
);
CREATE TABLE Clip (
    URL VARCHAR(255),
    Titolo VARCHAR(255),
    Data DATE,
    Durata INTEGER,
    Numero_Visualizzazioni INTEGER,
	CONSTRAINT clip_pk PRIMARY KEY (URL),
    CONSTRAINT clip_multimedia_fk FOREIGN KEY (URL) REFERENCES Multimedia(URL) on delete cascade
);
CREATE TABLE Tag_Live_Passate (
    URL VARCHAR(255),
    Tag VARCHAR(255),
	CONSTRAINT tag_live_passate_pk PRIMARY KEY (URL),
    CONSTRAINT tag_live_passate_fk FOREIGN KEY (URL) REFERENCES Live_Passate(URL) on delete cascade,
    CONSTRAINT tag_nome_fk2 FOREIGN KEY (Tag) REFERENCES Tag(Nome) on delete cascade
);
CREATE TABLE Tag_Video (
    URL VARCHAR(255),
    Tag VARCHAR(255),
	CONSTRAINT tag_video_pk PRIMARY KEY (URL),
    CONSTRAINT tag_video_fk FOREIGN KEY (URL) REFERENCES Multimedia(URL) on delete cascade,
    CONSTRAINT tag_nome_fk3 FOREIGN KEY (Tag) REFERENCES Tag(Nome) on delete cascade
);
CREATE TABLE Privilegi (
    Tipo VARCHAR(20),
	CONSTRAINT privilegi_pk PRIMARY KEY (Tipo)
);
CREATE TABLE Utente (
    IdUser SERIAL,
    Live_In_Diretta VARCHAR(255),
	CONSTRAINT utente_pk PRIMARY KEY (IdUser));

CREATE TABLE Registrato (
    Username VARCHAR(20),
    Password VARCHAR(255),
    Nome VARCHAR(255),
    Cognome VARCHAR(255),
    Data_di_nascita DATE,
    Email VARCHAR(255),
    Cellulare VARCHAR(255),
    Minuti_Trasmessi INTEGER,
    Media_Spettatori_Simultanei INTEGER,
    Portafoglio_di_Bit INTEGER,
    IdUser SERIAL,
    URL_Canale VARCHAR(255),
	CONSTRAINT registrato_utente_pk PRIMARY KEY (Username),
	CONSTRAINT unique_registrato_iduser UNIQUE (IdUser),
	CONSTRAINT unique_registrato_url_canale UNIQUE (URL_Canale)
);
CREATE TABLE Affiliate (
    Username VARCHAR(20),
    Sottoscrizioni INTEGER,
    Bit_Ricevuti INTEGER,
	CONSTRAINT affiliate_pk PRIMARY KEY (Username),
	CONSTRAINT unique_affiliate_username UNIQUE (Username)
);
CREATE TABLE Chat_Privata (
    Mittente VARCHAR(20),
    Destinatario VARCHAR(20),
    Data DATE,
    Ora TIME,
    Messaggio TEXT,
	CONSTRAINT chat_privata_pk PRIMARY KEY (Mittente, Destinatario, Data, Ora) 
);
CREATE TABLE Donazione (
    Registrato VARCHAR(20),
    Affiliate VARCHAR(20),
    Data DATE,
    Ora TIME,
    Valore INTEGER,
	CONSTRAINT donazione_pk PRIMARY KEY (Registrato, Affiliate, Data, Ora));

CREATE TABLE Follow (
    Registrato VARCHAR(20),
    Canale VARCHAR(255),
    CONSTRAINT follow_registrato_pk PRIMARY KEY (Registrato, Canale)
);

CREATE TABLE Live_In_Diretta (
    URL VARCHAR(255),
    Numero_Spettatori INTEGER,
    Ora_Inizio TIME,
    URL_Img_Copertina VARCHAR(255),
    Streamer VARCHAR(20),
	CONSTRAINT live_in_diretta_streamer_pk PRIMARY KEY (URL),
	CONSTRAINT unique_live_in_diretta_streamer UNIQUE (Streamer)
);
CREATE TABLE Messaggio (
    Live VARCHAR(255),
    Username VARCHAR(20),
    Orario TIME,
    Testo TEXT,
	CONSTRAINT messaggio_live_pk PRIMARY KEY (Live, Username, Orario)
);
CREATE TABLE Offerta (
    Affiliate VARCHAR(20),
    Tipo VARCHAR(20),
	CONSTRAINT offerta_affiliate_PK PRIMARY KEY (Affiliate, Tipo)
);
CREATE TABLE Subscription (
    Registrato VARCHAR(20),
    Affiliate VARCHAR(20),
	CONSTRAINT subscription_registrato_pk PRIMARY KEY (Registrato, Affiliate)
);

CREATE TABLE Tag_LiveInDiretta (
    URL VARCHAR(255),
    Tag VARCHAR(255),
	CONSTRAINT tag_live_in_diretta_pk PRIMARY KEY (URL)
);

ALTER TABLE Utente ADD CONSTRAINT utente_live_in_diretta_fk FOREIGN KEY (Live_In_Diretta) REFERENCES Live_In_Diretta(URL) on delete cascade;
ALTER TABLE Registrato ADD CONSTRAINT registrato_utente_fk FOREIGN KEY (IdUser) REFERENCES Utente(IdUser) on delete cascade;
ALTER TABLE Registrato ADD CONSTRAINT registrato_canale_fk FOREIGN KEY (URL_Canale) REFERENCES Canale(URL) on delete cascade;
ALTER TABLE Affiliate ADD CONSTRAINT affiliate_registrato_fk FOREIGN KEY (Username) REFERENCES Registrato(Username) on delete cascade;
ALTER TABLE Chat_Privata ADD CONSTRAINT chat_privata_mittente_fk FOREIGN KEY (Mittente) REFERENCES Registrato(Username) on delete cascade;
ALTER TABLE Chat_Privata ADD CONSTRAINT chat_privata_destinatario_fk FOREIGN KEY (Destinatario) REFERENCES Registrato(Username) on delete cascade;
ALTER TABLE Donazione ADD CONSTRAINT donazione_affiliate_fk FOREIGN KEY (Affiliate) REFERENCES Affiliate(Username) on delete cascade;
ALTER TABLE Donazione ADD CONSTRAINT donazione_registrato_fk FOREIGN KEY (Registrato) REFERENCES Registrato(Username) on delete cascade;
ALTER TABLE Live_In_Diretta ADD CONSTRAINT  live_in_diretta_streamer_fk FOREIGN KEY (Streamer) REFERENCES Registrato(Username) on delete cascade;
ALTER TABLE Live_In_Diretta ADD CONSTRAINT live_in_diretta_multimedia_fk FOREIGN KEY (URL) REFERENCES Multimedia(URL) on delete cascade;
ALTER TABLE Messaggio ADD CONSTRAINT messaggio_live_fk FOREIGN KEY (Live) REFERENCES Live_In_Diretta(URL)  on delete cascade;
ALTER TABLE Messaggio ADD CONSTRAINT messaggio_username_fk FOREIGN KEY (Username) REFERENCES Registrato(Username) on delete cascade;
ALTER TABLE Follow ADD CONSTRAINT follow_registrato_fk FOREIGN KEY (Registrato) REFERENCES Registrato(Username) on delete cascade;
ALTER TABLE Follow ADD CONSTRAINT follow_canale_fk FOREIGN KEY (Canale) REFERENCES Canale(URL) on delete cascade;
ALTER TABLE Offerta ADD CONSTRAINT offerta_affiliate_fk FOREIGN KEY (Affiliate) REFERENCES Affiliate(Username) on delete cascade;
ALTER TABLE Offerta ADD CONSTRAINT offerta_privilegi_fk FOREIGN KEY (Tipo) REFERENCES Privilegi(Tipo) on delete cascade;
ALTER TABLE Subscription ADD CONSTRAINT subscription_registrato_fk FOREIGN KEY (Registrato) REFERENCES Registrato(Username) on delete cascade;
ALTER TABLE Subscription ADD CONSTRAINT subscription_affiliate_fk FOREIGN KEY (Affiliate) REFERENCES Affiliate(Username) on delete cascade;
ALTER TABLE Tag_LiveInDiretta ADD CONSTRAINT tag_live_in_diretta_fk FOREIGN KEY (URL) REFERENCES Live_In_Diretta(URL) on delete cascade;
ALTER TABLE Tag_LiveInDiretta ADD CONSTRAINT tag_nome_fk FOREIGN KEY (Tag) REFERENCES Tag(Nome) on delete cascade;
COMMIT TRANSACTION;
