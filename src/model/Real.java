/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe que representa a moeda Real.
 * Esta classe herda da classe Moedas e adiciona o atributo valorDeMercado específico para a moeda Real.
 * 
 * autor hugoe
 */
public class Real extends Moedas {
    private double valorDeMercado;

    /**
     * Construtor da classe Real.
     * 
     * @param valorDeMercado O valor de mercado do Real.
     */
    public Real(double valorDeMercado) {
        super("Real", valorDeMercado);
        this.valorDeMercado = valorDeMercado;
    }

    /**
     * Obtém o valor de mercado do Real.
     * 
     * @return O valor de mercado do Real.
     */
    public double getValorDeMercado() {
        return valorDeMercado;
    }

    /**
     * Define o valor de mercado do Real.
     * 
     * @param valorDeMercado O valor de mercado a ser definido.
     */
    public void setValorDeMercado(double valorDeMercado) {
        this.valorDeMercado = valorDeMercado;
    }
}
