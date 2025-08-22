package dao.generics;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import anotacao.TipoChave;
import dao.Persistente;
import exceptions.TipoChaveNaoEncontradaException;

public abstract class GenericDAO<T extends Persistente, E extends Serializable> implements IGenericDAO<T,E> {
	
	public abstract Class<T> getTipoClasse();
	
	public abstract void atualizarDados(T entity, T entityCadastrado);
	
	protected abstract String getQueryInsercao();
	
	protected abstract String getQueryExclusao();
	
	protected abstract String getQueryAtualizacao();
	
	protected abstract void setParametrosQueryInsercao(PreparedStatement stmInsert, T entity) throws SQLException;
	
	protected abstract void setParametrosQueryExclusao(PreparedStatement stmDelete, E valor) throws SQLException;
	
	protected abstract void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, T entity) throws SQLException;
	
	protected abstract void setParametrosQuerySelect(PreparedStatement stmUpdate, E valor) throws SQLException;
	
	public GenericDAO() {
		
	}
	
	public E getChave(T entity) throws TipoChaveNaoEncontradaException{
		Field[] fields = entity.getClass().getDeclaredFields();
		E returnValue = null;
		for(Field field : fields) {
			if(field.isAnnotationPresent(TipoChave.class)) {
				TipoChave tipoChave = field.getAnnotation(TipoChave.class);
				String nomeMetodo = tipoChave.value();
				
				try {
					Method method = entity.getClass().getMethod(nomeMetodo);
					returnValue = (E) method.invoke(entity);
					return returnValue;
					
				} catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e ) {
					e.printStackTrace();
					throw new TipoChaveNaoEncontradaException("Chave principal do objeto" + entity.getClass() + " não encontrada", e)
				}
			}
		}
		if(returnValue == null) {
			String msg = "Chave principal do objeto\" + entity.getClass() + \" não encontrada";
			System.out.print("****ERRO****" + msg);
		}
	}
	return null;
}
