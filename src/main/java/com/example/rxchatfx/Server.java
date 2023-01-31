package com.example.rxchatfx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
   static  ArrayList<Socket> connected= new ArrayList<>();

   public static void main(String[] args) {
       ServerSocket serverSocket = null;
       Socket socket = null;
       DataInputStream in = null;
       try {
           serverSocket = new ServerSocket(1234);
           System.out.println("Server started");
           int id = 0;
           while (true) {
               socket = serverSocket.accept();
               connected.add(socket);
               id++;
               in = new DataInputStream(socket.getInputStream());
               String nick = in.readUTF();
               System.out.println("Client connected" + "Nick: "+nick+"#"+id);

                for(Socket online : connected){
                     DataOutputStream out = new  DataOutputStream(online.getOutputStream());
                     out.writeUTF("Uzytkownik "+nick+"#"+id+" sie polaczyl");
                }

               User user = new User(nick,socket,id);
               Thread thread = new Thread(user);
               thread.start();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
