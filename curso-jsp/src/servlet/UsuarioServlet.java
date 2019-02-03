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
		String idUsuario = req.getParameter("id");
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
				Usuario usuario = this.usuarioDao.buscarPorId(Long.parseLong(idUsuario));
				RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroUsuario.jsp");
				req.setAttribute("usuario", usuario);
				req.setAttribute("usuarios", usuarios);
				dispatcher.forward(req, resp);

			} else if (acao.equalsIgnoreCase("deletar")) {
				this.usuarioDao.deletar(Long.parseLong(idUsuario));
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
		String nome = req.getParameter("nome");
		String senha = req.getParameter("senha");
		String telefone = req.getParameter("telefone");
		String cep = req.getParameter("cep");
		String rua = req.getParameter("rua");
		String bairro = req.getParameter("bairro");
		String cidade = req.getParameter("cidade");
		String estado = req.getParameter("estado");
		String ibge = req.getParameter("ibge");

		Usuario usuario = new Usuario();
		usuario.setId(id == null || id.isEmpty() ? null : Long.parseLong(id));
		usuario.setLogin(login);
		usuario.setNome(nome);
		usuario.setSenha(senha);
		usuario.setTelefone(telefone);
		usuario.setCep(cep);
		usuario.setRua(rua);
		usuario.setBairro(bairro);
		usuario.setCidade(cidade);
		usuario.setEstado(estado);
		usuario.setIbge(ibge);

		try {
			String msg = null;
			boolean isErroValidacao = false;
			if (login == null || login.isEmpty()) {
				msg = "Login deve ser informado";
				isErroValidacao = true;
			} else if (nome == null || nome.isEmpty()) {
				msg = "Nome deve ser informado";
				isErroValidacao = true;
			} else if (senha == null || senha.isEmpty()) {
				msg = "Senha deve ser informada";
				isErroValidacao = true;
			}

			if (!isErroValidacao) {
				Usuario usuarioParaAtualizar = this.usuarioDao.buscarPorId(usuario.getId());
				Usuario usuarioBuscado = this.usuarioDao.buscarPorLogin(login);
				if ((id == null || id.isEmpty() && usuarioBuscado != null) || (id != null && usuarioBuscado != null)) {
					msg = "Usuário já existe com o mesmo login!";
				}

				if (id == null || id.isEmpty() && usuarioBuscado == null) {
					this.usuarioDao.salvar(usuario);
					resp.sendRedirect("UsuarioServlet");
					return;
				} else if (id != null && !id.isEmpty()) {
					/*
					 * se encontrar usuario mesmo login, verificar se estou
					 * atualizando do meu proprio login
					 */
					if (usuarioBuscado == null || usuarioParaAtualizar.getLogin().equals(usuarioBuscado.getLogin())) {
						this.usuarioDao.atualizar(usuario);
						resp.sendRedirect("UsuarioServlet");
						return;
					}
				}
			}

			RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroUsuario.jsp");
			req.setAttribute("msg", msg);
			req.setAttribute("usuario", usuario);
			req.setAttribute("usuarios", this.usuarioDao.listarTodos());
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
