/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package nombit.bank;

import view.BankFrame;

/**
 * Classe principal para a aplicação NomBitBank.
 * Esta classe inicia a interface gráfica do usuário (GUI) ao criar e exibir um frame de banco.
 * 
 * @autor unifhnomura
 */
public class NomBitBank {

    /**
     * Método principal que serve como ponto de entrada para a aplicação.
     * 
     * @param args os argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        // Cria uma instância de BankFrame
        BankFrame bankFrame = new BankFrame();
        // Torna a janela visível
        bankFrame.setVisible(true);
    }
}
