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
    
        /**
     * Método para confirmar se a senha informada pelo usuário corresponde à senha do investidor.
     * Retorna true se a senha estiver correta, caso contrário, retorna false.
     */
    public Boolean confirmarSenha() {
        // Obtém a senha inserida pelo usuário
        String senha = view.getTxtSenha().getText();

        // Compara a senha inserida com a senha do investidor
        return investidor.getSenha().equals(senha);
    }

    /**
     * Método para obter e exibir o extrato completo das transações do investidor na interface gráfica.
     * Obtém as transações do investidor do banco de dados e as exibe na interface gráfica.
     */
    public void getExtrato() {
        String cpf = investidor.getCPF();

        // Inicialização da conexão e variáveis locais
        Conexao conexao = new Conexao();
        Connection conn = null;

        try {
            // Estabelece a conexão com o banco de dados e inicia uma transação
            conn = conexao.getConnection();
            conn.setAutoCommit(false); // Inicia uma transação

            // Instância do DAO para acesso aos dados das transações
            LoginDAO dao = new LoginDAO(conn);

            // Obtém a lista de transações do investidor
            List<String> transacoesList = dao.getTransacoes(cpf);

            // StringBuilder para construir o extrato completo
            StringBuilder extratoBuilder = new StringBuilder();

            // Adiciona cada transação ao extratoBuilder, separadas por quebras de linha
            for (String transacao : transacoesList) {
                extratoBuilder.append(transacao).append("\n");
            }

            // Atualiza a interface gráfica com o extrato completo das transações
            view.getTxtExtrato().setText(extratoBuilder.toString());

            // Confirma a transação
            conn.commit(); // Confirma a transação
        } catch (SQLException e) {
            // Em caso de erro, reverte a transação e exibe uma mensagem de erro
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
            // Fecha a conexão com o banco de dados após a execução do bloco try
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
