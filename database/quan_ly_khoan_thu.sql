USE quan_ly_khoan_thu;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quan_ly_khoan_thu`
--

-- --------------------------------------------------------

--
-- Các lệnh DROP TABLE IF EXISTS cho bảng `nop_tien`
--
DROP TABLE IF EXISTS `nop_tien`;

-- --------------------------------------------------------

--
-- Các lệnh DROP TABLE IF EXISTS cho bảng `khoan_thu`
--
DROP TABLE IF EXISTS `khoan_thu`;

-- --------------------------------------------------------

--
-- Các lệnh DROP TABLE IF EXISTS cho bảng `quan_he`
--
DROP TABLE IF EXISTS `quan_he`;

-- --------------------------------------------------------

--
-- Các lệnh DROP TABLE IF EXISTS cho bảng `chu_ho`
--
DROP TABLE IF EXISTS `chu_ho`;

-- --------------------------------------------------------

--
-- Các lệnh DROP TABLE IF EXISTS cho bảng `ho_khau`
--
DROP TABLE IF EXISTS `ho_khau`;

-- --------------------------------------------------------

--
-- Các lệnh DROP TABLE IF EXISTS cho bảng `nhan_khau`
--
DROP TABLE IF EXISTS `nhan_khau`;

-- --------------------------------------------------------

--
-- Các lệnh DROP TABLE IF EXISTS cho bảng `users`
--
DROP TABLE IF EXISTS `users`;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhan_khau`
--

CREATE TABLE `nhan_khau` (
  `ID` int(11) NOT NULL,
  `CMND` varchar(20) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `Ten` varchar(50) CHARACTER SET utf8 NOT NULL,
  `Tuoi` int(11) NOT NULL,
  `SDT` varchar(15) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ho_khau`
--

CREATE TABLE `ho_khau` (
  `MaHo` int(11) NOT NULL,
  `SoThanhVien` int(11) NOT NULL,
  `DiaChi` varchar(200) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chu_ho`
--

CREATE TABLE `chu_ho` (
  `MaHo` int(11) NOT NULL,
  `IDChuHo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quan_he`
--

CREATE TABLE `quan_he` (
  `MaHo` int(11) NOT NULL,
  `IDThanhVien` int(11) NOT NULL,
  `QuanHe` varchar(30) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `username` varchar(30) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `passwd` varchar(30) COLLATE utf8mb4_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khoan_thu`
--

CREATE TABLE `khoan_thu` (
  `MaKhoanThu` int(11) NOT NULL,
  `TenKhoanThu` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `SoTien` double NOT NULL,
  `LoaiKhoanThu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nop_tien`
--

CREATE TABLE `nop_tien` (
  `IDNopTien` int(11) NOT NULL,
  `MaKhoanThu` int(11) NOT NULL,
  `NgayThu` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- --------------------------------------------------------

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `nhan_khau`
--
ALTER TABLE `nhan_khau`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `ho_khau`
--
ALTER TABLE `ho_khau`
  ADD PRIMARY KEY (`MaHo`);

--
-- Chỉ mục cho bảng `chu_ho`
--
ALTER TABLE `chu_ho`
  ADD PRIMARY KEY (`MaHo`,`IDChuHo`),
  ADD KEY `chu_ho_2` (`IDChuHo`);

--
-- Chỉ mục cho bảng `quan_he`
--
ALTER TABLE `quan_he`
  ADD PRIMARY KEY (`MaHo`,`IDThanhVien`),
  ADD KEY `quan_he_2` (`IDThanhVien`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `khoan_thu`
--
ALTER TABLE `khoan_thu`
  ADD PRIMARY KEY (`MaKhoanThu`);

--
-- Chỉ mục cho bảng `nop_tien`
--
ALTER TABLE `nop_tien`
  ADD PRIMARY KEY (`IDNopTien`,`MaKhoanThu`),
  ADD KEY `nop_tien_2` (`MaKhoanThu`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `nhan_khau`
--
ALTER TABLE `nhan_khau`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT cho bảng `ho_khau`
--
ALTER TABLE `ho_khau`
  MODIFY `MaHo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `chu_ho`
--
ALTER TABLE `chu_ho`
  MODIFY `MaHo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `khoan_thu`
--
ALTER TABLE `khoan_thu`
  MODIFY `MaKhoanThu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `quan_he`
--
ALTER TABLE `quan_he`
  ADD CONSTRAINT `quan_he_1` FOREIGN KEY (`MaHo`) REFERENCES `ho_khau` (`MaHo`),
  ADD CONSTRAINT `quan_he_2` FOREIGN KEY (`IDThanhVien`) REFERENCES `nhan_khau` (`ID`);

--
-- Các ràng buộc cho bảng `chu_ho`
--
ALTER TABLE `chu_ho`
  ADD CONSTRAINT `chu_ho_1` FOREIGN KEY (`MaHo`) REFERENCES `ho_khau` (`MaHo`),
  ADD CONSTRAINT `chu_ho_2` FOREIGN KEY (`IDChuHo`) REFERENCES `nhan_khau` (`ID`);

--
-- Các ràng buộc cho bảng `nop_tien`
--
ALTER TABLE `nop_tien`
  ADD CONSTRAINT `nop_tien_1` FOREIGN KEY (`IDNopTien`) REFERENCES `nhan_khau` (`ID`),
  ADD CONSTRAINT `nop_tien_2` FOREIGN KEY (`MaKhoanThu`) REFERENCES `khoan_thu` (`MaKhoanThu`);


COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;