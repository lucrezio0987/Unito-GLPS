-- Inserimento 10 Utenti
INSERT INTO Utente DEFAULT VALUES;
INSERT INTO Utente DEFAULT VALUES;
INSERT INTO Utente DEFAULT VALUES;
INSERT INTO Utente DEFAULT VALUES;
INSERT INTO Utente DEFAULT VALUES;
INSERT INTO Utente DEFAULT VALUES;
INSERT INTO Utente DEFAULT VALUES;
INSERT INTO Utente DEFAULT VALUES;
INSERT INTO Utente DEFAULT VALUES;
INSERT INTO Utente DEFAULT VALUES;


INSERT INTO Canale(URL, URL_Trailer, Descrizione, Numero_di_Live, Numero_di_Video, Numero_di_Clip, URL_Img_di_Copertina, URL_Img_Profilo, URL_Ultima_Live) VALUES
    ('twitch.com/esportsgamingchannel', 'twitch.com/esportsgamingchannel/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/esportsgamingchannel/img_copertina.jpg', 'twitch.com/esportsgamingchannel/img_profilo.img', 'twitch.com/esportsgamingchannel/vqggnt8yg5nytc89n34ycm824t14c3myy23v5t2957yt'),
    ('twitch.com/artandmusicstreaming', 'twitch.com/artandmusicstreaming/trailer.mp4', 'descrizione di prova', 7, 7, 4, 'twitch.com/artandmusicstreaming/img_copertina.jpg', 'twitch.com/artandmusicstreaming/img_profilo.img', 'twitch.com/artandmusicstreaming/vq48nt8yg5nytc89n34ycm824t14c3myy23v5t2957yt'),
    ('twitch.com/lifestylevloggers', 'twitch.com/lifestylevloggers/trailer.mp4', 'descrizione di prova', 3, 2, 2, 'twitch.com/lifestylevloggers/img_copertina.jpg', 'twitch.com/lifestylevloggers/img_profilo.img', 'twitch.com/lifestylevloggers/vq48nt8yg5nytc89n34ycm82ee14c3myy23v5t2957yt'),
    ('twitch.com/gamingnewsupdates', 'twitch.com/gamingnewsupdates/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/gamingnewsupdates/img_copertina.jpg', 'twitch.com/gamingnewsupdates/img_profilo.img', 'twitch.com/gamingnewsupdates/ww48nt8yg5nytc89n34ycm824t14c3myy23v5t2957yt'),
    ('twitch.com/cookingandrecipeslive', 'twitch.com/cookingandrecipeslive/trailer.mp4', 'descrizione di prova', 0, 0, 0, 'twitch.com/cookingandrecipeslive/img_copertina.jpg', 'twitch.com/cookingandrecipeslive/img_profilo.img', 'twitch.com/cookingandrecipeslive/vq48nt8yg5nytc89n34ycm824004c3myy23v5t2957yt');

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
    ('League_of_Legends', 'twitch.com/categorie/League_of_Legends.jpg');


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
    ('italiano'),
    ('inglese'),
    ('francese'),
    ('russo'),
    ('tedesco'),
    ('accesso_anticipato'),
    ('drop_attivi');

