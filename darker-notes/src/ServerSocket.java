import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import javax.websocket.*; // for space
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@ServerEndpoint(value = "/ws")
public class ServerSocket {
	private static Vector<Session> sessionVector = new Vector<Session>();
	private static HashMap<String, Vector<Session>> hm = new HashMap<String, Vector<Session>>();
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Connection made!");
		sessionVector.add(session);
	}
	@OnMessage
	public void onMessage(String message, Session session) {
		//called when the frontend makes a request
		//message is a json w/ action, email, fileID, rawFileData
		//Here 
		//Using GSON
		JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();

		String action = jsonObject.get("action").getAsString();
		if (action.equals("addUtoMap")) {
			String email = jsonObject.get("email").getAsString();
			if (hm.get(email) != null) {
				hm.get(email).add(session);
			}
			else {
				hm.put(email, new Vector<Session>());
				hm.get(email).add(session);
			}
		}
		else if (action.equals("removeUFromMap")) {
			String email = jsonObject.get("email").getAsString();
			if (hm.get(email) != null) {
				hm.get(email).remove(session);
			}
		}
		else if (action.equals("SendFile")) {
			//add a new notification for the user
			String emailTo = jsonObject.get("emailTo").getAsString();
			String email = jsonObject.get("email").getAsString();
			String fileID = jsonObject.get("fileID").getAsString();
			
			//Begin database access
			Connection conn = null;
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			try {
				//Set up Connection
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/db?user=root&password=password&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
				
				ps = conn.prepareStatement("SELECT * FROM Users WHERE email=?");
				ps.setString(1, email);
				rs = ps.executeQuery();
				rs.next();
				String nameFrom = rs.getString("fullName");
				
				ps2 = conn.prepareStatement("SELECT * FROM Users WHERE email=?");
				ps2.setString(1,  emailTo);
				rs2 = ps2.executeQuery();
				
				
				if (rs2.next()) {
					//The user we are emailing exists
					int userID = rs2.getInt("userID");
					//INSERT INTO Notifications (notificationID, userID, fromName, isRead, fileID) VALUES (1, 4, Connor Buckley, 0, 1);
					ps3 = conn.prepareStatement("INSERT INTO Notifications (userID, fromName, isRead, fileID) VALUES (?, ?, ?, ?);");
					ps3.setString(1, Integer.toString(userID));
					ps3.setString(2, nameFrom);
					ps3.setString(3, "0");
					ps3.setString(4, fileID);
					ps3.executeUpdate();
					
					//Notify other user of new file if they are logged in
					
					//check if they are logged in?
					// check if the session is in sessionVector<>
					
					if  (hm.get(emailTo) != null) {
						Vector<Session> sendToThese = hm.get(emailTo);
						for (Session s : sendToThese) {
							try {
								if (sessionVector.contains(s)) {
									s.getBasicRemote().sendText("You have a new notification");
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			} catch(SQLException sqle) {
				System.out.println("sqle SERVERSOCKET.java: " + sqle.getMessage());
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
					if(rs2!=null) {
						rs2.close();
					}
					if(ps2!=null) {
						ps2.close();
					}
					if(ps3!=null) {
						ps3.close();
					}
				} catch (SQLException sqle) {
					System.out.println("sqle closing stream:-" + sqle.getMessage());
				}
			}
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
	
}
	