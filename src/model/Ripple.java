/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Random;

/**
 * Classe que representa a criptomoeda Ripple.
 * Esta classe herda da classe Moedas e implementa a interface Tarifacao para definir as taxas de compra e venda.
 * 
 * @autor unifhnomura
 */
public class Ripple extends Moedas implements Tarifacao {
    
    /**
     * Construtor da classe Ripple.
     * @param valor O valor da Ripple.
     */
    public Ripple(double valor) {
        super("Ripple", valor);
    }

    @Override
    public double valorTaxadoDeCompra(double valor) {
        // Aplica uma taxa de 1% para a compra
        return valor * 0.99;
    }

    @Override
    public double valorTaxadoDeVenda(double valor) {
        // Aplica uma taxa de 1% para a venda
        return valor * this.getValor() * 0.99;
    }

    @Override
    public double taxaDeCompra(double valor) {
        // Calcula 1% de taxa de compra
        return valor * 0.01;
    }

    @Override
    public double taxaDeVenda(double valor) {
        // Calcula 1% de taxa de venda
        return valor * this.getValor() * 0.01;
    }

    /**
     * Atualiza a cotação da Ripple com base em um valor aleatório entre -5% e +5%.
     */
    public void attCotacao() {
        Random random = new Random();
        double percentual = 5 * random.nextDouble();
        int positivo = random.nextInt(2);

        if (positivo == 1) {
            double cotacao = this.getValor() * (1 + (percentual / 100));
            this.setValor(cotacao);
        } else {
            double cotacao = this.getValor() * (1 - (percentual / 100));
            this.setValor(cotacao);
        }
    }
}
