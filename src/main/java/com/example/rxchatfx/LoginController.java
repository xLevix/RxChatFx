package com.example.rxchatfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.DataOutputStream;
import java.net.Socket;

public class LoginController {

    @FXML
    private Button nick_send;

    @FXML
    private TextField username;

    Socket socket = null;
    DataOutputStream out = null;

    @FXML
    void Connect(ActionEvent event) {
        try {
            if (username.getText().isEmpty()) return;
            socket = new Socket("localhost", 1234);
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(username.getText());
            Client.startChat(socket, username.getText());
            username.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
