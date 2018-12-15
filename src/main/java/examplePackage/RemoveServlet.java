package examplePackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microsoft.sqlserver.jdbc.SQLServerException;

/**
 * Servlet implementation class RemoveServlet
 */
@WebServlet("/RemoveServlet")
public class RemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection currentCon = null;
	ResultSet rs = null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		try {
			String name = request.getParameter("userName");

			
			currentCon = ConnectionManager.getConnection();
			//stmt = currentCon.createStatement();
			
			PreparedStatement pstmt = currentCon.prepareStatement(
					   "DELETE FROM Modelarz WHERE NazwaUzytkownika = ?");
			pstmt.setString(1, name);
			
			
			try {
				Boolean wynik = pstmt.execute();
				response.sendRedirect(request.getContextPath() + "/logged/admin/removeSuccess.jsp");
				
			}catch(SQLServerException e) {
				//e.printStackTrace();
				System.out.println("Problem z usunięciem użytkownika");
				response.sendRedirect(request.getContextPath() + "/logged/admin/removeFailure.jsp");
			}

			
		}

		catch (Throwable theException) {
			System.out.println(theException);
		}
	}

}
