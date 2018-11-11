

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class autoSave
 */
@WebServlet("/autoSave")
public class autoSave extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//We have a new autoSave request from a client
		//From previous page, extract parameters
		String email = request.getParameter("email");
		String fileID = request.getParameter("fileID");
		String fileContent = request.getParameter("fileContent");
		String fileName = request.getParameter("fileName");
		
		//make a new thread and run it
		saveThread SA = new saveThread(email, fileID, fileContent, fileName);
		SA.start();
	}
}
