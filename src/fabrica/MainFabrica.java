package fabrica;

public class MainFabrica {
    public static void main(String[] args) {
    	
    	new java.io.File("src/logs").mkdirs();

    	
        final int NUM_ESTACOES = 4;
        final int FUNCIONARIOS_POR_ESTACAO = 5;
        final int TOTAL_FUNCIONARIOS = NUM_ESTACOES * FUNCIONARIOS_POR_ESTACAO;

        Estoque estoque = new Estoque(500);
        EsteiraFabrica esteira = new EsteiraFabrica();

        Thread servidorDespacho = new Thread(new DespachanteFabricaServer(esteira));
        servidorDespacho.start();

        Thread[] threads = new Thread[TOTAL_FUNCIONARIOS];
        int threadIndex = 0;
 
        for (int i = 0; i < NUM_ESTACOES; i++) {
            EstacaoMontagem estacao = new EstacaoMontagem(i, esteira, estoque);
            for (int j = 0; j < FUNCIONARIOS_POR_ESTACAO; j++) {
                Funcionario funcionario = new Funcionario(j, estacao);
                threads[threadIndex] = new Thread(funcionario, "Funcionario-E" + i + "-F" + j);
                threads[threadIndex].start();
                threadIndex++;
            }
        }

        for (Thread t : threads) {
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
