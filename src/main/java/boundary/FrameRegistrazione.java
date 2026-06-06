package boundary;

import controller.ControllerAccesso;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameRegistrazione {
    private JPanel registrazionePane;
    private JTextField textFieldEmail;
    private JTextField textFieldNome;
    private JTextField textField1Cognome;
    private JPasswordField passwordField;
    private JTextField textFieldIndSped;
    private JButton buttonImgPr;
    private JCheckBox checkBoxAdmin;
    private JTextField textFieldBadge;
    private JButton buttonConfReg;
    private JLabel labelEsito;
    private JLabel textAdmin;
    private JLabel indSpedLabel;
    private JLabel profImgTxt;

    public FrameRegistrazione() {
        checkBoxAdmin.setSelected(false);
        textFieldBadge.setVisible(false);
        textFieldBadge.setEnabled(false);
        textAdmin.setVisible(false);


        buttonConfReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                    String email=textFieldEmail.getText();
                    String nome=textFieldNome.getText();
                    String cognome=textField1Cognome.getText();
                    String password=passwordField.getText();
                    String indirizzoSpedizione=textFieldIndSped.getText();
                    boolean isAdmin=checkBoxAdmin.isSelected();
                    String badge=textFieldBadge.getText();

                    int esito=-1;
                    if(isAdmin){
                        if(email.length()<=40 && email.contains("@")
                                && nome.length()<=40 && nome.matches("[\\p{L}\\s']+")
                                && cognome.length()<=40 && cognome.matches("[\\p{L}\\s']+")
                                && password.length()>=8 && password.length()<=40
                                && badge.length()==20 && badge.matches("[0-9]+"))
                            esito = ControllerAccesso.creaNuovoAmministratore(email, nome, cognome, password, badge);
                    } else {
                        if(email.length()<=40 && email.contains("@")
                                && nome.length()<=40 && nome.matches("[\\p{L}\\s']+")
                                && cognome.length()<=40 && cognome.matches("[\\p{L}\\s']+")
                                && password.length()>=8 && password.length()<=40
                                && indirizzoSpedizione.length()<=100 && indirizzoSpedizione.length()>=5)
                            esito = ControllerAccesso.creaNuovoCliente(email, nome, cognome, password, indirizzoSpedizione);
                    }
                    if(esito== ControllerAccesso.AMM_CREATO){
                        labelEsito.setText("Amministratore registrato con successo.");
                    } else if(esito== ControllerAccesso.CLIE_CREATO){
                        labelEsito.setText("Cliente registrato con successo.");
                    } else {
                        labelEsito.setText("Errore dimensione campi.");
                    }
                }
            }
        );

        checkBoxAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldBadge.setEnabled(checkBoxAdmin.isSelected());
                textFieldBadge.setVisible(checkBoxAdmin.isSelected());
                textAdmin.setVisible(checkBoxAdmin.isSelected());
                textFieldIndSped.setVisible(!checkBoxAdmin.isSelected());
                indSpedLabel.setVisible(!checkBoxAdmin.isSelected());
                profImgTxt.setVisible(!checkBoxAdmin.isSelected());
                buttonImgPr.setVisible(!checkBoxAdmin.isSelected());
            }
        });
    }

    public JFrame apriFormReg(){
        JFrame frame=new JFrame("Registrazione");
        frame.setContentPane(registrazionePane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }
}
