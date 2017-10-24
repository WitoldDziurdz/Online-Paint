package client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectionController {
    private MainController mainController;
    private Stage stage;

    @FXML
    public TextField userName;

    @FXML
    public TextField ip;

    @FXML
    public TextField port;

    private boolean checkName(String string){
        if (string == null || string == "") return false;
        return true;
    }

    private boolean checkIp(String ip){
        if (ip == null || ip == "") return false;
        return true;
    }

    private boolean checkPort(String ip){
        if (ip == null || ip == "") return false;
        return true;
    }

    private String getName(){
        String name = userName.getText();
        if(checkName(name)){
            return  name;
        }else {
            return "user" + Math.random()*1000;
        }
    }

    private String getIp(){
        String ip = this.ip.getText();
        if(checkIp(ip)){
            return  ip;
        }else {
            return "localhost";
        }
    }


    private int getPort(){
        String portText = this.port.getText();
        if(checkPort(portText)){
            return Integer.parseInt(portText);
        }else {
            return 4040;
        }
    }

    public void handlerGetConnect(ActionEvent event) {
        String name = getName();
        String ip = getIp();
        int port = getPort();
        mainController.connectToServer(ip, port, name);
        stage.close();
    }

    public void handlerCancelConnect(ActionEvent event) {
        stage.close();
    }

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void setStage( Stage stage){
        this.stage = stage;
    }
}
