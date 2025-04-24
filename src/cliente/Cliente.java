package cliente;

import java.util.List;
import java.util.Random;

import carro.Carro;
import loja.LogLoja;
import loja.Loja;

public class Cliente implements Runnable {
    private final int id;
    private final List<Loja> lojas;
    private final GaragemCliente garagem;
    private final Random random = new Random();

    public Cliente(int id, List<Loja> lojas) {
        this.id = id;
        this.lojas = lojas;
        this.garagem = new GaragemCliente();
    }

    @Override
    public void run() {
        while (true) {
            Loja loja = lojas.get(random.nextInt(lojas.size()));

            try {
                Carro carro = loja.venderCarro();

                if (carro != null) {
                    garagem.adicionar(carro);
                    LogCliente.registraCompra(id, loja.getNome(), carro);
                    LogLoja.registraVenda(loja.getNome(), carro, id);	
                    
                    System.out.println("[Cliente " + id + "] comprou o carro " + carro.getId() +
                            " (" + carro.getTipo() + ", " + carro.getCor() + ") da " + loja.getNome());
                } else {
                    if (todasLojasEncerradas()) {
                        System.out.println("[Cliente " + id + "] Encerrando. Nenhuma loja com carros.");
                        break;
                    }
                    Thread.sleep(200);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private boolean todasLojasEncerradas() {
        for (Loja loja : lojas) {
            if (!loja.comprasFinalizadas()) return false;
        }
        return true;
    }
}
