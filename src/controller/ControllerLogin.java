/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.LoginDAO;
import model.Investidor;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import view.LoginFrame;
import view.MenuFrame;

/**
 *
 * @author unifhnomura
 */
public class ControllerLogin {
     private LoginFrame view;

    public ControllerLogin(LoginFrame view) {
        this.view = view;
    }
    
        /**
     * Método para realizar o login do investidor.
     * Verifica se as credenciais do investidor são válidas e, em caso afirmativo, exibe o menu principal.
     */
    public void loginInvestidor() {
        // Cria um objeto Investidor com as credenciais fornecidas na interface gráfica
        Investidor investidor = new Investidor(null, view.getTxtSenha().getText(), view.getTxtCpf().getText());

        // Inicialização da conexão com o banco de dados
        Conexao conexao = new Conexao();

        try {
            // Estabelece a conexão com o banco de dados
            Connection conn = conexao.getConnection();

            // Instância do DAO para acesso aos dados de login
            LoginDAO dao = new LoginDAO(conn);

            // Consulta o banco de dados para verificar as credenciais do investidor
            ResultSet res = dao.consultar(investidor);

            // Verifica se as credenciais são válidas
            if(res.next()) {
                // Se as credenciais forem válidas, obtém os dados do investidor
                String nome = res.getString("nome");
                String cpf = res.getString("cpf");
                String senha = res.getString("senha");

                // Exibe o menu principal com os dados do investidor autenticado
                MenuFrame viewInvestidor = new MenuFrame(new Investidor(nome, senha, cpf));
                viewInvestidor.setVisible(true);

                // Fecha a tela de login
                view.dispose();

                // Fecha a conexão com o banco de dados
                conn.close();
            } else {
                // Se as credenciais não forem válidas, exibe uma mensagem de erro
                JOptionPane.showMessageDialog(view, "Login não efetuado");
            }
        } catch(SQLException e) {
            // Em caso de erro de conexão, exibe uma mensagem de erro
            JOptionPane.showMessageDialog(view, "Erro de conexão: " + e.getMessage());
        }
    }
}
