import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		
		//First, prompt for user login -> email and password
		Scanner scan = new Scanner(System.in);
		System.out.println("Log In");
		System.out.print("Email: ");
		String inUN = "";
		inUN = scan.next();
		System.out.print("Password: ");
		String inPass = "";
		inPass = scan.next();
		
		boolean logIn = validateLogin(inUN, inPass);
		System.out.println("Validating..." + logIn);
		
		while (!logIn) {
			System.out.println("Invalid log in");
			System.out.println("Log In (Or Type \"Exit\" to Exit)");
			System.out.print("Email: ");
			inUN = scan.next();
			if (inUN.equals("Exit")) {
				break;
			}
			System.out.print("Password: ");
			inPass = scan.next();
			logIn = validateLogin(inUN, inPass);
			System.out.println("Validating..." + logIn);
		}
		
		String email = inUN;
		
		//validateLog
		
		Vector<String> files = new Vector<String>();
		Vector<String> rawdata = new Vector<String>();
 
			

		// we now have a vector of threads initialized with files
		
		
		//after they log in, prompt user for files in a loop, or log out
		boolean exit = false;
		while (!exit) {
			System.out.println("Menu:");
			System.out.println("1. Add a new File");
			System.out.println("2. Save and Log Out");
			System.out.println("3. Save and Exit Program");
			
			String selection = scan.next();
			if (selection.equals("3")) {
				System.out.println("Thank you for using the Darker Notes File Loader!");
				exit = true;
				break;
			}
			if (selection.equals("1")) {
				
				String filename = "";
				
				//add a new file
				System.out.println("Filename: ");
				filename = scan.next();
				System.out.println("Filepath: ");
				String filepath = "";
				filepath = scan.next();
					
				byte[] encoded = null;
				String rawData = "";
				try {
					encoded = Files.readAllBytes(Paths.get(filepath));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					rawData = new String(encoded, "utf8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				files.add(filename);
				
				System.out.println("RawData: " + rawData);
				
				rawData = rawData.replace("\n\n", "<div><br></div>");
				rawData = rawData.replace("\n", "<br>");
				//rawData = rawData.replace("\\", "\\\\");
				
				
				
				//rawData = rawData.replaceAll("\"","\\\\\"");
				
				
				
				rawdata.add(rawData);
				
			}
			if (selection.equals("2")) {
				System.out.println("Logging user " + email + " out!");
				exit = true;
				break;
			}
		}
		
		scan.close();
		
		//for each file, spawn a new thread and run it
		
		Vector<FileThread> threads = new Vector<FileThread>(); 
		
		for (int i = 0; i < files.size(); i++) {
			threads.add(new FileThread(files.get(i), rawdata.get(i), email));
		}
		
		for (Thread t : threads) {
			t.start();
		}
	
	}

	public static boolean validateLogin(String inUN, String inPass) {
		//From previous page, extract parameters
		String email = inUN;
		String pass = inPass;
		String hashPass = "";
		
		//Set up variables to store return value
		boolean success = true;
		
		//Check for null input
		if (pass == null) {
			success = false;
			pass = "";
		}
		else if (pass.length() == 0) {
			success = false;
			pass = "";
		}
		if (email == null) {
			success = false;
			email = "";
		}
		else if (email.length() == 0) {
			success = false;
			email = "";
		}
		
		//Hash using sha256
		try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hashInBytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));
	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashInBytes) {
	        	sb.append(String.format("%02x", b));
	        }
	        hashPass = sb.toString();
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
		}
		
		//Begin database access
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		if (success) {
			try {
				success = false;
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?user=root&password=password&useSSL=false&allowPublicKeyRetrieval=true");
				
				//Validate email and password with server
				ps = conn.prepareStatement("SELECT * FROM Users WHERE email=?");
				ps.setString(1, email);
				rs = ps.executeQuery();
				
				if (rs.next()) {
					//If a user with that email exists, check the password
					if (Objects.equals(hashPass, rs.getString("hashPass"))) {
						success = true;
					}
					else {
						success = false;
					}
				}
				else {
					//Check details of invalid login
					success = false;
				}
				//Set up a JSON return
				return success;
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
		else {
			return success;
		}
		return success;
	}
}
