package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import model.Investidor;

/**
 *
 * @author unifhnomura
 */
public class LoginDAO {
    private Connection conn;

    public LoginDAO(Connection conexao) {
        this.conn = conexao;
    }
    
    public ResultSet consultar(Investidor investidor) throws SQLException{

        String sql = "SELECT * FROM investidores WHERE cpf = ? and senha = ?;";
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, investidor.getCPF());
        statement.setString(2, investidor.getSenha());
        statement.execute();
        
        ResultSet resultado = statement.getResultSet();
        conn.close();
        return resultado;
    }
}
