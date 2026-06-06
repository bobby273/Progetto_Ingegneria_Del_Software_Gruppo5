package boundary;

import controller.ControllerAccesso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//fare semrpe check per credenziali amministratore al login

public class FrameAccesso {
    private JTextField textFieldEmail;
    private JPasswordField passwordField;
    private JButton accediButton;
    private JPanel accessoPane;
    private JLabel labelEsito;

    public FrameAccesso(){
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = textFieldEmail.getText();
                String password = passwordField.getText();
                int esito = -1;
                if(email.length()<=40 && password.length()<=40 && password.length()>=8 && email.contains("@")){
                    esito = ControllerAccesso.checkTipoUtente(email, password);
                    if (esito == ControllerAccesso.UTENTE_AMM) {
                        //chiamata alla frame di admin
                        labelEsito.setText("Amministratore");
                    } else if (esito == ControllerAccesso.UTENTE_CLIE){
                        //chiamata alla frame di cliente
                        labelEsito.setText("Cliente");
                    }
                    else
                        //chiamata a frame di errore?
                        labelEsito.setText("Utente non trovato");
                } else
                    labelEsito.setText("Errore dimensione campi.");
            }
        });
    }

    public JFrame apriFormAcc(){
        JFrame frame=new JFrame("Accesso");
        frame.setContentPane(accessoPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }
}
