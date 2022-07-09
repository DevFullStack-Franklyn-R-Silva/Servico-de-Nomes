package com.hadesfranklyn.chat;

public class IMC {
	private String peso;
	private String altura;

	public IMC() {
	}

	public IMC(String peso, String altura) {
		this.peso = peso;
		this.altura = altura;
	}

	public String getWeight() {
		return peso;
	}

	public void setWeight(String weight) {
		this.peso = weight;
	}

	public String getHeight() {
		return altura;
	}

	public void setHeight(String altura) {
		this.altura = altura;
	}

	public String calcularIMC() {
		float peso = Float.parseFloat(this.peso);
		float altura = Float.parseFloat(this.altura);

		float imc = (float) (peso / Math.pow(altura, 2));

		if (imc < 18.5) {
			return "Sua Classificação é de Magreza. Seu imc: " + imc+"\n";
		} else if ((imc > 18.5) && (imc < 24.9)) {
			return "Sua Classificação Está Dentro do Normal. Seu imc: " + imc+"\n";
		} else if ((imc > 25.0) && (imc < 29.9)) {
			return "Sua Classificação é excesso de peso. Seu imc: " + imc+"\n";
		} else if ((imc > 30.0) && (imc < 34.9)) {
			return "Sua Classificação é de Sobrepeso I. Seu imc: " + imc+"\n";
		} else if ((imc > 35.0) && (imc < 39.9)) {
			return "Sua Classificação é Obesidade II. Seu imc: " + imc+"\n";
		} else if (imc >= 40.0) {
			return "Sua Classificação é Obesidade Grave III. Seu imc: " + imc+"\n";
		}
		return "";
	}

	@Override
	public String toString() {
		return calcularIMC();

	}
}
