package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mysql.jdbc.Connection;

import object.File;

public class DbPersistImpl {

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	//add a file
	public int addFile(File f) {
		Date date = new Date();
        Timestamp sqlTimeStamp = new Timestamp(date.getTime());//get the time stamp
        
		String sql = "INSERT INTO htmlfiles (file_id, filename, content, date) VALUES"
				+ "('"+f.getId()+"','"+f.getFileName()+"','"+f.getFileContent()+"','"+sqlTimeStamp+"')";
		return DbAccessImpl.create(sql);
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	//add the score for an html file to the database
	public int addScore(int score, File file) {
		
		Date date = new Date();
        Timestamp sqlTimeStamp = new Timestamp(date.getTime()); //add it with the current date
        
		String sql = "INSERT INTO scores (score, file_id, date) VALUES"
				+ "('"+score+"','"+file.getId() +"','"+sqlTimeStamp+"')";
		int scoreInput = DbAccessImpl.create(sql);
		
		String id = file.getId();
		if (id.indexOf("(") != -1){
			id = id.substring(0, id.indexOf("("));//get the id (minus the "()" if it was a duplicate upload
		}
		
		String sqlAllScores =  "SELECT * FROM scores WHERE file_id LIKE '%" + id + "%';";

		ResultSet r = DbAccessImpl.retrieve(sqlAllScores);
		int overallScore = 0;
		int s = 0;
		int counter = 0;
		
		try {
			while (r.next()){//get all the scores for that id
				s = r.getInt("score");
				overallScore += s;//add each score to the overall score
				counter++;//keep track of how many we added
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		overallScore /= counter;//get the average
		
		String sqlCheckForID = "SELECT * FROM avgscores WHERE avgFile_id = '" + id + "';"; 
		ResultSet rs = DbAccessImpl.retrieve(sqlCheckForID);
		int checker = 0;
		int currentAvg = 0;
		
		try {
			if (rs.next()){
				checker = 1;
				currentAvg = rs.getInt("avgscore");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if (checker == 1){//if there's already an average score stored, update instead
			int newOverall = (overallScore + currentAvg)/2;
			String sqlUpdateAvg = "UPDATE avgscores SET avgscore = '" + newOverall + "' WHERE avgfile_id = '" + id + "';";
			int update = DbAccessImpl.update(sqlUpdateAvg);
		}else{//otherwise, inset the avg score
			String sqlInsertAvg = "INSERT INTO avgscores (avgscore, avgFile_id) VALUES ('"+overallScore+"','"+ id + "')";
			int input = DbAccessImpl.create(sqlInsertAvg);
		}
		
		return scoreInput;
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//get all scores for a file
	public List<Integer> retreiveScores(String file_id) {
		String sqlGetFileID = "SELECT * FROM htmlfiles WHERE filename = '" + file_id + "';";
		ResultSet r1 = DbAccessImpl.retrieve(sqlGetFileID);
		String id = null;
		
		try {
			if (r1.next()){//get the filename to cross-reference the scores db
				id = r1.getString("file_id");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		String sql = "SELECT * FROM scores WHERE file_id = '" + id + "';";
		ResultSet r = DbAccessImpl.retrieve(sql);
		System.out.println(sql);

		List<Integer> allScores = new ArrayList<Integer>();
		
		
		try {
			while (r.next()){	//get all of the scores for that filename out of the scores db
				int score = r.getInt("score");
				allScores.add(score);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allScores;
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------

	//get all files and scores from a certain range
	public List<File> retrieveFileBtwnDates(String date1, String date2) {
		String sql = "SELECT * FROM scores WHERE date BETWEEN '" + date1 + "' AND '" + date2 + "';";
		ResultSet r = DbAccessImpl.retrieve(sql);
		List<File> files = new ArrayList<File>();
		
		
		try {
			while (r.next()){	
				String id = r.getString("file_id");//get the filename to display
				String sqlGetFileName = "SELECT filename FROM htmlfiles WHERE file_id = '" + id + "';";
				ResultSet r2 = DbAccessImpl.retrieve(sqlGetFileName);
				String name = null;
				
				if (r2.next()){
					name = r2.getString("filename");
				}
				
				int score = r.getInt("score");//and get the score
				
				File f = new File();
				f.setFileName(name);;
				f.setScore(score);
				
				files.add(f);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return files;
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------

	//returns the highest score listed in the db
	public File retrieveHighestScore() {
		File f = new File();

		String sql = "SELECT * FROM scores ORDER BY score DESC;";
		ResultSet r = DbAccessImpl.retrieve(sql);
		
		try {
			if (r.next()){
				f.setFileName(r.getString("file_id"));
				f.setScore(r.getInt("score"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return f;
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------

	//returns the lowest score listed in the db
	public File retrieveLowestScore() {
		File f = new File();

		String sql = "SELECT * FROM scores ORDER BY score ASC;";
		ResultSet r = DbAccessImpl.retrieve(sql);
		
		try {
			if (r.next()){
				f.setFileName(r.getString("file_id"));
				f.setScore(r.getInt("score"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return f;
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------

	//gets all average scores and then calculates the average of all those averages
	public List<File> allRunsAverage() {
		String sql = "SELECT * FROM avgscores;";
		
		ResultSet r = DbAccessImpl.retrieve(sql);
		List<File> files = new ArrayList<File>();
		int totalAverage = 0;
		int counter = 0;
		
		try {
			while (r.next()){	
				String id = r.getString("avgfile_id");
				int score = r.getInt("avgscore");
				File f = new File();
				f.setId(id);
				f.setScore(score);
				
				
				files.add(f);
				
				totalAverage += score;
				counter ++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (counter != 0){
			totalAverage /= counter;
		}
		
		File f = new File();
		f.setId("Final Average");
		f.setScore(totalAverage);
		files.add(f);
		
		return files;
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------

	//gets all filenames for the dropdown in the explore section
	public List<String> getAllFileNames() {
		String sql = "SELECT * FROM htmlfiles;";
		ResultSet r = DbAccessImpl.retrieve(sql);
		
		List<String> names = new ArrayList<String>();
		
		try {
			while (r.next()){
				names.add(r.getString("filename"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return names;
	}

}