package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CapturarExcecaoServlet")
public class CapturarExcecaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String valorParam = req.getParameter("valorParam");
			System.out.println(valorParam);
			
			Integer.parseInt(valorParam); // força uma execption
			
			resp.setStatus(200);
			resp.getWriter().write("Processado com sucesso");
			
		} catch (Exception e) {
			resp.setStatus(500);
			resp.getWriter().write("Erro ao processar: " + e.getMessage());
		}
	}

}
