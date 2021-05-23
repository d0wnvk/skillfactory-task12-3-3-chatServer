package task1233;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    Socket socket;
    Scanner in;
    PrintStream out;
    ChatServer server;
    public static int clientCount = 1;
    int clientNumber;

    public Client(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        clientNumber = clientCount++;
        new Thread(this).start();
    }

    void receive(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            in = new Scanner(is);
            out = new PrintStream(os);

            out.println("Welcome to chat =)");
            String input = in.nextLine();

            while (!input.equals("bye")) {
                server.sendAll("Client # " + clientNumber + " says : " + input);
                input = in.nextLine();
            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
