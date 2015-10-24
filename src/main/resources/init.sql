DROP TABLE IF EXISTS `AM_Attach`;
DROP TABLE IF EXISTS `AM_Thread`;
DROP TABLE IF EXISTS `AM_Subject`;
DROP TABLE IF EXISTS `AM_User`;
DROP TABLE IF EXISTS `AM_User_State`;

CREATE TABLE `AM_User_State` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Login Times` int(11) DEFAULT 0,
  `Publish Times` int(11) DEFAULT 0,
  `Last Login Time` datetime,
  `Last IP` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `Status` int(11) NOT NULL DEFAULT 0,
  `Role` int(11) NOT NULL DEFAULT 0,
  `Exp Level` int(11) NOT NULL DEFAULT 0,
  `Currency` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `AM_User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `Password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `Real Name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `Gender` int(11) NOT NULL DEFAULT 1,
  `Birthday` date DEFAULT '1980-01-01',
  `Mobile` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `Email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `Grade` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `Signature` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `User State Id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`User State Id`) REFERENCES `AM_User_State`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `AM_Subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `Publish Date` datetime NOT NULL,
  `Update Date` datetime NOT NULL,
  `Read Time` int(11) DEFAULT 0,
  `On Top` bool NOT NULL DEFAULT FALSE,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `AM_Thread` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Subject Id` int(11) NOT NULL,
  `Content` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `Publish Date` Date NOT NULL,
  `Author Id` int(11) NOT NULL,
  `Author Hidden` bool NOT NULL DEFAULT FALSE,
  `Publish IP` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `Editted` bool NOT NULL DEFAULT FALSE,
  `Edit Time` datetime,
  `Status` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`Subject Id`) REFERENCES `AM_Subject`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`Author Id`) REFERENCES `AM_User`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `AM_Attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Thread Id` int(11) NOT NULL,
  `Title` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `Type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `Link` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`Thread Id`) REFERENCES `AM_Thread`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;