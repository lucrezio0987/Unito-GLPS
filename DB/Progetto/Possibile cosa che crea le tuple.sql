INSERT INTO Affiliate (Username, Sottoscrizione, Bit_Ricevuti) VALUES 
                        ('alice', 'Premium', 1000),
                        ('bob', 'Basic', 500),
                        ('carol', 'Premium', 2000),
                        ('dave', 'Basic', 750),
                        ('eve', 'Premium', 3000),
                        ('frank', 'Basic', 900),
                        ('george', 'Premium', 4000),
                        ('helen', 'Basic', 1100),
                        ('ian', 'Premium', 5000),
                        ('jane', 'Basic', 1300);

INSERT INTO Canale (URL, URL_Trailer, Descrizione, Numero_di_Live, Numero_di_Video, Numero_di_Clip, URL_Img_di_Copertina, URL_Img_Profilo, URL_Ultima_Live) VALUES 
                    ('https://www.youtube.com/channel/UCXzJvyY7pho4eV0Z4bhPbuw', 'https://www.youtube.com/watch?v=1234', 'Canale di video divertenti', 2, 50, 15, 'https://storage.googleapis.com/my-bucket/cover.jpg', 'https://storage.googleapis.com/my-bucket/profile.jpg', 'https://www.youtube.com/watch?v=abcd'),
                    ('https://www.youtube.com/channel/UCrWQTTvefdPdmB2Ekw0x5ew', 'https://www.youtube.com/watch?v=5678', 'Canale di cucina italiana', 10, 100, 30, 'https://storage.googleapis.com/my-bucket/cover2.jpg', 'https://storage.googleapis.com/my-bucket/profile2.jpg', 'https://www.youtube.com/watch?v=efgh'),
                    ('https://www.youtube.com/channel/UClFZvzFg-5UWzJx61lMxk-Q', 'https://www.youtube.com/watch?v=9101', 'Canale di sport estremi', 5, 60, 20, 'https://storage.googleapis.com/my-bucket/cover3.jpg', 'https://storage.googleapis.com/my-bucket/profile3.jpg', 'https://www.youtube.com/watch?v=ijkl'),
                    ('https://www.youtube.com/channel/UCGjnPuvQWT3qpF0jvnCaxmQ', 'https://www.youtube.com/watch?v=1212', 'Canale di tutorial di programmazione', 8, 80, 25, 'https://storage.googleapis.com/my-bucket/cover4.jpg', 'https://storage.googleapis.com/my-bucket/profile4.jpg', 'https://www.youtube.com/watch?v=mnop'),
                    ('https://www.youtube.com/channel/UCtawCR_4DnZfMSUdFiGkREA', 'https://www.youtube.com/watch?v=3434', 'Canale di videogiochi retro', 3, 40, 10, 'https://storage.googleapis.com/my-bucket/cover5.jpg', 'https://storage.googleapis.com/my-bucket/profile5.jpg', 'https://www.youtube.com/watch?v=qrst'),
                    ('https://www.youtube.com/channel/UCVbJq9ku10xqU42FW3svk6A', 'https://www.youtube.com/watch?v=5656', 'Canale di moda e bellezza', 12, 120, 35, 'https://storage.googleapis.com/my-bucket/cover6.jpg', 'https://storage.googleapis.com/my-bucket/profile6.jpg', 'https://www.youtube.com/watch?v=uvwx'),
                    ('https://www.youtube.com/channel/UCPyk-nvYoc-JO0q3nDk-ojw', 'https://www.youtube.com/watch?v=2323', 'Canale di viaggi e avventure', 7, 70, 22, 'https://storage.googleapis.com/my-bucket/cover7.jpg', 'https://storage.googleapis.com/my-bucket/profile7.jpg', 'https://www.youtube.com/watch?v=xyz'),
                    ('https://www.youtube.com/channel/UCjpZ4qB3YckBcRrCFSH0NMw', 'https://www.youtube.com/watch?v=6767', 'Canale di recensioni di prodotti', 9, 90, 28, 'https://storage.googleapis.com/my-bucket/cover8.jpg', 'https://storage.googleapis.com/my-bucket/profile8.jpg', 'https://www.youtube.com/watch?v=abab'),
                    ('https://www.youtube.com/channel/UC7_MEFZUSx0TPpfrCoMRSRQ', 'https://www.youtube.com/watch?v=8989', 'Canale di musica indie', 6, 50, 18, 'https://storage.googleapis.com/my-bucket/cover9.jpg', 'https://storage.googleapis.com/my-bucket/profile9.jpg', 'https://www.youtube.com/watch?v=cdcd'),
                    ('https://www.youtube.com/channel/UC5kKfSZVqzJxFkRlxu3JLYg', 'https://www.youtube.com/watch?v=3434', 'Canale di arte e illustrazione', 4, 30, 12, 'https://storage.googleapis.com/my-bucket/cover10.jpg', 'https://storage.googleapis.com/my-bucket/profile10.jpg', 'https://www.youtube.com/watch?v=efef');

