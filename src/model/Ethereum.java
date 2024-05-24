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
public class Ethereum extends Moedas{
    public Ethereum(double valor) {
        super("Ethereum", valor);
    }

    public double valorTaxadoDeCompra(double valor){
        double taxa = 0;
        
        // 1%
        taxa = valor * 0.99;
        return taxa;
    }
    
    public double valorTaxadoDeVenda(double valor){
        double taxa = 0;
        
        // 2%
        taxa = valor * 0.98;
        return taxa;
    }
    
        public double taxaDeCompra (double valor){
        double taxa = valor * 0.02;
        return taxa;
    }
    
    public double taxaDeVenda (double valor){
        double taxa = valor * 0.03;
        return taxa;
    }
}
