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
import java.util.Random;
import javax.swing.JOptionPane;
import model.Bitcoin;
import model.Carteira;
import model.Investidor;
import view.SellCriptFrame;

/**
 *
 * @author hugoe
 */
public class ControllerSellCript {
    private SellCriptFrame view;
    private Investidor investidor;

    public ControllerSellCript(SellCriptFrame view, Investidor investidor) {
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
    
    public void sellBitcoin(){
        double quantidade = Double.parseDouble(view.getTxtQuantidadeCript().getText());
        
        Conexao conexao = new Conexao();
        Connection conn = null;

        try {
            conn = conexao.getConnection();
            conn.setAutoCommit(false); // Inicia uma transação

            LoginDAO dao = new LoginDAO(conn);
            Carteira res = dao.getSaldo(investidor);

            double saldoAtual = res.getSaldoBitcoin();
            double novoSaldo = saldoAtual - quantidade;
            
            // Verifica se o saldo atualizado será negativo
            if (novoSaldo < 0) {
                JOptionPane.showMessageDialog(view, "Erro: Saldo insuficiente para realizar a compra.");
                return;
            }

            double valorDeMercado = dao.getCotacao("Bitcoin");
            
            System.out.println("Valor do BTC: " + valorDeMercado);
  
            Bitcoin btc = new Bitcoin(valorDeMercado);
            
            double quantidadeDeCripto = quantidade * valorDeMercado;
            double valor = res.getSaldoBitcoin() - quantidade;
            
            double reais = res.getSaldoReal() + btc.valorTaxadoDeVenda(quantidade);
            
            res.setSaldoBitcoin(valor);
            res.setSaldoReal(reais);
            res.setSaldoBitcoin(novoSaldo);
            
            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_bitcoin = ? WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, res.getSaldoReal());
                pstmt.setDouble(2, res.getSaldoBitcoin());
                pstmt.setString(3, investidor.getCPF());
                pstmt.executeUpdate();
            }

            // Insere a transação na tabela 'transacoes'
            String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, investidor.getCPF());
                pstmt.setString(2, "Bitcoin");
                pstmt.setDouble(3, quantidadeDeCripto);
                pstmt.setString(4, "Venda");
                pstmt.setDouble(5, btc.taxaDeVenda(quantidade));
                pstmt.executeUpdate();
            }
            
            String mensagem = String.format(
                    "Venda realizada com sucesso!\n" +
                    "Valor vendido BTC em R$: %.2f\n" +
                    "Saldo atualizado: \n" +
                    "R$ %.2f\n" +
                    "BTC %.10f",
                    quantidadeDeCripto,
                    res.getSaldoReal(),
                    res.getSaldoBitcoin()
                    );
            
            JOptionPane.showMessageDialog(view, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);
            
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
