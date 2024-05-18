package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	DAO dao = new DAO();
	
	/** The voluntario. */
	JavaBeans voluntario = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();
	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		if (action.equals("/main")) {
			voluntarios(request, response);
		} else if (action.equals("/insert")) {
			adicionarVoluntario(request, response);
		} else if (action.equals("/select")) {
			listarVoluntario(request, response);
		} else if (action.equals("/update")) {
			editarVoluntario(request, response);
		} else if (action.equals("/delete")) {
			removerVoluntario(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}

		// teste de conexão
		dao.testeConexão();
	}

	/**
	 * Voluntarios.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Listar voluntários
	protected void voluntarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Criando um objeto que irá receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarVoluntarios();

		// encaminhas a lista ao documento agenda.jsp
		request.setAttribute("voluntarios", lista);
		RequestDispatcher rd = request.getRequestDispatcher("voluntarios.jsp");
		rd.forward(request, response);
	}

	/**
	 * Adicionar voluntario.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Novo Voluntario
	protected void adicionarVoluntario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Setar as variáveis JavaBeans
		voluntario.setNome(request.getParameter("nome"));
		voluntario.setTelefone(request.getParameter("telefone"));
		voluntario.setEmail(request.getParameter("email"));

		// invocar o método "inserirVoluntario" passando o objeto "voluntario"
		dao.inserirVoluntario(voluntario);
		// redirecionar para o doc voluntarios.jsp
		response.sendRedirect("main");

	}

	/**
	 * Listar voluntario.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Editar voluntário
	protected void listarVoluntario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Setar a variável JavaBeans
		voluntario.setId(request.getParameter("id"));
		// executar o método selecionarVoluntário() (DAO)
		dao.selecionarVoluntario(voluntario);

		// Settar os atributos do form com o conteúdo JavaBeans
		request.setAttribute("id", voluntario.getId());
		request.setAttribute("nome", voluntario.getNome());
		request.setAttribute("telefone", voluntario.getTelefone());
		request.setAttribute("email", voluntario.getEmail());

		// encaminhar os dados ao doc editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	/**
	 * Editar voluntario.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void editarVoluntario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setar as variáveis JavaBeans
		voluntario.setId(request.getParameter("id"));
		voluntario.setNome(request.getParameter("nome"));
		voluntario.setTelefone(request.getParameter("telefone"));
		voluntario.setEmail(request.getParameter("email"));

		// executar o método alterarVoluntario
		dao.alterarVoluntario(voluntario);

		// redirecionar para o documento voluntarios.jsp (atualizando as alterações)
		response.sendRedirect("main");
	}

	/**
	 * Remover voluntario.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Remover um contato
	protected void removerVoluntario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setar a variável id JavaBeans
		voluntario.setId(request.getParameter("id"));
		// executar o método deletarVoluntario
		dao.deletarVoluntario(voluntario);
		// redirecionar para o documento voluntarios.jsp (atualizando as alterações)
		response.sendRedirect("main");
	}

	/**
	 * Gerar relatorio.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Gerar Relatório em PDF
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		try {
			// Tipo de conteúdo
			response.setContentType("apllication/pdf");
			// nome do documento
			response.addHeader("Content-Disposition", "inline; filename=" + "voluntarios.pdf");
			// criar documento
			PdfWriter.getInstance(documento, response.getOutputStream());

			// abrir o documento para gerar o conteúdo
			documento.open();
			documento.add(new Paragraph("Lista de Voluntários: "));
			documento.add(new Paragraph(" "));
			// criar uma tabela
			PdfPTable tabela = new PdfPTable(3);

			// cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Telefone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			// popular a tabela com os voluntarios
			ArrayList<JavaBeans> lista = dao.listarVoluntarios();
			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getTelefone());
				tabela.addCell(lista.get(i).getEmail());
			}

			documento.add(tabela);

			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
}
