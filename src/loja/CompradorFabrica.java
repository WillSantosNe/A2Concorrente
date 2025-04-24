package loja;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import carro.Carro;
import carro.CorCarro;
import carro.TipoVeiculo;
import fabrica.LogFabrica;

public class CompradorFabrica implements Runnable {
    private final Loja loja;
    private final String enderecoFabrica;
    private final int portaFabrica = 7000;
    private boolean encerrado = false;

    public CompradorFabrica(Loja loja, String enderecoFabrica) {
        this.loja = loja;
        this.enderecoFabrica = enderecoFabrica;
    }

    @Override
    public void run() {
        while (!encerrado) {
            try (Socket socket = new Socket(enderecoFabrica, portaFabrica);
                 PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                saida.println("GET_CAR");
                String resposta = entrada.readLine();

                if ("END".equals(resposta)) {
                    encerrado = true;

                } else if ("EMPTY".equals(resposta)) {
                    Thread.sleep(200);

                } else if (resposta != null && resposta.startsWith("CAR;")) {
                    String[] dados = resposta.split(";");
                    int id = Integer.parseInt(dados[1]);
                    CorCarro cor = CorCarro.valueOf(dados[2]);
                    TipoVeiculo tipo = TipoVeiculo.valueOf(dados[3]);
                    int idEstacao = Integer.parseInt(dados[4]);
                    int idFuncionario = Integer.parseInt(dados[5]);
                    int posicaoFabrica = Integer.parseInt(dados[6]);

                    Carro carro = new Carro(idEstacao, idFuncionario);
                    carro.setPosicaoEsteiraFabrica(posicaoFabrica);

                    int posicaoLoja = loja.getEsteira().adicionar(carro);
                    carro.setLojaDestino(loja);
                    carro.setPosicaoEsteiraLoja(posicaoLoja);

                    LogFabrica.registraVenda(carro, loja.getNome(), posicaoLoja);
                    System.out.println("[CompradorFabrica] Veículo " + id + " (" + cor + ", " + tipo + ") recebido e armazenado na esteira da loja na posição " + posicaoLoja + ".");

                    loja.notificarNovoCarro();
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignorado) {}
            }
        }
        System.out.println("[CompradorFabrica] Encerrado. Loja nao recebera mais carros.");
    }
}
