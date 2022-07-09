package com.hadesfranklyn.chat;

public class CPF {
	private String cpf;

	public CPF() {
	}

	public CPF(String cpf) {
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean calcularCPF() {
		String[] cpf = this.cpf.split("");
		int soma = 0;
		soma += Integer.parseInt(cpf[0]) * 10;
		soma += Integer.parseInt(cpf[1]) * 9;
		soma += Integer.parseInt(cpf[2]) * 8;
		soma += Integer.parseInt(cpf[3]) * 7;
		soma += Integer.parseInt(cpf[4]) * 6;
		soma += Integer.parseInt(cpf[5]) * 5;
		soma += Integer.parseInt(cpf[6]) * 4;
		soma += Integer.parseInt(cpf[7]) * 3;
		soma += Integer.parseInt(cpf[8]) * 2;
		soma = (soma * 10) % 11;
		if (soma == 10 || soma == 11) {
			soma = 0;
		}
		if (soma != Integer.parseInt(cpf[9])) {
			return false;
		}

		soma = 0;
		soma += Integer.parseInt(cpf[0]) * 11;
		soma += Integer.parseInt(cpf[1]) * 10;
		soma += Integer.parseInt(cpf[2]) * 9;
		soma += Integer.parseInt(cpf[3]) * 8;
		soma += Integer.parseInt(cpf[4]) * 7;
		soma += Integer.parseInt(cpf[5]) * 6;
		soma += Integer.parseInt(cpf[6]) * 5;
		soma += Integer.parseInt(cpf[7]) * 4;
		soma += Integer.parseInt(cpf[8]) * 3;
		soma += Integer.parseInt(cpf[9]) * 2;
		soma = (soma * 10) % 11;

		if (soma == 10 || soma == 11) {
			soma = 0;
		}

		if (soma != Integer.parseInt(cpf[10])) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		if (calcularCPF() == false) {
			return "CPF Inválido!";
		} else {
			return "CPF Válido!";
		}
	}

}
