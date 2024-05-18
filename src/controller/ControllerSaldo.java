/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.LoginDAO;
import model.Investidor;
import view.ExibirSaldoFrame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
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
    
    public Boolean confirmarSenha(){

        String senha = view.getTxtSenha().getText();
        
        if(investidor.getSenha().equalsIgnoreCase(senha) == true){
            return true;
        }
        return false;
    }
    
    public void consultarSaldo(){
        System.out.println("teste3");
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            LoginDAO dao = new LoginDAO(conn);
            Carteira res = dao.getSaldo(investidor);

            System.out.println("saldo em real: " + res.getSaldoReal());
            System.out.println("saldo em bitcoin: " + res.getSaldoBitcoin());
            System.out.println("saldo em ethereum: " + res.getSaldoEthereum());
            System.out.println("saldo em ripple: " + res.getSaldoRipple());
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(view, "Erro: " + e.getMessage());
        }
    }
}
