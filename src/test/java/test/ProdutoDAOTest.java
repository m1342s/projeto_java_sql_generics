package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import dao.IProdutoDAO;
import dao.ProdutoDAO;
import domain.Produto;
import exceptions.DAOException;
import exceptions.MaisdeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;

public class ProdutoDAOTest {
	
	private IProdutoDAO produtoDAO;
	
	public ProdutoDAOTest() {
		produtoDAO = new ProdutoDAO();
	}
	
	@After
	public void end() throws DAOException{
		Collection<Produto> list = produtoDAO.buscarTodos();
		list.forEach(prod ->{
			try {
				produtoDAO.excluir(prod.getCodigo());
			} catch(DAOException e ) {
				e.printStackTrace();
			}
		});
	}
	
	private Produto criarProduto(String codigo) throws TipoChaveNaoEncontradaException,DAOException{
		Produto produto= new Produto();
		produto.setCodigo(codigo);
		produto.setDescricao("Produto 1");
		produto.setNome("Produto 1");
		produto.setValor(BigDecimal.TEN);
		produtoDAO.cadastrar(produto);
		return produto;
		
	}
	
	private void excluir(String valor) throws DAOException{
		this.produtoDAO.excluir(valor);
	}
	
	@Test
	public void pesquisar() throws MaisdeUmRegistroException,TableException,DAOException,TipoChaveNaoEncontradaException{
		Produto produto = criarProduto("A1");
		Assert.assertNotNull(produto);
		Produto produtoDB = this.produtoDAO.consultar(produto.getCodigo());
		Assert.assertNotNull(produtoDB);
		excluir(produtoDB.getCodigo());
	}
	
	@Test
	public void salvar() throws TipoChaveNaoEncontradaException,DAOException{
		Produto produto = criarProduto("A2");
		Assert.assertNotNull(produto);
		excluir(produto.getCodigo());
	}
	
	@Test
	public void excluir() throws DAOException,TipoChaveNaoEncontradaException,MaisdeUmRegistroException,TableException{
		Produto produto = criarProduto("A3");
		Assert.assertNotNull(produto);
		excluir(produto.getCodigo());
		Produto produtoBD = this.produtoDAO.consultar(produto.getCodigo());
		assertNull(produtoBD);
	}
	
	@Test
	public void alterarCliente() throws TipoChaveNaoEncontradaException,DAOException,MaisdeUmRegistroException,TableException{
		Produto produto = criarProduto("A4");
		produto.setNome("Rodrigo Pires");
		produtoDAO.alterar(produto);
		Produto produtoDB = this.produtoDAO.consultar(produto.getCodigo());
		assertNotNull(produtoDB);
		Assert.assertEquals("Rodrigo Pires", produtoDB.getNome());
		
		excluir(produto.getCodigo());
		Produto produtoBD1 = this.produtoDAO.consultar(produto.getCodigo());
		assertNull(produtoBD1);
	}
	
	@Test
	public void buscarTodos() throws DAOException,TipoChaveNaoEncontradaException{
		criarProduto("A5");
		criarProduto("A6");
		Collection<Produto> list = produtoDAO.buscarTodos();
		assertTrue(list != null);
		assertTrue(list.size()==2);
		
		for(Produto prod : list) {
			excluir(prod.getCodigo());
		}
		
		list = produtoDAO.buscarTodos();
		assertTrue(list != null);
		assertTrue(list.size()==0);
	}
}
