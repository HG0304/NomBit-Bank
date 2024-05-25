/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Random;

/**
 *
 * @author unifhnomura
 */
public class Ripple extends Moedas implements tarifacao{
    public Ripple(double valor) {
        super("Ripple", valor);
    }
    
    public double valorTaxadoDeCompra(double valor){
        double taxa = 0;
        
        // 1%
        taxa = valor * 0.99;
        return taxa;
    }
    
    public double valorTaxadoDeVenda(double valor){
        double taxa = 0;
        
        // 1%
        valor = valor * this.getValor();
        taxa = valor * 0.99;
        return taxa;
    }
    
    public double taxaDeCompra (double valor){
        double taxa = valor * 0.01;
        return taxa;
    }
    
    public double taxaDeVenda (double valor){
        double taxa = valor * this.getValor() * 0.01;
        return taxa;
    }
    
    public void attCotacao() {
        Random random = new Random();

        double percentual = 0 + (5 - 0) * random.nextDouble();
        int positivo = random.nextInt(2);
        
        if(positivo == 1){
            double cotacao = this.getValor() * (1 + (percentual / 100));
            this.setValor(cotacao);
        } else {
            double cotacao = this.getValor() * (1 + ( -percentual / 100));
            this.setValor(cotacao);
        }
        
    }
}
