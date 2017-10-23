package services;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Closeable{
    public final Socket socket;
    public final ObjectOutputStream outputStream;
    public final ObjectInputStream inputStream;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void send(Message message) throws IOException {
        synchronized (outputStream){
            outputStream.writeObject(message);
        }
    }


    public Message receive() throws IOException, ClassNotFoundException {
        synchronized (inputStream){
            return (Message)inputStream.readObject();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connection that = (Connection) o;

        if (socket != null ? !socket.equals(that.socket) : that.socket != null) return false;
        if (inputStream != null ? !inputStream.equals(that.inputStream) : that.inputStream != null) return false;
        return outputStream != null ? outputStream.equals(that.outputStream) : that.outputStream == null;
    }

    @Override
    public int hashCode() {
        int result = socket != null ? socket.hashCode() : 0;
        result = 31 * result + (inputStream != null ? inputStream.hashCode() : 0);
        result = 31 * result + (outputStream != null ? outputStream.hashCode() : 0);
        return result;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
}
