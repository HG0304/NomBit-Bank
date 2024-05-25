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

        /**
     * Método para realizar um saque na conta do investidor.
     */
    public void sacar() {
        // Obtém o valor do saque digitado na interface gráfica
        double valorSaque = Double.parseDouble(view.getTxtValorDoSaque().getText());

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

            // Calcula o novo saldo após o saque
            double saldoAtual = res.getSaldoReal();
            double novoSaldo = saldoAtual - valorSaque;

            // Verifica se o saldo atualizado será negativo
            if (novoSaldo < 0) {
                JOptionPane.showMessageDialog(view, "Erro: Saldo insuficiente para realizar o saque.");
                return;
            }

            // Atualiza o saldo na tabela 'carteiras'
            String updateQuery = "UPDATE carteiras SET saldo_real = ? WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setDouble(1, novoSaldo);
                pstmt.setString(2, investidor.getCPF());
                pstmt.executeUpdate();
            }

            // Insere a transação na tabela 'transacoes'
            String insertQuery = "INSERT INTO transacoes (cpf, tipo_moeda, valor, tipo_transacao, taxa) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, investidor.getCPF());
                pstmt.setString(2, "Real");
                pstmt.setDouble(3, valorSaque);
                pstmt.setString(4, "Saque");
                pstmt.setDouble(5, 0);
                pstmt.executeUpdate();
            }

            conn.commit(); // Confirma a transação

            // Exibe uma mensagem informando que o saque foi realizado com sucesso
            JOptionPane.showMessageDialog(view, String.format(
                    "Saque realizado com sucesso!\nSaldo atualizado: R$ %.2f", novoSaldo
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
