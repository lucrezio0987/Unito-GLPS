    BEGIN;

    DROP TABLE IF EXISTS Affiliate CASCADE;
    DROP TABLE IF EXISTS Canale CASCADE;
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
    DROP TABLE IF EXISTS Registrato CASCADE;
    DROP TABLE IF EXISTS Social CASCADE;
    DROP TABLE IF EXISTS Subscription CASCADE;
    DROP TABLE IF EXISTS Tag_LiveInDiretta CASCADE;
    DROP TABLE IF EXISTS Tag_LivePassate CASCADE;
    DROP TABLE IF EXISTS Tag_Video CASCADE;
    DROP TABLE IF EXISTS Tag CASCADE;
    DROP TABLE IF EXISTS Utente CASCADE;
    DROP TABLE IF EXISTS Video CASCADE;

    CREATE TABLE Affiliate (
        Username VARCHAR(255) PRIMARY KEY,
        Sottoscrizione VARCHAR(255),
        Bit_Ricevuti INTEGER
    );

    CREATE TABLE Canale (
        URL VARCHAR(255) PRIMARY KEY,
        URL_Trailer VARCHAR(255),
        Descrizione TEXT,
        Numero_di_Live INTEGER,
        Numero_di_Video INTEGER,
        Numero_di_Clip INTEGER,
        URL_Img_di_Copertina VARCHAR(255),
        URL_Img_Profilo VARCHAR(255),
        URL_Ultima_Live VARCHAR(255)
    );

    CREATE TABLE Categoria (
        Nome VARCHAR(255) PRIMARY KEY,
        URL_img VARCHAR(255)
    );

    CREATE TABLE Chat_Privata (
        Mittente VARCHAR(255),
        Destinatario VARCHAR(255),
        Data DATE,
        Ora TIME,
        Messaggio TEXT,
        PRIMARY KEY (Mittente, Destinatario, Data, Ora)
    );

    CREATE TABLE Clip (
        URL VARCHAR(255) PRIMARY KEY,
        Titolo VARCHAR(255),
        Data DATE,
        Durata TIME,
        Numero_Visualizzazioni INTEGER
    );

    CREATE TABLE Donazione (
        Registrato VARCHAR(255),
        Affiliate VARCHAR(255),
        Data DATE,
        Ora TIME,
        Valore FLOAT,
        PRIMARY KEY (Registrato, Affiliate, Data, Ora)
    );

    CREATE TABLE Follow (
        Registrato VARCHAR(255),
        Canale VARCHAR(255),
        PRIMARY KEY (Registrato, Canale)
    );

    CREATE TABLE Live_In_Diretta (
        URL VARCHAR(255) PRIMARY KEY,
        Numero_Spettatori INTEGER,
        Ora_Inizio TIME,
        URL_Img_Copertina VARCHAR(255),
        Streamer VARCHAR(255)
    );

    CREATE TABLE Live_Passate (
        URL VARCHAR(255) PRIMARY KEY,
        Titolo VARCHAR(255),
        Data DATE,
        Ora_Inizio TIME,
        Ora_Fine TIME,
        Durata TIME,
        Numero_Spettatori INTEGER,
        Media_Spettatori INTEGER
    );

    CREATE TABLE Live_Programmate (
        Canale VARCHAR(255),
        Data DATE,
        Ora_Inizio TIME,
        Ora_Fine TIME,
        Titolo VARCHAR(255),
        Categoria VARCHAR(255),
        PRIMARY KEY (Canale, Data, Ora_Inizio)
    );

    CREATE TABLE Messaggio (
        Live VARCHAR(255),
        Username VARCHAR(255),
        Orario TIME,
        Testo TEXT,
        PRIMARY KEY (Live, Username, Orario)
    );

    CREATE TABLE Multimedia (
        URL VARCHAR(255) PRIMARY KEY,
        URL_Img_Copertina VARCHAR(255),
        Canale VARCHAR(255),
        Categoria VARCHAR(255)
    );

    CREATE TABLE Offerta (
        Affiliate VARCHAR(255),
        Tipo VARCHAR(255),
        PRIMARY KEY (Affiliate, Tipo)
    );

    CREATE TABLE Privilegi (
        Tipo VARCHAR(255) PRIMARY KEY
    );

    CREATE TABLE Registrato (
        Username VARCHAR(255) PRIMARY KEY,
        Password VARCHAR(255),
        Nome VARCHAR(255),
        Cognome VARCHAR(255),
        Data_di_nascita DATE,
        Email VARCHAR(255),
        Cellulare VARCHAR(255),
        Minuti_Trasmessi INTEGER,
        Media_Spettatori_Simultanei INTEGER,
        Portafoglio_di_Bit FLOAT,
        IdUser INTEGER,
        URL_Canale VARCHAR(255)
    );

    CREATE TABLE Social (
        URL_Account VARCHAR(255) PRIMARY KEY,
        Nome VARCHAR(255),
        Canale VARCHAR(255)
    );

    CREATE TABLE Subscription (
        Registrato VARCHAR(255),
        Affiliate VARCHAR(255),
        PRIMARY KEY (Registrato, Affiliate)
    );

    CREATE TABLE Tag_LiveInDiretta (
        URL VARCHAR(255),
        Tag VARCHAR(255),
        PRIMARY KEY (URL, Tag)
    );

    CREATE TABLE Tag_LivePassate (
        URL VARCHAR(255),
        Tag VARCHAR(255),
        PRIMARY KEY (URL, Tag)
    );

    CREATE TABLE Tag_Video (
        URL VARCHAR(255),
        Tag VARCHAR(255),
        PRIMARY KEY (URL, Tag)
    );

    CREATE TABLE Tag (
        Nome VARCHAR(255) PRIMARY KEY
    );

    CREATE TABLE Utente (
        IdUser INTEGER PRIMARY KEY,
        Live_In_Diretta VARCHAR(255) REFERENCES Live_In_Diretta (URL)
    );

    CREATE TABLE Video (
        URL VARCHAR(255) PRIMARY KEY,
        Titolo VARCHAR(255),
        Data DATE,
        Durata TIME,
        Numero_Visualizzazioni INTEGER,
        Fonte VARCHAR(255)
    );

    -- Vincoli di chiave secondaria
    ALTER TABLE Affiliate		    ADD CONSTRAINT fk_Registrato_Affiliate 		FOREIGN KEY (Username) 			REFERENCES Registrato (Username) 	ON DELETE CASCADE;
    ALTER TABLE Chat_Privata	    ADD CONSTRAINT fk_Registrato_Destinatario 	FOREIGN KEY (Destinatario) 		REFERENCES Registrato (Username) 	ON DELETE CASCADE;
    ALTER TABLE Chat_Privata	    ADD CONSTRAINT fk_Registrato_Mittente 		FOREIGN KEY (Mittente) 			REFERENCES Registrato (Username) 	ON DELETE CASCADE;
    ALTER TABLE Clip			    ADD CONSTRAINT fk_Multimedia_Clip 			FOREIGN KEY (URL) 				REFERENCES Multimedia (URL) 		ON DELETE CASCADE;
    ALTER TABLE Donazione		    ADD CONSTRAINT fk_Affiliate_Donazione 		FOREIGN KEY (Affiliate) 		REFERENCES Affiliate (Username) 	ON DELETE CASCADE;
    ALTER TABLE Donazione		    ADD CONSTRAINT fk_Registrato_Donazione 		FOREIGN KEY (Registrato) 		REFERENCES Registrato (Username) 	ON DELETE CASCADE;
    ALTER TABLE Follow      	    ADD CONSTRAINT fk_Canale_Follow 			FOREIGN KEY (Canale) 			REFERENCES Canale (URL) 			ON DELETE CASCADE;
    ALTER TABLE Follow  		    ADD CONSTRAINT fk_Registrato_Follow 		FOREIGN KEY (Registrato) 		REFERENCES Registrato (Username) 	ON DELETE CASCADE;
    ALTER TABLE Live_In_Diretta     ADD CONSTRAINT fk_Registrato_Live 			FOREIGN KEY (Streamer) 			REFERENCES Registrato (Username) 	ON DELETE CASCADE;
    ALTER TABLE Live_In_Diretta     ADD CONSTRAINT fk_Multimedia_Live 			FOREIGN KEY (URL) 				REFERENCES Multimedia (URL) 		ON DELETE CASCADE;
    ALTER TABLE Live_Passate        ADD CONSTRAINT fk_Multimedia_LivePassate 	FOREIGN KEY (URL) 				REFERENCES Multimedia (URL) 		ON DELETE CASCADE;
    ALTER TABLE Live_Programmate    ADD CONSTRAINT fk_Canale_LiveProgrammate 	FOREIGN KEY (Canale) 			REFERENCES Canale (URL) 			ON DELETE CASCADE;
    ALTER TABLE Live_Programmate    ADD CONSTRAINT fk_Categoria_LiveProgrammate FOREIGN KEY (Categoria) 		REFERENCES Categoria (Nome) 		ON DELETE CASCADE;
    ALTER TABLE Messaggio 		    ADD CONSTRAINT fk_Live_Messaggio 			FOREIGN KEY (Live) 				REFERENCES Live_In_Diretta (URL) 	ON DELETE CASCADE;
    ALTER TABLE Messaggio 		    ADD CONSTRAINT fk_Registrato_Messaggio 		FOREIGN KEY (Username) 			REFERENCES Registrato (Username) 	ON DELETE CASCADE;
    ALTER TABLE Multimedia 		    ADD CONSTRAINT fk_Canale_Multimedia 		FOREIGN KEY (Canale) 			REFERENCES Canale (URL) 			ON DELETE CASCADE;
    ALTER TABLE Multimedia 		    ADD CONSTRAINT fk_Categoria_Multimedia 		FOREIGN KEY (Categoria) 		REFERENCES Categoria (Nome) 		ON DELETE CASCADE;
    ALTER TABLE Offerta 			ADD CONSTRAINT fk_Affiliate_Offerta 		FOREIGN KEY (Affiliate) 		REFERENCES Affiliate (Username) 	ON DELETE CASCADE;
    ALTER TABLE Offerta 			ADD CONSTRAINT fk_Privilegi_Offerta 		FOREIGN KEY (Tipo) 				REFERENCES Privilegi (Tipo) 		ON DELETE CASCADE;
    ALTER TABLE Registrato 		    ADD CONSTRAINT fk_Utente_Registrato 		FOREIGN KEY (IdUser) 			REFERENCES Utente (IdUser) 			ON DELETE CASCADE;
    ALTER TABLE Registrato 	 	    ADD CONSTRAINT fk_Canale_Registrato 		FOREIGN KEY (URL_Canale) 		REFERENCES Canale (URL) 			ON DELETE CASCADE;
    ALTER TABLE Social 			    ADD CONSTRAINT fk_Canale_Social 			FOREIGN KEY (Canale) 			REFERENCES Canale (URL) 			ON DELETE CASCADE;
    ALTER TABLE Subscription 	    ADD CONSTRAINT fk_Affiliate_Subscription 	FOREIGN KEY (Affiliate) 		REFERENCES Affiliate (Username) 	ON DELETE CASCADE;
    ALTER TABLE Subscription 	    ADD CONSTRAINT fk_Registrato_Subscription 	FOREIGN KEY (Registrato) 		REFERENCES Registrato (Username) 	ON DELETE CASCADE;
    ALTER TABLE Tag_LiveInDiretta   ADD CONSTRAINT fk_Tag_LiveInDiretta_Tag 	FOREIGN KEY (Tag) 				REFERENCES Tag (Nome) 				ON DELETE CASCADE;
    ALTER TABLE Tag_LiveInDiretta   ADD CONSTRAINT fk_Tag_LiveInDiretta_Live 	FOREIGN KEY (URL) 			 	REFERENCES Live_In_Diretta (URL) 	ON DELETE CASCADE;
    ALTER TABLE Tag_LivePassate 	ADD CONSTRAINT fk_Tag_LivePassate_Tag 		FOREIGN KEY (Tag) 				REFERENCES Tag (Nome) 				ON DELETE CASCADE;
    ALTER TABLE Tag_LivePassate 	ADD CONSTRAINT fk_Tag_LivePassate_Live 		FOREIGN KEY (URL) 				REFERENCES Live_Passate (URL) 		ON DELETE CASCADE;
    ALTER TABLE Tag_Video 		    ADD CONSTRAINT fk_Tag_Video_Tag 			FOREIGN KEY (Tag) 				REFERENCES Tag (Nome) 				ON DELETE CASCADE;
    ALTER TABLE Tag_Video 		    ADD CONSTRAINT fk_Tag_Video_Video 			FOREIGN KEY (URL) 				REFERENCES Video (URL) 				ON DELETE CASCADE;
    ALTER TABLE Utente 			    ADD CONSTRAINT fk_Live_In_Diretta_Utente 	FOREIGN KEY (Live_In_Diretta) 	REFERENCES Live_In_Diretta (URL) 	ON DELETE CASCADE;
    ALTER TABLE Video 			    ADD CONSTRAINT fk_Live_Passate_Video 		FOREIGN KEY (Fonte) 			REFERENCES Live_Passate (URL) 		ON DELETE CASCADE;
    ALTER TABLE Video 			    ADD CONSTRAINT fk_Multimedia_Video 			FOREIGN KEY (URL) 				REFERENCES Multimedia (URL) 		ON DELETE CASCADE;

    -- Vincoli unici
    ALTER TABLE Affiliate 		ADD CONSTRAINT unique_Username_Affiliate        UNIQUE (Username);
    ALTER TABLE Live_In_Diretta ADD CONSTRAINT unique_Streamer_Live_In_Diretta 	UNIQUE (Streamer);
    ALTER TABLE Registrato 		ADD CONSTRAINT unique_IdUser_Registrato         UNIQUE (IdUser);
    ALTER TABLE Registrato 		ADD CONSTRAINT unique_URL_Canale_Registrato     UNIQUE (URL_Canale);
    ALTER TABLE Video 			ADD CONSTRAINT unique_Fonte_Video               UNIQUE (Fonte);

    COMMIT;

