package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Usuario;

@WebFilter(urlPatterns = { "/pages/*" })
public class FilterAutenticacao implements Filter {

	@Override
	public void destroy() {
		System.out.println("### FilterAutenticacao.destroy");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		System.out.println("### FilterAutenticacao.doFilter");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		HttpSession session = request.getSession();
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

		if (usuarioLogado == null) { // usuario não logado
			response.sendRedirect("AutenticacaoServlet");
			return;
		}

		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("### FilterAutenticacao.init");
	}

}