INSERT INTO Live_Passate (URL, Titolo, Data, Ora_Inizio, Ora_Fine, Durata, Numero_Spettatori, Media_Spettatori) VALUES
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-10.mp4', 'live_del_2019-05-10', '2019-05-10', 150 , 200, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-10.mp4'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-11.mp4', 'live_del_2019-05-11', '2019-05-11', 150 , 200, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-11.mp4'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-12.mp4', 'live_del_2019-05-12', '2019-05-12', 150 , 200, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-12.mp4'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-13.mp4', 'live_del_2019-05-13', '2019-05-13', 150 , 200, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-13.mp4'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-14.mp4', 'live_del_2019-05-14', '2019-05-14', 150 , 200, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-14.mp4'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-15.mp4', 'live_del_2019-05-15', '2019-05-15', 150 , 200, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-15.mp4'),
    ('twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-16.mp4', 'live_del_2019-05-16', '2019-05-16', 150 , 200, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-16.mp4'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-02.mp4', 'live_del_2019-05-02', '2019-05-02', 150 , 200, 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-02.mp4'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-10.mp4', 'live_del_2019-05-10', '2019-05-10', 150 , 200, 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-10.mp4'),
    ('twitch.com/lifestylevloggers/live_passate/live_del_2019-05-11.mp4', 'live_del_2019-05-11', '2019-05-11', 150 , 200, 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-11.mp4');

INSERT INTO Video (URL,Titolo, Data, Durata, Numero_Visualizzazioni, Fonte) VALUES
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-10.mp4', 'video_live_del_2019-05-10', '2019-05-10', 2.50 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-10.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-11.mp4', 'video_live_del_2019-05-11', '2019-05-11', 2.50 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-11.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-12.mp4', 'video_live_del_2019-05-12', '2019-05-12', 2.50 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-12.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-13.mp4', 'video_live_del_2019-05-13', '2019-05-13', 2.50 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-13.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-14.mp4', 'video_live_del_2019-05-14', '2019-05-14', 2.50 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-14.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-15.mp4', 'video_live_del_2019-05-15', '2019-05-15', 2.50 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-15.mp4'),
    ('twitch.com/artandmusicstreaming/video/video_live_del_2019-05-16.mp4', 'video_live_del_2019-05-16', '2019-05-16', 2.50 , 100, 'twitch.com/artandmusicstreaming/live_passate/live_del_2019-05-16.mp4'),
    ('twitch.com/lifestylevloggers/video/video_live_del_2019-05-02.mp4', 'video_live_del_2019-05-02', '2019-05-02', 2.50 , 100, 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-02.mp4'),
    ('twitch.com/lifestylevloggers/video/video_live_del_2019-05-10.mp4', 'video_live_del_2019-05-10', '2019-05-10', 2.50 , 100, 'twitch.com/lifestylevloggers/live_passate/live_del_2019-05-10.mp4');

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

INSERT INTO Registrato (Username, Password, Nome, Cognome, Data_di_nascita, Email, Cellulare, Minuti_Trasmessi, Media_Spettatori_Simultanei, Portafoglio_di_Bit, IdUser, URL_Canale) VALUES
    ('esportsgamingchannel', '!12345Abc!', 'Mario', 'Rossi', '2000-02-05', 'esportsgamingchannel@gmail.com', '+393774441550', 0, 200, 0, 1, 'twitch.com/esportsgamingchannel'),
    ('artandmusicstreaming', '!12345Abc!', 'Luca', 'Verdi', '2000-01-05', 'artandmusicstreaming@gmail.com', '+393774441551', 1050, 200, 30, 2, 'twitch.com/artandmusicstreaming'),
    ('lifestylevloggers', '!12345Abc!', 'Carlo', 'Neri', '2001-10-07', 'lifestylevloggers@gmail.com', '+393774441552', 0, 200, 5, 3, 'twitch.com/lifestylevloggers'),
    ('gamingnewsupdates', '!12345Abc!', 'Guido', 'Lavespa', '2003-12-20', 'gamingnewsupdates@gmail.com', '+393774441553', 450, 200, 25, 4, 'twitch.com/gamingnewsupdates'),
    ('cookingandrecipeslive', '!12345Abc!', 'Gustavo', 'Lapizza', '2003-03-24', 'cookingandrecipeslive@gmail.com', '+393774441554', 0, 200, 55, 5, 'twitch.com/cookingandrecipeslive');

INSERT INTO Affiliate (Username, Sottoscrizioni, Bit_Ricevuti) VALUES
    ('artandmusicstreaming', '_____', 20),
    ('lifestylevloggers', '_____', 20),

INSERT INTO Messaggio_Privato (Mittente, Data, Ora, Messaggio, Destinatario) VALUES
INSERT INTO Donazione (Registrato, Affiliate, Data, Ora, Valore) VALUES
INSERT INTO Follow (Registrato, Canale) VALUES
INSERT INTO Live_In_Diretta (URL, Numero_Spettatori, Ora_Inizio, URL_Img_Copertina, Streamer) VALUES
INSERT INTO Messaggio (Live, Username, Orario, Testo) VALUES
INSERT INTO Offerta (Affiliate, Tipo) VALUES
INSERT INTO Subscription (Registrato, Affiliate) VALUES
INSERT INTO Tag_LiveInDiretta (URL, Tag) VALUES
INSERT INTO Spettatore (Utente, Live_In_Diretta) VALUES

