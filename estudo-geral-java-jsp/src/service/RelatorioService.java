package service;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioService implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String FOLDER_RELATORIOS = "/relatorios";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private String SEPARATOR = File.separator;
	private String caminhoArquivoRelatorio = null;
	private JRExporter exporter = null;
	private String caminhoSubReport_Dir = "";
	private File arquivoGerado = null;

	private String gerarRelatorio(List<?> listDataBeanCollection, HashMap parametrosRelatorio, String nomeRelatorioJasper, String nomeRelatorioSaida, ServletContext servletContext)
			throws Exception {

		/**
		 * cria a lista de collectionDataSource de beans que carregam os dados
		 * para o relatorio
		 */
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDataBeanCollection);

		/**
		 * Fornece o caminho fisico até a pasta que contem os relatorios .jarper
		 */
		String caminhoRelatorio = servletContext.getRealPath(FOLDER_RELATORIOS);

		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jarper");

		if (caminhoRelatorio == null || (caminhoRelatorio != null && caminhoRelatorio.isEmpty()) || !file.exists()) {
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SEPARATOR = "";
		}

		/** caminho para imagens */
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio);

		/** caminho completo até o relatorio compilado indicado */
		String caminhoArquivosJasper = caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jarper";
		
		/** faz carregamento do relatório */
		JasperReport relatorioJarper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivosJasper);
		
		/** settar os parametros SUBREPORT_DIR com o caminho fisico para subreport */
		caminhoSubReport_Dir = caminhoRelatorio + SEPARATOR;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubReport_Dir);
		
		/** carrega o arquivo */
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJarper, parametrosRelatorio, jrbcds);
		
		exporter = new JRPdfExporter();
		
		/** caminho do relatorio exportado */
		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + ".pdf";
		
		/** criar novo arquivo exportado */
		arquivoGerado = new File(caminhoArquivoRelatorio);
		
		/** prepara a impressao */
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
		
		/** executa a exportação */
		exporter.exportReport();
		
		/** remove o arquivo do servidor apos ser feito o download */
		arquivoGerado.deleteOnExit();
		
		return caminhoArquivoRelatorio;
	}
}
