package dao;

import dao.generics.IGenericDAO;
import domain.Venda;
import exceptions.DAOException;
import exceptions.TipoChaveNaoEncontradaException;

public interface IVendaDAO extends IGenericDAO<Venda,String> {
	
	public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException,DAOException;
	
	public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException,DAOException;
}
