-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 11, 2026 at 01:30 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `iznajmljivanje_konzola`
--

-- --------------------------------------------------------

--
-- Table structure for table `fizickolice`
--

CREATE TABLE `fizickolice` (
  `idKlijent` int(11) NOT NULL,
  `ime` varchar(100) NOT NULL,
  `prezime` varchar(100) NOT NULL,
  `JMBG` char(13) NOT NULL,
  `brojLicneKarte` varchar(20) NOT NULL
) ;

--
-- Dumping data for table `fizickolice`
--

INSERT INTO `fizickolice` (`idKlijent`, `ime`, `prezime`, `JMBG`, `brojLicneKarte`) VALUES
(14, 'Stefanovic', 'Matija', '1002005076026', '1111111111');

-- --------------------------------------------------------

--
-- Table structure for table `klijent`
--

CREATE TABLE `klijent` (
  `idKlijent` int(11) NOT NULL,
  `adresa` varchar(200) NOT NULL,
  `telefon` varchar(50) NOT NULL,
  `email` varchar(150) NOT NULL
) ;

--
-- Dumping data for table `klijent`
--

INSERT INTO `klijent` (`idKlijent`, `adresa`, `telefon`, `email`) VALUES
(1, 'Milosa Obilica 24', '0211599730', 'izmenjen@gmail.comm'),
(10, 'Milaaosa Obilica 24', '0211599730', 'test@gmail.com'),
(14, 'Milosa Obrenovica 32', '0611599730', 'matija@gmail.com'),
(15, 'Adresa 123', '01101010', 'testemails@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `oprema`
--

CREATE TABLE `oprema` (
  `idOprema` int(11) NOT NULL,
  `naziv` varchar(200) NOT NULL,
  `serijskiBroj` varchar(100) DEFAULT NULL,
  `opis` text DEFAULT NULL,
  `cenaPoDanu` double NOT NULL DEFAULT 0,
  `kolicinaDostupna` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `oprema`
--

INSERT INTO `oprema` (`idOprema`, `naziv`, `serijskiBroj`, `opis`, `cenaPoDanu`, `kolicinaDostupna`) VALUES
(2, 'Playstation 5', '15', '', 500, 15),
(3, 'Xbox One X', '12221', '', 330, 25),
(4, 'Nintendo Switch', '12125661', '', 370, 12),
(5, 'Steam Deck', '99001231', '', 440, 11),
(6, 'Playstation 5 kontroler', '991211465', '', 100, 20),
(7, 'Playstation 4 Pro', '00112188', '', 350, 25),
(8, 'Wii U', '00199214', '', 100, 5),
(9, 'Nintendo 3DS', '11412145', '', 700, 6);

-- --------------------------------------------------------

--
-- Table structure for table `pravnolice`
--

CREATE TABLE `pravnolice` (
  `idKlijent` int(11) NOT NULL,
  `nazivFirme` varchar(200) NOT NULL,
  `PIB` varchar(20) NOT NULL,
  `maticniBroj` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `pravnolice`
--

INSERT INTO `pravnolice` (`idKlijent`, `nazivFirme`, `PIB`, `maticniBroj`) VALUES
(1, 'Nova firma', '123456', '123456'),
(10, 'anova firma1', '121234', '121234');

-- --------------------------------------------------------

--
-- Table structure for table `prss`
--

CREATE TABLE `prss` (
  `idRadnik` int(11) NOT NULL,
  `idStrucnaSprema` int(11) NOT NULL,
  `datumSticanja` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `radnik`
--

CREATE TABLE `radnik` (
  `idRadnik` int(11) NOT NULL,
  `ime` varchar(100) NOT NULL,
  `prezime` varchar(100) NOT NULL,
  `korisnickoIme` varchar(100) NOT NULL,
  `sifra` varchar(255) NOT NULL,
  `email` varchar(150) NOT NULL
) ;

--
-- Dumping data for table `radnik`
--

INSERT INTO `radnik` (`idRadnik`, `ime`, `prezime`, `korisnickoIme`, `sifra`, `email`) VALUES
(1, 'Admin', 'Admin', 'admin', 'admin321', 'admin@konzole.rs'),
(2, 'Rajković', 'Boban', 'boban', '123', 'boban@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `stavkaugovora`
--

CREATE TABLE `stavkaugovora` (
  `idUgovor` int(11) NOT NULL,
  `rb` int(11) NOT NULL,
  `kolicina` int(11) NOT NULL DEFAULT 1,
  `iznos` double NOT NULL DEFAULT 0,
  `datumOd` date DEFAULT NULL,
  `datumDo` date DEFAULT NULL,
  `idOprema` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `stavkaugovora`
--

INSERT INTO `stavkaugovora` (`idUgovor`, `rb`, `kolicina`, `iznos`, `datumOd`, `datumDo`, `idOprema`) VALUES
(7, 1, 12, 12000, NULL, NULL, 2),
(7, 2, 12, 11880, NULL, NULL, 3),
(8, 1, 3, 1500, NULL, NULL, 2),
(8, 2, 2, 1400, NULL, NULL, 9),
(9, 1, 11, 16500, NULL, NULL, 2),
(10, 1, 2, 740, NULL, NULL, 4),
(11, 1, 1, 700, NULL, NULL, 9);

-- --------------------------------------------------------

--
-- Table structure for table `strucnasprema`
--

CREATE TABLE `strucnasprema` (
  `idStrucnaSprema` int(11) NOT NULL,
  `nazivSpreme` varchar(150) NOT NULL,
  `stepen` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `strucnasprema`
--

INSERT INTO `strucnasprema` (`idStrucnaSprema`, `nazivSpreme`, `stepen`) VALUES
(1, 'Srednja skola', 'IV stepen'),
(2, 'Visoka stručna sprema', 'VII stepen'),
(3, 'Master', 'VII stepen');

-- --------------------------------------------------------

--
-- Table structure for table `ugovor`
--

CREATE TABLE `ugovor` (
  `idUgovor` int(11) NOT NULL,
  `datumIzdavanja` date NOT NULL,
  `popust` int(11) NOT NULL DEFAULT 0,
  `datumVracanjaPlaniran` date NOT NULL,
  `datumVracanjaStvarni` date DEFAULT NULL,
  `ukupanIznos` double NOT NULL DEFAULT 0,
  `status` varchar(20) NOT NULL DEFAULT 'Aktivan',
  `idRadnik` int(11) NOT NULL,
  `idKlijent` int(11) NOT NULL
) ;

--
-- Dumping data for table `ugovor`
--

INSERT INTO `ugovor` (`idUgovor`, `datumIzdavanja`, `popust`, `datumVracanjaPlaniran`, `datumVracanjaStvarni`, `ukupanIznos`, `status`, `idRadnik`, `idKlijent`) VALUES
(7, '2026-03-08', 0, '2026-03-11', NULL, 23880, 'Aktivan', 1, 1),
(8, '2026-03-08', 0, '2026-03-09', NULL, 2900, 'Aktivan', 1, 10),
(9, '2026-03-11', 15, '2026-03-14', NULL, 14025, 'Aktivan', 1, 10),
(10, '2026-03-09', 0, '2026-03-09', '2026-03-11', 740, 'Zavrsen', 1, 1),
(11, '2026-03-09', 25, '2026-03-09', NULL, 525, 'Aktivan', 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `fizickolice`
--
ALTER TABLE `fizickolice`
  ADD PRIMARY KEY (`idKlijent`),
  ADD UNIQUE KEY `JMBG` (`JMBG`);

--
-- Indexes for table `klijent`
--
ALTER TABLE `klijent`
  ADD PRIMARY KEY (`idKlijent`);

--
-- Indexes for table `oprema`
--
ALTER TABLE `oprema`
  ADD PRIMARY KEY (`idOprema`);

--
-- Indexes for table `pravnolice`
--
ALTER TABLE `pravnolice`
  ADD PRIMARY KEY (`idKlijent`),
  ADD UNIQUE KEY `PIB` (`PIB`),
  ADD UNIQUE KEY `maticniBroj` (`maticniBroj`);

--
-- Indexes for table `prss`
--
ALTER TABLE `prss`
  ADD PRIMARY KEY (`idRadnik`,`idStrucnaSprema`),
  ADD KEY `fk_prss_ss` (`idStrucnaSprema`);

--
-- Indexes for table `radnik`
--
ALTER TABLE `radnik`
  ADD PRIMARY KEY (`idRadnik`),
  ADD UNIQUE KEY `korisnickoIme` (`korisnickoIme`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `stavkaugovora`
--
ALTER TABLE `stavkaugovora`
  ADD PRIMARY KEY (`idUgovor`,`rb`),
  ADD KEY `fk_su_oprema` (`idOprema`);

--
-- Indexes for table `strucnasprema`
--
ALTER TABLE `strucnasprema`
  ADD PRIMARY KEY (`idStrucnaSprema`),
  ADD UNIQUE KEY `nazivSpreme` (`nazivSpreme`);

--
-- Indexes for table `ugovor`
--
ALTER TABLE `ugovor`
  ADD PRIMARY KEY (`idUgovor`),
  ADD KEY `fk_ugovor_radnik` (`idRadnik`),
  ADD KEY `fk_ugovor_klijent` (`idKlijent`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `klijent`
--
ALTER TABLE `klijent`
  MODIFY `idKlijent` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `oprema`
--
ALTER TABLE `oprema`
  MODIFY `idOprema` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `radnik`
--
ALTER TABLE `radnik`
  MODIFY `idRadnik` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `strucnasprema`
--
ALTER TABLE `strucnasprema`
  MODIFY `idStrucnaSprema` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `ugovor`
--
ALTER TABLE `ugovor`
  MODIFY `idUgovor` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `fizickolice`
--
ALTER TABLE `fizickolice`
  ADD CONSTRAINT `fk_fizicko_klijent` FOREIGN KEY (`idKlijent`) REFERENCES `klijent` (`idKlijent`);

--
-- Constraints for table `pravnolice`
--
ALTER TABLE `pravnolice`
  ADD CONSTRAINT `fk_pravno_klijent` FOREIGN KEY (`idKlijent`) REFERENCES `klijent` (`idKlijent`);

--
-- Constraints for table `prss`
--
ALTER TABLE `prss`
  ADD CONSTRAINT `fk_prss_radnik` FOREIGN KEY (`idRadnik`) REFERENCES `radnik` (`idRadnik`),
  ADD CONSTRAINT `fk_prss_ss` FOREIGN KEY (`idStrucnaSprema`) REFERENCES `strucnasprema` (`idStrucnaSprema`);

--
-- Constraints for table `stavkaugovora`
--
ALTER TABLE `stavkaugovora`
  ADD CONSTRAINT `fk_su_oprema` FOREIGN KEY (`idOprema`) REFERENCES `oprema` (`idOprema`),
  ADD CONSTRAINT `fk_su_ugovor` FOREIGN KEY (`idUgovor`) REFERENCES `ugovor` (`idUgovor`) ON DELETE CASCADE;

--
-- Constraints for table `ugovor`
--
ALTER TABLE `ugovor`
  ADD CONSTRAINT `fk_ugovor_klijent` FOREIGN KEY (`idKlijent`) REFERENCES `klijent` (`idKlijent`),
  ADD CONSTRAINT `fk_ugovor_radnik` FOREIGN KEY (`idRadnik`) REFERENCES `radnik` (`idRadnik`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
