package br.edu.utfpr.td.cotsi.transacoes.consumer;

public class Transacao {

	private String codigo;
	private String cedente;
	private String pagador;
	private Double valor;
	private String vencimento;

	

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCedente() {
		return cedente;
	}

	public void setCedente(String cedente) {
		this.cedente = cedente;
	}

	public String getPagador() {
		return pagador;
	}

	public void setPagador(String pagador) {
		this.pagador = pagador;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getVencimento() {
		return vencimento;
	}

	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}

	@Override
	public String toString() {
		return "Transacao [codigo=" + codigo + ", cedente=" + cedente + ", pagador=" + pagador + ", valor=" + valor
				+ ", vencimento=" + vencimento + "]";
	}
	
	

}
