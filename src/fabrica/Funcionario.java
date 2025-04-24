package fabrica;

public class Funcionario implements Runnable {
    private final int idFuncionario;
    private final EstacaoMontagem estacao;

    public Funcionario(int idFuncionario, EstacaoMontagem estacao) {
        this.idFuncionario = idFuncionario;
        this.estacao = estacao;
    }

    @Override
    public void run() {
        try {
            while (true) {
                boolean produzido = estacao.produzirCarro(idFuncionario);
                if (!produzido) break;
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
