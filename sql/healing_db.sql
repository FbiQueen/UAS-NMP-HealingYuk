-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2025 at 12:58 PM
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
-- Database: `healing_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `favorites`
--

CREATE TABLE `favorites` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `favorites`
--

INSERT INTO `favorites` (`id`, `user_id`, `location_id`) VALUES
(10, 2, 2),
(11, 2, 1),
(15, 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE `locations` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `image_url` text DEFAULT NULL,
  `short_desc` text DEFAULT NULL,
  `full_desc` text DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `locations`
--

INSERT INTO `locations` (`id`, `name`, `image_url`, `short_desc`, `full_desc`, `category`) VALUES
(1, 'Raja Ampat', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Raja_Ampat%2C_Mutiara_Indah_di_Timur_Indonesia.jpg/1200px-Raja_Ampat%2C_Mutiara_Indah_di_Timur_Indonesia.jpg', 'Kabupaten Raja Ampat kepulauan yang indah dan menakjubkan.', 'Secara geografis, Raja Ampat terletak di kawasan segitiga karang dunia yang dikenal sebagai pusat keanekaragaman hayati laut global. Posisi strategis ini, dikombinasikan dengan kondisi oseanografi yang unik, menciptakan habitat yang ideal bagi berbagai spesies laut.\r\nPenelitian ilmiah menunjukkan bahwa Raja Ampat memiliki tingkat endemisme yang tinggi, dengan beberapa spesies yang hanya ditemukan di perairan ini. Ekosistem terumbu karang yang masih dalam kondisi pristine menjadi rujukan penting bagi upaya konservasi global.\r\nSistem pengelolaan berbasis masyarakat melalui Marine Protected Area telah berhasil menjaga kelestarian ekosistem. Zonasi yang ketat memastikan bahwa aktivitas wisata tidak mengganggu proses reproduksi dan feeding behavior dari berbagai spesies laut.\r\nKeberhasilan model konservasi Raja Ampat menjadi contoh bagaimana pariwisata berkelanjutan dapat memberikan manfaat ekonomi bagi masyarakat lokal sekaligus menjaga kelestarian alam. Ini adalah bukti nyata bahwa Indonesia memiliki kapasitas untuk menjadi pemimpin dalam eco-tourism global.', 'Natural'),
(2, 'Kopitagram', 'https://nnc-media.netralnews.com/2025/04/IMG-Netral-News-User-11993-OZ9TDFR4M2.jpg', 'Kopi enak', 'enak banget', 'Nature'),
(3, 'Caraboa billiard', 'https://carabaobilliards.com/wp-content/uploads/2023/08/Snapinsta.app_363320693_240184882285614_3498454147200424651_n_1080.jpg', 'good', 'panjang', 'Billiard'),
(4, 'The Mulia', 'https://content.themulia.com/proxy/https://cdn.prod.website-files.com/6624ff6a5db57a668993dd5e/66fd12251ac8edf84caddd72_TMB%20-%20Oasis%20Pool%20revised.webp', 'bali Luxury Suites', 'Escape to The Mulia - Nusa Dua, Bali, a luxurious all-suite haven on the serene shores of Geger Beach. Revel in panoramic ocean views, an iconic infinity pool, exquisite Balinese carvings, attentive butler service, priority dining, and complimentary gourmet offerings. Experience unparalleled elegance and comfort.', 'Hotel');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `created_at`) VALUES
(2, 'user1', 'user@example.com', '$2y$10$OouODl4Qo4kyBDjktRYrfeiUtI38xkupdP8pKlDEK3qW.D2OWzdOO', '2025-07-03 13:54:56'),
(3, 'user2', 'user2@example.com', '$2y$10$MBMj0mfJP/r195cl7.hXDeTVJbq4ax/QN8KaihiMvhuxJI5xSed0q', '2025-07-04 00:10:49');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `favorites`
--
ALTER TABLE `favorites`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `location_id` (`location_id`);

--
-- Indexes for table `locations`
--
ALTER TABLE `locations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `favorites`
--
ALTER TABLE `favorites`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `locations`
--
ALTER TABLE `locations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `favorites`
--
ALTER TABLE `favorites`
  ADD CONSTRAINT `favorites_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `favorites_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
