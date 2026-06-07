package boundary;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.ControllerCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameDettaglioProdotto extends JFrame {

    private JSpinner spinnerQtaDesiderataProdotto;
    private JButton btnAggiungiACarrello;
    private JLabel lblNomeProdotto;
    private JLabel lblPrezzoProdotto;
    private JLabel lblCategoriaProdotto;
    private JLabel lblQtaDispProdotto;
    private JLabel lblScontatoProdotto;
    private JTextArea txtAreaDescrizioneProdotto;
    private JPanel panelProdotto;
    private JLabel lblEsito;

    //costruttore per definire impostazioni finestra e invocare il metodo fillProdotto()
    public FrameDettaglioProdotto(String nome, float prezzo, String categoria, int qtaDisp, boolean isScontato, String descrizione) {
        lblEsito.setVisible(false);
        setTitle("Visualizzazione prodotto: " + nome);
        setContentPane(panelProdotto);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        fillProdotto(nome, prezzo, categoria, qtaDisp, isScontato, descrizione);

        setVisible(true);

        btnAggiungiACarrello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Chiama il metodo da eseguire quando si preme il pulsante

                System.out.println("Bottone aggiungi al carrello premuto");
                String nomeProdotto = lblNomeProdotto.getText();
                int qtaDesiderata = (int) spinnerQtaDesiderataProdotto.getValue();

                checkInput(nomeProdotto, qtaDesiderata);
            }
        });
    }

    private void fillProdotto(String nome, float prezzo, String categoria, int qtaDisp, boolean isScontato, String descrizione) {
        lblNomeProdotto.setText(nome);
        lblPrezzoProdotto.setText(String.valueOf(prezzo) + " €");
        lblCategoriaProdotto.setText(categoria);
        lblQtaDispProdotto.setText(String.valueOf(qtaDisp));

        if (isScontato) {
            lblScontatoProdotto.setText("Sì");
        } else {
            lblScontatoProdotto.setText("No");
        }
        txtAreaDescrizioneProdotto.setText(descrizione);

    }

    private boolean checkInput(String nomeProdotto, int qtaDesiderata) {
        boolean esito = false;
        if (!nomeProdotto.isEmpty() && nomeProdotto.length() < 1000) {

            if (!ControllerCliente.esisteProdotto(nomeProdotto)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore! Non presente nel catalogo!",
                        "Errore aggiunta al carrello",
                        JOptionPane.ERROR_MESSAGE);
                return false; // Interrompiamo tutto subito
            }

            if (qtaDesiderata > 0) {
                //Quantità inserita valida; si procede al controller
                esito = ControllerCliente.AggiungiAlCarrello(nomeProdotto, qtaDesiderata);

                if (esito) {
                    //tutto ok, aggiorna la visualizzazione del carrello
                    lblEsito.setText("Aggiunto al carrello!");
                    spinnerQtaDesiderataProdotto.setValue(0);
                    lblEsito.setForeground(Color.GREEN);
                    lblEsito.setVisible(true);

                    JOptionPane.showMessageDialog(
                            null,
                            "Prodotto ok",
                            "Conferma aggiunta al carrello",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Errore! Condizioni verificate, ma qualcosa è andato storto!",
                            "Errore aggiunta al carrello",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } else {
                //Quantità inserita non valida
                lblEsito.setText("ERRORE! Quantità inserita non valida!");
                lblEsito.setForeground(Color.RED);
                spinnerQtaDesiderataProdotto.setValue(0);
                lblEsito.setVisible(true);

                JOptionPane.showMessageDialog(
                        null,
                        "Errore! Quantità richiesta non valida!",
                        "Errore aggiunta al carrello",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            //prodotto non presente nel catalogo
            lblEsito.setText("ERRORE! Prodotto non presente nel catalogo o non valido!");
            lblEsito.setForeground(Color.RED);
            spinnerQtaDesiderataProdotto.setValue(0);
            lblEsito.setVisible(true);
        }
        return esito;
    }

    /*
    private void fillProdottoStub() {
        //String nomeProdotto, double prezzoProdotto, String categoriaProdotto, int qtaDispProdotto, double scontatoProdotto, String descrizioneProdotto
        lblNomeProdotto.setText("Prodotto 1");
        lblPrezzoProdotto.setText("100.00");
        lblCategoriaProdotto.setText("Categoria 1");
        lblQtaDispProdotto.setText("10");
        lblScontatoProdotto.setText("10.00");
        txtAreaDescrizioneProdotto.setText("Descrizione prodotto 1");
    }
     */

    /*
    //main per testare la funzionalità
    public static void main(String[] args) {
        //chiama costruttore della finestra
        new FrameDettaglioProdotto();
    }
    */

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
        panelProdotto = new JPanel();
        panelProdotto.setLayout(new GridLayoutManager(7, 3, new Insets(2, 2, 2, 2), -1, -1));
        panelProdotto.setMinimumSize(new Dimension(450, 300));
        panelProdotto.setPreferredSize(new Dimension(1450, 1300));
        final JLabel label1 = new JLabel();
        label1.setText("Nome: ");
        panelProdotto.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblNomeProdotto = new JLabel();
        lblNomeProdotto.setText("Label");
        panelProdotto.add(lblNomeProdotto, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(116, 17), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Prezzo: ");
        panelProdotto.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblPrezzoProdotto = new JLabel();
        lblPrezzoProdotto.setText("Label");
        panelProdotto.add(lblPrezzoProdotto, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Categoria");
        panelProdotto.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Quantità disponibile: ");
        panelProdotto.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Scontato");
        panelProdotto.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Descrizione: ");
        panelProdotto.add(label6, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblCategoriaProdotto = new JLabel();
        lblCategoriaProdotto.setText("Label");
        panelProdotto.add(lblCategoriaProdotto, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblQtaDispProdotto = new JLabel();
        lblQtaDispProdotto.setText("Label");
        panelProdotto.add(lblQtaDispProdotto, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblScontatoProdotto = new JLabel();
        lblScontatoProdotto.setText("Label");
        panelProdotto.add(lblScontatoProdotto, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelProdotto.add(spacer1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelProdotto.add(spacer2, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panelProdotto.add(spacer3, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panelProdotto.add(spacer4, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelProdotto.add(panel1, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Quantità desiderata:");
        panel1.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel1.add(spacer5, new GridConstraints(1, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        spinnerQtaDesiderataProdotto = new JSpinner();
        panel1.add(spinnerQtaDesiderataProdotto, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAggiungiACarrello = new JButton();
        btnAggiungiACarrello.setText("Aggiungi al carrello");
        panel1.add(btnAggiungiACarrello, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel1.add(spacer6, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel1.add(spacer7, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        lblEsito = new JLabel();
        lblEsito.setText("Label");
        panel1.add(lblEsito, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panelProdotto.add(spacer8, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        txtAreaDescrizioneProdotto = new JTextArea();
        txtAreaDescrizioneProdotto.setEditable(false);
        panelProdotto.add(txtAreaDescrizioneProdotto, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer9 = new Spacer();
        panelProdotto.add(spacer9, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        panelProdotto.add(spacer10, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelProdotto;
    }

}
