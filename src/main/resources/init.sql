DROP TABLE IF EXISTS `AM_Attach`;
DROP TABLE IF EXISTS `AM_Subject_Threads`;
DROP TABLE IF EXISTS `AM_Thread`;
DROP TABLE IF EXISTS `AM_Subject`;





CREATE TABLE `AM_Subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `on top` bit(1) DEFAULT NULL,
  `publish date` datetime DEFAULT NULL,
  `read time` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `update date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `AM_Thread` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Author Hidden` bit(1) DEFAULT NULL,
  `Content` longtext,
  `Edit Time` datetime DEFAULT NULL,
  `Editted` bit(1) DEFAULT NULL,
  `Publish Date` datetime DEFAULT NULL,
  `Publish IP` varchar(255) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `Subject Id` int(11) DEFAULT NULL,
  `Author Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_subject_id` (`Subject Id`),
  KEY `fk_author_id` (`Author Id`),
  CONSTRAINT `fk_subject_id_tosub` FOREIGN KEY (`Subject Id`) REFERENCES `AM_Subject` (`id`),
  CONSTRAINT `fk_author_id_to_user` FOREIGN KEY (`Author Id`) REFERENCES `AM_User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `AM_Attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Link` varchar(255) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `Type` varchar(255) DEFAULT NULL,
  `Thread Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `key_thread` (`Thread Id`),
  CONSTRAINT `fk_thread` FOREIGN KEY (`Thread Id`) REFERENCES `AM_Thread` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `AM_Subject_Threads` (
  `AM_Subject_Id` int(11) NOT NULL,
  `Threads_Id` int(11) NOT NULL,
  UNIQUE KEY `uk_threads_id` (`threads_id`),
  KEY `fk_am_subject_id` (`am_subject_id`),
  CONSTRAINT `fk_am_subject` FOREIGN KEY (`AM_Subject_Id`) REFERENCES `AM_Subject` (`id`),
  CONSTRAINT `fk_am_thread` FOREIGN KEY (`Threads_Id`) REFERENCES `AM_Thread` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

