import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class saveThread extends Thread {
	
	String email;
	String fileID;
	String fileContent;
	String fileName;
	
	public saveThread(String email, String fileID, String fileContent) {
		this.email = email;
		this.fileID = fileID;
		this.fileContent = fileContent;
	}
	
	public void run() {
		//Begin database access
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try {
			//Set up Connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/db?user=root&password=password&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
			
			//Do all database queries here, make sure to lock/monitor these queries
			
			//First check if this file already exists in the database, otherwise we need to add a new file
			ps = conn.prepareStatement("SELECT * FROM Access WHERE fileID=?");
			ps.setString(1, fileID);
			
			
			rs = ps.executeQuery();
			
			
			if (rs.next()) {
				//This file already exists
				//connect email and currentFileID and update the row for that file with textBoxContent
				//UPDATE `db`.`Files` SET `rawData` = 'This is a test!' WHERE (`fileID` = '1');
				ps2 = conn.prepareStatement("UPDATE `db`.`Files` SET `rawData` = ? WHERE (`fileID` = ?);");
				ps2.setString(1, fileContent);
				ps2.setString(2, fileID);
				
				
				ps2.executeUpdate();
				
			}
			else {
				//add a new file
				
				//ADD HRIDAY's CODE HERE
			}
			
		} catch(SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch(ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if(conn!=null) {
					conn.close();
				}
				if(ps!=null) {
					ps.close();
				}
				if(rs!=null) {
					rs.close();
				}
				if(ps2!=null) {
					ps2.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle closing stream:-" + sqle.getMessage());
			}
		}
		
	}
}
