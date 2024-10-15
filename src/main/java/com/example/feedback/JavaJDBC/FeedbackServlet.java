package com.example.feedback.JavaJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        String name = request.getParameter("name");
        String bookName = request.getParameter("bookName");
        String feedback = request.getParameter("feedback");

       
        String url = "jdbc:mysql://localhost:3306/feedbackdb";
        String username = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
           
            String sql = "INSERT INTO feedback (name, book_name, feedback) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, bookName);
                stmt.setString(3, feedback);
                stmt.executeUpdate();
            }
            
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h3>Feedback saved</h3>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}