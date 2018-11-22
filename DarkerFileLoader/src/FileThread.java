import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class FileThread extends Thread {
	
	String Email;
	String filename;
	String rawdata;
	
	FileThread(String fn, String rd, String email) {
		this.filename = fn;
		this.rawdata = rd;
		this.Email = email;
	}

	@Override
	public void run() {
				
		//From previous page, extract parameters
		String email = this.Email;
		String fileName = this.filename;
		String rawData= this.rawdata;
		
		System.out.println("in a new filethread for " + email + " with file " + filename);
		
		//Set up variables to hold response
		boolean success = true;
		
		//no need to check for null input?
		
		//Error checking for empty fileName in servlet?
		//How are we passing in rawData?
		
		//Begin database access
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		PreparedStatement ps6 = null;
		ResultSet rs = null;
		ResultSet rs5 = null;
		ResultSet rs6 = null;
		
		int newFileId = -1;
		
		
		//If we didn't have null input, go into main database access
		if (success) {
			try {
				//success = false;
				Class.forName("com.mysql.jdbc.Driver");
				//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?user=root&password=password&useSSL=false");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/db?user=root&password=password&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
				
				ps = conn.prepareStatement("INSERT INTO Files (rawData,fileName) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, rawData);
				ps.setString(2, fileName);
				ps.executeUpdate();
				rs6 = ps.getGeneratedKeys();
				
				if(rs6.next()) {
					newFileId=rs6.getInt(1);
				}
				
		
				
				//Check if email already exists in our database
				ps5 = conn.prepareStatement("SELECT * FROM Users WHERE email=?");
				ps5.setString(1, email);
				rs5 = ps5.executeQuery();
				rs5.next();
				int uID = rs5.getInt("userID");
				
				
				
				
				ps4 = conn.prepareStatement("INSERT INTO Access (userID, fileID) VALUES (?,?);");
				ps4.setString(1, Integer.toString(uID));
				ps4.setString(2, Integer.toString(newFileId));
				ps4.executeUpdate();
				
				
				
				
				
				
			} catch(SQLException sqle) {
				System.out.println("sqle in FileThread.java: " + sqle.getMessage());
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
					if(ps5!=null) {ps5.close();}
					if(ps6!=null) {ps6.close();}
					if(rs5!=null) {rs5.close();}
					if(rs6!=null) {rs6.close();}
				} catch (SQLException sqle) {
					System.out.println("sqle closing stream:-" + sqle.getMessage());
				}
			}
		}
	
				
			
		
		
		
	}
	
}
