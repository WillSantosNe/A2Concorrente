package fabrica;

import java.util.concurrent.Semaphore;
import carro.Carro;

public class EstacaoMontagem {
    private final int idEstacao;
    private final Semaphore[] ferramentas;
    private final EsteiraFabrica esteira;
    private final Estoque estoque;

    private static final int TOTAL_FUNCIONARIOS = 5;
    private static final Semaphore controleAcessoPecas = new Semaphore(5);

    public EstacaoMontagem(int idEstacao, EsteiraFabrica esteira, Estoque estoque) {
        this.idEstacao = idEstacao;
        this.esteira = esteira;
        this.estoque = estoque;
        this.ferramentas = new Semaphore[TOTAL_FUNCIONARIOS];

        for (int i = 0; i < TOTAL_FUNCIONARIOS; i++) {
            ferramentas[i] = new Semaphore(1);
        }
    }

    public boolean produzirCarro(int idFuncionario) throws InterruptedException {
        int ferramentaEsquerda = idFuncionario;
        int ferramentaDireita = (idFuncionario + 1) % TOTAL_FUNCIONARIOS;

        if (idFuncionario < 4) {
            ferramentas[ferramentaEsquerda].acquire();
            ferramentas[ferramentaDireita].acquire();
        } else {
            ferramentas[ferramentaDireita].acquire();
            ferramentas[ferramentaEsquerda].acquire();
        }

        try {
            controleAcessoPecas.acquire();
            try {
                if (!estoque.consumir()) {
                    return false;
                }
            } finally {
                controleAcessoPecas.release();
            }

            Carro carro = new Carro(idEstacao, idFuncionario);
            int posicao = esteira.adicionar(carro);
            System.out.println("Carro " + carro.getId() + " adicionado na esteira da Fábrica");
            LogFabrica.registraProducao(carro, posicao);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            ferramentas[ferramentaEsquerda].release();
            ferramentas[ferramentaDireita].release();
        }
    }
}
