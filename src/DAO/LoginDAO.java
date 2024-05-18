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
}
