package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDao;
import model.Usuario;

@WebServlet("/CarregarDadosDataTableServlet")
public class CarregarDadosDataTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private dao.UsuarioDao usuarioDao = new UsuarioDao();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			List<Usuario> usuarios = this.usuarioDao.listarTodos();
			
			if (!usuarios.isEmpty()) {
				String data = "";
				int totalUsuarios = usuarios.size();
				int index = 1;
				
				for (Usuario usuario : usuarios) {
					data +=  " ["+
					      "\""+usuario.getId()+"\", "+
					      "\""+usuario.getLogin()+"\""+
				    "]";
					if (index < totalUsuarios){
						data += ",";
					}
					
					index++;
				}
				
				String json = "{"+
					  "\"draw\": 1,"+
					  "\"recordsTotal\": "+usuarios.size()+","+
					  "\"recordsFiltered\": "+usuarios.size()+","+
					  "\"data\": ["+data+"]"+ // fechamento do data
				"}";// fechamento do json
				
				resp.setStatus(200); // reposta completa OK
				resp.getWriter().write(json); // json de resposta (escreve a resposta Http)
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
