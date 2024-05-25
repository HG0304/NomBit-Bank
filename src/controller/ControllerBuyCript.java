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
            
            double cotacao = dao.getCotacao("Bitcoin");
            boolean cotacaoExiste = true;
            if(cotacao == 0){
                Random random = new Random();

                // valor inial será entre 120k e 360k pq é a cotacao max e min do ultimo ano
                double min = 120000;
                double max = 360000;
                cotacao = min + (max - min) * random.nextDouble();
                cotacaoExiste = false;
            }
            Bitcoin btc = new Bitcoin(cotacao);
            
            double valorTaxadoDeTransacao = btc.valorTaxadoDeCompra(quantidade); 
            double taxaDeCambio = 1 / btc.getValor();
            double quantidadeDeCripto = valorTaxadoDeTransacao * taxaDeCambio;
            
            double valor = res.getSaldoBitcoin() + quantidadeDeCripto;
            res.setSaldoBitcoin(valor);
            
            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_bitcoin = ? WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, res.getSaldoReal());
                pstmt.setDouble(2, res.getSaldoBitcoin());
                pstmt.setString(3, investidor.getCPF());
                pstmt.executeUpdate();
            }

            // Insere a transação na tabela 'transacoes'
            String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa, cotacao) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, investidor.getCPF());
                pstmt.setString(2, "Bitcoin");
                pstmt.setDouble(3, quantidadeDeCripto);
                pstmt.setString(4, "Compra");
                pstmt.setDouble(5, btc.taxaDeCompra(quantidade));
                pstmt.setDouble(6, cotacao);
                pstmt.executeUpdate();
            }
            
            
            String insertCotacao = "INSERT INTO cotacoes (moeda, cotacao) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCotacao)){
                pstmt.setString(1, "Bitcoin");
                pstmt.setDouble(2, cotacao);
                pstmt.executeUpdate();
            }
            
            
            String mensagem = String.format(
                    "Compra realizada com sucesso!\n" +
                    "Valor comprado BTC: %.10f\n" +
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
            
            double cotacao = dao.getCotacao("Ethereum");
            boolean cotacaoExiste = true;
            if(cotacao == 0){
                Random random = new Random();
            
                // valor inial será entre 7.6k e 20k pq é a cotacao max e min do ultimo ano
                double min = 7600;
                double max = 20000;
                cotacao = min + (max - min) * random.nextDouble();
                cotacaoExiste = false;
            }
            //////////////////////////////////////////////////////////////////////
            // logica para comprar cripto
            
            Ethereum eth = new Ethereum(cotacao);
            
            
            double valorTaxadoDeTransacao = eth.valorTaxadoDeCompra(quantidade); 
            double taxaDeCambio = 1 / eth.getValor(); 
            double quantidadeDeCripto = valorTaxadoDeTransacao * taxaDeCambio;
            
            double valor = res.getSaldoEthereum()+ quantidadeDeCripto;
            res.setSaldoEthereum(valor);
            
            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_ethereum = ? WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, res.getSaldoReal());
                pstmt.setDouble(2, res.getSaldoEthereum());
                pstmt.setString(3, investidor.getCPF());
                pstmt.executeUpdate();
            }

            // Insere a transação na tabela 'transacoes'
            String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa, cotacao) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, investidor.getCPF());
                pstmt.setString(2, "Ethereum");
                pstmt.setDouble(3, quantidadeDeCripto);
                pstmt.setString(4, "Compra");
                pstmt.setDouble(5, eth.taxaDeCompra(quantidade));
                pstmt.setDouble(6, cotacao);
                pstmt.executeUpdate();
            }

            String insertCotacao = "INSERT INTO cotacoes (moeda, cotacao) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCotacao)){
                pstmt.setString(1, "Ethereum");
                pstmt.setDouble(2, cotacao);
                pstmt.executeUpdate();
            }
            
            String mensagem = String.format(
                    "Compra realizada com sucesso!\n" +
                    "Valor comprado ETH: %.10f\n" +
                    "Saldo atualizado: \n" +
                    "R$ %.2f\n" +
                    "ETH %.10f",
                    quantidadeDeCripto,
                    res.getSaldoReal(),
                    res.getSaldoEthereum()
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
            
            double cotacao = dao.getCotacao("Ripple");
            boolean cotacaoExiste = true;
            if(cotacao == 0){
                Random random = new Random();
                
                // valor inial será entre 2.2 e 4 pq é a cotacao max e min do ultimo ano
                double min = 2.2;
                double max = 4;
                cotacao = min + (max - min) * random.nextDouble();
                cotacaoExiste = false;
            }
            Ripple xrp = new Ripple(cotacao);
            
            double valorTaxadoDeTransacao = xrp.valorTaxadoDeCompra(quantidade); 
            double taxaDeCambio = 1 / xrp.getValor(); 
            double quantidadeDeCripto = valorTaxadoDeTransacao * taxaDeCambio;
            
            double valor = res.getSaldoRipple()+ quantidadeDeCripto;
            res.setSaldoRipple(valor);
            
            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_ripple = ? WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, res.getSaldoReal());
                pstmt.setDouble(2, res.getSaldoRipple());
                pstmt.setString(3, investidor.getCPF());
                pstmt.executeUpdate();
            }

            // Insere a transação na tabela 'transacoes'
            String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa, cotacao) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, investidor.getCPF());
                pstmt.setString(2, "Ripple");
                pstmt.setDouble(3, quantidadeDeCripto);
                pstmt.setString(4, "Compra");
                pstmt.setDouble(5, xrp.taxaDeCompra(quantidade));
                pstmt.setDouble(6, cotacao);
                pstmt.executeUpdate();
            }

            String insertCotacao = "INSERT INTO cotacoes (moeda, cotacao) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCotacao)){
                pstmt.setString(1, "Ripple");
                pstmt.setDouble(2, cotacao);
                pstmt.executeUpdate();
            }
            
            String mensagem = String.format(
                    "Compra realizada com sucesso!\n" +
                    "Valor comprado XRP: %.2f\n" +
                    "Saldo atualizado: \n" +
                    "R$ %.2f\n" +
                    "XRP %.2f",
                    quantidadeDeCripto,
                    res.getSaldoReal(),
                    res.getSaldoRipple()
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
