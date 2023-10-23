import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * Classe que vai servir como um ponto de ligacao entre cliente e servidor
 *
 * @author Andre Reis 58192
 * @author Daniel Nunes 58257
 * @author Joao Pereira 58189
 */
public class Handler implements Runnable {

    private Socket client;
    private static final String GET_HEADER = "GET /";
    private static final String VERSION_HEADER = " HTTP/1.1\r";
    private static final String POST_HEADER = "POST /";
    private Boolean checkUrl = true;
    private List<Handler> threads;
    private int count;
    private int status;// Se for -1 a conexao acabou

    /**
     * Constroi um Handler e inicializa variaveis
     *
     * @param clientSocket  socket do cliente
     * @param activeThreads numero de threads ativas
     * @param counter       contador total de threads
     */
    public Handler(Socket clientSocket, List<Handler> threads, int counter) {
        this.client = clientSocket;
        this.threads = threads;
        this.count = counter;
    }

    @Override
    /**
     * Metodo que vai ser a conexao entre o server e client, vai ler os pedidos e
     * verificar assim como mandar uma resposta ao client
     */
    public void run() {
        try {
            System.out.println("Client number: " + this.count);
            while (!this.client.isClosed() && this.status != -1) {
                BufferedReader input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
                PrintWriter output = new PrintWriter(this.client.getOutputStream());
                String pedido = reader(input);
                if (!pedido.equals("")) {
                    System.out.println("Client number: " + this.count + " send");
                    System.out.println(pedido);
                }
                if (this.threads.indexOf(this) > 4) { // verificacao numero de ligaçoes ativas
                    output.print("HTTP/1.1 503 SERVICE UNAVAILABLE\r\n");
                    output.print("\r\n");
                    output.flush();
                } else {
                    if (pedido.startsWith("GET")) { // respostas para GET
                        if (verifyGET(pedido)) {
                            output.print("HTTP/1.1 200 OK\r\n");
                            output.print("\r\n");
                            output.print(readFile());
                            output.flush();
                        } else {
                            if (checkUrl) {
                                output.print("HTTP/1.1 400 BAD REQUEST\r\n");
                            } else {
                                output.print("HTTP/1.1 404 NOT FOUND\r\n");
                            }
                            output.print("\r\n");
                            output.flush();
                        }
                        checkUrl = true;
                    } else if (pedido.startsWith("POST")) { // respostas para POST
                        if (verifyPost(pedido)) {
                            output.print("HTTP/1.1 200 OK\r\n");
                            output.print("\r\n");
                            output.print(readFile());
                            output.flush();
                        } else {
                            output.print("HTTP/1.1 400 BAD REQUEST\r\n");
                            output.print("\r\n");
                            output.flush();
                        }
                    } else {
                        output.print("HTTP/1.1 501 NOT IMPLEMENTED\r\n");
                        output.print("\r\n");
                        output.flush();
                    }
                }
            }
        } catch (Exception e) {
        }
        System.out.println("Client number " + this.count + " disconected");
        threads.remove(this);
    }

    /**
     * Funcao que vai ler as informacoes presentes num bufferedReader e guardar numa
     * unica string, o mesmo tambem vai verificar quando uma conexao acabou.
     * Neste caso a informacao vao ser os pedidos de um cliente
     *
     * @param in O bufferedReader que contem as informacoes
     * @return Uma string que contem as informacoes de um pedido
     * @throws IOException
     */
    private String reader(BufferedReader in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch = in.read();
        while (ch != -1 && in.ready()) {// O buffer vai nao ficar ready pq fica vazio entao nao le o ultimo char
            sb.append((char) ch);
            ch = in.read();
        }
        if (ch != -1) {
            sb.append((char) ch);
        }
        this.status = ch;
        return sb.toString();
    }

    /**
     * Metodo que verifica se um pedido do tipo GET esta bem formado
     *
     * @param request pedido GET dado
     * @return True ou False se o pedido esta ou nao bem formatado
     */
    private Boolean verifyGET(String request) {
        String[] pedido = request.split("\n");
        if (pedido.length < 2) {
            return false;
        }
        String[] cabecalho = pedido[0].split(" ");
        if ((cabecalho[0] + " /").equals(GET_HEADER) && ((" " + cabecalho[2]).equals(VERSION_HEADER))) {
            if (cabecalho[1].equals("/index.html") || cabecalho[1].equals("/")) {
                if (pedido.length > 2) {
                    return true;
                }
            } else {
                checkUrl = false;
            }
        }
        return false;
    }

    /**
     * Metodo que verifica se um pedido do tipo POST esta bem formado
     *
     * @param request o pedido POST dado
     * @return True ou False se o pedido esta ou nao bem formado
     */
    private Boolean verifyPost(String request) {
        String[] pedido = request.split("\n");
        if (pedido.length < 2) {
            return false;
        }
        int separador = 0;
        for (int i = 0; i < pedido.length; i++) {
            if (pedido[i].equals("\r")) {// Porque foi splited
                separador = i;
            }
        }
        String[] cabecalho = pedido[0].split(" ");
        if ((cabecalho[0] + " /").equals(POST_HEADER)
                && ((" " + cabecalho[2]).equals(VERSION_HEADER))) {
            if (pedido[separador + 1] != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que ira ler o conteudo de um ficheiro e devolver uma String com o seu
     * conteudo
     *
     * @return String com o conteudo do ficheiro
     * @throws FileNotFoundException
     * @return Uma String que contem as informacoes presentes num arquivo
     */
    private String readFile() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("index.html"));
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        return sb.toString();
    }
}
