package test.dao;

import java.util.Collection;

import dao.IProdutoDAO;
import domain.Produto;
import exceptions.DAOException;
import exceptions.MaisdeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;

public class ProdutoDAOMock implements IProdutoDAO {

	@Override
	public Boolean cadastrar(Produto entity) throws TipoChaveNaoEncontradaException, DAOException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void excluir(String valor) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(Produto entity) throws TipoChaveNaoEncontradaException, DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Produto consultar(String valor) throws MaisdeUmRegistroException, TableException, DAOException {
		Produto produto = new Produto();
		produto.setCodigo(valor);
		return produto;
	}

	@Override
	public Collection<Produto> buscarTodos() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
