import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que cria um servidor que recebe pedidos de varios clientes, com uma
 * limitaçao de 5 por vez (ativos)
 * Esta tambem ira verificar os pedidos e enviar a reposta ao cliente
 *
 * @author Andre Reis 58192
 * @author Daniel Nunes 58257
 * @author Joao Pereira 58189
 */
public class MyHttpServer {

    private static List<Handler> threads = Collections.synchronizedList(new ArrayList<Handler>());

    /**
     * A main da classe server que vai aceitar todas as conexoes pedidas ao server,
     * esta vai limitar o numero de clientes ativos por 5
     *
     *
     * @param args A lista de argumentos, a mesma vai conter o numero da porta de
     *             comunicacao
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Argumentos inválidos");
        }
        try {
            int portNumber = Integer.parseInt(args[0]);
            int counter = 1;
            ServerSocket sSocket = new ServerSocket(portNumber);
            System.out.println("Server opened");
            while (true) {
                Handler handler = new Handler(sSocket.accept(), threads, counter);
                Thread thread = new Thread(handler);
                thread.start();
                threads.add(handler);
                counter++;
            }
        } catch (Exception e) {
            System.out.println("Algo correu mal");
        }
    }
}
