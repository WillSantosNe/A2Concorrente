package loja;

import carro.Carro;

public class Loja {
    private final String nome;
    private final EsteiraLoja esteira;
    private final Thread compradorFabrica;
    private boolean comprasEncerradas = false;

    public Loja(String nome, String hostFabrica) {
        this.nome = nome;
        this.esteira = new EsteiraLoja();
        this.compradorFabrica = new Thread(new CompradorFabrica(this, hostFabrica));
        this.compradorFabrica.start();
    }

    public synchronized Carro venderCarro() throws InterruptedException {
        while (esteira.estaVazia()) {
            if (comprasEncerradas) return null;
            wait();
        }

        Carro carro = esteira.retirar();
        return carro;
    }

    public synchronized void notificarNovoCarro() {
        notifyAll();
    }

    public void encerrarCompras() {
        comprasEncerradas = true;
        synchronized (this) {
            notifyAll();
        }
    }

    public boolean comprasFinalizadas() {
        return comprasEncerradas && esteira.estaVazia();
    }

    public String getNome() {
        return nome;
    }

    public EsteiraLoja getEsteira() {
        return esteira;
    }

    public void aguardarCompradorFinalizar() {
        try {
            compradorFabrica.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
}
