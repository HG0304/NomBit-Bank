/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 * Interface para tarifação de transações de compra e venda de criptomoedas.
 */
public interface Tarifacao {
    /**
     * Calcula o valor taxado de compra com base no valor da transação.
     * @param valor O valor da transação.
     * @return O valor taxado de compra.
     */
    public double valorTaxadoDeCompra(double valor);

    /**
     * Calcula o valor taxado de venda com base no valor da transação.
     * @param valor O valor da transação.
     * @return O valor taxado de venda.
     */
    public double valorTaxadoDeVenda(double valor);

    /**
     * Calcula a taxa de compra com base no valor da transação.
     * @param valor O valor da transação.
     * @return A taxa de compra.
     */
    public double taxaDeCompra(double valor);

    /**
     * Calcula a taxa de venda com base no valor da transação.
     * @param valor O valor da transação.
     * @return A taxa de venda.
     */
    public double taxaDeVenda(double valor);
}
