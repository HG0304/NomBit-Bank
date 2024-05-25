package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import model.Carteira;
import model.Investidor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author unifhnomura
 */
public class LoginDAO {
    private Connection conn;
    
    // construtor
    public LoginDAO(Connection conexao) {
        this.conn = conexao;
    }
    
    // metodo consultor que recebe um parametro Investidor
    public ResultSet consultar(Investidor investidor) throws SQLException{
        
        // string que contem a query sql
        String sql = "SELECT * FROM investidores WHERE cpf = ? and senha = ?;";
        
        // formata a query
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, investidor.getCPF());
        statement.setString(2, investidor.getSenha());
        statement.execute();
        
        // retorna o ResultSet
        ResultSet resultado = statement.getResultSet();
        conn.close();
        return resultado;
    }
    
    // getSaldo realiza a consulta no banco de dados recebendo como parametro
    // o investidor e retornando um objeto da classe Carteira
    public Carteira getSaldo(Investidor investidor) throws SQLException{
        
        // string que contem a query sql
        String sql = "SELECT * FROM carteiras WHERE cpf = ?;";
        
        // formata a query
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, investidor.getCPF());
        statement.execute();
        
        ResultSet resultado = statement.getResultSet();
        resultado.next();
        
        // armazena o saldo de cada moeda nas variaveis
        double saldoReal = Double.parseDouble(resultado.getString("saldo_real"));
        double saldoBitcoin = Double.parseDouble(resultado.getString("saldo_bitcoin"));
        double saldoEthereum = Double.parseDouble(resultado.getString("saldo_ethereum"));
        double saldoRipple = Double.parseDouble(resultado.getString("saldo_ripple"));
        
        // cria o objeto Carteira e retirna ela
        return new Carteira(saldoReal, saldoBitcoin, saldoEthereum, saldoRipple);
    }
    
    // motodo getCotacao realiza a consulta no banco de dados e retorna a ultima
    // cotacao cadastrada
    public double getCotacao(String moeda) throws SQLException {
        
        // query sql
        String sql = "SELECT c1.cotacao FROM cotacoes c1 WHERE c1.moeda = ? AND c1.data_hora = (\n" +
                     "SELECT MAX(c2.data_hora)\n" +
                     "FROM cotacoes c2\n" +
                     "WHERE c2.moeda = c1.moeda);";
        
        // formata a query
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, moeda);  // Define o valor do parâmetro de moeda
        ResultSet resultado = pstmt.executeQuery();
        
        // estrutura condicional que verifica se existe alguma cotacao cadastrada
        if (resultado.next()) {
            double cotacao = resultado.getDouble("cotacao");
            return cotacao;
        } else { // caso nao tenha cotacao retorna zero
            return 0.0;
        }
    }

    public List<String> getTransacoes(String cpf) throws SQLException {
    // Consulta SQL para selecionar transações de um investidor específico
    String sqlTransacoes = "SELECT cpf, tipo_moeda, valor, data_hora, tipo_transacao, taxa, cotacao " +
                           "FROM transacoes " +
                           "WHERE cpf = ?";

    // Preparar a declaração SQL
    PreparedStatement pstmtTransacoes = conn.prepareStatement(sqlTransacoes);
    pstmtTransacoes.setString(1, cpf);  // Define o valor do parâmetro cpf
    ResultSet resultadoTransacoes = pstmtTransacoes.executeQuery();

    // Lista para armazenar as transações formatadas
    List<String> transacoesList = new ArrayList<>();

    // Processar cada linha do resultado da consulta
    while (resultadoTransacoes.next()) {
        String tipoMoeda = resultadoTransacoes.getString("tipo_moeda");
        double valor = resultadoTransacoes.getDouble("valor");
        Timestamp dataHora = resultadoTransacoes.getTimestamp("data_hora");
        String tipoTransacao = resultadoTransacoes.getString("tipo_transacao");
        double taxa = resultadoTransacoes.getDouble("taxa");
        double cotacao = resultadoTransacoes.getDouble("cotacao");

        // Formatar a data e hora
        String formattedDataHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dataHora);

        // Formatar a transação
        String formattedTransacao = String.format("%s %s %.2f %s CT: %.2f TX: %.2f",
                                                  formattedDataHora, tipoTransacao, valor, tipoMoeda, cotacao, taxa);

        // Obter os saldos atuais da carteira do investidor
        Map<String, Double> saldos = getSaldo(cpf);
        String formattedSaldo = String.format("REAL: %.2f BTC: %.8f ETH: %.8f XRP: %.8f",
                                              saldos.get("REAL"), saldos.get("BTC"),
                                              saldos.get("ETH"), saldos.get("XRP"));

        // Combinar a transação formatada com os saldos
        transacoesList.add(formattedTransacao + " " + formattedSaldo);
    }

    // Fechar o ResultSet e o PreparedStatement
    resultadoTransacoes.close();
    pstmtTransacoes.close();

    // Retornar a lista de transações formatadas
    return transacoesList;
}

    // Placeholder para o método de obter os saldos atuais de um investidor
    private Map<String, Double> getSaldo(String cpf) throws SQLException {
        // Consulta SQL para obter os saldos da carteira do investidor
        String sql = "SELECT saldo_real, saldo_bitcoin, saldo_ethereum, saldo_ripple FROM carteiras WHERE cpf = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, cpf);
        ResultSet resultado = pstmt.executeQuery();

        // Map para armazenar os saldos
        Map<String, Double> saldos = new HashMap<>();
        if (resultado.next()) {
            saldos.put("REAL", resultado.getDouble("saldo_real"));
            saldos.put("BTC", resultado.getDouble("saldo_bitcoin"));
            saldos.put("ETH", resultado.getDouble("saldo_ethereum"));
            saldos.put("XRP", resultado.getDouble("saldo_ripple"));
        }

        // Fechar o ResultSet e o PreparedStatement
        resultado.close();
        pstmt.close();

        // Retornar os saldos
        return saldos;
    }


}
