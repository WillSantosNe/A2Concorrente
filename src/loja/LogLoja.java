package loja;

import carro.Carro;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogLoja {
    private static String caminho(String nomeLoja) {
        return "src/logs/log_loja_" + nomeLoja + ".txt";
    }
    

    public static synchronized void registraCompra(Carro carro, int posLoja, String loja) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminho(loja), true))) {
            writer.printf("LOJA: %s, ID: %d, COR: %s, TIPO: %s, ESTACAO: %d, FUNC: %d, POS_EST: %d, POS_LOJA: %d\n",
                    loja,
                    carro.getId(), carro.getCor(), carro.getTipo(),
                    carro.getIdEstacao(), carro.getIdFuncionario(),
                    carro.getPosicaoEsteiraFabrica(), posLoja);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void registraVenda(String loja, Carro carro, int clienteId) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminho(loja), true))) {
            writer.printf("VENDA -> LOJA: %s, CLIENTE: %d, ID: %d, COR: %s, TIPO: %s\n",
                    loja, clienteId,
                    carro.getId(), carro.getCor(), carro.getTipo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
