package client.controllers;

import client.ClientThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sharps.Clear;
import sharps.Line;
import sharps.Sharp;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static javafx.stage.Modality.APPLICATION_MODAL;


public class MainController {
    public  BlockingQueue<Sharp> sharpToSend = new ArrayBlockingQueue<Sharp>(100000);

    private  double sizeLine = 1;
    private  double startX = 0;
    private  double startY = 0;
    boolean start = true;

    @FXML
    public Canvas board;
    @FXML
    public ColorPicker penColor;

    @FXML
    public void handleDownSizePen(ActionEvent actionEvent) {
        sizeLine +=1;
    }

    public void handleUpSizePen(ActionEvent actionEvent) {
        if(sizeLine > 1) {
            sizeLine -= 1;
        }
    }


    private void addToSend(Sharp sharp){
        try {
            sharpToSend.put(sharp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  synchronized void drawSharp(Sharp sharp){
        GraphicsContext gc = board.getGraphicsContext2D();
        sharp.draw(gc);
    }

    public void handleMouseDragged(MouseEvent mouseEvent) {
        double finishX = mouseEvent.getX();
        double finishY = mouseEvent.getY();
        Color color = penColor.getValue();
        if(!start) {
            Line line = new Line(color, sizeLine,startX,startY,finishX,finishY);
            addToSend(line);
            //drawSharp(line);
            startX = finishX;
            startY = finishY;
        }else{
            startY = finishY;
            startX = finishX;
            start = false;
        }
    }

    public void handleMouseRealised(MouseEvent mouseEvent) {
        start = true;
    }

    public void handlerFormConnectToServer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/connection.fxml"));
        Parent parent = loader.load();
        ConnectionController controller = loader.getController();
        controller.setMainController(this);
        Stage stage = new Stage();
        controller.setStage(stage);
        stage.initModality(APPLICATION_MODAL);
        stage.setTitle("Connect to server");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }


    public void connectToServer( String ip, int port,String name){
        ClientThread clientThread = new ClientThread(this,ip,port,name);
        new Thread(clientThread).start();
    }

    public void handlerClearCanvas(ActionEvent event) {
        addToSend(new Clear());
    }
}
