package boundary;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.ControllerAccesso;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
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
                                            public void actionPerformed(ActionEvent e) {
                                                String email = textFieldEmail.getText();
                                                String nome = textFieldNome.getText();
                                                String cognome = textField1Cognome.getText();
                                                String password = passwordField.getText();
                                                String indirizzoSpedizione = textFieldIndSped.getText();
                                                boolean isAdmin = checkBoxAdmin.isSelected();
                                                String badge = textFieldBadge.getText();

                                                int esito = -1;
                                                if (isAdmin) {
                                                    if (email.length() <= 40 && email.contains("@")
                                                            && nome.length() <= 40 && nome.matches("[\\p{L}\\s']+")
                                                            && cognome.length() <= 40 && cognome.matches("[\\p{L}\\s']+")
                                                            && password.length() >= 8 && password.length() <= 40
                                                            && badge.length() == 20 && badge.matches("[0-9]+"))
                                                        esito = ControllerAccesso.creaNuovoAmministratore(email, nome, cognome, password, badge);
                                                } else {
                                                    if (email.length() <= 40 && email.contains("@")
                                                            && nome.length() <= 40 && nome.matches("[\\p{L}\\s']+")
                                                            && cognome.length() <= 40 && cognome.matches("[\\p{L}\\s']+")
                                                            && password.length() >= 8 && password.length() <= 40
                                                            && indirizzoSpedizione.length() <= 100 && indirizzoSpedizione.length() >= 5)
                                                        esito = ControllerAccesso.creaNuovoCliente(email, nome, cognome, password, indirizzoSpedizione);
                                                }
                                                if (esito == ControllerAccesso.AMM_CREATO) {
                                                    labelEsito.setText("Amministratore registrato con successo.");
                                                } else if (esito == ControllerAccesso.AMM_EXS) {
                                                    labelEsito.setText("Amministratore gia' registrato con queste credenziali.");
                                                } else if (esito == ControllerAccesso.CLIE_CREATO) {
                                                    labelEsito.setText("Cliente registrato con successo.");
                                                } else if (esito == ControllerAccesso.CLIE_EXS) {
                                                    labelEsito.setText("Cliente gia' registrato con queste credenziali.");
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

    public JFrame apriFormReg() {
        JFrame frame = new JFrame("Registrazione");
        frame.setContentPane(registrazionePane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        registrazionePane = new JPanel();
        registrazionePane.setLayout(new GridLayoutManager(13, 4, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Email");
        registrazionePane.add(label1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Nome");
        registrazionePane.add(label2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Cognome");
        registrazionePane.add(label3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Password");
        registrazionePane.add(label4, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        indSpedLabel = new JLabel();
        indSpedLabel.setText("Indirizzo di spedizione");
        registrazionePane.add(indSpedLabel, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        profImgTxt = new JLabel();
        profImgTxt.setText("Immagine del profilo");
        registrazionePane.add(profImgTxt, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1Cognome = new JTextField();
        registrazionePane.add(textField1Cognome, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldNome = new JTextField();
        registrazionePane.add(textFieldNome, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldEmail = new JTextField();
        registrazionePane.add(textFieldEmail, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField = new JPasswordField();
        registrazionePane.add(passwordField, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldIndSped = new JTextField();
        registrazionePane.add(textFieldIndSped, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buttonImgPr = new JButton();
        buttonImgPr.setText("Carica");
        registrazionePane.add(buttonImgPr, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Amministratore");
        registrazionePane.add(label5, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxAdmin = new JCheckBox();
        checkBoxAdmin.setSelected(false);
        checkBoxAdmin.setText("");
        registrazionePane.add(checkBoxAdmin, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelEsito = new JLabel();
        labelEsito.setText("In attesa dell'esito...");
        registrazionePane.add(labelEsito, new GridConstraints(12, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        registrazionePane.add(spacer1, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        registrazionePane.add(spacer2, new GridConstraints(11, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textAdmin = new JLabel();
        textAdmin.setText("Badge Amministratore");
        registrazionePane.add(textAdmin, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldBadge = new JTextField();
        textFieldBadge.setEnabled(true);
        registrazionePane.add(textFieldBadge, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buttonConfReg = new JButton();
        buttonConfReg.setText("Registrati");
        registrazionePane.add(buttonConfReg, new GridConstraints(10, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        registrazionePane.add(spacer3, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        registrazionePane.add(spacer4, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Esito");
        registrazionePane.add(label6, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        registrazionePane.add(spacer5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return registrazionePane;
    }

}
