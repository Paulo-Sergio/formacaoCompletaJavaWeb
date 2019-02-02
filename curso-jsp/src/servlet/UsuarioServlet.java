package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (acao == null) {
			acao = "listar";
		}

		try {
			usuarios = this.usuarioDao.listarTodos();

			if (acao.equalsIgnoreCase("listar")) {
				RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroUsuario.jsp");
				req.setAttribute("usuarios", usuarios);
				dispatcher.forward(req, resp);

			} else if (acao.equalsIgnoreCase("editar")) {
				Usuario usuario = this.usuarioDao.buscarPorLogin(login);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroUsuario.jsp");
				req.setAttribute("usuario", usuario);
				dispatcher.forward(req, resp);

			} else if (acao.equalsIgnoreCase("deletar")) {
				this.usuarioDao.deletar(login);
				resp.sendRedirect("UsuarioServlet");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String login = req.getParameter("login");
		String senha = req.getParameter("senha");

		Usuario usuario = new Usuario();
		usuario.setId(id == null || id.isEmpty() ? 0 : Long.parseLong(id));
		usuario.setLogin(login);
		usuario.setSenha(senha);

		if (id == null || id.isEmpty()) {
			this.usuarioDao.salvar(usuario);
		} else {
			this.usuarioDao.atualizar(usuario);
		}

		try {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroUsuario.jsp");
			req.setAttribute("usuarios", this.usuarioDao.listarTodos());

			dispatcher.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
