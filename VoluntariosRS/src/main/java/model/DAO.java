package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; //JDBC
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	
	/**  Módulo de conexão *. */

	// Parâmetros de conexão

	private String driver = "com.mysql.cj.jdbc.Driver"; /** The url. */
 /*
														 * Essa linha só vai funcionar se você tiver baixado o DRIVER
														 * direto do SQL e por ele na pasta Lib
														 */
	private String url = "jdbc:mysql://127.0.0.1:3306/db_voluntarios?useTimezone=true&serverTimezone=UTC"; 
 /** The user. */
 /*
																											 * Ip do
																											 * SERVER,
																											 * nome do
																											 * Banco de
																											 * Dados e
																											 * fuso
																											 * horário
																											 * de
																											 * referência
																											 * universal
																											 */
	private String user = "root";
	
	/** The password. */
	private String password = "@Nbk18asd";

	// Método de conexão

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Teste conexão.
	 */
	// teste de conexão
	public void testeConexão() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 *  CRUD CREATE *.
	 *
	 * @param voluntario the voluntario
	 */
	public void inserirVoluntario(JavaBeans voluntario) {
		String create = "insert into voluntariados (nome, telefone, email) values (?, ?, ?)";
		try {
			// arbrir conexão com o Banco
			Connection con = conectar();

			// Preparar a query para execução no BD
			PreparedStatement pst = con.prepareStatement(create);

			// Substituir os parametros (?) pelo conteúdo das variávbeis JavaBeans
			pst.setString(1, voluntario.getNome());
			pst.setString(2, voluntario.getTelefone());
			pst.setString(3, voluntario.getEmail());

			// Executar a query
			pst.executeUpdate();

			// Encerrar a conexão com o banco
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 *  CRUD READ *.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> listarVoluntarios() {
		// Criando um objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> voluntarios = new ArrayList<>();
		String read = "select * from voluntariados order by nome";
		try {
			Connection con = conectar();

			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery(); /* executa a query (comando sql acima) */

			// O laço abaixo será executado enquanto houver contatos
			while (rs.next()) {
				// variáveis de apoio que recebem os dados do banco
				String id = rs.getString(1);
				String nome = rs.getString(2);
				String telefone = rs.getString(3);
				String email = rs.getString(4);
				// Populando o ArrayList
				voluntarios.add(new JavaBeans(id, nome, telefone, email));
			}
			con.close();
			return voluntarios;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 *  CRUD UPDATE *.
	 *
	 * @param voluntario the voluntario
	 */
	
	//selecionar voluntario
	public void selecionarVoluntario(JavaBeans voluntario) {
		String read2 = "select * from voluntariados where id = ?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			
			pst.setString(1, voluntario.getId());
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				voluntario.setId(rs.getString(1));
				voluntario.setNome(rs.getString(2));
				voluntario.setTelefone(rs.getString(3));
				voluntario.setEmail(rs.getString(4));
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Alterar voluntario.
	 *
	 * @param voluntario the voluntario
	 */
	// Editar o voluntario
	public void alterarVoluntario (JavaBeans voluntario) {
		String update = "update voluntariados set nome = ?, telefone=?, email=? where id=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, voluntario.getNome());
			pst.setString(2, voluntario.getTelefone());
			pst.setString(3, voluntario.getEmail());
			pst.setString(4, voluntario.getId());
			
			pst.executeUpdate();
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 *  CRUD DELETE *.
	 *
	 * @param voluntario the voluntario
	 */
	public void deletarVoluntario(JavaBeans voluntario) {
		/*AS variáveis dentro do JavaBeans são encapsuladas
		 * e só conseguem acessar essas variáveis através do objeto acima, "voluntario" */
		String delete = "delete from voluntariados where id=?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, voluntario.getId()); 
			pst.executeUpdate(); /*Para executar essa Query no Banco de Dados*/
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	
	}
}
