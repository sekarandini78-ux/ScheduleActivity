-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 28, 2026 at 02:22 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_chronosys`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_kegiatan`
--

CREATE TABLE `tb_kegiatan` (
  `id_kegiatan` int(11) NOT NULL,
  `id_pengguna` int(11) NOT NULL,
  `nama_kegiatan` varchar(100) NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `jam` time NOT NULL,
  `prioritas` varchar(20) NOT NULL,
  `keterangan` text DEFAULT NULL,
  `status` varchar(30) DEFAULT 'Belum Selesai'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tb_kegiatan`
--

INSERT INTO `tb_kegiatan` (`id_kegiatan`, `id_pengguna`, `nama_kegiatan`, `kategori`, `tanggal`, `jam`, `prioritas`, `keterangan`, `status`) VALUES
(1, 2, 'Proyek Akhir', 'Akademik', '2026-06-30', '09:00:00', 'Tinggi', 'Penyelesaian aplikasi Chronosys', 'Belum Selesai'),
(2, 2, 'Belajar Ujian', 'Akademik', '2026-06-26', '12:00:00', 'Tinggi', 'Materi Pemrograman Berorientasi Objek', 'Selesai'),
(3, 3, 'Rapat Organisasi', 'Organisasi', '2026-06-30', '10:00:00', 'Sedang', 'Pembagian tugas babak baru', 'Belum Selesai'),
(5, 2, 'Kerkom Projek', 'Akademik', '2026-06-24', '07:00:00', 'Tinggi', 'Kerkom Projek Pbo', 'Selesai'),
(6, 2, 'Latihan Perkusi', 'Organisasi', '2026-06-26', '15:30:14', 'Sedang', 'Latihan Rutin Perkusi', 'Selesai'),
(7, 2, 'Rapat Himfo', 'Organisasi', '2026-06-25', '15:25:01', 'Sedang', 'Rapat Proker Pendidikan', 'Selesai');

-- --------------------------------------------------------

--
-- Table structure for table `tb_pengguna`
--

CREATE TABLE `tb_pengguna` (
  `id_pengguna` int(11) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `hak_akses` enum('admin','user') NOT NULL DEFAULT 'user',
  `aktif_notifikasi` tinyint(1) DEFAULT 0,
  `waktu_pengingat` int(11) DEFAULT 15
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tb_pengguna`
--

INSERT INTO `tb_pengguna` (`id_pengguna`, `nama_lengkap`, `username`, `email`, `password`, `hak_akses`, `aktif_notifikasi`, `waktu_pengingat`) VALUES
(1, 'Dwi Sekar Andini', 'sekar', 'dwi.sekar@mail.com', 'admin123', 'admin', 0, 15),
(2, 'Adelia Dwi Arafah', 'adelia', 'adelia.dwi@mail.com\r\n', 'user123', 'user', 1, 5),
(3, 'Delphia Oktaviani', 'delphia', 'delphia.okta@mail.com', 'user123', 'user', 1, 5);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_kegiatan`
--
ALTER TABLE `tb_kegiatan`
  ADD PRIMARY KEY (`id_kegiatan`),
  ADD KEY `id_pengguna` (`id_pengguna`);

--
-- Indexes for table `tb_pengguna`
--
ALTER TABLE `tb_pengguna`
  ADD PRIMARY KEY (`id_pengguna`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_kegiatan`
--
ALTER TABLE `tb_kegiatan`
  MODIFY `id_kegiatan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `tb_pengguna`
--
ALTER TABLE `tb_pengguna`
  MODIFY `id_pengguna` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_kegiatan`
--
ALTER TABLE `tb_kegiatan`
  ADD CONSTRAINT `tb_kegiatan_ibfk_1` FOREIGN KEY (`id_pengguna`) REFERENCES `tb_pengguna` (`id_pengguna`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
