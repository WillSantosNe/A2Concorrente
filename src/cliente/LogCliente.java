package cliente;

import carro.Carro;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogCliente {
    private static final String CAMINHO = "src/logs/log_cliente.txt";

    public static synchronized void registraCompra(int clienteId, String loja, Carro carro) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CAMINHO, true))) {
            writer.printf("CLIENTE: %d, LOJA: %s, ID: %d, COR: %s, TIPO: %s\n",
                    clienteId, loja,
                    carro.getId(), carro.getCor(), carro.getTipo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
