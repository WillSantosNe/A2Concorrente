package fabrica;

import carro.Carro;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogFabrica {
    private static final String CAMINHO_PRODUCAO = "src/logs/log_producao.txt";
    private static final String CAMINHO_VENDA = "src/logs/log_venda_fabrica.txt";

    public static synchronized void registraProducao(Carro carro, int posEsteira) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CAMINHO_PRODUCAO, true))) {
            writer.printf("ID: %d, COR: %s, TIPO: %s, ESTACAO: %d, FUNCIONARIO: %d, POS_EST: %d\n",
                    carro.getId(), carro.getCor(), carro.getTipo(),
                    carro.getIdEstacao(), carro.getIdFuncionario(), posEsteira);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void registraVenda(Carro carro, String loja, int posLoja) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CAMINHO_VENDA, true))) {
            writer.printf("ID: %d, COR: %s, TIPO: %s, ESTACAO: %d, FUNCIONARIO: %d, POS_EST: %d, LOJA: %s, POS_LOJA: %d\n",
                    carro.getId(), carro.getCor(), carro.getTipo(),
                    carro.getIdEstacao(), carro.getIdFuncionario(),
                    carro.getPosicaoEsteiraFabrica(), loja, posLoja);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}