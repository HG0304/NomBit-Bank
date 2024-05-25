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
    
    /**
 * Método para realizar a compra de Bitcoin.
 * Obtém a quantidade desejada de Bitcoin informada pelo usuário e realiza as devidas operações no banco de dados
 * para atualizar o saldo da carteira do investidor e registrar a transação de compra de Bitcoin.
 */
public void buyBitcoin() {
    // Obtém a quantidade de Bitcoin desejada informada pelo usuário
    double quantidade = Double.parseDouble(view.getTxtQuantidadeCript().getText());
    
    // Inicialização da conexão e variáveis locais
    Conexao conexao = new Conexao();
    Connection conn = null;

    try {
        // Estabelece a conexão com o banco de dados e inicia uma transação
        conn = conexao.getConnection();
        conn.setAutoCommit(false); // Inicia uma transação

        // Instância do DAO para acesso aos dados da carteira
        LoginDAO dao = new LoginDAO(conn);
        
        // Obtém o saldo atual da carteira do investidor
        Carteira res = dao.getSaldo(investidor);

        // Calcula o novo saldo após a compra de Bitcoin
        double saldoAtual = res.getSaldoReal();
        double novoSaldo = saldoAtual - quantidade;

        // Verifica se o saldo atualizado será negativo
        if (novoSaldo < 0) {
            JOptionPane.showMessageDialog(view, "Erro: Saldo insuficiente para realizar a compra.");
            return;
        }

        // Atualiza o saldo real na carteira do investidor
        res.setSaldoReal(novoSaldo);
        
        // Verifica a cotação atual do Bitcoin no banco de dados
        double cotacao = dao.getCotacao("Bitcoin");
        boolean cotacaoExiste = true;
        
        // Se não houver cotação no banco de dados, gera uma cotação aleatória
        if (cotacao == 0) {
            Random random = new Random();
            double min = 120000;
            double max = 360000;
            cotacao = min + (max - min) * random.nextDouble();
            cotacaoExiste = false;
        }
        
        // Cria uma instância da classe Bitcoin com a cotação obtida
        Bitcoin btc = new Bitcoin(cotacao);
        
        // Calcula o valor da transação considerando a taxa de compra e taxa de câmbio
        double valorTaxadoDeTransacao = btc.valorTaxadoDeCompra(quantidade); 
        double taxaDeCambio = 1 / btc.getValor();
        double quantidadeDeCripto = valorTaxadoDeTransacao * taxaDeCambio;
        
        // Atualiza o saldo de Bitcoin na carteira do investidor
        double valor = res.getSaldoBitcoin() + quantidadeDeCripto;
        res.setSaldoBitcoin(valor);
        
        // Atualiza o saldo real e de Bitcoin na tabela 'carteiras'
        String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_bitcoin = ? WHERE cpf = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, res.getSaldoReal());
            pstmt.setDouble(2, res.getSaldoBitcoin());
            pstmt.setString(3, investidor.getCPF());
            pstmt.executeUpdate();
        }

        // Insere a transação de compra na tabela 'transacoes'
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
        
        // Insere a nova cotação do Bitcoin na tabela 'cotacoes' se não existir
        if (!cotacaoExiste) {
            String insertCotacao = "INSERT INTO cotacoes (moeda, cotacao) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCotacao)){
                pstmt.setString(1, "Bitcoin");
                pstmt.setDouble(2, cotacao);
                pstmt.executeUpdate();
            }
        }
        
        // Exibe uma mensagem de sucesso com os detalhes da compra e os saldos atualizados
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

    
    /**
 * Método para realizar a compra de Ethereum.
 * Obtém a quantidade desejada de Ethereum informada pelo usuário e realiza as devidas operações no banco de dados
 * para atualizar o saldo da carteira do investidor e registrar a transação de compra de Ethereum.
 */
