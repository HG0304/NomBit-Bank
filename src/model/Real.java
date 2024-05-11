/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author unifhnomura
 */
public class Real extends Moedas{
    double valorDeMercado;

    public double getValorDeMercado() {
        return valorDeMercado;
    }

    public void setValorDeMercado(double valorDeMercado) {
        this.valorDeMercado = valorDeMercado;
    }

    public Real(double valorDeMercado) {
        super("Real", valorDeMercado);
        this.valorDeMercado = valorDeMercado;
    }
    
    
}
