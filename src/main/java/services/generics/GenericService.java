package services.generics;

import java.io.Serializable;

import dao.Persistente;
import dao.generics.IGenericDAO;

public abstract class GenericService<T extends Persistente,E extends Serializable> implements IGenericService<T,E>{
	
	protected IGenericDAO <T,E> dao;
	
	public GenericService(IGenericDAO<T,E> dao) {
		this.dao = dao;
	}
	
}

