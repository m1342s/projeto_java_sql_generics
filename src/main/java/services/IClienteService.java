package services;

import domain.Cliente;
import exceptions.DAOException;
import services.generics.IGenericService;

public interface IClienteService extends IGenericService<Cliente,Long> {
	
	Cliente buscarPorCpf(Long cpf) throws DAOException;
}
