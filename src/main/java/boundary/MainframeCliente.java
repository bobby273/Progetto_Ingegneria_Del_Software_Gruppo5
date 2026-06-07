package boundary;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.ControllerCliente;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainframeCliente extends JFrame {
    private JPanel contentPanel;
    private JButton VediCarrello;
    private JButton VisualizzaStoricoOrdini;
    private JButton RicercaProdotto;
    private JButton VisualizzaDettaglioOrdine;
    private JScrollPane CatalogoScrollPane;
    private JPanel CatalogoPane;
    private JFrame frameStoricoOrdini;
    private ControllerCliente controllerCliente;

    public MainframeCliente() {
        setTitle("Benvenuto cliente");
        setContentPane(contentPanel);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setting per avere finestra a scorrimento (amazon-style)
        CatalogoPane.setLayout(new BoxLayout(CatalogoPane, BoxLayout.Y_AXIS));

        fillCatalogo();

        CatalogoScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        if (this.controllerCliente == null) {
            this.controllerCliente = new ControllerCliente();
        }

        VediCarrello.addActionListener(e -> {
            new FrameCarrello();
        });

        RicercaProdotto.addActionListener(e -> {

            FrameRicercaProdotti.apri_form_ricerca_cliente(controllerCliente, risultati -> mostraRisultatiRicerca(risultati));

        });
        VisualizzaStoricoOrdini.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (frameStoricoOrdini == null || !frameStoricoOrdini.isDisplayable()) {

                    FrameStoricoOrdini frameStoricoOrdini = new FrameStoricoOrdini(controllerCliente);

                    frameStoricoOrdini.setLocationRelativeTo(null);

                    frameStoricoOrdini.setVisible(true);


                } else {

                    frameStoricoOrdini.toFront();
                    frameStoricoOrdini.requestFocus();

                }
            }
        });

        setVisible(true);
    }


    private void fillCatalogo() {
        CatalogoPane.removeAll();

        //Chiama database con controller (restituisce lista di array di stringhe: [0]=Nome, [1]=Descrizione)
        List<String[]> prodotti = ControllerCliente.getCatalogoBreve();

        if (prodotti == null || prodotti.isEmpty()) {
            JLabel lblVuoto = new JLabel("Nessun prodotto nel catalogo al momento", SwingConstants.CENTER);
            lblVuoto.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            lblVuoto.setForeground(Color.GRAY);
            lblVuoto.setAlignmentX(Component.CENTER_ALIGNMENT);

            CatalogoPane.add(Box.createVerticalGlue());
            CatalogoPane.add(lblVuoto);
            CatalogoPane.add(Box.createVerticalGlue());
        } else {
            for (String[] p : prodotti) {
                JPanel panelProdotto = new JPanel(new BorderLayout(15, 10));
                panelProdotto.setBackground(Color.WHITE);
                //Estetica
                panelProdotto.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220,220,220), 1, true),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)
                ));

                panelProdotto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                //I dati veri e propri spacchettati
                String nomeProdotto = p[0];
                String descrizioneProdotto = p[1];
                String testoHtml = "<html><div style='width: 350px;'>" +
                        "<h3 style='margin:0; color:#2c3e50; font-family: Segoe UI;'>" + nomeProdotto + "</h3>" +
                        "<p style='margin:4px 0 0 0; color:#7f8c8d; font-family: Segoe UI;'>" + descrizioneProdotto + "</p>" +
                        "</div></html>";

                //Scriviamo
                JLabel lblInfo = new JLabel(testoHtml);
                JButton btnInfo = new JButton("Altre info");
                btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnInfo.setBackground(new Color(230, 230, 230)); // Azzurro moderno
                btnInfo.setForeground(new Color(44,62,80));
                btnInfo.setFocusPainted(false); // Toglie il brutto bordino di selezione

                // Button
                btnInfo.addActionListener(e -> {
                    ControllerCliente.apriDettaglioProdotto(nomeProdotto); //Chiamata a Controller
                });

                panelProdotto.add(lblInfo, BorderLayout.CENTER);
                panelProdotto.add(btnInfo, BorderLayout.EAST);

                CatalogoPane.add(panelProdotto);
                CatalogoPane.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        CatalogoPane.revalidate();
        CatalogoPane.repaint();
    }

    //metodo per mostrare i risultati della ricerca
    public void mostraRisultatiRicerca(List<String[]> prodottiFiltrati) {
        CatalogoPane.removeAll();
        if (prodottiFiltrati == null || prodottiFiltrati.isEmpty()) {
            JLabel lblVuoto = new JLabel("Nessun prodotto trovato per i criteri inseriti.", SwingConstants.CENTER);
            lblVuoto.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            lblVuoto.setForeground(Color.GRAY);
            lblVuoto.setAlignmentX(Component.CENTER_ALIGNMENT);
            CatalogoPane.add(Box.createVerticalGlue());
            CatalogoPane.add(lblVuoto);
            CatalogoPane.add(Box.createVerticalGlue());
        } else {
            for (String[] p : prodottiFiltrati) {
                JPanel panelProdotto = new JPanel(new BorderLayout(15, 10));
                panelProdotto.setBackground(Color.WHITE);
                // Estetica
                panelProdotto.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)
                ));
                panelProdotto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
                String nomeProdotto = p[0];
                String descrizioneProdotto = p[1];
                String testoHtml = "<html><div style='width: 350px;'>" +
                        "<h3 style='margin:0; color:#2c3e50; font-family: Segoe UI;'>" + nomeProdotto + "</h3>" +
                        "<p style='margin:4px 0 0 0; color:#7f8c8d; font-family: Segoe UI;'>" + descrizioneProdotto + "</p>" +
                        "</div></html>";
                JLabel lblInfo = new JLabel(testoHtml);
                JButton btnInfo = new JButton("Altre info");
                btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnInfo.setBackground(new Color(230, 230, 230));
                btnInfo.setForeground(new Color(44, 62, 80));
                btnInfo.setFocusPainted(false);
                btnInfo.addActionListener(e -> {
                    ControllerCliente.apriDettaglioProdotto(nomeProdotto);
                });
                panelProdotto.add(lblInfo, BorderLayout.CENTER);
                panelProdotto.add(btnInfo, BorderLayout.EAST);
                CatalogoPane.add(panelProdotto);
                CatalogoPane.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        CatalogoPane.revalidate();
        CatalogoPane.repaint();
    }

    public JFrame apriUICliente(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MainframeCliente();
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
        contentPanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.setBackground(new Color(-657931));
        VediCarrello = new JButton();
        VediCarrello.setText("Visualizzazione carrello");
        contentPanel.add(VediCarrello, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RicercaProdotto = new JButton();
        RicercaProdotto.setText("Ricerca di prodotti");
        contentPanel.add(RicercaProdotto, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        VisualizzaDettaglioOrdine = new JButton();
        VisualizzaDettaglioOrdine.setText("Visualizzazione dettaglio ordine");
        contentPanel.add(VisualizzaDettaglioOrdine, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CatalogoScrollPane = new JScrollPane();
        contentPanel.add(CatalogoScrollPane, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        CatalogoPane = new JPanel();
        CatalogoPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        CatalogoScrollPane.setViewportView(CatalogoPane);
        VisualizzaStoricoOrdini = new JButton();
        VisualizzaStoricoOrdini.setText("Visualizzazione storico ordini");
        contentPanel.add(VisualizzaStoricoOrdini, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}
