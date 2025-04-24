package fabrica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import carro.Carro;

public class DespachanteFabricaServer implements Runnable {
    private final EsteiraFabrica esteira;
    private final int porta = 7000;

    public DespachanteFabricaServer(EsteiraFabrica esteira) {
        this.esteira = esteira;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("[DespachanteFabrica] Servidor iniciado na porta " + porta);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> tratarLoja(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tratarLoja(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)
        ) {
            String requisicao = in.readLine();

            if ("GET_CAR".equals(requisicao)) {
                synchronized (esteira) {
                    if (esteira.producaoEncerrada() && esteira.estaVazia()) {
                        out.println("END");
                        return;
                    }
                }

                Carro carro = esteira.retirar();
                if (carro == null) {
                    out.println("EMPTY");
                } else {
                    String dados = String.format("CAR;%d;%s;%s;%d;%d;%d",
                            carro.getId(),
                            carro.getCor(),
                            carro.getTipo(),
                            carro.getIdEstacao(),
                            carro.getIdFuncionario(),
                            carro.getPosicaoEsteiraFabrica());

                    out.println(dados);
                }
            } else {
                out.println("INVALID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
