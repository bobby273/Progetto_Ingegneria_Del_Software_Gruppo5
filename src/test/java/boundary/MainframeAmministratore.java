package boundary;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class MainframeAmministratore extends JFrame {
    public JPanel contentPane;
    private JButton visualizzaDettaglioOrdineButton;
    private JButton consultaAndamentoButton;
    private JButton visualizzaOrdiniRicevutiButton;
    private JButton cercaProdottoButton;
    private JButton creaProdottoButton;
    private JScrollPane CatalogoScrollPane;
    private JPanel CatalogoPane;

    public MainframeAmministratore() {
        setTitle("Benvenuto amministratore");
        setContentPane(contentPane);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setting per avere finestra a scorrimento (amazon-style)
        CatalogoPane.setLayout(new BoxLayout(CatalogoPane, BoxLayout.Y_AXIS));

        fillCatalogo();

        setVisible(true);
    }

    private void fillCatalogo() {
        CatalogoPane.removeAll();

        //TODO: Sto simulando stub, qua ci andrà la lista vera con il vero fill
        //visualizzazione dei prodotti
        for (int i = 0; i < 20; i++) {
            JPanel panelProdotto = new JPanel(new BorderLayout(15, 10));
            panelProdotto.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(5, 5, 5, 5),
                    BorderFactory.createEtchedBorder()
            ));

            panelProdotto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            String nomeProdotto = "Prodotto <" + i + ">";
            String descrizioneProdotto = "Descrizione prodotto <" + i + ">";
            String testoHtml = "<html><h3 style='margin:0;'>" + nomeProdotto + "</h3>" +
                    "<p style='margin:0;'>" + descrizioneProdotto + "</p></html>";

            JLabel lblInfo = new JLabel(testoHtml);
            JPanel panelButtons = new JPanel(new GridLayout(1,2, 5, 0));
            JButton btnInfo = new JButton("Altre info");
            JButton btnModifica = new JButton("Modifica");
            panelButtons.add(btnInfo);
            panelButtons.add(btnModifica);


            final int idProdotto = i;
            btnInfo.addActionListener(e -> JOptionPane.showMessageDialog(this, "Apertura dettagli prodotto " + idProdotto));
            btnModifica.addActionListener(e -> JOptionPane.showMessageDialog(this, "Modifica prodotto " + idProdotto));

            panelProdotto.add(lblInfo, BorderLayout.CENTER);
            panelProdotto.add(panelButtons, BorderLayout.EAST);

            CatalogoPane.add(panelProdotto);
            CatalogoPane.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        CatalogoPane.revalidate();
        CatalogoPane.repaint();
    }

    /* IL VERO FILL CATALOGO
    import javax.persistence.EntityManager;
import java.util.List;

private void fillCatalogo() {
    CatalogoPane.removeAll();

    // 1. Apriamo l'EntityManager tramite il tuo JpaUtil
    EntityManager em = JpaUtil.getEntityManager();

    try {
        // 2. Facciamo la query (nota: "Prodotto" è il nome della classe Java, non della tabella!)
        List<Prodotto> listaProdotti = em.createQuery("SELECT p FROM Prodotto p", Prodotto.class)
                                         .getResultList();

        // 3. Cicliamo sugli oggetti invece che sul ResultSet
        for (Prodotto p : listaProdotti) {

            // Creazione riga (identica a prima, ma usiamo p.getNome() invece di rs.getString("nome"))
            JPanel panelProdotto = new JPanel(new BorderLayout(15, 10));
            panelProdotto.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(5, 5, 5, 5),
                    BorderFactory.createEtchedBorder()
            ));
            panelProdotto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            JLabel lblNome = new JLabel("<html><h3 style='margin:0;'>" + p.getNome() + "</h3></html>");
            JButton btnModifica = new JButton("Modifica");

            btnModifica.addActionListener(e -> {
                System.out.println("Modifico il prodotto: " + p.getNome());
                // Qui ora puoi passare l'intero oggetto Prodotto p alla finestra di modifica!
            });

            panelProdotto.add(lblNome, BorderLayout.CENTER);
            panelProdotto.add(btnModifica, BorderLayout.EAST);

            CatalogoPane.add(panelProdotto);
            CatalogoPane.add(Box.createRigidArea(new Dimension(0, 5)));
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Errore JPA: " + e.getMessage());
    } finally {
        em.close(); // Fondamentale: chiudiamo sempre l'EntityManager
    }

    CatalogoPane.revalidate();
    CatalogoPane.repaint();
}
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainframeAmministratore();
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
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        visualizzaDettaglioOrdineButton = new JButton();
        visualizzaDettaglioOrdineButton.setText("Visualizza Dettaglio Ordine");
        contentPane.add(visualizzaDettaglioOrdineButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        consultaAndamentoButton = new JButton();
        consultaAndamentoButton.setText("Consulta Andamento");
        contentPane.add(consultaAndamentoButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        visualizzaOrdiniRicevutiButton = new JButton();
        visualizzaOrdiniRicevutiButton.setText("Visualizza Ordini Ricevuti");
        contentPane.add(visualizzaOrdiniRicevutiButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cercaProdottoButton = new JButton();
        cercaProdottoButton.setText("Cerca Prodotto");
        panel1.add(cercaProdottoButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        creaProdottoButton = new JButton();
        creaProdottoButton.setText("Crea Prodotto");
        panel1.add(creaProdottoButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CatalogoScrollPane = new JScrollPane();
        contentPane.add(CatalogoScrollPane, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        CatalogoPane = new JPanel();
        CatalogoPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        CatalogoScrollPane.setViewportView(CatalogoPane);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}

