package boundary;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.ControllerCliente;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.List;
import java.util.Locale;


public class FrameCarrello extends JFrame {

    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private JPanel panelCarrello;

    public FrameCarrello(String emailUtente) {
        //design
        setTitle("Il tuo carrello");
        setContentPane(contentPanel);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //Scorrimento
        panelCarrello.setLayout(new BoxLayout(panelCarrello, BoxLayout.Y_AXIS));

        //Riempimento
        fillCarrello(emailUtente);
        setVisible(true);

    }

    /*
    //Per testare se la schermata va
    public static void main(String[] args) {
        new FrameCarrello("r.giove@gmail.com");
    }
*/

    private void fillCarrello(String emailUtente) {
        panelCarrello.removeAll();

        List<String[]> prodotti = ControllerCliente.getCarrelloBreve();

        if (prodotti == null || prodotti.isEmpty()) {
            JLabel lblVuoto = new JLabel("Nessun prodotto nel catalogo al momento", SwingConstants.CENTER);
            lblVuoto.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            lblVuoto.setForeground(Color.GRAY);
            lblVuoto.setAlignmentX(Component.CENTER_ALIGNMENT);

            panelCarrello.add(Box.createVerticalGlue());
            panelCarrello.add(lblVuoto);
            panelCarrello.add(Box.createVerticalGlue());
        } else {
            for (String[] p : prodotti) {
                JPanel panelProdotto = new JPanel(new BorderLayout(15, 10));
                panelProdotto.setBackground(Color.WHITE);
                panelProdotto.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                panelProdotto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

                //Spacchetto la stringa
                String nome = p[0];
                String descrizione = p[1];
                String prezzo = p[2];
                String categoria = p[3];
                String quantita = p[4];
                String disponibile = p[5];

                if(disponibile.equals("true")){
                    disponibile="Sì";
                }
                else{
                    disponibile="No";
                }

                String testoHtml = "<html><div style='width: 350px; font-family: Segoe UI;'>" +
                        "<h3 style='margin:0; color:#2c3e50;'>" + nome + " <span style='font-size:10px; color:#bdc3c7'>(" + categoria + ")</span></h3>" +
                        "<p style='margin:4px 0 8px 0; color:#7f8c8d;'>" + descrizione + "</p>" +
                        "<p style='margin:0; font-size:11px;'>Prezzo: <b style='color:#27ae60;'>" + prezzo + " €</b> | Quantità: <b>" + quantita + "</b> | Disponibile? "+ disponibile + "</p>" +
                        "</div></html>";

                JLabel lblInfo = new JLabel(testoHtml);
                JButton btnRimuovi = new JButton("Rimuovi dal carrello");
                btnRimuovi.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnRimuovi.setBackground(new Color(230, 230, 230));
                btnRimuovi.setForeground(new Color(192, 57, 43));
                btnRimuovi.setFocusPainted(false);

                //Action per la rimozione (btnRimuovi)
                btnRimuovi.addActionListener(e -> {
                    int dialogResult = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler rimuovere " + nome + "?", "Conferma", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        boolean rimosso = ControllerCliente.rimuoviProdottoDalCarrello(nome);
                        if (rimosso) {
                            JOptionPane.showMessageDialog(this, "Prodotto rimosso con successo.");
                            fillCarrello(emailUtente); // Ricarica la grafica per far sparire il pannello
                            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                        } else {
                            JOptionPane.showMessageDialog(this, "Errore nella rimozione.", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                panelProdotto.add(lblInfo, BorderLayout.CENTER);
                panelProdotto.add(btnRimuovi, BorderLayout.EAST);
                panelCarrello.add(panelProdotto);
                panelCarrello.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            JButton btnProcediOrdine = new JButton("Procedi all'ordine");
            btnProcediOrdine.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnProcediOrdine.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnProcediOrdine.setBackground(new Color(41, 128, 185)); // Blu
            btnProcediOrdine.setForeground(new Color(32, 29, 29));
            btnProcediOrdine.setFocusPainted(false);
            btnProcediOrdine.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnProcediOrdine.setMaximumSize(new Dimension(250, 40));

            btnProcediOrdine.addActionListener(e -> {
                // Apre il frame per completare l'ordine
                new FrameCreaOrdine(emailUtente);
            });

            panelCarrello.add(Box.createRigidArea(new Dimension(0, 20))); // Spazio sopra
            panelCarrello.add(btnProcediOrdine);
            panelCarrello.add(Box.createRigidArea(new Dimension(0, 20))); // Spazio sotto
        }
        panelCarrello.revalidate();
        panelCarrello.repaint();
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
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.setBackground(new Color(-657931));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-13877680));
        Font label1Font = this.$$$getFont$$$("Segoe UI", Font.BOLD, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-13877680));
        label1.setText("Prodotti presenti nel carrello");
        contentPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPanel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        scrollPane = new JScrollPane();
        contentPanel.add(scrollPane, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelCarrello = new JPanel();
        panelCarrello.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane.setViewportView(panelCarrello);
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
        return contentPanel;
    }

}