INSERT INTO Categoria (Nome, URL_img) VALUES 
                        ('Tecnologia', 'https://example.com/tecnologia.jpg'), 
                        ('Sport', 'https://example.com/sport.jpg'), 
                        ('Cucina', 'https://example.com/cucina.jpg'), 
                        ('Viaggi', 'https://example.com/viaggi.jpg'), 
                        ('Musica', 'https://example.com/musica.jpg'), 
                        ('Cinema', 'https://example.com/cinema.jpg'), 
                        ('Politica', 'https://example.com/politica.jpg'), 
                        ('Arte', 'https://example.com/arte.jpg'), 
                        ('Letteratura', 'https://example.com/letteratura.jpg'), 
                        ('Auto', 'https://example.com/auto.jpg');

INSERT INTO Chat_Privata (Mittente, Destinatario, Data, Ora, Messaggio)  VALUES 
                            ('Mario', 'Luigi', '2021-01-01', '15:30:00', 'Ciao Luigi, come va?'),
                            ('Luigi', 'Mario', '2021-01-01', '15:31:00', 'Tutto bene Mario, grazie!'),
                            ('Pippo', 'Topolino', '2021-01-02', '12:00:00', 'Ciao Topolino, ci vediamo oggi al parco?'),
                            ('Topolino', 'Pippo', '2021-01-02', '12:01:00', 'Certo Pippo, alle 15.00 va bene?'),
                            ('Paperino', 'Paperina', '2021-01-03', '20:30:00', 'Ciao Paperina, come stai?'),
                            ('Paperina', 'Paperino', '2021-01-03', '20:31:00', 'Tutto bene Paperino, grazie!'),
                            ('Pluto', 'Minni', '2021-01-04', '09:00:00', 'Ciao Minni, ci vediamo stasera per la cena?'),
                            ('Minni', 'Pluto', '2021-01-04', '09:01:00', 'Si Pluto, alle 20.00 va bene?'),
                            ('Gastone', 'Paperone', '2021-01-05', '14:00:00', 'Ciao Paperone, ci vediamo oggi in ufficio?'),
                            ('Paperone', 'Gastone', '2021-01-05', '14:01:00', 'Si Gastone, alle 15.00 va bene?');

INSERT INTO Clip (URL, Titolo, Data, Durata, Numero_Visualizzazioni) VALUES 
                    ('https://www.youtube.com/watch?v=dQw4w9WgXcQ', 'Rick Astley - Never Gonna Give You Up (Video Ufficiale)', '1987-07-27', '00:03:32', 148388558),
                    ('https://www.youtube.com/watch?v=otCpCn0l4Wo', 'Queen - Bohemian Rhapsody (Official Video)', '1975-10-31', '00:05:54', 1084311562),
                    ('https://www.youtube.com/watch?v=_Yhyp-_hX2s', 'Metallica: Nothing Else Matters (Official Music Video)', '1992-02-25', '00:06:26', 807644563),
                    ('https://www.youtube.com/watch?v=0m9QUoW5KnY', 'The Cranberries - Zombie (Official Music Video)', '1994-09-19', '00:05:16', 1248124128),
                    ('https://www.youtube.com/watch?v=2Vv-BfVoq4g', 'Eminem - Without Me (Official Video)', '2002-05-16', '00:05:00', 702569272),
                    ('https://www.youtube.com/watch?v=j5-yKhDd64s', 'Linkin Park - Numb (Official Video)', '2003-02-18', '00:03:07', 1354970140),
                    ('https://www.youtube.com/watch?v=QFgv5onXXC8', 'Green Day: "Boulevard Of Broken Dreams" - [Official Video]', '2004-11-25', '00:04:48', 882603379),
                    ('https://www.youtube.com/watch?v=gOMhN-hfMtY', 'Eagles - Hotel California (Lyrics)', '1976-12-08', '00:06:31', 737891290),
                    ('https://www.youtube.com/watch?v=JGwWNGJdvx8', 'Bon Jovi - Livin´ On A Prayer (Official Music Video)', '1986-11-17', '00:04:10', 634307050),
                    ('https://www.youtube.com/watch?v=XfR9iY5y94s', 'Pharrell Williams - Happy (Official Music Video)', '2013-11-21', '00:03:53', 1666094078);

