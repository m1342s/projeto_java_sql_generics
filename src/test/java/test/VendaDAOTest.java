package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.ClienteDAO;
import dao.IClienteDAO;
import dao.IProdutoDAO;
import dao.IVendaDAO;
import dao.ProdutoDAO;
import dao.VendaDAO;
import dao.generics.jdbc.ConnectionFactory;
import domain.Cliente;
import domain.Produto;
import domain.Venda;
import domain.Venda.Status;
import exceptions.DAOException;
import exceptions.MaisdeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;

public class VendaDAOTest {
	
	private IVendaDAO vendaDAO;
	
	private IClienteDAO clienteDAO;
	
	private IProdutoDAO produtoDAO;
	
	private Cliente cliente;
	
	private Produto produto;
	
	public VendaDAOTest() {
		vendaDAO = new VendaDAO();
		clienteDAO = new ClienteDAO();
		produtoDAO = new ProdutoDAO();
	}
	
	@Before
	public void init() throws TipoChaveNaoEncontradaException,MaisdeUmRegistroException,TableException,DAOException{
		this.cliente = cadastrarCliente();
		this.produto = cadastrarProduto("A1",BigDecimal.TEN);
		
	}
	
	@After
	public void end() throws DAOException{
		excluirVendas();
		excluirProdutos();
		clienteDAO.excluir(this.cliente.getCpf());
	}
	
	private void excluirProdutos() throws DAOException{
		Collection<Produto> list = this.produtoDAO.buscarTodos();
		for(Produto prod : list) {
			this.produtoDAO.excluir(prod.getCodigo());
		}
	}
	@Test
	public void pesquisar() throws DAOException,TipoChaveNaoEncontradaException,MaisdeUmRegistroException,TableException{
		
		Venda venda = criarVenda("A1");
		Boolean retorno = VendaDAO.cadastrar(venda);
		assertTrue(retorno);
		Venda vendaConsultada = vendaDAO.consultar(venda.getCodigo());
		assertNotNull(vendaConsultada);
		assertEquals(venda.getCodigo(),vendaConsultada.getCodigo());
		
	}
	@Test
	public void salvar() throws TipoChaveNaoEncontradaException, DAOException, MaisdeUmRegistroException,TableException{
		
		Venda venda = criarVenda("A2");
		Boolean retorno = vendaDAO.cadastrar(venda);
		assertTrue(retorno);
		
		assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(20)));
		assertTrue(venda.getStatusVenda().equals(Status.INICIADA));
		
		Venda vendaConsultada = vendaDAO.consultar(venda.getCodigo());
		assertTrue(vendaConsultada.getId() != null);
		assertEquals(venda.getCodigo(),vendaConsultada.getCodigo());
	}
	
	@Test
	public void cancelarVenda() throws TipoChaveNaoEncontradaException, MaisdeUmRegistroException,TableException, DAOException{
		
		String codigoVenda = "A3";
		Venda venda = criarVenda(codigoVenda);
		Boolean retorno = vendaDAO.cadastrar(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda,venda.getCodigo());
		
		vendaDAO.cancelarVenda(venda);
		
		Venda vendaConsultada = vendaDAO.consultar(codigoVenda);
		assertEquals(codigoVenda,vendaConsultada.getCodigo());
		assertEquals(Status.CANCELADA,vendaConsultada.getStatusVenda());
		
	}
	
	@Test
	public void adicionarMaisProdutosDoMesmo() throws TipoChaveNaoEncontradaException, MaisdeUmRegistroException, TableException,DAOException{
		
		String codigoVenda = "A4";
		Venda venda = criarVenda(codigoVenda);
		Boolean retorno = vendaDAO.cadastrar(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda,venda.getCodigo());
		
		Venda vendaConsultada = vendaDAO.consultar(codigoVenda);
		vendaConsultada.adicionarProduto(produto,1);
		
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		BigDecimal valorTotal = BigDecimal.valueOf(30).setScale(2,RoundingMode.HALF_DOWN);
		assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
		assertTrue(vendaConsultada.getStatusVenda().equals(Status.INICIADA));
	}
	
	@Test
	public void adicionarProdutosDiferentes() throws TipoChaveNaoEncontradaException,MaisdeUmRegistroException,TableException,DAOException{
		
		String codigoVenda = ("A5");
		Venda venda = criarVenda(codigoVenda);
		Boolean retorno = vendaDAO.cadastrar(venda);
		assertTrue(retorno);
		assertNotNull(venda);
		assertEquals(codigoVenda,venda.getCodigo());
		
		Produto prod = cadastrarProduto(codigoVenda,BigDecimal.valueOf(50));
		assertNotNull(produto);
		assertEquals(codigoVenda,prod.getCodigo());
		
		Venda vendaConsultada = vendaDAO.consultar(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2,RoundingMode.HALF_DOWN);
		assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
		assertTrue(vendaConsultada.getStatusVenda().equals(Status.INICIADA));
	}
	
