package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TelefoneDao;
import dao.UsuarioDao;
import model.Telefone;
import model.Usuario;

@WebServlet("/TelefoneServlet")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDao usuarioDao = new UsuarioDao();
	private TelefoneDao telefoneDao = new TelefoneDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idUsuario = req.getParameter("idUsuario");
		String id = req.getParameter("id");
		String acao = req.getParameter("acao");

		if (acao == null) {
			acao = "listar";
		}

		try {
			if (acao.equalsIgnoreCase("listar")) {
				Usuario usuario = this.usuarioDao.buscarPorId(Long.parseLong(idUsuario));
				List<Telefone> telefonesDoUsuario = this.telefoneDao.listarTodos(Long.parseLong(idUsuario));
				req.getSession().setAttribute("usuarioEscolhido", usuario);

				RequestDispatcher dispatcher = req.getRequestDispatcher("/telefones.jsp");
				req.setAttribute("usuario", usuario);
				req.setAttribute("telefones", telefonesDoUsuario);
				dispatcher.forward(req, resp);
				
			} else if (acao.equalsIgnoreCase("deletar")) {
				this.telefoneDao.deletar(Long.parseLong(id));
				resp.sendRedirect("TelefoneServlet?idUsuario=" + idUsuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String numero = req.getParameter("numero");
		String tipo = req.getParameter("tipo");

		Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioEscolhido");

		Telefone telefone = new Telefone();
		telefone.setUsuarioId(usuario.getId());
		telefone.setNumero(numero);
		telefone.setTipo(tipo);

		this.telefoneDao.salvar(telefone);

		resp.sendRedirect("TelefoneServlet?idUsuario=" + usuario.getId());
	}
}
