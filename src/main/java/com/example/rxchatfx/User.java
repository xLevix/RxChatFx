package com.example.rxchatfx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import io.reactivex.rxjava3.core.Observable;


public class User implements Runnable{
    String nick;
    Socket socket;
    int id;
    User(String nick,Socket socket,int id){
        this.nick=nick;
        this.socket=socket;
        this.id=id;
    }

    @Override
    public void run() {
        Observable<String> sending = Observable.create(e -> {
            while (!socket.isClosed()) {
                try {
                    DataInputStream in = new DataInputStream((this.socket.getInputStream()));
                    String message= in.readUTF();
                    e.onNext(message);
                } catch (IOException ex) {

                    for(Socket online : Server.connected){
                        DataOutputStream out = new  DataOutputStream(online.getOutputStream());
                        out.writeUTF("Uzytkownik "+nick+"#"+id+" sie rozlaczyl");
                    }

                    socket.close();
                    System.out.println("Uzytkownik "+nick+"#"+id+" sie rozlaczyl");
                }
            }
        });


        sending.subscribe(e->{
            System.out.println(nick+"#"+id+": "+e);
            for(Socket online : Server.connected){
                DataOutputStream out = new  DataOutputStream(online.getOutputStream());
                out.writeUTF(nick+"#"+id+": "+e);
            }
        });
    }

}
