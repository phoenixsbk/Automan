DROP TABLE IF EXISTS `AMUser`;
DROP TABLE IF EXISTS `AMUserState`;

CREATE TABLE `AMUserState` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Currency` int(11) DEFAULT NULL,
  `Level` int(11) DEFAULT NULL,
  `LastIP` varchar(255) DEFAULT NULL,
  `LastLoginTime` datetime DEFAULT NULL,
  `LoginTimes` int(11) DEFAULT NULL,
  `PublishTimes` int(11) DEFAULT NULL,
  `Role` int(11) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `AMUser` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Birthday` date DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Gender` int(11) DEFAULT NULL,
  `Grade` varchar(255) DEFAULT NULL,
  `Mobile` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `RealName` varchar(255) DEFAULT NULL,
  `Signature` longtext,
  `Username` varchar(255) DEFAULT NULL,
  `UserStateId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_userstate_id` (`UserStateId`),
  CONSTRAINT `fk_userstate_id_to_userstate` FOREIGN KEY (`UserStateId`) REFERENCES `AMUserState` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;