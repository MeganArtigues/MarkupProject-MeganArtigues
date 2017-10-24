Create Table Statements:


CREATE TABLE `htmlfiles` (
  `file_id` varchar(45) NOT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `content` longblob,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `scores` (
  `score_id` int(11) NOT NULL AUTO_INCREMENT,
  `score` int(11) NOT NULL,
  `file_id` varchar(45) DEFAULT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`score_id`),
  KEY `scores_ibfk_1` (`file_id`),
  CONSTRAINT `scores_ibfk_1` FOREIGN KEY (`file_id`) REFERENCES `htmlfiles` (`file_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8

CREATE TABLE `avgscores` (
  `avgscore_id` int(11) NOT NULL AUTO_INCREMENT,
  `avgscore` int(11) NOT NULL,
  `avgfile_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`avgscore_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8


Queries from java code:

-Static queries:
    "SELECT * FROM scores ORDER BY score DESC;";
    
    "SELECT * FROM scores ORDER BY score ASC;";
    
    "SELECT * FROM avgscores;";
    
    "SELECT * FROM htmlfiles;";
    
    
-Dynamic queries:
    "INSERT INTO htmlfiles (file_id, filename, content, date) VALUES " ('"+f.getId()+"','"+f.getFileName()+"','"+f.getFileContent()+"','"+sqlTimeStamp+"')";
    
    "INSERT INTO scores (score, file_id, date) VALUES"
				+ "('"+score+"','"+file.getId() +"','"+sqlTimeStamp+"')";
                
    "SELECT * FROM scores WHERE file_id LIKE '%" + id + "%';";
    
    "UPDATE avgscores SET avgscore = '" + newOverall + "' WHERE avgfile_id = '" + id + "';";
    
    "INSERT INTO avgscores (avgscore, avgFile_id) VALUES ('"+overallScore+"','"+ id + "')";
    
    "SELECT * FROM htmlfiles WHERE filename = '" + file_id + "';";
    
    "SELECT * FROM scores WHERE file_id = '" + id + "';";
    
    "SELECT * FROM scores WHERE date BETWEEN '" + date1 + "' AND '" + date2 + "';";
    
    "SELECT filename FROM htmlfiles WHERE file_id = '" + id + "';";
    
    
    
    
