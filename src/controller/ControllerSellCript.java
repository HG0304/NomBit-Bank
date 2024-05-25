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
import model.Carteira;
import model.Ethereum;
import model.Investidor;
import model.Ripple;
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
    
        /**
     * Método para vender Bitcoin da carteira do investidor.
     */
    public void sellBitcoin() {
    // Obtém a quantidade de Bitcoin a ser vendida digitada na interface gráfica
    double quantidade = Double.parseDouble(view.getTxtQuantidadeCript().getText());
    
    // Inicialização da conexão com o banco de dados
    Conexao conexao = new Conexao();
    Connection conn = null;

    try {
        // Estabelece a conexão com o banco de dados
        conn = conexao.getConnection();
        conn.setAutoCommit(false); // Inicia uma transação

        // Instância do DAO para acessar os dados da carteira do investidor
        LoginDAO dao = new LoginDAO(conn);
        
        // Consulta o banco de dados para obter o saldo da conta do investidor
        Carteira res = dao.getSaldo(investidor);

        // Verifica se o saldo atualizado será negativo
        double saldoAtual = res.getSaldoBitcoin();
        double novoSaldo = saldoAtual - quantidade;
        if (novoSaldo < 0) {
            JOptionPane.showMessageDialog(view, "Erro: Saldo insuficiente para realizar a venda.");
            return;
        }

        // Obtém o valor de mercado atual do Bitcoin
        double valorDeMercado = dao.getCotacao("Bitcoin");

        // Cria uma instância da classe Bitcoin com o valor de mercado
        Bitcoin btc = new Bitcoin(valorDeMercado);
        
        // Calcula o valor total em reais da quantidade de Bitcoin a ser vendida
        double quantidadeDeCripto = quantidade * valorDeMercado;
        
        // Atualiza o saldo da conta em reais (Real) com o valor da venda
        double reais = res.getSaldoReal() + btc.valorTaxadoDeVenda(quantidade);
        res.setSaldoReal(reais);
        
        // Atualiza o saldo da conta em Bitcoin (BTC) após a venda
        res.setSaldoBitcoin(novoSaldo);
        
        // Atualiza o saldo na tabela 'carteiras'
        String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_bitcoin = ? WHERE cpf = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, res.getSaldoReal());
            pstmt.setDouble(2, novoSaldo);
            pstmt.setString(3, investidor.getCPF());
            pstmt.executeUpdate();
        }

        // Insere a transação na tabela 'transacoes'
        String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa, cotacao) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, investidor.getCPF());
            pstmt.setString(2, "Bitcoin");
            pstmt.setDouble(3, quantidadeDeCripto);
            pstmt.setString(4, "Venda");
            pstmt.setDouble(5, btc.taxaDeVenda(quantidade));
            pstmt.setDouble(6, valorDeMercado);
            pstmt.executeUpdate();
        }
        
        // Exibe uma mensagem informando que a venda foi realizada com sucesso
        String mensagem = String.format(
                "Venda realizada com sucesso!\n" +
                "Valor vendido BTC em R$: %.2f\n" +
                "Saldo atualizado: \n" +
                "R$ %.2f\n" +
                "BTC %.10f",
                quantidadeDeCripto,
                res.getSaldoReal(),
                novoSaldo
        );
        JOptionPane.showMessageDialog(view, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);
        
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
        // Fecha a conexão com o banco de dados
        if (conn != null) {
            try {
                conn.close(); // Fecha a conexão
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
}

    
        /**
     * Método para vender Ethereum da carteira do investidor.
     */
    public void sellEthereum() {
        // Obtém a quantidade de Ethereum a ser vendida digitada na interface gráfica
        double quantidade = Double.parseDouble(view.getTxtQuantidadeCript().getText());

        // Inicialização da conexão com o banco de dados
        Conexao conexao = new Conexao();
        Connection conn = null;

        try {
            // Estabelece a conexão com o banco de dados
            conn = conexao.getConnection();
            conn.setAutoCommit(false); // Inicia uma transação

            // Instância do DAO para acessar os dados da carteira do investidor
            LoginDAO dao = new LoginDAO(conn);

            // Consulta o banco de dados para obter o saldo da conta do investidor
            Carteira res = dao.getSaldo(investidor);

            // Verifica se o saldo atualizado será negativo
            double saldoAtual = res.getSaldoEthereum();
            double novoSaldo = saldoAtual - quantidade;
            if (novoSaldo < 0) {
                JOptionPane.showMessageDialog(view, "Erro: Saldo insuficiente para realizar a venda.");
                return;
            }

            // Obtém o valor de mercado atual do Ethereum
            double valorDeMercado = dao.getCotacao("Ethereum");

            // Cria uma instância da classe Ethereum com o valor de mercado
            Ethereum eth = new Ethereum(valorDeMercado);

            // Calcula o valor total em reais da quantidade de Ethereum a ser vendida
            double quantidadeDeCripto = quantidade * valorDeMercado;

            // Atualiza o saldo da conta em reais (Real) com o valor da venda
            double reais = res.getSaldoReal() + eth.valorTaxadoDeVenda(quantidade);
            res.setSaldoReal(reais);

            // Atualiza o saldo da conta em Ethereum (ETH) após a venda
            res.setSaldoEthereum(novoSaldo);

            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_ethereum = ? WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, res.getSaldoReal());
                pstmt.setDouble(2, novoSaldo);
                pstmt.setString(3, investidor.getCPF());
                pstmt.executeUpdate();
            }

            // Insere a transação na tabela 'transacoes'
            String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa, cotacao) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, investidor.getCPF());
                pstmt.setString(2, "Ethereum");
                pstmt.setDouble(3, quantidadeDeCripto);
                pstmt.setString(4, "Venda");
                pstmt.setDouble(5, eth.taxaDeVenda(quantidade));
                pstmt.setDouble(6, valorDeMercado);
                pstmt.executeUpdate();
            }

            // Exibe uma mensagem informando que a venda foi realizada com sucesso
            String mensagem = String.format(
                    "Venda realizada com sucesso!\n" +
                    "Valor vendido ETH em R$: %.2f\n" +
                    "Saldo atualizado: \n" +
                    "R$ %.2f\n" +
                    "ETH %.10f",
                    quantidadeDeCripto,
                    res.getSaldoReal(),
                    novoSaldo
            );
            JOptionPane.showMessageDialog(view, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);

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
            // Fecha a conexão com o banco de dados
            if (conn != null) {
                try {
                    conn.close(); // Fecha a conexão
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    
        /**
     * Método para vender Ripple da carteira do investidor.
     */
    public void sellRipple() {
        // Obtém a quantidade de Ripple a ser vendida digitada na interface gráfica
        double quantidade = Double.parseDouble(view.getTxtQuantidadeCript().getText());

        // Inicialização da conexão com o banco de dados
        Conexao conexao = new Conexao();
        Connection conn = null;

        try {
            // Estabelece a conexão com o banco de dados
            conn = conexao.getConnection();
            conn.setAutoCommit(false); // Inicia uma transação

            // Instância do DAO para acessar os dados da carteira do investidor
            LoginDAO dao = new LoginDAO(conn);

            // Consulta o banco de dados para obter o saldo da conta do investidor
            Carteira res = dao.getSaldo(investidor);

            // Verifica se o saldo atualizado será negativo
            double saldoAtual = res.getSaldoRipple();
            double novoSaldo = saldoAtual - quantidade;
            if (novoSaldo < 0) {
                JOptionPane.showMessageDialog(view, "Erro: Saldo insuficiente para realizar a venda.");
                return;
            }

            // Obtém o valor de mercado atual do Ripple
            double valorDeMercado = dao.getCotacao("Ripple");

            // Cria uma instância da classe Ripple com o valor de mercado
            Ripple xrp = new Ripple(valorDeMercado);

            // Calcula o valor total em reais da quantidade de Ripple a ser vendida
            double quantidadeDeCripto = quantidade * valorDeMercado;

            // Atualiza o saldo da conta em reais (Real) com o valor da venda
            double reais = res.getSaldoReal() + xrp.valorTaxadoDeVenda(quantidade);
            res.setSaldoReal(reais);

            // Atualiza o saldo da conta em Ripple (XRP) após a venda
            res.setSaldoRipple(novoSaldo);

            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_ripple = ? WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, res.getSaldoReal());
                pstmt.setDouble(2, novoSaldo);
                pstmt.setString(3, investidor.getCPF());
                pstmt.executeUpdate();
            }

            // Insere a transação na tabela 'transacoes'
            String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa, cotacao) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, investidor.getCPF());
                pstmt.setString(2, "Ripple");
                pstmt.setDouble(3, quantidadeDeCripto);
                pstmt.setString(4, "Venda");
                pstmt.setDouble(5, xrp.taxaDeVenda(quantidade));
                pstmt.setDouble(6, valorDeMercado);
                pstmt.executeUpdate();
            }

            // Exibe uma mensagem informando que a venda foi realizada com sucesso
            String mensagem = String.format(
                    "Venda realizada com sucesso!\n" +
                    "Valor vendido XRP em R$: %.2f\n" +
                    "Saldo atualizado: \n" +
                    "R$ %.2f\n" +
                    "XRP %.2f",
                    quantidadeDeCripto,
                    res.getSaldoReal(),
                    novoSaldo
            );
            JOptionPane.showMessageDialog(view, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);

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
            // Fecha a conexão com o banco de dados
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
