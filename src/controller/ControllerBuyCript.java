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
import view.BuyCriptFrame;

/**
 *
 * @author hugoe
 */
public class ControllerBuyCript {
    private BuyCriptFrame view;
    private Investidor investidor;

    public ControllerBuyCript(BuyCriptFrame view, Investidor investidor) {
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
    
    public void buyBitcoin(){
        double quantidade = Double.parseDouble(view.getTxtQuantidadeCript().getText());
        
        Conexao conexao = new Conexao();
        Connection conn = null;

        try {
            conn = conexao.getConnection();
            conn.setAutoCommit(false); // Inicia uma transação

            LoginDAO dao = new LoginDAO(conn);
            Carteira res = dao.getSaldo(investidor);

            double saldoAtual = res.getSaldoReal();
            double novoSaldo = saldoAtual - quantidade;

            // Verifica se o saldo atualizado será negativo
            if (novoSaldo < 0) {
                JOptionPane.showMessageDialog(view, "Erro: Saldo insuficiente para realizar a compra.");
                return;
            }

            res.setSaldoReal(novoSaldo);
            
            //////////////////////////////////////////////////////////////////////
            // logica para comprar cripto
            Random random = new Random();
            
            // valor inial será entre 120k e 360k pq é a cotacao max e min do ultimo ano
            double min = 120000;
            double max = 360000;
            double valorDeMercado = min + (max - min) * random.nextDouble();
            Bitcoin bitcoin = new Bitcoin(valorDeMercado);
            
            
            double valorTaxadoDeTransacao = bitcoin.valorTaxadoDeCompra(quantidade); 
            double taxaDeCambio = 1 / bitcoin.getValor(); 
            double quantidadeDeCripto = valorTaxadoDeTransacao * taxaDeCambio;
            
            res.setSaldoBitcoin(quantidadeDeCripto);
            System.out.println("Saldo BTC " + res.getSaldoBitcoin());
            
            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_bitcoin = ? WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, res.getSaldoReal());
                pstmt.setDouble(2, quantidadeDeCripto);
                pstmt.setString(3, investidor.getCPF());
                pstmt.executeUpdate();
            }

            // Insere a transação na tabela 'transacoes'
            String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, investidor.getCPF());
                pstmt.setString(2, "Bitcoin");
                pstmt.setDouble(3, quantidadeDeCripto);
                pstmt.setString(4, "Compra");
                pstmt.setDouble(5, bitcoin.taxaDeCompra(quantidade));
                pstmt.executeUpdate();
            }

            conn.commit(); // Confirma a transação

            JOptionPane.showMessageDialog(view, String.format(
                    "Compra realizada com sucesso!\nSaldo atualizado: \nR$ %.2f\nBTC %.10f",
                    res.getSaldoReal(),
                    res.getSaldoBitcoin()
            ));

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
