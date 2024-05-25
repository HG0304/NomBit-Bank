/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe que representa a carteira do investidor, contendo saldos em diversas moedas.
 */
public class Carteira {
    private double saldoReal;
    private double saldoBitcoin;
    private double saldoEthereum;
    private double saldoRipple;

    // Construtor padrão que inicializa todos os saldos com zero
    public Carteira() {
        this.saldoReal = 0;
        this.saldoBitcoin = 0;
        this.saldoEthereum = 0;
        this.saldoRipple = 0;
    }

    // Construtor que inicializa os saldos com os valores fornecidos
    public Carteira(double saldoReal, double saldoBitcoin, double saldoEthereum, double saldoRipple) {
        this.saldoReal = saldoReal;
        this.saldoBitcoin = saldoBitcoin;
        this.saldoEthereum = saldoEthereum;
        this.saldoRipple = saldoRipple;
    }

    // Getters e setters para saldoReal
    public double getSaldoReal() {
        return saldoReal;
    }

    public void setSaldoReal(double saldoReal) {
        this.saldoReal = saldoReal;
    }

    // Getters e setters para saldoBitcoin
    public double getSaldoBitcoin() {
        return saldoBitcoin;
    }

    public void setSaldoBitcoin(double saldoBitcoin) {
        this.saldoBitcoin = saldoBitcoin;
    }

    // Getters e setters para saldoEthereum
    public double getSaldoEthereum() {
        return saldoEthereum;
    }

    public void setSaldoEthereum(double saldoEthereum) {
        this.saldoEthereum = saldoEthereum;
    }

    // Getters e setters para saldoRipple
    public double getSaldoRipple() {
        return saldoRipple;
    }

    public void setSaldoRipple(double saldoRipple) {
        this.saldoRipple = saldoRipple;
    }
}