INSERT INTO Donazione(Registrato, Affiliate, Data, Ora, Valore) VALUES 
                    ('Mario Rossi', 'Croce Rossa', '2021-01-01', '10:00:00', 50.0),
                    ('Claudia Bianchi', 'RedCross', '2021-02-04', '16:30:00', 25.0),
                    ('Giovanni Neri', 'AVIS', '2021-03-10', '08:45:00', 100.0),
                    ('Carla Verdi', 'ANPAS', '2021-04-15', '14:20:00', 75.0),
                    ('Luigi Gialli', 'Telethon', '2021-05-20', '11:10:00', 20.0),
                    ('Anna Marroni', 'Fondazione Umberto Veronesi', '2021-06-22', '09:15:00', 150.0),
                    ('Marco Verdi', 'CRI', '2021-07-25', '13:00:00', 30.0),
                    ('Paola Bianchi', 'MSF', '2021-08-29', '18:00:00', 70.0),
                    ('Andrea Rossi', 'ONG per la Vita', '2021-09-03', '15:40:00', 200.0),
                    ('Sara Neri', 'Save the Children', '2021-10-08', '11:30:00', 50.0);

INSERT INTO Follow (Registrato, Canale) VALUES 
                    ('Utente1', 'Canale1'),
                    ('Utente2', 'Canale1'),
                    ('Utente3', 'Canale2'),
                    ('Utente4', 'Canale3'),
                    ('Utente5', 'Canale2'),
                    ('Utente6', 'Canale3'),
                    ('Utente7', 'Canale4'),
                    ('Utente8', 'Canale5'),
                    ('Utente9', 'Canale4'),
                    ('Utente10', 'Canale5');

INSERT INTO Live_In_Diretta (URL, Numero_Spettatori, Ora_Inizio, URL_Img_Copertina, Streamer) VALUES 
                            ('www.twitch.tv/user1/1', 158, '2021-06-12 21:00:00', 'www.twitch.tv/user1/img/1', 'user1'),
                            ('www.twitch.tv/user2/1', 212, '2021-06-12 20:30:00', 'www.twitch.tv/user2/img/1', 'user2'),
                            ('www.twitch.tv/user3/1', 45, '2021-06-12 22:00:00', 'www.twitch.tv/user3/img/1', 'user3'),
                            ('www.youtube.com/user4/1', 1000, '2021-06-12 19:00:00', 'www.youtube.com/user4/img/1', 'user4'),
                            ('www.youtube.com/user5/1', 500, '2021-06-12 18:30:00', 'www.youtube.com/user5/img/1', 'user5'),
                            ('www.facebook.com/user6/1', 200, '2021-06-12 20:00:00', 'www.facebook.com/user6/img/1', 'user6'),
                            ('www.facebook.com/user7/1', 50, '2021-06-12 21:30:00', 'www.facebook.com/user7/img/1', 'user7'),
                            ('www.twitch.tv/user8/1', 75, '2021-06-12 19:30:00', 'www.twitch.tv/user8/img/1', 'user8'),
                            ('www.youtube.com/user9/1', 800, '2021-06-12 22:30:00', 'www.youtube.com/user9/img/1', 'user9'),
                            ('www.facebook.com/user10/1', 120, '2021-06-12 18:00:00', 'www.facebook.com/user10/img/1', 'user10');

