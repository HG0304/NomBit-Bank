/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author unifhnomura
 */
public class Investidor extends Pessoa{
    private String senha;
    private Carteira carteira;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Investidor(String Nome, String senha, String CPF) {
        super(Nome, CPF);
        this.senha = senha;
        this.carteira = new Carteira();
    }
    
    public Carteira getCarteira(){
        return carteira;
    }
    
}
