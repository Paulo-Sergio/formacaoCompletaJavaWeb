package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDao;
import model.Usuario;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDao usuarioDao = new UsuarioDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String acao = req.getParameter("acao");
		String login = req.getParameter("login");

		if (acao.equalsIgnoreCase("delete")) {
			this.usuarioDao.delete(login);

			try {
				RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroUsuario.jsp");
				req.setAttribute("usuarios", this.usuarioDao.listarTodos());
				dispatcher.forward(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String senha = req.getParameter("password");

		Usuario usuario = new Usuario();
		usuario.setLogin(login);
		usuario.setSenha(senha);

		this.usuarioDao.salvar(usuario);

		try {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroUsuario.jsp");
			req.setAttribute("usuarios", this.usuarioDao.listarTodos());

			dispatcher.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
