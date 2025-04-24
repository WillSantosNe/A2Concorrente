package loja;

import java.util.ArrayList;
import java.util.List;

import cliente.Cliente;

public class MainLoja {
    public static void main(String[] args) throws InterruptedException {
    	
    	new java.io.File("src/logs").mkdirs();

    	
        final int NUM_LOJAS = 3;
        final int NUM_CLIENTES = 20;
        final String HOST_FABRICA = "localhost";

        List<Loja> lojas = new ArrayList<>();
        for (int i = 1; i <= NUM_LOJAS; i++) {
            lojas.add(new Loja("Loja" + i, HOST_FABRICA));
        }

        List<Thread> clientes = new ArrayList<>();
        for (int i = 1; i <= NUM_CLIENTES; i++) {
            Cliente cliente = new Cliente(i, lojas);
            Thread t = new Thread(cliente, "Cliente-" + i);
            clientes.add(t);
            t.start();
        }

        // Espera os compradores da fábrica terminarem
        Thread.sleep(2000); // espera 2 segundos após produção acabar

        for (Loja loja : lojas) {
            loja.aguardarCompradorFinalizar(); // espera o recebimento de carros parar
            loja.encerrarCompras();            // só então avisa os clientes
        }
     

        // Espera os clientes terminarem
        for (Thread t : clientes) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("[MainLoja] Todos os clientes finalizaram suas compras.");

    }
}
