-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Client: 127.0.0.1
-- Généré le: Mar 28 Octobre 2014 à 11:58
-- Version du serveur: 5.5.27-log
-- Version de PHP: 5.4.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `testpersonne`
--

-- --------------------------------------------------------

--
-- Structure de la table `film`
--

CREATE TABLE IF NOT EXISTS `film` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITRE` varchar(40) NOT NULL,
  `ID_REA` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_REA` (`ID_REA`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `film`
--

INSERT INTO `film` (`ID`, `TITRE`, `ID_REA`) VALUES
(1, 'Arche perdue', 1),
(2, 'Alien', 2),
(3, 'Temple Maudit', 1),
(4, 'Blade Runner', 2),
(5, 'Alien3', 4),
(6, 'Fight Club', 4),
(7, 'Orange Mecanique', 3);

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE IF NOT EXISTS `personne` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOM` varchar(40) NOT NULL,
  `PRENOM` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `personne`
--

INSERT INTO `personne` (`ID`, `NOM`, `PRENOM`) VALUES
(1, 'Spielberg', 'Steven'),
(2, 'Scott', 'Ridley'),
(3, 'Kubrick', 'Stanley'),
(4, 'Fincher', 'David');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `film`
--
ALTER TABLE `film`
  ADD CONSTRAINT `film_ibfk_1` FOREIGN KEY (`ID_REA`) REFERENCES `personne` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;