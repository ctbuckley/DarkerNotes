import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.websocket.*; // for space
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@ServerEndpoint(value = "/ws")
public class ServerSocket {
	private static Vector<Session> sessionVector = new Vector<Session>();
	//Change ^ to Map: key is email -> value is session object
	
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Connection made!");
		
		sessionVector.add(session);
		//Replace with map insert ^
		
	}
	@OnMessage
	public void onMessage(String message, Session session) {
		//called when the frontend makes a request
		System.out.println(message);
		//message is a json w/ action, email, fileID, rawFileData
		
		//Using GSON
		JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
		
		System.out.println(jsonObject.get("action").getAsString());
		System.out.println(jsonObject.get("email").getAsString()); 
		System.out.println(jsonObject.get("fileID").getAsString()); 
		System.out.println(jsonObject.get("rawData").getAsString()); 

		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("Save")) {
			//Call the multithreaded autosave for that user
			System.out.println("Would call save File because request is to save");
			
			//saveFileForUser("input", "input", "input");
			
		}
	}
	@OnClose
	public void close(Session session) {
		
		
		System.out.println("Disconnecting!");
		
		
		sessionVector.remove(session);
		//^ Replace with map remove
		
	}
	@OnError
	public void error(Throwable error) {
		System.out.println("Error!");
	}
	
	//Multithreading here ...
	public void saveFileForUser(String email, String currentFileID, String textBoxContent) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/db?user=root&password=password&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
			
			//connect email and currentFileID and update the row for that file with textBoxContent
			
			//UPDATE `db`.`Files` SET `rawData` = 'This is a test!' WHERE (`fileID` = '1');
			
			//ps.executeUpdate()
			
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
			} catch (SQLException sqle) {
				System.out.println("sqle closing stream:-" + sqle.getMessage());
			}
		}	
	}
}
	