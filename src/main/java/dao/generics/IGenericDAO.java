package dao.generics;

import java.io.Serializable;
import java.util.Collection;

import dao.Persistente;
import exceptions.DAOException;
import exceptions.MaisdeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;

public interface IGenericDAO<T extends Persistente, E extends Serializable> {

	public Boolean cadastrar (T entity) throws TipoChaveNaoEncontradaException,DAOException;
	
	public void excluir(E valor) throws DAOException;
	
	public void alterar(T entity) throws TipoChaveNaoEncontradaException,DAOException;
	
	public T consultar(E valor) throws MaisdeUmRegistroException,TableException,DAOException;
	
	public Collection<T> buscarTodos() throws DAOException;
}
