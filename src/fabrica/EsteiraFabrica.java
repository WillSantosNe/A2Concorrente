package fabrica;

import carro.Carro;

public class EsteiraFabrica {
    private static final int CAPACIDADE = 40;

    private final Carro[] buffer = new Carro[CAPACIDADE];
    private int inicio = 0;
    private int fim = 0;
    private int total = 0;

    private boolean producaoEncerrada = false;

    public synchronized int adicionar(Carro carro) throws InterruptedException {
        while (total == CAPACIDADE) {
            if (producaoEncerrada) return -1;
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
            if (producaoEncerrada) return null;
            wait();
        }

        Carro carro = buffer[inicio];
        buffer[inicio] = null;
        inicio = (inicio + 1) % CAPACIDADE;
        total--;
        notifyAll();
        return carro;
    }

    public synchronized void encerrarProducao() {
        this.producaoEncerrada = true;
        notifyAll();
    }

    public synchronized boolean estaVazia() {
        return total == 0;
    }

    public synchronized boolean producaoEncerrada() {
        return producaoEncerrada;
    }
}
