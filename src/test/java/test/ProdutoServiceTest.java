package test;

import domain.Produto;
import exceptions.DAOException;
import exceptions.TipoChaveNaoEncontradaException;
import services.IProdutoService;
import services.ProdutoService;
import test.dao.*;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.IProdutoDAO;

public class ProdutoServiceTest {
	
	private IProdutoService produtoService;
	
	private Produto produto;
	
	public ProdutoServiceTest() {
		IProdutoDAO dao = new ProdutoDAOMock();
		produtoService = new ProdutoService(dao);
	}
	
	@Before
	public void init() {
		produto = new Produto();
		produto.setCodigo("A1");
		produto.setDescricao("Produto 1");
		produto.setNome("Produto 1");
		produto.setValor(BigDecimal.TEN);
	}
	
	@Test
	public void pesquisar() throws DAOException{
		Produto produtor = this.produtoService.consultar(produto.getCodigo());
		Assert.assertNotNull(produtor);
		
	};
	
	@Test
	public void salvar() throws TipoChaveNaoEncontradaException,DAOException{
		Boolean retorno = produtoService.cadastrar(produto);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluir() throws DAOException{
		produtoService.excluir(produto.getCodigo());
	}
	
	@Test
	public void alterar() throws TipoChaveNaoEncontradaException,DAOException{
		produto.setNome("Rodrigo Pires");
		produtoService.alterar(produto);
		
		Assert.assertEquals("Rodrigo Pires",produto.getNome());
	}
	
	
}
