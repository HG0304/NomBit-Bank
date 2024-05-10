/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.LoginFrame;

/**
 *
 * @author unifhnomura
 */
public class ControllerLogin {
     private LoginFrame view;

    public ControllerLogin(LoginFrame view) {
        this.view = view;
    }
    
    public void loginAluno(){
        Investidor investidor = new Investidor(null, view.getTxtUsuario().getText(),
                                      view.getTxtSenha().getText());
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            AlunoDAO dao = new AlunoDAO(conn);
            ResultSet res = dao.consultar(aluno);
            
            if(res.next()){
                JOptionPane.showMessageDialog(view, "Login feito!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                String nome = res.getString("nome");
                String usuario = res.getString("usuario");
                String senha = res.getString("senha");
                UsuarioFrame viewUsuario = new UsuarioFrame(new Aluno(nome, usuario, senha));
                viewUsuario.setVisible(true);
                view.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(view, "Login nao efetuado");
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(view, "Erro de conexao");
        }
    }
}
