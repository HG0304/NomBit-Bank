/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Random;

/**
 * Classe que representa a criptomoeda Bitcoin.
 */
public class Bitcoin extends Moedas implements Tarifacao {
    
    /**
     * Construtor da classe Bitcoin.
     * @param valor O valor da Bitcoin.
     */
    public Bitcoin(double valor) {
        super("Bitcoin", valor);
    }
    
    @Override
    public double valorTaxadoDeCompra(double valor) {
        // Taxa de 2%
        return valor * 0.98;
    }

    @Override
    public double valorTaxadoDeVenda(double valor) {
        // Taxa de 3%
        return valor * this.getValor() * 0.97;
    }

    @Override
    public double taxaDeCompra(double valor) {
        // 2% de taxa
        return valor * 0.02;
    }

    @Override
    public double taxaDeVenda(double valor) {
        // 3% de taxa
        return valor * this.getValor() * 0.03;
    }
    
    /**
     * Atualiza a cotação da Bitcoin com base em um valor aleatório entre -5% e +5%.
     */
    public void attCotacao() {
        Random random = new Random();

        double percentual = 0 + (5 - 0) * random.nextDouble();
        int positivo = random.nextInt(2);
        
        if (positivo == 1) {
            double cotacao = this.getValor() * (1 + (percentual / 100));
            this.setValor(cotacao);
        } else {
            double cotacao = this.getValor() * (1 + (-percentual / 100));
            this.setValor(cotacao);
        }
    }
}