@Test(expected = DAOException.class)
public void salvarVendaMesmoCodigoExistente() throws TipoChaveNaoEncontradaException,DAOException{
	
	String codigoVenda = "A6";
	Venda venda = criarVenda(codigoVenda);
	Boolean retorno = vendaDAO.cadastrar(venda);
	assertTrue(retorno);
	
	Boolean retorno1 = vendaDAO.cadastrar(venda);
	assertFalse(retorno1);
	assertTrue(venda.getStatusVenda().equals(Status.INICIADA));
}

@Test
public void removerProdutos() throws TipoChaveNaoEncontradaException,MaisdeUmRegistroException,TableException, DAOException{
	
	String codigoVenda = "A7";
	Venda venda = criarVenda(codigoVenda);
	Boolean retorno = vendaDAO.cadastrar(venda);
	assertTrue(retorno);
	assertNotNull(venda);
	assertEquals(codigoVenda,venda.getCodigo());
	
	Produto prod = cadastrarProduto(codigoVenda,BigDecimal.valueOf(50));
	assertNotNull(prod);
	assertEquals(codigoVenda,venda.getCodigo());
	
	Venda vendaConsultada = vendaDAO.consultar(codigoVenda);
	vendaConsultada.adicionarProduto(prod,1);
	assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
	BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2,RoundingMode.HALF_DOWN);
	assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
	
	vendaConsultada.removerProduto(prod, 1);
	assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 2);
	valorTotal = BigDecimal.valueOf(20).setScale(2,RoundingMode.HALF_DOWN);
	assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
	assertTrue(vendaConsultada.getStatusVenda().equals(Status.INICIADA));
	
	
}

@Test
public void removerApenasUmProduto() throws TipoChaveNaoEncontradaException,MaisdeUmRegistroException,TableException,DAOException{
	
	String codigoVenda = "A8";
	Venda venda = criarVenda(codigoVenda);
	Boolean retorno = vendaDAO.cadastrar(venda);
	assertTrue(retorno);
	assertNotNull(venda);
	assertEquals(codigoVenda,venda.getCodigo());
	
	Produto prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
	assertNotNull(prod);
	assertEquals(codigoVenda,prod.getCodigo());
	
	Venda vendaConsultada = vendaDAO.consultar(codigoVenda);
	vendaConsultada.adicionarProduto(prod, 1);
	assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
	BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2,RoundingMode.HALF_DOWN);
	assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
	
	vendaConsultada.removerProduto(prod,1);
	assertTrue(vendaConsultada.getQuantidadeTotalProdutos()==2);
	valorTotal = BigDecimal.valueOf(20).setScale(2,RoundingMode.HALF_DOWN);
	assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
	assertTrue(vendaConsultada.getStatusVenda().equals(Status.INICIADA));
	
}

@Test
public void removerTodosProdutos() throws TipoChaveNaoEncontradaException,MaisdeUmRegistroException,TableException,DAOException{
	
	String codigoVenda = "A9";
	Venda venda = criarVenda(codigoVenda);
	Boolean retorno = vendaDAO.cadastrar(venda);
	assertTrue(retorno);
	assertNotNull(venda);
	assertEquals(codigoVenda,venda.getCodigo());
	
	Produto prod = cadastrarProduto(codigoVenda,BigDecimal.valueOf(50));
	assertNotNull(prod);
	assertEquals(codigoVenda,prod.getCodigo());
	
	Venda vendaConsultada = vendaDAO.consultar(codigoVenda);
	vendaConsultada.adicionarProduto(prod,1);
	assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
	BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2,RoundingMode.HALF_DOWN);
	assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
	
	vendaConsultada.removerTodosProdutos();
	assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 0);
	assertTrue(vendaConsultada.getValorTotal().equals(BigDecimal.valueOf(0)));
	assertTrue(vendaConsultada.getStatusVenda().equals(Status.INICIADA));
}

