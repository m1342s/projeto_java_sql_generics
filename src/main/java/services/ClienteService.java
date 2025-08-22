package services;

import java.util.Collection;

import dao.IClienteDAO;
import domain.Cliente;
import exceptions.DAOException;
import exceptions.TipoChaveNaoEncontradaException;
import services.generics.GenericService;

public class ClienteService extends GenericService<Cliente,Long> implements IClienteService {
	
	public ClienteService(IClienteDAO clienteDAO) {
		super(clienteDAO);
	}

	@Override
	public Boolean cadastrar(Cliente entity) throws TipoChaveNaoEncontradaException, DAOException {
		// TODO Auto-generated method stub
		return null;
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
	public Cliente consultar(Long valor) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Cliente> buscarTodos() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente buscarPorCpf(Long cpf) throws DAOException {
		try {
			return this.dao.consultar(cpf);
		}catch(MaisdeUmRegistroException | TableException e) {
			e.printStackTrace();
		}
		return null;
	}
}
