# ClientServerProject

Computer Networks Project

This project was carried out within the scope of the Computer Networks course, which consists of a Client Server paradigm with GET, POST operations returning the server's response (Ex: 400,404 500 etc...)

## Instructions for compiling, running and testing

The work was produced using java openjdk 17.0.3 2022-04-19 LTS

This project contains 3 classes, to test and evaluate this project it is necessary to follow the following instructions:

1. Compile a Handler class using the command, "javac Handler.java"

2. Compile as other two classes, "javac MyHttpClient.java MyHttpServer.java"

3. If the compilation goes well, put it in the same directory and compile the TestMp1 class “$javac TestMp1.java”

4. Open a terminal and launch the server class “$ java MyHttpServer 5555” (in this example the MyHttpServer class asks for a TCP port number where the server will accept connections as a parameter).

5. Open another terminal and launch the test class “$java TestMp1 localhost 5555”

6. Use the test class interactive menu to generate client requests and server responses, and then check the validity of the requests/response

7. To test multithreaded functionality, we suggest launching different instances of the TestMp1 class until reaching the advertised limit and trying to launch and use a later instance of the class to interact with the server. A server should react with an error to requests from the last launched instance until all others are up.

### Work done by

- João Pereira @fc58189
- Daniel Nunes @fc58257
- André Reis @fc58192

The final grade of this project was 16.5/20.
