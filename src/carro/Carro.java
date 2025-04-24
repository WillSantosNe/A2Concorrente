package carro;

import loja.Loja;

public class Carro {
	private static int idCounter = 1;
	private static int corIndex = 0;
	private static int tipoIndex = 0;

	private int id;
	private CorCarro cor;
	private TipoVeiculo tipo;
	private int idEstacao;
	private int idFuncionario;
	private int posicaoEsteiraFabrica;
	private Loja lojaDestino;
	private int posicaoEsteiraLoja;

	public Carro(int idEstacao, int idFuncionario) {
		this.id = gerarId();
		this.cor = gerarCor();
		this.tipo = gerarTipo();
		this.idEstacao = idEstacao;
		this.idFuncionario = idFuncionario;
	}

	private static synchronized int gerarId() {
		return idCounter++;
	}

	private static synchronized CorCarro gerarCor() {
		return CorCarro.proximaCor(corIndex++);
	}

	private static synchronized TipoVeiculo gerarTipo() {
		return TipoVeiculo.proximoTipo(tipoIndex++);
	}

	public int getId() {
		return id;
	}

	public CorCarro getCor() {
		return cor;
	}

	public TipoVeiculo getTipo() {
		return tipo;
	}

	public int getIdEstacao() {
		return idEstacao;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public int getPosicaoEsteiraFabrica() {
		return posicaoEsteiraFabrica;
	}

	public Loja getLojaDestino() {
		return lojaDestino;
	}

	public int getPosicaoEsteiraLoja() {
		return posicaoEsteiraLoja;
	}

	public void setLojaDestino(Loja lojaDestino) {
		this.lojaDestino = lojaDestino;
	}

	public void setPosicaoEsteiraLoja(int posicao) {
		this.posicaoEsteiraLoja = posicao;
	}

	public void setPosicaoEsteiraFabrica(int posicaoEsteiraFabrica) {
		this.posicaoEsteiraFabrica = posicaoEsteiraFabrica;
	}

}