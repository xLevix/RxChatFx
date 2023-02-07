package com.example.rxchatfx;

import io.reactivex.rxjava3.core.Observable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatController {

    @FXML
    private TextArea messages;

    @FXML
    private TextArea new_message;

    @FXML
    private Button send_message;

    Socket socket = null;
    DataOutputStream out = null;
    Thread thread = null;

    @FXML
    void Send(ActionEvent event) {
        if (!new_message.getText().isEmpty()) {
            try {
                out = new DataOutputStream(socket.getOutputStream());
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                out.writeUTF("Data: " + dateFormat.format(date) + "\n Wiadomosc: " + new_message.getText());
                new_message.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        Thread thread = new Thread(() -> {
            Observable.create(emitter -> {
                try {
                    while (true) {
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        String message = in.readUTF();
                        emitter.onNext(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).subscribe(s -> {
                retriveMessage(s.toString());
            });
        });
        thread.start();
    }

    private void retriveMessage(String message) {
        Platform.runLater(() -> {
            String chat = messages.getText();
            chat += "\n" + message;
            messages.setText(chat);
        });
    }

    public void kill() {
        try {
            thread.interrupt();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
