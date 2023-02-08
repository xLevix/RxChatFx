package com.example.rxchatfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Client extends Application {

    static Scene scene;
    static Stage stage;
    static ChatController chatController;

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("Login.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.show();
        Client.stage = stage;
    }

    static void startChat(Socket socket, String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("Chat.fxml"));
        fxmlLoader.load();
        chatController = fxmlLoader.getController();
        chatController.setSocket(socket);
        scene.setRoot(fxmlLoader.getRoot());
        stage.setHeight(650);
        stage.setWidth(1000);
        stage.setTitle("Witaj " + username);
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception{
        chatController.kill(Thread.currentThread());
    }

}
