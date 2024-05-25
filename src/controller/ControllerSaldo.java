/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.LoginDAO;
import view.ExibirSaldoFrame;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Carteira;
import model.Investidor;

/**
 *
 * @author hugoe
 */
public class ControllerSaldo {
    private ExibirSaldoFrame view;
    private Investidor investidor;

    public ControllerSaldo(ExibirSaldoFrame view, Investidor investidor) {
        this.view = view;
        this.investidor = investidor;
    }
    
        /**
     * Método para confirmar se a senha inserida corresponde à senha do investidor.
     * @return true se a senha estiver correta, caso contrário, retorna false.
     */
    public Boolean confirmarSenha() {
        // Obtém a senha digitada na interface gráfica
        String senha = view.getTxtSenha().getText();

        // Compara a senha digitada com a senha do investidor
        if(investidor.getSenha().equals(senha)) {
            return true; // Retorna true se as senhas coincidirem
        }
        return false; // Retorna false se as senhas não coincidirem
    }

    /**
     * Método para consultar e exibir o saldo da conta do investidor.
     */
    public void consultarSaldo() {
        // Inicialização da conexão com o banco de dados
        Conexao conexao = new Conexao();

        try {
            // Estabelece a conexão com o banco de dados
            Connection conn = conexao.getConnection();

            // Instância do DAO para acessar os dados da carteira do investidor
            LoginDAO dao = new LoginDAO(conn);

            // Consulta o banco de dados para obter o saldo da conta do investidor
            Carteira res = dao.getSaldo(investidor);

            // Formata a mensagem com os saldos das diferentes moedas
            String mensagem = String.format(
                "Saldo da conta em Real: R$ %.2f\n" +
                "Saldo da conta em Bitcoin: BTC %.10f\n" +
                "Saldo da conta em Ethereum: ETH %.10f\n" +
                "Saldo da conta em Ripple: XRP %.2f",
                res.getSaldoReal(),
                res.getSaldoBitcoin(),
                res.getSaldoEthereum(),
                res.getSaldoRipple()
            );

            // Exibe a mensagem com os saldos da conta do investidor
            JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);

        } catch(SQLException e) {
            // Em caso de erro de conexão, exibe uma mensagem de erro
            JOptionPane.showMessageDialog(view, "Erro: " + e.getMessage());
        }
    }
}
