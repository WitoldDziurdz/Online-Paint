package server;


import services.ConsoleHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private static int port = 4040;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server start.");
            while (true){
                System.out.println("Waiting for a new client...");
                Socket socket = serverSocket.accept();
                Thread thread  = new Thread(new Handler(socket));
                thread.start();
            }
        } catch (IOException e) {
            ConsoleHelper.write(e.getMessage());
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                ConsoleHelper.write(e.getMessage());
            }
        }
    }


}
