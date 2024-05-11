/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author unifhnomura
 */
public class Bitcoin extends Moedas{
        double valorDeMercado;

    public double getValorDeMercado() {
        return valorDeMercado;
    }

    public void setValorDeMercado(double valorDeMercado) {
        this.valorDeMercado = valorDeMercado;
    }

    public Bitcoin(double valorDeMercado) {
        super("Bitcoin", valorDeMercado);
        this.valorDeMercado = valorDeMercado;
    }
    
    
}
