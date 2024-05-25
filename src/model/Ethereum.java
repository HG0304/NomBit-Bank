/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Random;

/**
 * Classe que representa a criptomoeda Ethereum.
 */
public class Ethereum extends Moedas implements Tarifacao {
    
    /**
     * Construtor da classe Ethereum.
     * @param valor O valor da Ethereum.
     */
    public Ethereum(double valor) {
        super("Ethereum", valor);
    }

    @Override
    public double valorTaxadoDeCompra(double valor) {
        // Taxa de 1%
        return valor * 0.99;
    }

    @Override
    public double valorTaxadoDeVenda(double valor) {
        // Taxa de 2%
        return valor * this.getValor() * 0.98;
    }

    @Override
    public double taxaDeCompra(double valor) {
        // 1% de taxa
        return valor * 0.01;
    }

    @Override
    public double taxaDeVenda(double valor) {
        // 2% de taxa
        return valor * this.getValor() * 0.02;
    }

    /**
     * Atualiza a cotação da Ethereum com base em um valor aleatório entre -5% e +5%.
     */
    public void attCotacao() {
        Random random = new Random();

        double percentual = random.nextDouble() * 5; // Gera um percentual entre 0 e 5
        boolean positivo = random.nextBoolean(); // Determina se o percentual é positivo ou negativo
        
        if (positivo) {
            this.setValor(this.getValor() * (1 + (percentual / 100)));
        } else {
            this.setValor(this.getValor() * (1 - (percentual / 100)));
        }
    }
}
