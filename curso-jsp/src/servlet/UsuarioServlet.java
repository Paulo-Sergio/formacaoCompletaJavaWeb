package servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import dao.UsuarioDao;
import model.Usuario;

@WebServlet("/UsuarioServlet")
@MultipartConfig
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
			} else if (acao.equalsIgnoreCase("download")) {
				Usuario usuario = this.usuarioDao.buscarPorId(Long.parseLong(idUsuario));
				if (usuario != null) {
					String contentType = "";
					byte[] fileBytes = null;
					String tipo = req.getParameter("tipo");

					if (tipo.equalsIgnoreCase("imagem")) {
						contentType = usuario.getContentType();
						/*
						 * Converte a base64 da imagem do banco para byte[]
						 */
						fileBytes = new Base64().decodeBase64(usuario.getFotoBase64());
					} else if (tipo.equalsIgnoreCase("curriculo")) {
						contentType = usuario.getContentTypeCurriculo();
						/*
						 * Converte a base64 do pdf do banco para byte[]
						 */
						fileBytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
					}

					// image/png (contentType)
					resp.setHeader("Content-Disposition", "attachment;filename=arquivo." + contentType.split("\\/")[1]);

					/* coloca os bytes em um objeto de entrada para processar */
					InputStream inputStream = new ByteArrayInputStream(fileBytes);
					/* inicio da resposta para o navegador */
					int read = 0;
					byte[] bytes = new byte[1024];
					ServletOutputStream outputStream = resp.getOutputStream();

					while ((read = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}

					outputStream.flush();
					outputStream.close();
				}
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
		usuario.setCep(cep);
		usuario.setRua(rua);
		usuario.setBairro(bairro);
		usuario.setCidade(cidade);
		usuario.setEstado(estado);
		usuario.setIbge(ibge);

		try {
			/** Inicio File Upload de imagens e pdf */
			if (ServletFileUpload.isMultipartContent(req)) {
				/* processa imagem */
				Part imagemFoto = req.getPart("foto");
				if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
					String fotoBase64 = new Base64().encodeBase64String(this.converteStreamParaBytes(imagemFoto.getInputStream()));

					usuario.setFotoBase64(fotoBase64);
					usuario.setContentType(imagemFoto.getContentType());
				} else {
					usuario.setFotoBase64(req.getParameter("fotoTemp"));
					usuario.setContentType(req.getParameter("contentTypeTemp"));
				}

				/* processa pdf */
				Part curriculoPdf = req.getPart("curriculo");
				if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
					String curriculoBase64 = new Base64().encodeBase64String(this.converteStreamParaBytes(curriculoPdf.getInputStream()));

					usuario.setCurriculoBase64(curriculoBase64);
					usuario.setContentTypeCurriculo(curriculoPdf.getContentType());
				} else {
					usuario.setCurriculoBase64(req.getParameter("curriculoTemp"));
					usuario.setContentTypeCurriculo(req.getParameter("curriculoContentTypeTemp"));
				}

			}
			/** FIM File Upload de imagens e pdf */

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
				Usuario usuarioBuscado = this.usuarioDao.buscarPorLogin(login);
				if ((id == null || id.isEmpty() && usuarioBuscado != null) || (id != null && usuarioBuscado != null)) {
					msg = "Usu�rio j� existe com o mesmo login!";
				}

				if (id == null || id.isEmpty() && usuarioBuscado == null) {
					this.usuarioDao.salvar(usuario);
					resp.sendRedirect("UsuarioServlet");
					return;
				} else if (id != null && !id.isEmpty()) {
					// se encontrar usuario mesmo login, verificar se estou
					// atualizando do meu proprio login
					Usuario usuarioParaAtualizar = this.usuarioDao.buscarPorId(usuario.getId());
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

	/* Converte a entrada de fluxo de dados da imagem para array de bytes */
	private byte[] converteStreamParaBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int reads = inputStream.read();
		while (reads != -1) {
			outputStream.write(reads);
			reads = inputStream.read();
		}

		return outputStream.toByteArray();
	}
}
