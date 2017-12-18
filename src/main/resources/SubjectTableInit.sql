DROP TABLE IF EXISTS `AMVoteRecord`;
DROP TABLE IF EXISTS `AMVoteItem`;
DROP TABLE IF EXISTS `AMVote`;
DROP TABLE IF EXISTS `AMThreadStatus`;
DROP TABLE IF EXISTS `AMSubjectStatus`;
DROP TABLE IF EXISTS `AMThread`;
DROP TABLE IF EXISTS `AMSubject`;

CREATE TABLE `AMSubject` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `PublishDate` datetime DEFAULT NULL,
  `Views` int(11) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `AMSubjectStatus` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `SubjectId` int(11) NOT NULL,
  `Status` varchar(50) NOT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_subject_id_to_subject` FOREIGN KEY (`SubjectId`) REFERENCES `AMSubject` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `AMVote` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `SubjectId` int(11) NOT NULL,
  `EndDate` datetime DEFAULT NULL,
  `MaxVoteItem` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_vote_subjectid_to_subject` FOREIGN KEY (`SubjectId`) REFERENCES `AMSubject` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `AMVoteItem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `VoteId` int(11) NOT NULL,
  `Content` varchar(255) NOT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_voteitem_voteid_to_vote` FOREIGN KEY (`VoteId`) REFERENCES `AMVote` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `AMVoteRecord` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `VoteItemId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `VoteDate` datetime NOT NULL,
  `IP` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_uservote_voteitemid_to_voteitem` FOREIGN KEY (`VoteItemId`) REFERENCES `AMVoteItem` (`Id`),
  CONSTRAINT `fk_uservote_userid_to_user` FOREIGN KEY (`UserId`) REFERENCES `AMUser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `AMThread` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Content` longtext,
  `EditDate` datetime DEFAULT NULL,
  `PublishDate` datetime DEFAULT NULL,
  `IP` varchar(255) DEFAULT NULL,
  `SubjectId` int(11) NOT NULL,
  `AuthorId` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_subject_id_tosub` FOREIGN KEY (`SubjectId`) REFERENCES `AMSubject` (`Id`),
  CONSTRAINT `fk_author_id_to_user` FOREIGN KEY (`AuthorId`) REFERENCES `AMUser` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `AMThreadStatus` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ThreadId` int(11) NOT NULL,
  `Status` varchar(50) NOT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_status_id_to_thread` FOREIGN KEY (`ThreadId`) REFERENCES `AMThread` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;