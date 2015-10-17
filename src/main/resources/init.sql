CREATE TABLE `AM_User_State` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Login Times` int(11) DEFAULT 0,
  `Publish Times` int(11) DEFAULT 0,
  `Last Login Time` datetime,
  `Last IP` varchar(100),
  `Status` int(11) NOT NULL DEFAULT 0,
  `Role` int(11) NOT NULL DEFAULT 0,
  `Exp Level` int(11) NOT NULL DEFAULT 0,
  `Currency` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `AM_User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Real Name` varchar(100) NOT NULL,
  `Gender` int(11) NOT NULL DEFAULT 1,
  `Birthday` date DEFAULT '1980-01-01',
  `Mobile` varchar(50),
  `Email` varchar(200),
  `Grade` varchar(50),
  `Signature` text,
  `User State Id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`User State Id`) REFERENCES `AM_User_State`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;