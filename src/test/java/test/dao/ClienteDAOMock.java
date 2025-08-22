package test.dao;

import java.util.Collection;

import dao.IClienteDAO;
import domain.Cliente;
import exceptions.DAOException;
import exceptions.MaisdeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;

public class ClienteDAOMock implements IClienteDAO {

	@Override
	public Boolean cadastrar(Cliente entity) throws TipoChaveNaoEncontradaException, DAOException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void excluir(Long valor) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(Cliente entity) throws TipoChaveNaoEncontradaException, DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cliente consultar(Long valor) throws MaisdeUmRegistroException, TableException, DAOException {
		Cliente cliente = new Cliente();
		cliente.setCpf(valor);
		return cliente;
	}

	@Override
	public Collection<Cliente> buscarTodos() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
