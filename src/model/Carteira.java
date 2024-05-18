/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author unifhnomura
 */
public class Carteira {
    double saldoReal, saldoBitcoin, saldoEthereum, saldoRipple;

    public double getSaldoReal() {
        return saldoReal;
    }

    public void setSaldoReal(double saldoReal) {
        this.saldoReal = saldoReal;
    }

    public double getSaldoBitcoin() {
        return saldoBitcoin;
    }

    public void setSaldoBitcoin(double saldoBitcoin) {
        this.saldoBitcoin = saldoBitcoin;
    }

    public double getSaldoEthereum() {
        return saldoEthereum;
    }

    public void setSaldoEthereum(double saldoEthereum) {
        this.saldoEthereum = saldoEthereum;
    }

    public double getSaldoRipple() {
        return saldoRipple;
    }

    public void setSaldoRipple(double saldoRipple) {
        this.saldoRipple = saldoRipple;
    }

    public Carteira() {
        this.saldoReal = 0;
        this.saldoBitcoin = 0;
        this.saldoEthereum = 0;
        this.saldoRipple = 0;
    }

    public Carteira(double saldoReal, double saldoBitcoin, double saldoEthereum, double saldoRipple) {
        this.saldoReal = saldoReal;
        this.saldoBitcoin = saldoBitcoin;
        this.saldoEthereum = saldoEthereum;
        this.saldoRipple = saldoRipple;
    }
    
    
}
