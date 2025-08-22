package test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import dao.ClienteDAO;
import dao.IClienteDAO;
import domain.Cliente;
import exceptions.DAOException;
import exceptions.MaisdeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;

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
				clienteDAO.excluir(cli.getCpf());
			} catch(DAOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Test
	public void pesquisarCliente() throws MaisdeUmRegistroException,TableException,TipoChaveNaoEncontradaException,DAOException{
		Cliente cliente = new Cliente();
		cliente.setCpf(123123123123L);
		cliente.setNome("Rodrigo");
		cliente.setCidade("São Paulo");
		cliente.setEnd("End");
		cliente.setEstado("SP");
		cliente.setNumero(10);
		cliente.setTel(1199999999L);
		clienteDAO.cadastrar(cliente);
		
		Cliente clienteConsultado = clienteDAO.consultar(cliente.getCpf());
		Assert.assertNotNull(clienteConsultado);
		
		clienteDAO.excluir(cliente.getCpf());
	}
	@Test
	public void salvarCliente() throws MaisdeUmRegistroException,TableException,TipoChaveNaoEncontradaException,DAOException{
		Cliente cliente = new Cliente();
		cliente.setCpf(56565656565656L);
		cliente.setNome("Rodrigo");
		cliente.setCidade("São Paulo");
		cliente.setEnd("End");
		cliente.setEstado("SP");
		cliente.setNumero(10);
		cliente.setTel(1199999999L);
		Boolean retorno = clienteDAO.cadastrar(cliente);
		Assert.assertTrue(retorno);
		

		
		Cliente clienteConsultado = clienteDAO.consultar(cliente.getCpf());
		Assert.assertNotNull(clienteConsultado);
		
		clienteDAO.excluir(cliente.getCpf());
	}
	@Test
	public void excluirCliente() throws MaisdeUmRegistroException,TableException,TipoChaveNaoEncontradaException,DAOException{
		Cliente cliente = new Cliente();
		cliente.setCpf(56565656565656L);
		cliente.setNome("Rodrigo");
		cliente.setCidade("São Paulo");
		cliente.setEnd("End");
		cliente.setEstado("SP");
		cliente.setNumero(10);
		cliente.setTel(1199999999L);
		Boolean retorno = clienteDAO.cadastrar(cliente);
		Assert.assertTrue(retorno);
		

		
		Cliente clienteConsultado = clienteDAO.consultar(cliente.getCpf());
		Assert.assertNotNull(clienteConsultado);
		
		clienteDAO.excluir(cliente.getCpf());
		clienteConsultado = clienteDAO.consultar(cliente.getCpf());
		Assert.assertNull(clienteConsultado);
	}
	@Test
	public void alterarCliente() throws MaisdeUmRegistroException,TableException,TipoChaveNaoEncontradaException,DAOException{
		Cliente cliente = new Cliente();
		cliente.setCpf(56565656565656L);
		cliente.setNome("Rodrigo");
		cliente.setCidade("São Paulo");
		cliente.setEnd("End");
		cliente.setEstado("SP");
		cliente.setNumero(10);
		cliente.setTel(1199999999L);
		Boolean retorno = clienteDAO.cadastrar(cliente);
		Assert.assertTrue(retorno);
		

		
		Cliente clienteConsultado = clienteDAO.consultar(cliente.getCpf());
		Assert.assertNotNull(clienteConsultado);
		
		clienteConsultado.setNome("Rodrigo Pires");
		clienteDAO.alterar(clienteConsultado);
		
		Cliente clienteAlterado = clienteDAO.consultar(clienteConsultado.getCpf());
		Assert.assertNotNull(clienteAlterado);
		Assert.assertEquals("Rodrigo Pires", clienteAlterado.getNome());
		
		clienteDAO.excluir(cliente.getCpf());
		clienteConsultado=clienteDAO.consultar(cliente.getCpf());
		Assert.assertNull(clienteConsultado);
	}
	
	@Test
	public void buscarTodos() throws TipoChaveNaoEncontradaException,DAOException{
		Cliente cliente = new Cliente();
		cliente.setCpf(56565656565656L);
		cliente.setNome("Rodrigo");
		cliente.setCidade("São Paulo");
		cliente.setEnd("End");
		cliente.setEstado("SP");
		cliente.setNumero(10);
		cliente.setTel(1199999999L);
		Boolean retorno = clienteDAO.cadastrar(cliente);
		Assert.assertTrue(retorno);
		
		Cliente cliente1 = new Cliente();
		cliente.setCpf(565656565656569L);
		cliente.setNome("Rodrigo");
		cliente.setCidade("São Paulo");
		cliente.setEnd("End");
		cliente.setEstado("SP");
		cliente.setNumero(10);
		cliente.setTel(1199999999L);
		Boolean retorno1 = clienteDAO.cadastrar(cliente1);
		Assert.assertTrue(retorno1);
		
		Collection<Cliente> list = clienteDAO.buscarTodos();
		assertTrue(list != null);
		assertTrue(list.size()==2);
		
		list.forEach(cli -> {
			try {
				clienteDAO.excluir(cli.getCpf());
			} catch(DAOException e) {
				e.printStackTrace();			
		}
		});
		Collection<Cliente> list1 = clienteDAO.buscarTodos();
		assertTrue(list1 != null);
		assertTrue(list1.size()==0);
		

	}
}
