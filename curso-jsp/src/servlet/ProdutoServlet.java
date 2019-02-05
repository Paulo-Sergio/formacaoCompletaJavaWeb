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
		String id = req.getParameter("id");
		String nome = req.getParameter("nome");
		String quantidade = req.getParameter("quantidade");
		String valor = req.getParameter("valor");

		Produto produto = new Produto();
		produto.setId(id == null || id.isEmpty() ? null : Long.parseLong(id));
		produto.setNome(nome);
		if (quantidade != null && !quantidade.isEmpty())
			produto.setQuantidade(Integer.parseInt(quantidade));
		if (valor != null && !valor.isEmpty())
			produto.setValor(Double.parseDouble(valor));

		try {
			String msg = null;
			boolean isErroValidacao = false;
			if (nome == null || nome.isEmpty()) {
				msg = "Nome deve ser informado";
				isErroValidacao = true;
			} else if (quantidade == null || quantidade.isEmpty()) {
				msg = "Quantidade deve ser informado";
				isErroValidacao = true;
			} else if (valor == null || valor.isEmpty()) {
				msg = "Valor R$ deve ser informado";
				isErroValidacao = true;
			}

			if (!isErroValidacao) {
				Produto produtosBuscado = this.produtoDao.buscarPorNome(nome);
				if ((id == null || id.isEmpty() && produtosBuscado != null) || (id != null && produtosBuscado != null)) {
					msg = "Produto de mesmo nome já existe!";
				}

				if (id == null || id.isEmpty() && produtosBuscado == null) {
					this.produtoDao.salvar(produto);
					resp.sendRedirect("ProdutoServlet");
					return;
				} else if (id != null && !id.isEmpty()) {
					// se encontrar produto mesmo nome, verificar se estou
					// atualizando do meu proprio protudo
					Produto produtoParaAtualizar = this.produtoDao.buscarPorId(produto.getId());
					if (produtosBuscado == null || produtoParaAtualizar.getNome().equals(produtosBuscado.getNome())) {
						this.produtoDao.atualizar(produto);
						resp.sendRedirect("ProdutoServlet");
						return;
					}
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
