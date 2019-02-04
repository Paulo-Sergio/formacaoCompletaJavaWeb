package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String fileUpload = req.getParameter("fileUpload");
			System.out.println(fileUpload); // imagem em base64
			
			// guarda imagem no banco por exemplo:
			// this.testeDao().atualizaImagem(fileUpload);
			
			resp.getWriter().write("Upload realizado com sucesso");
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write("Erro fatal ao realizare upload");
		}
		
	}

}
