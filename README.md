# ClientServerProject

Projeto de Redes de Computadores

Este projeto foi realizado no âmbito da cadeira de Redes de Computadores, em que consiste em um paradigma Cliente Servidor com as operações GET, POST retornando a resposta do servidor (Ex: 400,404 500 etc...)

## Instruções para a compilação, execução e testagem

O trabalho foi produzido utilizando o java openjdk 17.0.3 2022-04-19 LTS

Este projeto contém 3 classes, para testar e avaliar este projeto é necessário seguir as seguintes instruções:

1. Compilar a classe Handler utilizando o comando, "javac Handler.java"

2. Compliar as outras duas classes, "javac MyHttpClient.java MyHttpServer.java"

3. Se a compilação correr bem, colocar no mesmo diretório e compilar a classe TestMp1 “$ javac TestMp1.java”

4. Abrir um terminal e lançar a classe servidor “$ java MyHttpServer 5555” (neste exemplo a classe MyHttpServer pede um número de porto TCP onde o servidor ficará aceitando conexões como parâmetro).  

5. Abrir um outro terminal e lançar a classe de teste “$ java TestMp1 localhost 5555”  

6. Usar o menu interativo da classe de teste para gerar os pedidos do cliente e as respostas do servidor, e então verificar a validade dos pedidos/resposta

7. Para testar a funcionalidade multithread, sugerimos lançar diferentes instâncias da classe TestMp1 até atingir o limite especificado no enunciado e tentar lançar e usar uma ulterior instância da classe para interagir com o servidor. Um servidor deverá reagir com um erro aos pedidos da última instância lancada até que todas as outras estejam ativas.

### Trabalho realizado por

- João Pereira @fc58189
- Daniel Nunes @fc58257
- André Reis @fc58192
