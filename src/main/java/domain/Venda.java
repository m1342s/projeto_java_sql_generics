package domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import anotacao.ColunaTabela;
import anotacao.Tabela;
import dao.Persistente;

@Tabela("TB_VENDA")
public class Venda implements Persistente {
	
	public enum Status {
		INICIADA, CONCLUIDA,CANCELADA;
		
		public static Status getByName(String value) {
			for(Status status : Status.values()) {
				if(status.name().equals(value)) {
					return status;
				}
			}
			return null;
		}
	}
	
	@ColunaTabela(dbName="id",setJavaName="setId")
	private Long id;
	
	@ColunaTabela(dbName="codigo",setJavaName="setCodigo")
	private String codigo;
	
	@ColunaTabela(dbName="id_cliente_fk",setJavaName="setIdClienteFk")
	private Cliente cliente;
	
	private Set<ProdutoQuantidade> produtos;
	

	@ColunaTabela(dbName="valor_total",setJavaName="setValorTotal")
	private BigDecimal valorTotal;
	
	@ColunaTabela(dbName="data_venda",setJavaName="setDataVenda")
	private Instant dataVenda;
	
	@ColunaTabela(dbName="status_venda",setJavaName="setstatusVenda")
	private Status status;
	
	public Venda() {
		produtos=new HashSet<>();
	}
	
	
	
	

	public String getCodigo() {
		return codigo;
	}





	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}





	public Cliente getCliente() {
		return cliente;
	}





	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}





	public Set<ProdutoQuantidade> getProdutos() {
		return produtos;
	}





	public void setProdutos(Set<ProdutoQuantidade> produtos) {
		this.produtos = produtos;
	}





	public BigDecimal getValorTotal() {
		return valorTotal;
	}





	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}





	public Instant getDataVenda() {
		return dataVenda;
	}





	public void setDataVenda(Instant dataVenda) {
		this.dataVenda = dataVenda;
	}





	public Status getStatusVenda() {
		return status;
	}





	public void setStatusVenda(Status statusVenda) {
		this.status = statusVenda;
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
	
	public void adicionarProduto(Produto produto, Integer quantidade) {
		validarStatus();
		Optional<ProdutoQuantidade> op = produtos.stream().filter(filter -> filter.getProduto().getCodigo().equals(produto.getCodigo())).findAny();
	    if(op.isPresent()) {
	    	ProdutoQuantidade produtQtd=op.get();
	    	if(produtQtd.getQuantidade()>quantidade) {
	    		produtQtd.remover(quantidade);
	    		recalcularTotalVenda();
	    		
	    	} else {
	    		produtos.remove(op.get());
	    		recalcularTotalVenda();

	    	}
	    }
	}
	
	public void removerProduto(Produto produto, Integer quantidade) {
		validarStatus();
		Optional<ProdutoQuantidade> op =
				produtos.stream().filter(filter -> filter.getProduto().getCodigo().equals(produto.getCodigo())).findAny();
	if(op.isPresent()) {
		ProdutoQuantidade produtpQtd=op.get();
		if(produtpQtd.getQuantidade()>quantidade) {
			produtpQtd.remover(quantidade);
			recalcularTotalVenda();
		} else {
			produtos.remove(op.get());
			recalcularTotalVenda();

			
		}
	}
	
	}
	
	public void removerTodosProdutos() {
		validarStatus();
		produtos.clear();
		valorTotal = BigDecimal.ZERO;
	}
	
	public Integer getQuantidadeTotalProdutos() {
		int result = produtos.stream().reduce(0,(partialCountResult, prod) -> partialCountResult + prod.getQuantidade(), Integer::sum);
	    return result;
	}


	private void recalcularTotalVenda() {
		BigDecimal valorTotal = BigDecimal.ZERO;
		for(ProdutoQuantidade prod : this.produtos) {
			valorTotal = valorTotal.add(prod.getValorTotal());
			
		}
		this.valorTotal=valorTotal;
	}





	private void validarStatus() {
		if(this.status==Status.CONCLUIDA) {
			throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
		}
		
	}
	

}
