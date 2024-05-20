package controller;

import model.Investidor;
import view.BuyCriptFrame;

/**
 *
 * @author hugoe
 */
public class ControllerBuyCript {
    private BuyCriptFrame view;
    private Investidor investidor;

    public ControllerBuyCript(BuyCriptFrame view, Investidor investidor) {
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
}
