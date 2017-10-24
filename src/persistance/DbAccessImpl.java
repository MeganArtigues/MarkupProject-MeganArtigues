package persistance;

/*
 * Author: Shane Gilmer
 * Purpose: This class is to provide useful methods to be used later
 * in the logic layer
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbAccessImpl{

//------------------------------------------------------------------------------------------------
	
	/*
	 * Establishes a connection to the markupprojectdb database
	 */
	public static Connection connect() {
		Connection con = null;
		try {
			Class.forName(DbAccessConfig.DRIVE_NAME);
			con = DriverManager.getConnection(DbAccessConfig.CONNECTION_URL,
					DbAccessConfig.DB_CONNECTION_USERNAME, DbAccessConfig.DB_CONNECTION_PASSWORD );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

//------------------------------------------------------------------------------------------------
	
	/*
	 * Uses a connection to return a Result Set according to the query parameter
	 */
	public static ResultSet retrieve(String query) {
		Connection con = connect();
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery( query );
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

//------------------------------------------------------------------------------------------------
	
	/*
	 * Creates a tuple in the markupprojectdb database according to the query
	 */
	public static int create(String query) {
		Connection con = connect();
		int success = 0;
		try {
			Statement stmt = con.createStatement();
			success = stmt.executeUpdate( query );
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect(con);
		}
		return success;
	}

//------------------------------------------------------------------------------------------------
	
	/*
	 * Updates a tuple in the markupprojectdb database according to the query
	 */
	public static int update(String query) {
		Connection con = connect();
		int success = 0;
		try {
			Statement stmt = con.createStatement();
			success = stmt.executeUpdate( query );
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect(con);
		}
		return success;
	}

//------------------------------------------------------------------------------------------------
	
	/*
	 * Deletes a tuple in the markupprojectdb database according to the query
	 */
	public static int delete(String query) {
		Connection con = connect();
		int success = 0;
		try {
			Statement stmt = con.createStatement();
			success = stmt.executeUpdate( query );
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect(con);
		}
		return success;
	}
	
//------------------------------------------------------------------------------------------------

	/*
	 * Disconnects the connection
	 */
	public static void disconnect(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}