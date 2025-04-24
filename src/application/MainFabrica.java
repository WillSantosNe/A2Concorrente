package application;

import fabrica.EstacaoMontagem;
import fabrica.EsteiraFabrica;
import fabrica.Estoque;
import fabrica.Funcionario;

public class MainFabrica {
    public static void main(String[] args) {
        int totalEstacoes = 4;
        int funcionariosPorEstacao = 5;
        int totalFuncionarios = totalEstacoes * funcionariosPorEstacao;

        Estoque estoque = new Estoque(500);
        EsteiraFabrica esteira = new EsteiraFabrica();

        Thread[] funcionarios = new Thread[totalFuncionarios];
        int indice = 0;

        for (int idEstacao = 0; idEstacao < totalEstacoes; idEstacao++) {

            EstacaoMontagem estacao = new EstacaoMontagem(idEstacao, esteira, estoque);
            
            for (int idFuncionario = 0; idFuncionario < funcionariosPorEstacao; idFuncionario++) {
                Funcionario func = new Funcionario(idFuncionario, estacao);
                funcionarios[indice] = new Thread(func, "Funcionario-E" + idEstacao + "-F" + idFuncionario);
                funcionarios[indice].start();
                indice++;
            }
        }

        for (Thread t : funcionarios) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        esteira.encerrarProducao();
        System.out.println("[MainFabrica] Produção finalizada. Todos os carros foram produzidos.");
    }
}

