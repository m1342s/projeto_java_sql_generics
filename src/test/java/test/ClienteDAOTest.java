package test;

import java.util.Collection;

import org.junit.After;

public class ClienteDAOTest {
	
	private IClienteDAO clienteDAO;
	
	public ClienteDAOTest() {
		clienteDAO= new ClienteDAO();
	}
	
	@After
	public void end() throws DAOException{
		Collection<Cliente> list = clienteDAO.buscarTodos();
		list.forEach(cli->{
			try {
				
			} catch(DAOException e) {
				e.printStackTrace();
			}
		});
	}
}
