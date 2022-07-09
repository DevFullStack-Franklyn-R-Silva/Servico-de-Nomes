package com.hadesfranklyn.chat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Franklyn Roberto da Silva
 */
public class ServicoDeNomes implements Runnable {

	public static final String SERVER_ADDRESS = "127.0.0.1";

	private final Scanner scanner;

	private Cliente clientSocket;

	public static void main(String[] args) {
		try {
			ServicoDeNomes client = new ServicoDeNomes();
			client.start();
		} catch (IOException e) {
			System.out.println("Erro ao conectar ao servidor: " + e.getMessage());
		}
	}

	public ServicoDeNomes() {
		scanner = new Scanner(System.in);
	}

	private void start() throws IOException {
		final Socket socket = new Socket(SERVER_ADDRESS, Servidor.PORT);
		clientSocket = new Cliente(socket);
		System.out.println("Cliente conectado ao servidor no endereço " + SERVER_ADDRESS + " e porta "
				+ Servidor.PORT);

		login();

		new Thread(this).start();
		messageLoop();
	}

	private void login() {
		System.out.print("Digite seu login: ");
		final String login = scanner.nextLine();
		clientSocket.setLogin(login);
		clientSocket.sendMsg(login);
	}

	private void messageLoop() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		boolean verdadeiro = true;
		while (true) {
			System.out.println("Tela de Menu");
			verdadeiro = true;
			System.out.println("Digite a seleção: 'imc' ou 'cpf'");
			String selecao = sc.nextLine();
			int contador = 0;
			System.out.println(selecao);
			if (selecao.equals("imc")) {
				System.out.println("-------- IMC --------");
				while (verdadeiro) {
					IMC imc = new IMC();
					while (verdadeiro) {
						System.out.println("Digite seu IMC:");
						System.out.println("Peso: ");
						String peso = sc.nextLine();
						System.out.println("Altura: ");
						String altura = sc.nextLine();
						System.out.println("Deseja sair? 's' ou 'n'");
						selecao = sc.nextLine();
						
						if(selecao.equals("s")) {
							verdadeiro = false;
						}
						try {
							imc = new IMC(peso, altura);
							System.out.println(imc.toString());

							contador += 1;

							clientSocket.sendMsg("Event " + contador + ":\n" + "- Request => {'imc': '" + peso + ", "
									+ altura + "'}\n" + "- Reponse => {'res': '" + imc.toString() + "'}");
						} catch (NumberFormatException e) {
							System.out.println("Erro: " + e.getMessage());
							System.out.println("Valores inválidos, digite novamente");
						} catch (IllegalStateException e) {
							System.out.println("Erro: " + e.getMessage());
						}
					}
				}
			} else {
				System.out.println("-------- CPF VALIDADOR --------");
				while (verdadeiro) {
					CPF cpf = new CPF();

					while (verdadeiro) {
						System.out.println("Digite seu cpf: 000.000.000-00");
						String n = sc.nextLine();

						n = n.replace(".", "").replace("-", "").replace(",", "");
						
						System.out.println("Deseja sair? 's' ou 'n'");
						selecao = sc.nextLine();
						
						if(selecao.equals("s")) {
							verdadeiro = false;
						}
						try {
							cpf = new CPF(n);
							System.out.println(cpf.toString());
							contador += 1;
							
							clientSocket.sendMsg("Event " + contador + ":\n" + "- Request => {'cpf': '" + n + "'}\n"
									+ "- Reponse => {'res': '" + cpf.toString() + "'}");
						} catch (NumberFormatException e) {
							System.out.println("Erro: " + e.getMessage());
							System.out.println("Valores inválidos, digite novamente");
						} catch (IllegalStateException e) {
							System.out.println("Erro: " + e.getMessage());
						}
					}
				}
			}
		}
	}

	@Override
	public void run() {
		String msg;
		while ((msg = clientSocket.getMessage()) != null) {
			System.out.println(msg);
		}
	}
}
