package com.hadesfranklyn.chat;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author Franklyn Roberto da Silva
 */
public class Cliente implements Closeable {
  
    private String login;


    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

   
    public Cliente(final Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

   
    public boolean sendMsg(String msg) {
        out.println(msg);
        
        return !out.checkError();
    }

   
    public String getMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }


    @Override
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch(IOException e){
            System.err.println("Erro ao fechar socket: " + e.getMessage());
        }
    }

    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }

    public boolean isOpen(){
        return !socket.isClosed();
    }
}
