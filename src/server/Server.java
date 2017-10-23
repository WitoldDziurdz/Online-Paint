package server;

import services.Connection;
import services.ConsoleHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static int port = 4040;
    private static Map<Integer, Connection> connections = new ConcurrentHashMap<Integer, Connection>();

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
