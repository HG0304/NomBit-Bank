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
import model.Ethereum;
import model.Investidor;
import model.Ripple;
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
            Bitcoin btc = new Bitcoin(valorDeMercado);
            
            
            double valorTaxadoDeTransacao = btc.valorTaxadoDeCompra(quantidade); 
            double taxaDeCambio = 1 / btc.getValor(); 
            double quantidadeDeCripto = valorTaxadoDeTransacao * taxaDeCambio;
            
            double valor = res.getSaldoBitcoin() + quantidadeDeCripto;
            res.setSaldoBitcoin(valor);
            
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
                pstmt.setDouble(5, btc.taxaDeCompra(quantidade));
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
    
    public void buyEthereum(){
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
            
            // valor inial será entre 7.6k e 20k pq é a cotacao max e min do ultimo ano
            double min = 7600;
            double max = 20000;
            double valorDeMercado = min + (max - min) * random.nextDouble();
            Ethereum eth = new Ethereum(valorDeMercado);
            
            
            double valorTaxadoDeTransacao = eth.valorTaxadoDeCompra(quantidade); 
            double taxaDeCambio = 1 / eth.getValor(); 
            double quantidadeDeCripto = valorTaxadoDeTransacao * taxaDeCambio;
            
            double valor = res.getSaldoEthereum()+ quantidadeDeCripto;
            res.setSaldoEthereum(valor);
            
            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_ethereum = ? WHERE cpf = ?";
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
                pstmt.setString(2, "Ethereum");
                pstmt.setDouble(3, quantidadeDeCripto);
                pstmt.setString(4, "Compra");
                pstmt.setDouble(5, eth.taxaDeCompra(quantidade));
                pstmt.executeUpdate();
            }

            conn.commit(); // Confirma a transação

            JOptionPane.showMessageDialog(view, String.format(
                    "Compra realizada com sucesso!\nSaldo atualizado: \nR$ %.2f\nETH %.10f",
                    res.getSaldoReal(),
                    res.getSaldoEthereum()
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
    
    public void buyRipple(){
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
            
            // valor inial será entre 2.2 e 4 pq é a cotacao max e min do ultimo ano
            double min = 2.2;
            double max = 4;
            double valorDeMercado = min + (max - min) * random.nextDouble();
            Ripple xrp = new Ripple(valorDeMercado);
            
            
            double valorTaxadoDeTransacao = xrp.valorTaxadoDeCompra(quantidade); 
            double taxaDeCambio = 1 / xrp.getValor(); 
            double quantidadeDeCripto = valorTaxadoDeTransacao * taxaDeCambio;
            
            double valor = res.getSaldoRipple()+ quantidadeDeCripto;
            res.setSaldoRipple(valor);
            
            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_ripple = ? WHERE cpf = ?";
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
                pstmt.setString(2, "Ripple");
                pstmt.setDouble(3, quantidadeDeCripto);
                pstmt.setString(4, "Compra");
                pstmt.setDouble(5, xrp.taxaDeCompra(quantidade));
                pstmt.executeUpdate();
            }

            conn.commit(); // Confirma a transação

            JOptionPane.showMessageDialog(view, String.format(
                    "Compra realizada com sucesso!\nSaldo atualizado: \nR$ %.2f\nXRP %.2f",
                    res.getSaldoReal(),
                    res.getSaldoRipple()
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
