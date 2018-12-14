package examplePackage;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AuthenticationFilter
 */
@WebServlet("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("currentSessionUser") != null) {   //checking whether the session exists
        	//System.out.println(((UserBean)session.getAttribute("currentSessionUser")).getFirstName());
            // pass the request along the filter chain
        	System.out.println("Niby byla sesja");
            chain.doFilter(request, response);
        } else {

        	
            this.context.log("Unauthorized access request");
            System.out.println("Chciales wejsc bez zalogowania");
            res.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    public void destroy() {
        //close any resources here
    }
}
