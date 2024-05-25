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
public class Bitcoin extends Moedas{
    public Bitcoin(double valor) {
        super("Bitcoin", valor);
    }
    
    public double valorTaxadoDeCompra(double valor){
        double taxa = 0;
        
        // 2%
        taxa = valor * 0.98;
        return taxa;
    }
    
    public double valorTaxadoDeVenda(double valor){
        double taxa = 0;
        
        // 3%
        valor = valor * this.getValor();
        taxa = valor * 0.97;
        return taxa;
    }
    
    public double taxaDeCompra (double valor){
        double taxa = valor * 0.02;
        return taxa;
    }
    
    public double taxaDeVenda (double valor){
        double taxa = valor * this.getValor() * 0.03;
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
