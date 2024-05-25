package controller;

import DAO.Conexao;
import DAO.LoginDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Carteira;
import model.Investidor;
import view.DepositoFrame;
import java.sql.PreparedStatement;

/**
 *
 * @author hugoe
 */
public class ControllerDeposito {
    private DepositoFrame view;
    private Investidor investidor;

    public ControllerDeposito(DepositoFrame view, Investidor investidor) {
        this.view = view;
        this.investidor = investidor;
    }

        /**
    * Método para realizar um depósito na conta do investidor.
    * Obtém o valor do depósito informado pelo usuário e realiza as devidas operações no banco de dados
    * para atualizar o saldo da carteira do investidor e registrar a transação de depósito.
    */
   public void Depositar() {
       // Obtém o valor do depósito informado pelo usuário
       double valorDeposito = Double.parseDouble(view.getTxtValorDoDeposito().getText());

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

           // Calcula o novo saldo após o depósito
           double saldoAtual = res.getSaldoReal();
           res.setSaldoReal(saldoAtual + valorDeposito);

           // Atualiza o saldo real na carteira do investidor na tabela 'carteiras'
           String updateQuery = "UPDATE carteiras SET saldo_real = ? WHERE cpf = ?";
           try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
               pstmt.setDouble(1, res.getSaldoReal());
               pstmt.setString(2, investidor.getCPF());
               pstmt.executeUpdate();
           }

           // Insere a transação de depósito na tabela 'transacoes'
           String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa) VALUES (?, ?, ?, ?, ?)";
           try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
               pstmt.setString(1, investidor.getCPF());
               pstmt.setString(2, "Real");
               pstmt.setDouble(3, valorDeposito);
               pstmt.setString(4, "Depósito");
               pstmt.setDouble(5, 0);
               pstmt.executeUpdate();
           }

           // Confirma a transação
           conn.commit(); // Confirma a transação

           // Exibe uma mensagem de sucesso com o saldo atualizado
           JOptionPane.showMessageDialog(view, String.format(
                   "Depósito realizado com sucesso!\nSaldo atualizado: R$ %.2f", res.getSaldoReal()
           ));

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
