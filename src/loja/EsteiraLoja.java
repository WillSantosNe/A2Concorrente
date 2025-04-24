package loja;

import carro.Carro;

public class EsteiraLoja {
	private static final int CAPACIDADE = 40;

	private final Carro[] buffer = new Carro[CAPACIDADE];
	private int inicio = 0;
	private int fim = 0;
	private int total = 0;

	public synchronized int adicionar(Carro carro) throws InterruptedException {
		while (total == CAPACIDADE) {
			wait();
		}
		buffer[fim] = carro;
		int posicao = fim;
		fim = (fim + 1) % CAPACIDADE;
		total++;
		notifyAll();
		return posicao;
	}

	public synchronized Carro retirar() throws InterruptedException {
		while (total == 0) {
			wait();
		}
		Carro carro = buffer[inicio];
		buffer[inicio] = null;
		inicio = (inicio + 1) % CAPACIDADE;
		total--;
		notifyAll();
		return carro;
	}

	public synchronized boolean estaVazia() {
		return total == 0;
	}

	public synchronized boolean estaCheia() {
		return total == CAPACIDADE;
	}
}
