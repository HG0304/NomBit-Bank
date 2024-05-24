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

     public void Depositar() {
        double valorDeposito = Double.parseDouble(view.getTxtValorDoDeposito().getText());

        Conexao conexao = new Conexao();
        Connection conn = null;

        try {
            conn = conexao.getConnection();
            conn.setAutoCommit(false); // Inicia uma transação

            LoginDAO dao = new LoginDAO(conn);
            Carteira res = dao.getSaldo(investidor);

            double saldoAtual = res.getSaldoReal();
            res.setSaldoReal(saldoAtual + valorDeposito);

            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ? WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, res.getSaldoReal());
                pstmt.setString(2, investidor.getCPF());
                pstmt.executeUpdate();
            }

            // Insere a transação na tabela 'transacoes'
            String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, investidor.getCPF());
                pstmt.setString(2, "Real");
                pstmt.setDouble(3, valorDeposito);
                pstmt.setString(4, "Depósito");
                pstmt.setDouble(5, 0);
                pstmt.executeUpdate();
            }

            conn.commit(); // Confirma a transação

            JOptionPane.showMessageDialog(view, String.format(
                    "Depósito realizado com sucesso!\nSaldo atualizado: R$ %.2f", res.getSaldoReal()
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
