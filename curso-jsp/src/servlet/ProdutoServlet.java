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

import dao.ProdutoDao;
import model.Produto;

@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProdutoDao produtoDao = new ProdutoDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String acao = req.getParameter("acao");
		String idProduto = req.getParameter("id");
		List<Produto> produtos = new ArrayList<Produto>();

		if (acao == null) {
			acao = "listar";
		}

		try {
			produtos = this.produtoDao.listarTodos();

			if (acao.equalsIgnoreCase("listar")) {
				RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroProduto.jsp");
				req.setAttribute("produtos", produtos);
				dispatcher.forward(req, resp);

			} else if (acao.equalsIgnoreCase("editar")) {
				Produto produto = this.produtoDao.buscarPorId(Long.parseLong(idProduto));
				RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroProduto.jsp");
				req.setAttribute("produto", produto);
				req.setAttribute("produtos", produtos);
				dispatcher.forward(req, resp);

			} else if (acao.equalsIgnoreCase("deletar")) {
				this.produtoDao.deletar(Long.parseLong(idProduto));
				resp.sendRedirect("ProdutoServlet");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String acao = req.getParameter("acao");
		String id = req.getParameter("id");
		String nome = req.getParameter("nome");
		String quantidade = req.getParameter("quantidade");
		String valor = req.getParameter("valor");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			resp.sendRedirect("ProdutoServlet");
			return;
		}

		Produto produto = new Produto();
		produto.setId(id == null || id.isEmpty() ? null : Long.parseLong(id));
		produto.setNome(nome);
		produto.setQuantidade(Integer.parseInt(quantidade));
		produto.setValor(Double.parseDouble(valor));

		try {
			String msg = null;
			boolean isErroValidacao = false;
			if (nome == null || nome.isEmpty()) {
				msg = "Nome deve ser informado";
			}

			if (!isErroValidacao) {
				boolean isExisteProduto = this.produtoDao.isExistePorNome(nome);
				if ((id == null || id.isEmpty() && isExisteProduto) || (id != null && isExisteProduto)) {
					msg = "Produto de mesmo nome já existe!";
				}

				if (id == null || id.isEmpty() && !isExisteProduto) {
					this.produtoDao.salvar(produto);
					resp.sendRedirect("ProdutoServlet");
					return;
				} else if (id != null && !id.isEmpty()) {
					this.produtoDao.atualizar(produto);
					resp.sendRedirect("ProdutoServlet");
					return;
				}
			}

			RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroProduto.jsp");
			req.setAttribute("msg", msg);
			req.setAttribute("produto", produto);
			req.setAttribute("produtos", this.produtoDao.listarTodos());
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
