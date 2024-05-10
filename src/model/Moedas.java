/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author unifhnomura
 */
public class Moedas {
    // type = 1 -> Real
    // type = 2 -> Bitcoin
    // type = 3 -> Ethereum
    // type = 4 -> Ripple
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public Moedas(int type) {
        this.type = type;
    }
    
    
}
