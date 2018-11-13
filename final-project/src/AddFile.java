import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddFile")
public class AddFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//From previous page, extract parameters
		String email = request.getParameter("email");
		String fileName = request.getParameter("file");
		String rawData= request.getParameter("data");
		
		//don't know what to do with lastUpdate
		String lastUpdate="";
		
		//Set up variables to hold response
		boolean success = true;
		String errorMsg = "";
		
		//no need to check for null input?
		
		//Error checking for empty fileName in servlet?
		//How are we passing in rawData?
		
		//Begin database access
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		//If we didn't have null input, go into main database access
		if (success) {
			try {
				//success = false;
				Class.forName("com.mysql.jdbc.Driver");
				//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?user=root&password=password&useSSL=false");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/db?user=root&password=password&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
				
				ps = conn.prepareStatement("INSERT INTO Files (rawData,fileName,lastUpdate) VALUES ('\" + rawData + \"', '\" +  fileName + \"', '\" + lastUpdate + \"');\"");
				ps.executeUpdate();		
				
				//Set up a JSON return
				String objectToReturn =
						  "{\n"
							+ "\"success\": \"" + success + "\",\n"
							+ "\"data\": {\n"
								+ "\"errorMsg\": \"" + errorMsg + "\",\n"
								+ "\"fileName\": \"" + fileName + "\",\n"
								+ "\"email\": \"" + email + "\"\n"
							+ "}\n" 
						+ "}";
				out.print(objectToReturn);
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
					if(ps2!=null) {
						ps2.close();
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
			String objectToReturn =
					  "{\n"
						+ "\"success\": \"" + success + "\",\n"
						+ "\"data\": {\n"
							+ "\"errorMsg\": \"" + errorMsg + "\",\n"
							+ "\"fileName\": \"\",\n"
							+ "\"email\": \"\"\n"
						+ "}\n" 
					+ "}";
			out.print(objectToReturn);
		}
		
	}
}