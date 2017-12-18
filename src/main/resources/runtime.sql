DROP TABLE IF EXISTS `AMSessionToken`;

CREATE TABLE `AMSessionToken` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `UserId` int(11) NOT NULL,
  `Username` varchar(255) NOT NULL,
  `AuthToken` varchar(100) NOT NULL,
  `SessionStartDate` datetime NOT NULL,
  `SessionEndDate` datetime NOT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_token_userid_to_user_id` FOREIGN KEY (`UserId`) REFERENCES `AMUser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;