package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import connection.SingleConnection;

/**
 * EXEMPLO DO USO DE FILTER PARA CONEXAO AO BANCO
 * @author paulo
 *
 */
/*@WebFilter(urlPatterns = { "/*" })*/
public class Filter implements javax.servlet.Filter {

	private static Connection connection = SingleConnection.getConnection();

	@Override
	public void destroy() {
		System.out.println("connection destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
		try {
			System.out.println("connection dofilter");

			filter.doFilter(request, response);
			connection.commit();

		} catch (Exception e) {
			try {
				e.printStackTrace();
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		connection = SingleConnection.getConnection();
		System.out.println("connection init");
	}

}