public void buyEthereum() {
    // Obtém a quantidade de Ethereum desejada informada pelo usuário
    double quantidade = Double.parseDouble(view.getTxtQuantidadeCript().getText());
    
    // Inicialização da conexão e variáveis locais
    Conexao conexao = new Conexao();
    Connection conn = null;

    try {
        // Estabelece a conexão com o banco de dados e inicia uma transação
        conn = conexao.getConnection();
        conn.setAutoCommit(false); // Inicia uma transação

        // Instância do DAO para acesso aos dados da carteira
        LoginDAO dao = new LoginDAO(conn);
        
        // Obtém o saldo atual da carteira do investidor
        Carteira res = dao.getSaldo(investidor);

        // Calcula o novo saldo após a compra de Ethereum
        double saldoAtual = res.getSaldoReal();
        double novoSaldo = saldoAtual - quantidade;

        // Verifica se o saldo atualizado será negativo
        if (novoSaldo < 0) {
            JOptionPane.showMessageDialog(view, "Erro: Saldo insuficiente para realizar a compra.");
            return;
        }

        // Atualiza o saldo real na carteira do investidor
        res.setSaldoReal(novoSaldo);
        
        // Verifica a cotação atual do Ethereum no banco de dados
        double cotacao = dao.getCotacao("Ethereum");
        boolean cotacaoExiste = true;
        
        // Se não houver cotação no banco de dados, gera uma cotação aleatória
        if (cotacao == 0) {
            Random random = new Random();
            double min = 7600;
            double max = 20000;
            cotacao = min + (max - min) * random.nextDouble();
            cotacaoExiste = false;
        }
        
        // Cria uma instância da classe Ethereum com a cotação obtida
        Ethereum eth = new Ethereum(cotacao);
        
        // Calcula o valor da transação considerando a taxa de compra e taxa de câmbio
        double valorTaxadoDeTransacao = eth.valorTaxadoDeCompra(quantidade); 
        double taxaDeCambio = 1 / eth.getValor(); 
        double quantidadeDeCripto = valorTaxadoDeTransacao * taxaDeCambio;
        
        // Atualiza o saldo de Ethereum na carteira do investidor
        double valor = res.getSaldoEthereum()+ quantidadeDeCripto;
        res.setSaldoEthereum(valor);
        
        // Atualiza o saldo real e de Ethereum na tabela 'carteiras'
        String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_ethereum = ? WHERE cpf = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, res.getSaldoReal());
            pstmt.setDouble(2, res.getSaldoEthereum());
            pstmt.setString(3, investidor.getCPF());
            pstmt.executeUpdate();
        }

        // Insere a transação de compra na tabela 'transacoes'
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

        // Insere a nova cotação do Ethereum na tabela 'cotacoes' se não existir
        if (!cotacaoExiste) {
            String insertCotacao = "INSERT INTO cotacoes (moeda, cotacao) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCotacao)){
                pstmt.setString(1, "Ethereum");
                pstmt.setDouble(2, cotacao);
                pstmt.executeUpdate();
            }
        }
        
        // Exibe uma mensagem de sucesso com os detalhes da compra e os saldos atualizados
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

    
    /**
 * Método para realizar a compra de Ripple.
 * Obtém a quantidade desejada de Ripple informada pelo usuário e realiza as devidas operações no banco de dados
 * para atualizar o saldo da carteira do investidor e registrar a transação de compra de Ripple.
 */
public void buyRipple() {
    // Obtém a quantidade de Ripple desejada informada pelo usuário
    double quantidade = Double.parseDouble(view.getTxtQuantidadeCript().getText());
    
    // Inicialização da conexão e variáveis locais
    Conexao conexao = new Conexao();
    Connection conn = null;

    try {
        // Estabelece a conexão com o banco de dados e inicia uma transação
        conn = conexao.getConnection();
        conn.setAutoCommit(false); // Inicia uma transação

        // Instância do DAO para acesso aos dados da carteira
        LoginDAO dao = new LoginDAO(conn);
        
        // Obtém o saldo atual da carteira do investidor
        Carteira res = dao.getSaldo(investidor);

        // Calcula o novo saldo após a compra de Ripple
        double saldoAtual = res.getSaldoReal();
        double novoSaldo = saldoAtual - quantidade;

        // Verifica se o saldo atualizado será negativo
        if (novoSaldo < 0) {
            JOptionPane.showMessageDialog(view, "Erro: Saldo insuficiente para realizar a compra.");
            return;
        }

        // Atualiza o saldo real na carteira do investidor
        res.setSaldoReal(novoSaldo);
        
        // Verifica a cotação atual do Ripple no banco de dados
        double cotacao = dao.getCotacao("Ripple");
        boolean cotacaoExiste = true;
        
        // Se não houver cotação no banco de dados, gera uma cotação aleatória
        if (cotacao == 0) {
            Random random = new Random();
            double min = 2.2;
            double max = 4;
            cotacao = min + (max - min) * random.nextDouble();
            cotacaoExiste = false;
        }
        
        // Cria uma instância da classe Ripple com a cotação obtida
        Ripple xrp = new Ripple(cotacao);
        
        // Calcula o valor da transação considerando a taxa de compra e taxa de câmbio
        double valorTaxadoDeTransacao = xrp.valorTaxadoDeCompra(quantidade); 
        double taxaDeCambio = 1 / xrp.getValor(); 
        double quantidadeDeCripto = valorTaxadoDeTransacao * taxaDeCambio;
        
        // Atualiza o saldo de Ripple na carteira do investidor
        double valor = res.getSaldoRipple()+ quantidadeDeCripto;
        res.setSaldoRipple(valor);
        
        // Atualiza o saldo real e de Ripple na tabela 'carteiras'
        String updateQuery = "UPDATE carteiras SET saldo_real = ?, saldo_ripple = ? WHERE cpf = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, res.getSaldoReal());
            pstmt.setDouble(2, res.getSaldoRipple());
            pstmt.setString(3, investidor.getCPF());
            pstmt.executeUpdate();
        }

        // Insere a transação de compra na tabela 'transacoes'
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

        // Insere a nova cotação do Ripple na tabela 'cotacoes' se não existir
        if (!cotacaoExiste) {
            String insertCotacao = "INSERT INTO cotacoes (moeda, cotacao) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCotacao)){
                pstmt.setString(1, "Ripple");
                pstmt.setDouble(2, cotacao);
                pstmt.executeUpdate();
            }
        }
        
        // Exibe uma mensagem de sucesso com os detalhes da compra e os saldos atualizados
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
