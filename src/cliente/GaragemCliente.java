package cliente;

import carro.Carro;

import java.util.LinkedList;
import java.util.List;

public class GaragemCliente {
    private final List<Carro> carros;

    public GaragemCliente() {
        this.carros = new LinkedList<>();
    }

    public synchronized void adicionar(Carro carro) {
        carros.add(carro);
    }

    public synchronized List<Carro> getCarros() {
        return new LinkedList<>(carros);
    }

    public synchronized int getTotalCarros() {
        return carros.size();
    }
}
