/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 *
 * @author unifhnomura
 */
public interface tarifacao {
    public double valorTaxadoDeCompra(double valor);
    public double valorTaxadoDeVenda(double valor);
    public double taxaDeCompra (double valor);
    public double taxaDeVenda (double valor);
}
