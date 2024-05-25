/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.LoginDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Bitcoin;
import model.Ethereum;
import model.Ripple;
import view.MenuFrame;

/**
 *
 * @author hugoe
 */
public class ControllerAttCotacao {
    private MenuFrame view;

    public ControllerAttCotacao(MenuFrame view) {
        this.view = view;
    }
    
    //Método para atualizar as cotações das criptomoedas Bitcoin, Ethereum e Ripple.
    //Conecta-se ao banco de dados para obter as cotações mais recentes de cada criptomoeda,
    //atualiza os objetos correspondentes e insere as novas cotações na tabela 'cotacoes'.

    public void attCotacao() {
        // Inicialização da conexão e variáveis de cotação
        Conexao conexao = new Conexao();
        Connection conn = null;
        double bitcoin;
        double ethereum;
        double ripple;

        try {
            // Estabelece a conexão com o banco de dados
            conn = conexao.getConnection();

            // Instância do DAO para acesso aos dados das cotações
            LoginDAO dao = new LoginDAO(conn);

            // Obtém as cotações mais recentes de cada criptomoeda
            bitcoin = dao.getCotacao("Bitcoin");
            ethereum = dao.getCotacao("Ethereum");
            ripple = dao.getCotacao("Ripple");

            // Instancia objetos das criptomoedas com as cotações obtidas
            Bitcoin btc = new Bitcoin(bitcoin);
            Ethereum eth = new Ethereum(ethereum);
            Ripple xrp = new Ripple(ripple);

            // Atualiza as cotações dos objetos criptomoeda
            btc.attCotacao();
            eth.attCotacao();
            xrp.attCotacao();

            // Insere as novas cotações na tabela 'cotacoes'
            String insertCotacao1 = "INSERT INTO cotacoes (moeda, cotacao) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCotacao1)){
                pstmt.setString(1, "Bitcoin");
                pstmt.setDouble(2, btc.getValor());
                pstmt.executeUpdate();
            }
            String insertCotacao2 = "INSERT INTO cotacoes (moeda, cotacao) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCotacao2)){
                pstmt.setString(1, "Ethereum");
                pstmt.setDouble(2, eth.getValor());
                pstmt.executeUpdate();
            }
            String insertCotacao3 = "INSERT INTO cotacoes (moeda, cotacao) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCotacao3)){
                pstmt.setString(1, "Ripple");
                pstmt.setDouble(2, xrp.getValor());
                pstmt.executeUpdate();
            }

            // Exibe uma mensagem de confirmação após a atualização das cotações
            JOptionPane.showMessageDialog(view, "Cotação atualizada!");

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
