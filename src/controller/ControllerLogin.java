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
    
    public void loginInvestidor(){
        Investidor investidor = new Investidor(null, view.getTxtSenha().getText(), view.getTxtCpf().getText());
       
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            LoginDAO dao = new LoginDAO(conn);
            ResultSet res = dao.consultar(investidor);
            
            if(res.next()){
                String nome = res.getString("nome");
                String cpf = res.getString("cpf");
                String senha = res.getString("senha");

                MenuFrame viewInvestidor = new MenuFrame(new Investidor(nome, senha, cpf));
                viewInvestidor.setVisible(true);
                view.dispose();
                conn.close();
                
            } else {
                JOptionPane.showMessageDialog(view, "Login nao efetuado");
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(view, "Erro de conexão: " + e.getMessage());
        }
    }
}
