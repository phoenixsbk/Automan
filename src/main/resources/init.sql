CREATE TABLE `AM_User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(100) NOT NULL,
  `EMail` varchar(200) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `LastLogin` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;