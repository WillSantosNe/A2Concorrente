package fabrica;

public class Estoque {
	private int capacidade;
	private int quantidadeDisponivel;

	public Estoque(int capacidade) {
		this.capacidade = capacidade;
		this.quantidadeDisponivel = capacidade;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public synchronized int getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}

	public synchronized void setQuantidadeDisponivel(int quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

	public synchronized boolean consumir() {
		if (quantidadeDisponivel <= 0) {
			return false;
		}
		quantidadeDisponivel--;
		return true;
	}

}
