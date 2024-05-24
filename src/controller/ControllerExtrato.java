/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.LoginDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.Investidor;
import view.ExtratoFrame;

/**
 *
 * @author hugoe
 */
public class ControllerExtrato {
    private ExtratoFrame view;
    private Investidor investidor;

    public ControllerExtrato(ExtratoFrame view, Investidor investidor) {
        this.view = view;
        this.investidor = investidor;
    }
    
    public Boolean confirmarSenha(){

        String senha = view.getTxtSenha().getText();
        
        if(investidor.getSenha().equals(senha) == true){
            return true;
        }
        return false;
    }
    
    public void getExtrato() {
    String cpf = investidor.getCPF();

    Conexao conexao = new Conexao();
    Connection conn = null;

    try {
        conn = conexao.getConnection();
        conn.setAutoCommit(false); // Inicia uma transação

        LoginDAO dao = new LoginDAO(conn);

        List<String> transacoesList = dao.getTransacoes(cpf);
        
        StringBuilder extratoBuilder = new StringBuilder();
        
        for (String transacao : transacoesList) {
            extratoBuilder.append(transacao).append("\n");
            System.out.println(transacao);
        }
        System.out.println("teste");
        // Atualizar a interface gráfica com o extrato completo
        view.getTxtExtrato().setText(extratoBuilder.toString());

        conn.commit(); // Confirma a transação
    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback(); // Reverte a transação em caso de erro
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro: " + e.getMessage());
    } finally {
        if (conn != null) {
            try {
                conn.close(); // Fecha a conexão
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
      }   
    }

}
