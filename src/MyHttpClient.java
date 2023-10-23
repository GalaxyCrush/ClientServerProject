import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A classe faz pedidos http de tipos GET, POST, mal formados e nao
 * implementados pelo server
 * Envia-os para o server assim como recebe as respostas do mesmo
 * 
 * @author Andre Reis 58192
 * @author Daniel Nunes 58257
 * @author Joao Pereira 58189
 */
public class MyHttpClient {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String hostName;

    /**
     * Construtor da classe MyHttpClient onde vamos atribuir um host e uma porta ao
     * socket de comunicacao do socket
     * 
     * @param hostName   O nome do host
     * @param portNumber O numero da porta
     * @throws IOException
     */
    public MyHttpClient(String hostName, int portNumber) throws IOException {
        this.hostName = hostName;
        this.socket = new Socket(hostName, portNumber);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Metodo que recebe um ObjectName e faz um pedido do tipo GET ao server
     * 
     * @param ObjectName url dado pelo cliente
     * @throws IOException
     */
    public void getResource(String ObjectName) throws IOException {
        this.out.print("GET /" + ObjectName + " HTTP/1.1\r\n");
        this.out.print(this.hostName + "\r\n");
        this.out.print("Content-length:" + 0 + "\r\n");
        this.out.print("\r\n");
        this.out.flush();
        System.out.println(reader(this.in));
    }

    /**
     * Metodo que vai mandar um pedido POST ao server com informacoes relativas a um
     * login
     * 
     * @param data Um array de String que contem as informacoes de um login
     * @throws IOException
     */
    public void postData(String[] data) throws IOException {
        String url = "/simpleForm.html";
        String campo1 = data[0].replace(": ", "=");
        String campo2 = data[1].replace(": ", "=");
        String dados = (campo1 + "&" + campo2);
        this.out.print("POST " + url + " HTTP/1.1\r\n");
        this.out.print(this.hostName + "\r\n");
        this.out.print("Content-length:" + dados.length() + "\r\n");
        this.out.print("Content-Type: application/x-www-form-urlencoded\r\n");
        this.out.print("\r\n");
        this.out.print(dados);
        this.out.flush();
        System.out.println((reader(this.in)));
    }

    /**
     * Metodo que manda um pedido nao implementado no client ao server
     * 
     * @param wrongMethodName O nome que nao foi implememtado
     * @throws IOException
     */
    public void sendUnimplementedMethod(String wrongMethodName) throws IOException {
        this.out.print(wrongMethodName + "/  HTTP/1.1\r\n");
        this.out.print("\r\n");
        this.out.flush();
        System.out.println((reader(this.in)));
    }

    /**
     * Verifica requests malformados
     * 
     * @param type erro associado
     * @throws IOException
     */
    public void malformedRequest(int type) throws IOException {
        switch (type) {
            case 1:
                this.out.print("GET / HTTP/1.1"); // nao tem \r\n
                this.out.print("\r\n");
                this.out.flush();
                System.out.println((reader(this.in)));
                break;
            case 2:
                this.out.print("GET /  HTTP/1.1\r\n"); // espaco extra
                this.out.print("\r\n");
                this.out.flush();
                System.out.println((reader(this.in)));
                break;
            case 3:
                this.out.print("GET / HTTP\r\n"); // falta versao
                this.out.print("\r\n");
                this.out.flush();
                System.out.println((reader(this.in)));
                break;
        }
    }

    /**
     * Metodo que fecha as conexoes do client ao server
     *
     * @throws IOException
     */
    public void close() throws IOException {
        this.out.close();
        this.in.close();
        this.socket.close();
    }

    /**
     * Funcao que vai ler as informacoes presentes no bufferreader do input do
     * server
     * As informacoes presentes sao as respostas do server
     *
     * @param in O bufferreader onde estao as informacoes
     */
    public String reader(BufferedReader in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch = in.read();
        while (ch != -1 && in.ready()) {// O buffer vai nao ficar ready pq fica vazio entao nao le o ultimo char
            sb.append((char) ch);
            ch = in.read();
        }
        if (ch != -1) {
            sb.append((char) ch);
        }
        return sb.toString();
    }
}
