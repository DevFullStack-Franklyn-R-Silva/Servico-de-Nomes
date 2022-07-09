package com.hadesfranklyn.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Franklyn Roberto da Silva
 */
public class Servidor {

    public static final int PORT = 4000;


    private ServerSocket serverSocket;


    private final List<Cliente> clientSocketList;

    public Servidor() {
        clientSocketList = new LinkedList<>();
    }


    public static void main(String[] args) {
        final Servidor server = new Servidor();
        try {
            server.start();
        } catch (IOException e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
        }
    }


    private void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println(
                "Servidor iniciado no endereço " + serverSocket.getInetAddress().getHostAddress() +
                " e porta " + PORT);
        clientConnectionLoop();
    }


    private void clientConnectionLoop() throws IOException {
        try {
            while (true) {
                System.out.println("Aguardando conexão de novo cliente");
                
                final Cliente clientSocket;
                try {
                    clientSocket = new Cliente(serverSocket.accept());
                    System.out.println("Cliente " + clientSocket.getRemoteSocketAddress() + " conectado");
                }catch(SocketException e){
                    System.err.println("Erro ao aceitar conexão do cliente. O servidor possivelmente está sobrecarregado:");
                    System.err.println(e.getMessage());
                    continue;
                }

           
                try {
                    new Thread(() -> clientMessageLoop(clientSocket)).start();
                    clientSocketList.add(clientSocket);
                }catch(OutOfMemoryError ex){
                    System.err.println(
                            "Não foi possível criar thread para novo cliente. O servidor possivelmente está sobrecarregdo. Conexão será fechada: ");
                    System.err.println(ex.getMessage());
                    clientSocket.close();
                }
            }
        } finally{
          
            stop();
        }
    }

 
    private void clientMessageLoop(final Cliente clientSocket){
        try {
            String msg;
            while((msg = clientSocket.getMessage()) != null){
                final SocketAddress clientIP = clientSocket.getRemoteSocketAddress();
                if("sair".equalsIgnoreCase(msg)){
                    return;
                }

                if(clientSocket.getLogin() == null){
                    clientSocket.setLogin(msg);
                    System.out.println("Cliente "+ clientIP + " logado como " + clientSocket.getLogin() +".");
                    msg = "Cliente " + clientSocket.getLogin() + " logado.";
                }
                else {
                    System.out.println("Mensagem recebida de "+ clientSocket.getLogin() +": " + msg);
                    msg = clientSocket.getLogin() + " diz: " + msg;
                };

                sendMsgToAll(clientSocket, msg);
            }
        } finally {
            clientSocket.close();
        }
    }
    

    private void sendMsgToAll(final Cliente sender, final String msg) {
        final Iterator<Cliente> iterator = clientSocketList.iterator();
        @SuppressWarnings("unused")
		int count = 0;
   
        while (iterator.hasNext()) {
           
            final Cliente client = iterator.next();
           
            if (!client.equals(sender)) {
                if(client.sendMsg(msg))
                    count++;
                else iterator.remove();
            }
        }
//        System.out.println("Mensagem encaminhada para " + count + " servidor");

        
    }

    private void stop()  {
        try {
            System.out.println("Finalizando servidor");
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar socket do servidor: " + e.getMessage());
        }
    }
}