@Test
public void finalizarVenda() throws TipoChaveNaoEncontradaException, MaisdeUmRegistroException,TableException,DAOException{
	String codigoVenda = "A10";
	Venda venda = criarVenda(codigoVenda);
	Boolean retorno = vendaDAO.cadastrar(venda);
	assertTrue(retorno);
	assertNotNull(venda);
	assertEquals(codigoVenda,venda.getCodigo());
	
	vendaDAO.finalizarVenda(venda);
	Venda vendaConsultada = vendaDAO.consultar(codigoVenda);
	assertEquals(venda.getCodigo(),vendaConsultada.getCodigo());
	assertEquals(Status.CONCLUIDA,vendaConsultada.getStatusVenda());
	
	vendaConsultada.adicionarProduto(this.produto,1);
	
}

@Test(expected=UnsupportedOperationException.class)
public void tentarAdicionarProdutosVendaFinalizada() throws TipoChaveNaoEncontradaException,MaisdeUmRegistroException,TableException,DAOException{
	String codigoVenda = "A11";
	Venda venda = criarVenda(codigoVenda);
	Boolean retorno = vendaDAO.cadastrar(venda);
	assertTrue(retorno);
	assertNotNull(venda);
	assertEquals(codigoVenda,venda.getCodigo());
	
	vendaDAO.finalizarVenda(venda);
	Venda vendaConsultada = vendaDAO.consultar(codigoVenda);
	assertEquals(venda.getCodigo(),vendaConsultada.getCodigo());
	assertEquals(Status.CONCLUIDA,vendaConsultada.getStatusVenda());
	
	vendaConsultada.adicionarProduto(this.produto, 1);
	
}
private Produto cadastrarProduto(String codigo,BigDecimal valor) throws TipoChaveNaoEncontradaException, MaisdeUmRegistroException,TableException,DAOException{
	Produto produto = new Produto();
	produto.setCodigo(codigo);
	produto.setDescricao("Produto 1");
	produto.setNome("Produto 1");
	produto.setValor(valor);
	produtoDAO.cadastrar(produto);
	return produto;
	
}


private Cliente cadastrarCliente() throws TipoChaveNaoEncontradaException, DAOException {
	Cliente cliente = new Cliente();
	cliente.setCpf(123123123123L);
	cliente.setNome("Rodrigo");
	cliente.setCidade("São Paulo");
	cliente.setEnd("End");
	cliente.setEstado("SP");
	cliente.setNumero(10);
	cliente.setTel(119999999L);
	clienteDAO.cadastrar(cliente);
	return cliente;
}

private Venda criarVenda(String codigo) {
	Venda venda = new Venda();
	venda.setCodigo(codigo);
	venda.setDataVenda(Instant.now());
	venda.setCliente(this.cliente);
	venda.setStatusVenda(Status.INICIADA);
	venda.adicionarProduto(this.produto, 2);
	return venda;
}

private void excluirVendas() throws DAOException{
	String sqlProd = "DELETE FROM TB_PRODUTO_QUANTIDADE";
	executeDelete(sqlProd);
	
	String sqlV = "DELETE FROM TB_VENDA";
	executeDelete(sqlV);
}

private void executeDelete(String sql) throws DAOException{
	Connection connection = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
		connection = getConnection();
		stm = connection.prepareStatement(sql);
		stm.executeUpdate();
		
	} catch(SQLException e ) {
		throw new DAOException("ERRO EXCLUINDO OBJETO", e);
	} finally {
		closeConnection(connection,stm,rs);
	}
}

protected void closeConnection(Connection connection,PreparedStatement stm,ResultSet rs ) {
	try {
		if(rs != null && rs.isClosed()) {
			rs.close();
		}
		if(stm != null && stm.isClosed()) {
			stm.close();
		}
		if(connection != null && !stm.isClosed()) {
			connection.close();
		}
	} catch(SQLException e1) {
		e1.printStackTrace();
	}
}
protected Connection getConnection() throws DAOException{
	try {
		return ConnectionFactory.getConnection();
	}catch(SQLException e) {
		throw new DAOException("ERRO ABRINDO CONEXÃO COM BANCO DE DADOS ", e);
	}
}

}