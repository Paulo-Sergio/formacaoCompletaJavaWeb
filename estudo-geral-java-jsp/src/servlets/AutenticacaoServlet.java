package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Usuario;

@WebServlet("/AutenticacaoServlet")
public class AutenticacaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String acao = req.getParameter("acao");
		
		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			HttpSession session = req.getSession();
			session.invalidate();
			resp.sendRedirect("AutenticacaoServlet");
			return;
		}
		
		 RequestDispatcher dispatcher = req.getRequestDispatcher("autenticar.jsp");
		 dispatcher.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String senha = req.getParameter("senha");
		
		Usuario usuario = new Usuario();
		usuario.setLogin(login);
		usuario.setSenha(senha);

		if (login.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("admin")) {
			req.getSession().setAttribute("usuarioLogado", usuario);
			resp.sendRedirect("pages/index.jsp");
		} else {
			resp.sendRedirect("AutenticacaoServlet");
		}
	}

}
