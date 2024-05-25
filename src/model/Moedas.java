/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe que representa uma moeda genérica.
 */
public class Moedas {
    private String type;
    private double valor;

    /**
     * Construtor da classe Moedas.
     * @param type O tipo da moeda.
     * @param valor O valor da moeda.
     */
    public Moedas(String type, double valor) {
        this.type = type;
        this.valor = valor;
    }

    public String getType() {
        return type;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}