package examplePackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.microsoft.sqlserver.jdbc.SQLServerException;

/**
 * Servlet implementation class ManageServlet
 */
@WebServlet("/ManageServlet")
public class ManageServlet extends HttpServlet {

	Connection currentCon = null;
	ResultSet rs = null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		try {
			String firstName = request.getParameter("fn");
			String lastName = request.getParameter("ln");
			String name = request.getParameter("un");
			String password = request.getParameter("pw");
			String password2 = request.getParameter("pw2");
			String role = request.getParameter("role");
			
			System.out.println("Rola: " + role);
			
			//String createQuery = "INSERT INTO Modelarz (NazwaUzytkownika, Haslo, Imie, Nazwisko) VALUES (" + "'" + name + "' " + "'" + password + "' " + "'" + firstName + "' " + "'" + lastName + "' " + ");";

			
			//Statement stmt = null;
			currentCon = ConnectionManager.getConnection();
			//stmt = currentCon.createStatement();
			
			PreparedStatement pstmt = currentCon.prepareStatement(
					   "INSERT INTO Modelarz (NazwaUzytkownika, Haslo, Imie, Nazwisko, Rola) VALUES (?,?,?,?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.setString(3, firstName);
			pstmt.setString(4, lastName);
			pstmt.setString(5, role);
			
			try {
				rs = pstmt.executeQuery();
				if(rs != null) {
					
				}
			}catch(SQLServerException e) {
				System.out.println("Jest już taki użytkownik");
			}


		}

		catch (Throwable theException) {
			System.out.println(theException);
		}
	}
}
