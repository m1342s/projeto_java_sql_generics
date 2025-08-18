package domain;

import java.math.BigDecimal;

import anotacao.ColunaTabela;
import anotacao.Tabela;
import anotacao.TipoChave;
import dao.Persistente;

@Tabela("TB_PRODUTO")
public class Produto implements Persistente {
	
	@ColunaTabela(dbName="id",setJavaName="setId")
	private Long id;
	
	@TipoChave("getCodigo")
	@ColunaTabela(dbName="codigo",setJavaName="setCodigo")
	private String codigo;
	
	@ColunaTabela(dbName="nome",setJavaName="setNome")
	private String nome;
	
	@ColunaTabela(dbName="descricao",setJavaName="setdescricao")
	private String descricao;
	
	@ColunaTabela(dbName="valor",setJavaName="setValor")
	private BigDecimal valor;
	
	

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

}
