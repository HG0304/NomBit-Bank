package controller;

import DAO.Conexao;
import DAO.LoginDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Carteira;
import model.Investidor;
import view.SaqueFrame;

/**
 *
 * @author hugoe
 */
public class ControllerSaque {
     private SaqueFrame view;
    private Investidor investidor;

    public ControllerSaque(SaqueFrame view, Investidor investidor) {
        this.view = view;
        this.investidor = investidor;
    }

    public void Sacar() {
        double valorSaque = Double.parseDouble(view.getTxtValorDoSaque().getText());

        Conexao conexao = new Conexao();
        Connection conn = null;

        try {
            conn = conexao.getConnection();
            conn.setAutoCommit(false); // Inicia uma transação

            LoginDAO dao = new LoginDAO(conn);
            Carteira res = dao.getSaldo(investidor);

            double saldoAtual = res.getSaldoReal();
            double novoSaldo = saldoAtual - valorSaque;

            // Verifica se o saldo atualizado será negativo
            if (novoSaldo < 0) {
                JOptionPane.showMessageDialog(view, "Erro: Saldo insuficiente para realizar o saque.");
                return;
            }

            res.setSaldoReal(novoSaldo);

            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ? WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, res.getSaldoReal());
                pstmt.setString(2, investidor.getCPF());
                pstmt.executeUpdate();
            }

            // Insere a transação na tabela 'transacoes'
            String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, investidor.getCPF());
                pstmt.setString(2, "Real");
                pstmt.setDouble(3, valorSaque);
                pstmt.setString(4, "Saque");
                pstmt.executeUpdate();
            }

            conn.commit(); // Confirma a transação

            JOptionPane.showMessageDialog(view, String.format(
                    "Saque realizado com sucesso!\nSaldo atualizado: R$ %.2f", res.getSaldoReal()
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
