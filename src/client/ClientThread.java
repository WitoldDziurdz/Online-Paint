package client;


import client.controllers.MainController;
import services.Connection;
import services.ConsoleHelper;
import services.Message;
import services.TypeMessage;

import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable{
    private volatile boolean clientConnected = false;
    private Socket socket;
    private String name;
    private MainController mainController;
    public ClientThread(MainController mainController, String ip, int port, String name) {
        try {
            this.socket = new Socket(ip,port);
            this.name = name;
            this.mainController = mainController;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveAndSendUserName(Connection connection) throws IOException, ClassNotFoundException {
        while (true){
            Message message = connection.receive();
            TypeMessage type = message.getType();
            if(type == TypeMessage.NAME_REQUEST){
                connection.send(new Message(name,TypeMessage.USER_NAME));
            }else if(type == TypeMessage.NAME_ACCEPTED){
                clientConnected = true;
                break;
            }else {
                throw new IOException("Unexpected MessageType");
            }
        }
    }

    private void sendMainLoopMessage(Connection connection){
        while (clientConnected){
            try {
                Message message = new Message(name, TypeMessage.DATA, mainController.sharpToSend.take());
                if(!clientConnected) break;
                connection.send(message);
            }catch (IOException e) {
                clientConnected  = false;
                e.printStackTrace();
                ConsoleHelper.write(e.getMessage());
                ConsoleHelper.write("Error send with message");
            } catch (InterruptedException e) {
                clientConnected  = false;
                e.printStackTrace();
                ConsoleHelper.write(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        try (Connection connection = new Connection(socket)){
            receiveAndSendUserName(connection);
            ReceiveThread receiveThread = new ReceiveThread(connection);
            receiveThread.start();
            sendMainLoopMessage(connection);
        } catch (IOException e) {
            clientConnected  = false;
        } catch (ClassNotFoundException e) {
           clientConnected = false;
        }
    }

    private class ReceiveThread extends Thread{

        private Connection connection;

        public ReceiveThread(Connection connection){
            this.connection = connection;
        }

        @Override
        public void run() {
             while (clientConnected){
                 try {
                     if(!clientConnected) break;
                     Message message = connection.receive();
                     TypeMessage type = message.getType();
                     if(type == TypeMessage.DATA){
                         mainController.drawSharp(message.getData());
                     }else {
                         throw new IOException("Unexpected MessageType");
                     }
                 } catch (IOException e) {
                     clientConnected = false;
                 } catch (ClassNotFoundException e) {
                    clientConnected = false;
                 }
             }
        }
    }

}
