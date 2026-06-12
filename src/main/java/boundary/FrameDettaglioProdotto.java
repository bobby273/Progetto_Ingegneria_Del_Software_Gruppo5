package boundary;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.ControllerCliente;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

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
    private JLabel lblDisponibile;


    //costruttore per definire impostazioni finestra e invocare il metodo fillProdotto()
    public FrameDettaglioProdotto(String nome, float prezzo, String categoria, int qtaDisp, boolean isScontato, String descrizione, boolean isDisponibile) {
        lblEsito.setVisible(false);
        setTitle("Visualizzazione prodotto: " + nome);
        setContentPane(panelProdotto);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        fillProdotto(nome, prezzo, categoria, qtaDisp, isScontato, descrizione, isDisponibile); //funzione per riempire a schermo i dettagli del prodotto

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

    private void fillProdotto(String nome, float prezzo, String categoria, int qtaDisp, boolean isScontato, String descrizione, boolean isDisponibile) {
        lblNomeProdotto.setText(nome);
        lblPrezzoProdotto.setText(String.valueOf(prezzo) + " €");
        lblCategoriaProdotto.setText(categoria);
        lblQtaDispProdotto.setText(String.valueOf(qtaDisp));

        if (isScontato) {
            lblScontatoProdotto.setText("Sì");
        } else {
            lblScontatoProdotto.setText("No");
        }
        if (isDisponibile) {
            lblDisponibile.setText("Sì");
        } else {
            lblDisponibile.setText("No");
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
        panelProdotto.setLayout(new GridLayoutManager(8, 3, new Insets(2, 2, 2, 2), -1, -1));
        panelProdotto.setBackground(new Color(-657931));
        panelProdotto.setMinimumSize(new Dimension(450, 300));
        panelProdotto.setPreferredSize(new Dimension(1450, 1300));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-13877680));
        Font label1Font = this.$$$getFont$$$("Segoe UI", Font.BOLD, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-13877680));
        label1.setText("Nome: ");
        panelProdotto.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblNomeProdotto = new JLabel();
        lblNomeProdotto.setText("Label");
        panelProdotto.add(lblNomeProdotto, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(116, 17), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-13877680));
        Font label2Font = this.$$$getFont$$$("Segoe UI", Font.BOLD, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Prezzo: ");
        panelProdotto.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblPrezzoProdotto = new JLabel();
        lblPrezzoProdotto.setText("Label");
        panelProdotto.add(lblPrezzoProdotto, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setBackground(new Color(-13877680));
        Font label3Font = this.$$$getFont$$$("Segoe UI", Font.BOLD, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Categoria");
        panelProdotto.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setBackground(new Color(-13877680));
        Font label4Font = this.$$$getFont$$$("Segoe UI", Font.BOLD, 18, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Quantità disponibile: ");
        panelProdotto.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setBackground(new Color(-13877680));
        Font label5Font = this.$$$getFont$$$("Segoe UI", Font.BOLD, 18, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Scontato");
        panelProdotto.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setBackground(new Color(-13877680));
        Font label6Font = this.$$$getFont$$$("Segoe UI", Font.BOLD, 18, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("Descrizione: ");
        panelProdotto.add(label6, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        panelProdotto.add(spacer4, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelProdotto.add(panel1, new GridConstraints(7, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        panelProdotto.add(spacer8, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        txtAreaDescrizioneProdotto = new JTextArea();
        txtAreaDescrizioneProdotto.setEditable(false);
        panelProdotto.add(txtAreaDescrizioneProdotto, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer9 = new Spacer();
        panelProdotto.add(spacer9, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        panelProdotto.add(spacer10, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setBackground(new Color(-13877680));
        Font label8Font = this.$$$getFont$$$("Segoe UI", Font.BOLD, 18, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setForeground(new Color(-3025959));
        label8.setText("Disponibile?");
        panelProdotto.add(label8, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        panelProdotto.add(spacer11, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        lblDisponibile = new JLabel();
        Font lblDisponibileFont = this.$$$getFont$$$(null, -1, -1, lblDisponibile.getFont());
        if (lblDisponibileFont != null) lblDisponibile.setFont(lblDisponibileFont);
        lblDisponibile.setText("Label");
        panelProdotto.add(lblDisponibile, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelProdotto;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