INSERT INTO Live_Passate (URL, Titolo, Data, Ora_Inizio, Ora_Fine, Durata, Numero_Spettatori, Media_Spettatori) VALUES 
                        ('https://www.example.com/video1', 'Title1', '2022-01-01', '14:00:00', '14:30:00', '00:30:00', 100, 50),
                        ('https://www.example.com/video2', 'Title2', '2022-01-02', '15:00:00', '16:00:00', '01:00:00', 200, 75),
                        ('https://www.example.com/video3', 'Title3', '2022-01-03', '18:00:00', '19:00:00', '01:00:00', 150, 90),
                        ('https://www.example.com/video4', 'Title4', '2022-01-04', '20:00:00', '20:30:00', '00:30:00', 50, 25),
                        ('https://www.example.com/video5', 'Title5', '2022-01-05', '12:00:00', '13:00:00', '01:00:00', 300, 150),
                        ('https://www.example.com/video6', 'Title6', '2022-01-06', '16:00:00', '17:30:00', '01:30:00', 250, 120),
                        ('https://www.example.com/video7', 'Title7', '2022-01-07', '19:00:00', '20:00:00', '01:00:00', 175, 80),
                        ('https://www.example.com/video8', 'Title8', '2022-01-08', '21:00:00', '22:00:00', '01:00:00', 100, 50),
                        ('https://www.example.com/video9', 'Title9', '2022-01-09', '13:00:00', '14:30:00', '01:30:00', 225, 100),
                        ('https://www.example.com/video10', 'Title10', '2022-01-10', '17:00:00', '17:45:00', '00:45:00', 75, 40);

INSERT INTO Live_Programmate(Canale, Data, Ora_Inizio, Ora_Fine, Titolo, Categoria)  VALUES 
                        ("Canale1", "2021-10-01", "09:00:00", "11:00:00", "Titolo1", "Categoria1"),
                        ("Canale2", "2021-10-02", "14:00:00", "16:00:00", "Titolo2", "Categoria2"),
                        ("Canale3", "2021-10-03", "18:00:00", "20:00:00", "Titolo3", "Categoria3"),
                        ("Canale4", "2021-10-04", "12:00:00", "14:00:00", "Titolo4", "Categoria4"),
                        ("Canale5", "2021-10-05", "16:00:00", "18:00:00", "Titolo5", "Categoria5"),
                        ("Canale6", "2021-10-06", "20:00:00", "22:00:00", "Titolo6", "Categoria6"),
                        ("Canale7", "2021-10-07", "10:00:00", "12:00:00", "Titolo7", "Categoria7"),
                        ("Canale8", "2021-10-08", "15:00:00", "17:00:00", "Titolo8", "Categoria8"),
                        ("Canale9", "2021-10-09", "19:00:00", "21:00:00", "Titolo9", "Categoria9"),
                        ("Canale10", "2021-10-10", "11:00:00", "13:00:00", "Titolo10", "Categoria10");

