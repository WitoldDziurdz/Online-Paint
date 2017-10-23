package server;

import services.Connection;
import services.ConsoleHelper;
import services.Message;
import services.TypeMessage;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Handler implements Runnable {
    private static Map<String,Connection> connections = new ConcurrentHashMap<>();
    private Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        String name = null;
        SocketAddress address = socket.getRemoteSocketAddress();
        ConsoleHelper.write("A new connection with a remote address: " + address);
        Connection connection = null;
        try {
            connection = new Connection(socket);
            name = getUserName(connection);
            sendMainLoop(connection,name);
        } catch (IOException e) {
            e.printStackTrace();
            ConsoleHelper.write("An error occurred while communicating with the remote address:" + address);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            ConsoleHelper.write("An error occurred while communicating with the remote address:" + address);
        }finally {
            if(name != null){
                connections.remove(name);
            }
            if(connection!=null){
                try {
                    connection.close();
                    ConsoleHelper.write("An error occurred while communicating with the remote address:" + address);
                    ConsoleHelper.write("The connection to the remote address is closed:" + address);
                } catch (IOException e) {
                    ConsoleHelper.write("An error occurred when closing the connection with the remote address:" + address);
                }
            }
        }
    }


    public boolean checkCorrectResponseName(Message message){
        if(message.getType()!= TypeMessage.USER_NAME) return false;
        if("" == message.getName()) return false;
        if(connections.containsKey( message.getName())){
            return false;
        }
        return true;
    }

    private String getUserName(Connection connection) throws IOException, ClassNotFoundException {
        String name = null;
        Message message = null;
        ConsoleHelper.write("Send client message with type NAME_REQUEST");
        connection.send(new Message(TypeMessage.NAME_REQUEST));
        message =  connection.receive();
        if(checkCorrectResponseName(message)){
            ConsoleHelper.write("Accepted userName with: " + message.getName());
            connections.put((String) message.getName(), connection);
            connection.send(new Message(TypeMessage.NAME_ACCEPTED));
            name = (String) message.getName();
        }else {
            name = getUserName(connection);
        }
        return name;
    }

    private void sendMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
        while (true){
            Message message = connection.receive();
            if(message.getType() == TypeMessage.DATA){
                sendBroadcastMessage(message);
            }else {
                ConsoleHelper.write("Incorrect data type");
            }
        }
    }

    public static void sendBroadcastMessage(Message message) {
        for (Connection connection : connections.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                ConsoleHelper.write("An error occured while sending the message");
            }
        }

    }

}
