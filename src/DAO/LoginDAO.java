package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import model.Carteira;
import model.Investidor;

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
    
    public Carteira getSaldo(Investidor investidor) throws SQLException{

        String sql = "SELECT * FROM carteiras WHERE cpf = ?;";
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, investidor.getCPF());
        statement.execute();
        
        ResultSet resultado = statement.getResultSet();
        resultado.next();
        
        double saldoReal = Double.parseDouble(resultado.getString("saldo_real"));
        double saldoBitcoin = Double.parseDouble(resultado.getString("saldo_bitcoin"));
        double saldoEthereum = Double.parseDouble(resultado.getString("saldo_ethereum"));
        double saldoRipple = Double.parseDouble(resultado.getString("saldo_ripple"));
   
        return new Carteira(saldoReal, saldoBitcoin, saldoEthereum, saldoRipple);
    }
    
    public double getCotacao(String moeda) throws SQLException {
        String sql = "SELECT c1.cotacao FROM cotacoes c1 WHERE c1.moeda = ? AND c1.data_hora = (\n" +
                     "SELECT MAX(c2.data_hora)\n" +
                     "FROM cotacoes c2\n" +
                     "WHERE c2.moeda = c1.moeda);";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, moeda);  // Define o valor do parâmetro de moeda
        ResultSet resultado = pstmt.executeQuery();

        if (resultado.next()) {
            double cotacao = resultado.getDouble("cotacao");
            return cotacao;
        } else {
            return 0.0;
        }
    }


}
