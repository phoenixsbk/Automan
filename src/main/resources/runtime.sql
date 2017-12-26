DROP TABLE IF EXISTS `AMSessionToken`;

CREATE TABLE `AMSessionToken` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(255) NOT NULL,
  `AuthToken` varchar(100) NOT NULL,
  `SessionStartDate` datetime NOT NULL,
  `SessionEndDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;