INSERT INTO Messaggio (Live, Username, Orario, Testo) VALUES 
                        ('calcio', 'mario', '11:30:00', 'Ciao a tutti, che bello giocare a calcio!'),
                        ('calcio', 'luca', '12:15:00', 'Hai visto la partita ieri sera?'),
                        ('calcio', 'simone', '13:45:00', 'Sto cercando qualche compagno di squadra, qualcuno è interessato?'),
                        ('calcio', 'laura', '14:30:00', 'Io gioco solo per divertirmi, ma mi piacerebbe imparare qualcosa di più.'),
                        ('calcio', 'giuseppe', '15:45:00', 'Ho appena trovato il campo perfetto per le nostre partite!'),
                        ('basket', 'paolo', '11:30:00', 'Questo weekend ho partecipato ad un torneo di basket molto divertente'),
                        ('basket', 'francesca', '12:15:00', 'Mi piacerebbe organizzare degli allenamenti per migliorare le nostre prestazioni'),
                        ('basket', 'mario', '13:45:00', 'Ci vediamo stasera in palestra per giocare una partita?'),
                        ('calcio', 'laura', '14:30:00', 'Qualcuno sa dove posso comprare una nuova maglia per la mia squadra?'),
                        ('basket', 'giuseppe', '15:45:00', 'Ho appena comprato una nuova palla, ne vogliamo provare a giocare un po\');

INSERT INTO Multimedia (URL, URL_Img_Copertina, Canale, Categoria) VALUES 
                        ('https://www.example.com/video1', 'https://www.example.com/image1.jpg', 'Canale1', 'Categoria1'),
                        ('https://www.example.com/video2', 'https://www.example.com/image2.jpg', 'Canale2', 'Categoria2'),
                        ('https://www.example.com/video3', 'https://www.example.com/image3.jpg', 'Canale3', 'Categoria3'),
                        ('https://www.example.com/video4', 'https://www.example.com/image4.jpg', 'Canale4', 'Categoria4'),
                        ('https://www.example.com/video5', 'https://www.example.com/image5.jpg', 'Canale5', 'Categoria5'),
                        ('https://www.example.com/video6', 'https://www.example.com/image6.jpg', 'Canale6', 'Categoria6'),
                        ('https://www.example.com/video7', 'https://www.example.com/image7.jpg', 'Canale7', 'Categoria7'),
                        ('https://www.example.com/video8', 'https://www.example.com/image8.jpg', 'Canale8', 'Categoria8'),
                        ('https://www.example.com/video9', 'https://www.example.com/image9.jpg', 'Canale9', 'Categoria9'),
                        ('https://www.example.com/video10', 'https://www.example.com/image10.jpg', 'Canale10', 'Categoria10');

INSERT INTO Privilegi (Tipo) VALUES 
                    ('Amministratore'),
                    ('Moderatore'),
                    ('Utente'),
                    ('Superuser'),
                    ('Editor'),
                    ('Sviluppatore'),
                    ('Tester'),
                    ('Analista'),
                    ('Designer'),
                    ('Assistente');

INSERT INTO Registrato(Username, Password, Nome, Cognome, Data_di_nascita, Email, Cellulare, Minuti_Trasmessi, Media_Spettatori_Simultanei, Portafoglio_di_Bit, IdUser, URL_Canale) VALUES
                     ("mario123", "password123", "Mario", "Rossi", "1990-01-01", "mario.rossi@email.com", "3331234567", 50, 10, 20, 1, "https://www.twitch.tv/mario"),
                     ("lisa321", "password456", "Lisa", "Verdi", "1995-05-15", "lisa.verdi@email.com", "3332345678", 100, 15, 30, 2, "https://www.twitch.tv/lisa"),
                     ("luigi456", "password789", "Luigi", "Bianchi", "1985-08-20", "luigi.bianchi@email.com", "3333456789", 200, 20, 40, 3, "https://www.twitch.tv/luigi"),
                     ("giulia567", "password012", "Giulia", "Neri", "1992-11-03", "giulia.neri@email.com", "3334567890", 150, 25, 35, 4, "https://www.twitch.tv/giulia"),
                     ("marco789", "password345", "Marco", "Gialli", "1998-02-28", "marco.gialli@email.com", "3335678901", 300, 30, 50, 5, "https://www.twitch.tv/marco");

INSERT INTO Social(URL_Account, Nome, Canale) VALUES
                  ('https://www.instagram.com/john', 'John', 'Instagram'),
                  ('https://www.facebook.com/sarah', 'Sarah', 'Facebook'),
                  ('https://www.linkedin.com/in/peter', 'Peter', 'LinkedIn'),
                  ('https://www.twitter.com/anna', 'Anna', 'Twitter'),
                  ('https://www.tiktok.com/@mark', 'Mark', 'TikTok');

INSERT INTO Subscription(Registrato, Affiliate) VALUES 
                            (1,2), 
                            (3,4), 
                            (2,5), 
                            (4,6), 
                            (1,3);

INSERT INTO Tag_LiveInDiretta(URL, Tag) VALUES 
                            ('www.provadiretta1.it','sport'), 
                            ('www.provadiretta2.it','cucina'), 
                            ('www.provadiretta3.it','musica'), 
                            ('www.provadiretta4.it','moda'), 
                            ('www.provadiretta5.it','giardinaggio');

INSERT INTO Tag_LivePassate(URL, Tag) VALUES 
                            ('https://www.esempio1.com', 'sport'),
                            ('https://www.esempio2.com', 'cinema'),
                            ('https://www.esempio3.com', 'musica'),
                            ('https://www.esempio4.com', 'tecnologia'),
                            ('https://www.esempio5.com', 'moda');

INSERT INTO Tag_Video(URL, Tag) VALUES
                    ('https://www.youtube.com/watch?v=abcdefghijk', 'comedy'),
                    ('https://www.youtube.com/watch?v=lmnopqrstuv', 'music'),
                    ('https://www.youtube.com/watch?v=xyz12345678', 'cooking'),
                    ('https://www.youtube.com/watch?v=pqrstuvwxy', 'sports'),
                    ('https://www.youtube.com/watch?v=123456789ab', 'travel');

INSERT INTO Tag (Nome) VALUES 
                ('Sport'), 
                ('Musica'), 
                ('Cucina'), 
                ('Viaggi'), 
                ('Cinema');

INSERT INTO Utente(IdUser, Live_In_Diretta) VALUES 
                    (1, 'Sì'),
                    (2, 'No'),
                    (3, 'Sì'),
                    (4, 'No'),
                    (5, 'Sì');