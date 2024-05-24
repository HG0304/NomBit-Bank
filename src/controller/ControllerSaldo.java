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
        
        if(investidor.getSenha().equals(senha) == true){
            return true;
        }
        return false;
    }
    
    public void consultarSaldo(){
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            LoginDAO dao = new LoginDAO(conn);
            Carteira res = dao.getSaldo(investidor);
            
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

            JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(view, "Erro: " + e.getMessage());
        }
    }
}
