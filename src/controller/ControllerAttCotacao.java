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
    
    
    
    public void attCotacao(){
        Conexao conexao = new Conexao();
        Connection conn = null;
        
        double bitcoin;
        double ethereum;
        double ripple;
        
        try{
            conn = conexao.getConnection();
            
            LoginDAO dao = new LoginDAO(conn);
            
            bitcoin = dao.getCotacao("Bitcoin");
            ethereum = dao.getCotacao("Ethereum");
            ripple = dao.getCotacao("Ripple");
            
            Bitcoin btc = new Bitcoin(bitcoin);
            Ethereum eth = new Ethereum(ethereum);
            Ripple xrp = new Ripple(ripple);
            
            btc.attCotacao();
            eth.attCotacao();
            xrp.attCotacao();
            
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
            
            JOptionPane.showMessageDialog(view, "Cotacao atualizada!");
            
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
