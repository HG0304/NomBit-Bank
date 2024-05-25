/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Classe que representa uma pessoa.
 * Esta classe serve como base para outras classes que representam tipos específicos de pessoas, como um investidor.
 * Contém atributos comuns como nome e CPF.
 * 
 * @autor unifhnomura
 */
public class Pessoa {
    private String Nome;
    private String CPF;

    /**
     * Construtor da classe Pessoa.
     * 
     * @param Nome O nome da pessoa.
     * @param CPF O CPF da pessoa.
     */
    public Pessoa(String Nome, String CPF) {
        this.Nome = Nome;
        this.CPF = CPF;
    }

    /**
     * Obtém o nome da pessoa.
     * 
     * @return O nome da pessoa.
     */
    public String getNome() {
        return Nome;
    }

    /**
     * Define o nome da pessoa.
     * 
     * @param Nome O nome a ser definido.
     */
    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    /**
     * Obtém o CPF da pessoa.
     * 
     * @return O CPF da pessoa.
     */
    public String getCPF() {
        return CPF;
    }

    /**
     * Define o CPF da pessoa.
     * 
     * @param CPF O CPF a ser definido.
     */
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
