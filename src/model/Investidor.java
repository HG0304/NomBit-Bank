/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe que representa um Investidor, herdando de Pessoa.
 */
public class Investidor extends Pessoa {
    private String senha;
    private Carteira carteira;

    /**
     * Construtor da classe Investidor.
     * @param Nome Nome do investidor.
     * @param senha Senha do investidor.
     * @param CPF CPF do investidor.
     */
    public Investidor(String Nome, String senha, String CPF) {
        super(Nome, CPF);
        this.senha = senha;
        this.carteira = new Carteira();
    }

    /**
     * Retorna a senha do investidor.
     * @return senha do investidor.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do investidor.
     * @param senha Nova senha do investidor.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna a carteira do investidor.
     * @return carteira do investidor.
     */
    public Carteira getCarteira() {
        return carteira;
    }
}
