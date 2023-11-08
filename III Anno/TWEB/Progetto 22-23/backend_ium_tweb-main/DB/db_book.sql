-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Gen 02, 2023 alle 12:22
-- Versione del server: 10.4.25-MariaDB
-- Versione PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_book`
--

DELIMITER $$
--
-- Procedure
--
CREATE DEFINER=`dbhelper`@`%` PROCEDURE `getCorsiByDoc` (IN `email` VARCHAR(50))   if(email='all') THEN
    BEGIN
        select distinct (c.nome) AS Corsi
        from utente u join lezione l on u.ID = l.Docente_ID join corso c on c.ID=l.Corso_ID
        where l.Stato='Libera';
    END;
    ELSE
        BEGIN
        select distinct (c.nome)AS Corsi
        from utente u join lezione l on u.ID = l.Docente_ID join corso c on c.ID=l.Corso_ID
        where u.Email=email AND l.Stato='Libera';
        END;
    END IF$$

CREATE DEFINER=`dbhelper`@`%` PROCEDURE `getDocByCorso` (IN `corso` VARCHAR(50))   BEGIN
        select  distinct(u.Email),u.ID, u.Password, u.Nome, u.Cognome, u.Ruolo, u.Attivo, u.PF, u.Stelle
        from corso c join lezione l on c.ID = l.Corso_ID join utente u on u.ID = l.Docente_ID
        where c.nome=corso and u.Attivo='true';
    END$$

CREATE DEFINER=`dbhelper`@`%` PROCEDURE `getLezioniLibereByDocente` (IN `email` VARCHAR(50))   BEGIN
        Select l.Data,l.Ora,l.prezzo,c.nome,u.Nome,u.Cognome,u.Email,u.PF
        From lezione l join utente u on u.ID = l.Docente_ID join corso c on c.ID = l.Corso_ID
        Where u.Email=email AND l.Stato = 'Libera' ;
    END$$

CREATE DEFINER=`dbhelper`@`%` PROCEDURE `getLezioniPrenotateByUt` (IN `Staus` VARCHAR(30), IN `email` VARCHAR(30))   IF (Staus = 'Prenotata' & email IS NOT NULL) THEN
        BEGIN
            select l.Data,l.Ora,l.Stato,c2.nome,u2.Nome,u2.Cognome,l.prezzo,l.Valutazione
            from utente u join lezione l on u.ID = l.Utente_ID join corso c2 on c2.ID = l.Corso_ID join utente u2 on u2.ID = l.Docente_ID
            where  l.Stato='Prenotata' AND u.Email=@email;
        END;
        ELSE IF(Staus = 'Conclusa' && email IS NOT NULL) THEN
              BEGIN
                select l.Data,l.Ora,l.Stato,c2.nome,u2.Nome,u2.Cognome,l.prezzo,l.Valutazione
                from utente u join lezione l on u.ID = l.Utente_ID join corso c2 on c2.ID = l.Corso_ID join utente u2 on u2.ID = l.Docente_ID
                where  l.Stato='Conclusa' AND u.Email=@email;
              END;


        ELSE
            BEGIN
            select l.Data,l.Ora,l.Stato,c2.nome,u2.Nome,u2.Cognome,l.prezzo,l.Valutazione
            from utente u join lezione l on u.ID = l.Utente_ID join corso c2 on c2.ID = l.Corso_ID join utente u2 on u2.ID = l.Docente_ID
            where  l.Stato='Libera';
        END;

        END IF;

        END IF$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struttura della tabella `corso`
--

CREATE TABLE `corso` (
  `ID` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `Attivo` enum('true','false') NOT NULL DEFAULT 'true'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `corso`
--

INSERT INTO `corso` (`ID`, `nome`, `Attivo`) VALUES
(2, 'Fisica', 'true'),
(3, 'Latino', 'true'),
(4, 'Italiano', 'true'),
(7, 'Algoritmi', 'true'),
(8, 'Algebra', 'true');

-- --------------------------------------------------------

--
-- Struttura della tabella `lezione`
--

CREATE TABLE `lezione` (
  `Data` date NOT NULL,
  `Ora` enum('15:00','16:00','17:00','18:00') NOT NULL,
  `Stato` enum('Libera','Conclusa','Prenotata') NOT NULL,
  `Corso_ID` int(11) NOT NULL,
  `Docente_ID` int(11) NOT NULL,
  `prezzo` double NOT NULL DEFAULT 10,
  `Utente_ID` int(11) NOT NULL DEFAULT 0,
  `Valutazione` enum('1','2','3','4','5','0') DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `lezione`
--

INSERT INTO `lezione` (`Data`, `Ora`, `Stato`, `Corso_ID`, `Docente_ID`, `prezzo`, `Utente_ID`, `Valutazione`) VALUES
('2022-11-03', '17:00', 'Conclusa', 7, 6, 10, 4, '3'),
('2022-11-09', '16:00', 'Conclusa', 2, 5, 10, 4, '4'),
('2022-11-10', '16:00', 'Libera', 4, 5, 10, 0, '4'),
('2022-11-11', '16:00', 'Prenotata', 2, 5, 10, 4, '0'),
('2022-11-11', '17:00', 'Prenotata', 7, 6, 10, 4, '0'),
('2022-11-29', '16:00', 'Conclusa', 7, 6, 10, 4, '4'),
('2022-11-29', '17:00', 'Conclusa', 7, 6, 10, 4, '4'),
('2022-11-29', '18:00', 'Conclusa', 7, 6, 10, 4, '5'),
('2022-11-30', '16:00', 'Libera', 7, 6, 10, 0, '0'),
('2022-12-01', '17:00', 'Conclusa', 7, 6, 10, 4, '5'),
('2022-12-03', '17:00', 'Libera', 8, 6, 10, 0, '0');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `ID` int(11) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `Cognome` varchar(30) NOT NULL,
  `Ruolo` enum('utente','docente','admin') NOT NULL,
  `Attivo` enum('true','false') NOT NULL DEFAULT 'true',
  `PF` varchar(100) NOT NULL DEFAULT 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAbBB_oglMX609dUtMkvQcL3nmKuqOQmVfR2VIj0he6Q&s',
  `Stelle` float NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`ID`, `Email`, `Password`, `Nome`, `Cognome`, `Ruolo`, `Attivo`, `PF`, `Stelle`) VALUES
(1, 'olly@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'oliviu', 'gratii', 'admin', 'true', 'C:/foto.jpeg', 0),
(2, 'matt@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'matteo', 'barone', 'admin', 'true', 'https://thispersondoesnotexist.com/image', 0),
(4, 'luigi@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'luigi', 'bianchi', 'utente', 'true', 'https://thispersondoesnotexist.com/image', 0),
(5, 'albero@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'Alberto', 'Angela', 'docente', 'true', 'https://thispersondoesnotexist.com/image', 4),
(6, 'barbero@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'Alessandro', 'Barbero', 'docente', 'true', 'https://www.pngmart.com/files/21/Admin-Profile-Vector-PNG-Clipart.png', 3.66667),
(7, 'super_mario@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'Mario', 'Super', 'utente', 'false', 'https://thispersondoesnotexist.com/image', 0),
(11, 'rick@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'rick', 'ross', 'utente', 'false', 'C:/foto.jpeg', 0),
(13, 'topolino@gmail.com', '0D394A6D32A64B5DC7F966C44E3110E0', 'Topolino', 'Disney', 'docente', 'false', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAbBB_oglMX609dUtMkvQcL3nmKuqOQmVfR2VIj0he6Q&s', 0),
(15, 'fratta@gmail.com', 'A41FF7EBE5A50C73B02DC7ACF5C85AC5', 'Marco', 'Frattarola', 'utente', 'false', 'https://thispersondoesnotexist.com/image', 0),
(16, 'adfaferfaefaewf@gmail.com', 'A41FF7EBE5A50C73B02DC7ACF5C85AC5', 'Marco', 'Frattarola', 'utente', 'false', 'https://thispersondoesnotexist.com/image', 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `corso`
--
ALTER TABLE `corso`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `nome` (`nome`);

--
-- Indici per le tabelle `lezione`
--
ALTER TABLE `lezione`
  ADD PRIMARY KEY (`Data`,`Ora`,`Docente_ID`),
  ADD KEY `Docente_ID` (`Docente_ID`),
  ADD KEY `Corso_ID` (`Corso_ID`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `corso`
--
ALTER TABLE `corso`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `lezione`
--
ALTER TABLE `lezione`
  ADD CONSTRAINT `lezione_ibfk_2` FOREIGN KEY (`Docente_ID`) REFERENCES `utente` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `lezione_ibfk_3` FOREIGN KEY (`Corso_ID`) REFERENCES `corso` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
