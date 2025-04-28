-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 26 avr. 2025 à 16:20
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `parc_attractions`
--

-- --------------------------------------------------------

--
-- Structure de la table `attraction`
--

DROP TABLE IF EXISTS `attraction`;
CREATE TABLE IF NOT EXISTS `attraction` (
  `id_attraction` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `prix` double NOT NULL,
  `description` text,
  `actif` tinyint(1) DEFAULT '1',
  `image_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_attraction`),
  UNIQUE KEY `nom` (`nom`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `attraction`
--

INSERT INTO `attraction` (`id_attraction`, `nom`, `type`, `prix`, `description`, `actif`, `image_path`) VALUES
(1, 'Grand Huit', 'Sensation', 25, 'Rollercoaster à sensations', 1, 'src/images/grand_huit.jpg'),
(2, 'Splash Zone', 'Aquatique', 20, 'Zone aquatique avec jets', 1, 'src/images/parc_aquatique.jpg'),
(3, 'Maison Hantée', 'Fantastique', 18, 'Parcours effrayant', 1, 'src/images/maison_hantee.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id_client` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `date_naissance` date NOT NULL,
  `type_client` enum('invite','membre','admin') DEFAULT 'invite',
  `points_fidelite` int DEFAULT '0',
  PRIMARY KEY (`id_client`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id_client`, `nom`, `email`, `mot_de_passe`, `date_naissance`, `type_client`, `points_fidelite`) VALUES
(1, 'Admin', 'admin@admin.com', 'admin', '1980-01-01', 'admin', 0),
(2, 'Alice', 'alice@example.com', 'alice123', '2005-06-12', 'membre', 5),
(3, 'Bob', 'bob@example.com', 'bob123', '1999-08-20', 'membre', 10),
(4, 'leo', 'leo@gmail.com', 'leo', '2025-04-22', 'membre', 0);

-- --------------------------------------------------------

--
-- Structure de la table `reduction`
--

DROP TABLE IF EXISTS `reduction`;
CREATE TABLE IF NOT EXISTS `reduction` (
  `id_reduction` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `type` enum('age','fidelite','promo_speciale') NOT NULL,
  `valeur` double NOT NULL,
  `condition_reduction` varchar(255) DEFAULT NULL,
  `actif` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_reduction`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reduction`
--

INSERT INTO `reduction` (`id_reduction`, `nom`, `type`, `valeur`, `condition_reduction`, `actif`) VALUES
(1, 'Réduc <12 ans', 'age', 0.2, 'age < 12', 1),
(2, 'Fidélité 10+', 'fidelite', 0.15, 'points > 10', 1),
(3, 'Promo Été', 'promo_speciale', 0.1, 'mois = 07', 1);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id_reservation` int NOT NULL AUTO_INCREMENT,
  `id_client` int DEFAULT NULL,
  `id_attraction` int DEFAULT NULL,
  `date_reservation` date NOT NULL,
  `date_creation` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `prix_total` double NOT NULL,
  `id_reduction_appliquee` int DEFAULT NULL,
  PRIMARY KEY (`id_reservation`),
  KEY `id_client` (`id_client`),
  KEY `id_attraction` (`id_attraction`),
  KEY `id_reduction_appliquee` (`id_reduction_appliquee`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id_reservation`, `id_client`, `id_attraction`, `date_reservation`, `date_creation`, `prix_total`, `id_reduction_appliquee`) VALUES
(1, 1, 1, '0000-00-00', '2025-04-26 15:48:02', 0, NULL),
(2, 2, 1, '0000-00-00', '2025-04-26 15:48:02', 0, NULL),
(3, 3, 1, '0000-00-00', '2025-04-26 15:48:02', 0, NULL),
(4, 1, 2, '0000-00-00', '2025-04-26 15:48:03', 0, NULL),
(5, 4, 2, '0000-00-00', '2025-04-26 15:48:03', 0, NULL),
(6, 2, 3, '0000-00-00', '2025-04-26 15:48:03', 0, NULL),
(7, 4, 1, '2025-04-28', '2025-04-26 16:04:37', 25, NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;