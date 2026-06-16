package boundary;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import controller.ControllerAmministratore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static controller.ControllerAccesso.checkLogin;


public class MainframeAmministratore extends JFrame {
    public JPanel contentPane;
    private JButton visualizzaDettaglioOrdineButton;
    private JButton consultaAndamentoButton;
    private JButton visualizzaOrdiniRicevutiButton;
    private JButton RicercaProdottoButton;
    private JButton creaProdottoButton;
    private JScrollPane CatalogoScrollPane;
    private JPanel CatalogoPane;
    private String emailUtente = "";
    private ControllerAmministratore controllerAmministratore;
    public static final int AMMINISTRATORE = 7;


    public MainframeAmministratore(String emailUtente) {
        this.emailUtente = emailUtente;
        setTitle("Benvenuto amministratore");
        setContentPane(contentPane);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (checkLogin(emailUtente, AMMINISTRATORE) != AMMINISTRATORE) {
            JOptionPane.showMessageDialog(this, "Accesso negato: credenziali non valide o utente non autorizzato.", "Errore di Autenticazione", JOptionPane.ERROR_MESSAGE);
        } else {
            // Setting per avere la finestra a scorrimento (amazon-style)
            CatalogoPane.setLayout(new BoxLayout(CatalogoPane, BoxLayout.Y_AXIS));

            //instanzia un controller se per qualunque motivo esso non dovesse esistere
            if (this.controllerAmministratore == null) {
                this.controllerAmministratore = new ControllerAmministratore(this.emailUtente);
            }

            String messaggioDlc = "Buy the DLC, you broke ass!";  //messaggio che compare quando si cerca di accedere a una funzione non implementata

            visualizzaDettaglioOrdineButton.addActionListener(e -> {
                // Richiede l'input all'utente e lo salva nella variabile id_ordine
                String id_ordine = JOptionPane.showInputDialog(this, "Inserisci l'id dell'ordine:", "0");
                //controllo per la correttezza del formato di id_ordine
                if(id_ordine.length()==10){
                    if(!id_ordine.matches("[0-9]+")){
                        JOptionPane.showMessageDialog(this, "Formato id ordine non valido. Deve essere composto da 10 cifre numeriche.", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Formato id ordine non valido. Lunghezza errata.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Controllo di sicurezza: verifica che l'utente non abbia premuto "Annulla" o inserito una stringa vuota
                if (id_ordine != null && !id_ordine.trim().isEmpty()) {
                    // Istanzia il nuovo frame passando il controller e l'ID appena recuperato
                    FrameDettaglioOrdine frameDettaglio = new FrameDettaglioOrdine(new ControllerAmministratore(emailUtente), id_ordine);
                    //se l'ordine non viene trovato questa variabile sara' impostata a false, evitando la creazione di una finestra vuota
                    if(frameDettaglio.isEnabled()){
                        frameDettaglio.setVisible(true);
                        frameDettaglio.setLocationRelativeTo(null);
                    }
                }
            });


            //le prossime tre funzioni sono tutte non implementate
            consultaAndamentoButton.addActionListener(e ->
                    JOptionPane.showMessageDialog(this, messaggioDlc, "DLC Required", JOptionPane.WARNING_MESSAGE)  //warning message è per far uscire un pannello di warning
            );

            visualizzaOrdiniRicevutiButton.addActionListener(e ->
                    JOptionPane.showMessageDialog(this, messaggioDlc, "DLC Required", JOptionPane.WARNING_MESSAGE)
            );

            RicercaProdottoButton.addActionListener(e -> {
                FrameRicercaProdotti.apri_form_ricerca_admin(controllerAmministratore, risultati -> mostraRisultatiRicerca(risultati));
            });

            creaProdottoButton.addActionListener(e -> { //da qui si apre tutta la logica del pulsante crea prodotto
                FrameCreaProdotto frameCreazione = new FrameCreaProdotto();  //istanzio il frame che ci permette di creare un nuovo prodotto
                frameCreazione.setVisible(true);  //lo rendo visibile
                frameCreazione.setLocationRelativeTo(null);  //grazie a questa opzione centro la finestra nello schermo


                //con questo blocco di codice faccio in modo che,
                // quando viene chiuso il frame crea prodotto (quindi completata la creazione),
                // si ritorna al catalogo che viene aggiornato automaticamente per essere coerente
                // con il cambiamento, tramite la funzione fillCatalogo()
                frameCreazione.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent windowEvent) {
                        fillCatalogo();
                    }
                });
            });

            // Primo riempimento del catalogo
            fillCatalogo();

            setVisible(true);
        }
    }

    //funzione per riempire il catalogo
    private void fillCatalogo() {
        CatalogoPane.removeAll();

        // Riceviamo una lista di array di stringhe dal Controller
        List<String[]> listaCatalogo = ControllerAmministratore.ottieniListaProdotti();  //chiamo la funzione di catalogo che permette di fare retrieval di tutti i prodotti

        if (listaCatalogo.isEmpty()) {
            // Forziamo il BorderLayout per questo ramo
            CatalogoPane.setLayout(new BorderLayout());

            JPanel emptyPanel = new JPanel(new GridBagLayout());
            emptyPanel.setBackground(CatalogoPane.getBackground());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(6, 0, 6, 0);
            gbc.anchor = GridBagConstraints.CENTER;

            JLabel iconLabel = new JLabel("📦");
            iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 56));
            emptyPanel.add(iconLabel, gbc);

            gbc.gridy = 1;
            JLabel titleLabel = new JLabel("Il catalogo è vuoto");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            titleLabel.setForeground(new Color(44, 62, 80));
            emptyPanel.add(titleLabel, gbc);

            gbc.gridy = 2;
            JLabel subtitleLabel = new JLabel("Non ci sono articoli disponibili in questo momento.");
            subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            subtitleLabel.setForeground(new Color(127, 140, 141));
            emptyPanel.add(subtitleLabel, gbc);

            CatalogoPane.add(emptyPanel, BorderLayout.CENTER);
        } else {
            CatalogoPane.setLayout(new BoxLayout(CatalogoPane, BoxLayout.Y_AXIS));
            for (String[] dati : listaCatalogo) {

                JPanel panelProdotto = new JPanel(new BorderLayout(15, 10));
                panelProdotto.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 5, 5),
                        BorderFactory.createEtchedBorder()
                ));
                panelProdotto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                // Estraiamo i dati dall'array usando gli indici stabiliti nella Facade
                String nomeProdotto = dati[0];
                String categoriaProdotto = dati[1];
                String prezzoProdotto = dati[2];
                String descrizioneProdotto = dati[3];
                String qtaProdotto = dati[4];
                boolean isDisponibile = Boolean.parseBoolean(dati[5]);
                boolean isScontato = Boolean.parseBoolean(dati[6]);


                String testoHtml = "<html><h3 style='margin:0; color:#2c3e50;'>" + nomeProdotto + "</h3>" +
                        "<p style='margin:0; font-size:11px; color:#7f8c8d;'>" + descrizioneProdotto + "</p>" +
                        "<b style='color:#27ae60;'>" + prezzoProdotto + " €</b> - Qta: " + qtaProdotto + "</html>";

                JLabel lblInfo = new JLabel(testoHtml);

                JPanel panelButtons = new JPanel(new GridLayout(1, 2, 5, 0));
                JButton btnGestione = new JButton("Gestisci");
                JButton btnRimuovi = new JButton("Rimuovi");
                panelButtons.add(btnGestione);
                panelButtons.add(btnRimuovi);

                btnRimuovi.addActionListener(e -> {
                    int risposta = JOptionPane.showConfirmDialog(this,
                            "Sei sicuro di voler eliminare permanentemente " + nomeProdotto + " dal catalogo?",
                            "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);  //richiedo una conferma di eliminazione

                    if (risposta == JOptionPane.YES_OPTION) {
                        boolean eliminato = ControllerAmministratore.rimuoviProdotto(nomeProdotto); //delego al controller
                        if (eliminato) {
                            JOptionPane.showMessageDialog(this, "Prodotto rimosso con successo.");
                            fillCatalogo();  //se l'eliminazione va a buon fine devo aggiornare il catalogo a schermo
                        } else {
                            JOptionPane.showMessageDialog(this, "Errore nell'eliminazione.", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                btnGestione.addActionListener(e -> {
                    // Passiamo le stringhe estratte direttamente al costruttore della frame che ci permette di gestire il prodotto
                    FrameGestisciProdotto frameGestione = new FrameGestisciProdotto(
                            nomeProdotto, categoriaProdotto, prezzoProdotto, descrizioneProdotto, qtaProdotto, isDisponibile, isScontato
                    );
                    frameGestione.setVisible(true);
                    frameGestione.setLocationRelativeTo(null);

                    frameGestione.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent windowEvent) {
                            fillCatalogo();
                        }  //anche in questo caso, completata la modifica chiamo fillCatalogo()
                    });
                });

                panelProdotto.add(lblInfo, BorderLayout.CENTER);
                panelProdotto.add(panelButtons, BorderLayout.EAST);

                CatalogoPane.add(panelProdotto);
                CatalogoPane.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        CatalogoPane.revalidate();
        CatalogoPane.repaint();
    }

    //metodo per mostrare i risultati della ricerca versione Amministratore
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
            CatalogoPane.setLayout(new BoxLayout(CatalogoPane, BoxLayout.Y_AXIS));
            for (String[] dati : prodottiFiltrati) {
                JPanel panelProdotto = new JPanel(new BorderLayout(15, 10));
                panelProdotto.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 5, 5),
                        BorderFactory.createEtchedBorder()
                ));
                panelProdotto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                // Estraiamo TUTTI i dati necessari dall'array
                String nomeProdotto = dati[0];
                String categoriaProdotto = dati[1];
                String prezzoProdotto = dati[2];
                String descrizioneProdotto = dati[3];
                String qtaProdotto = dati[4];
                boolean isDisponibile = Boolean.parseBoolean(dati[5]);
                boolean isScontato = Boolean.parseBoolean(dati[6]);

                String testoHtml = "<html><h3 style='margin:0; color:#2c3e50;'>" + nomeProdotto + "</h3>" +
                        "<p style='margin:0; font-size:11px; color:#7f8c8d;'>" + descrizioneProdotto + "</p>" +
                        "<b style='color:#27ae60;'>" + prezzoProdotto + " €</b> - Qta: " + qtaProdotto + "</html>";

                JLabel lblInfo = new JLabel(testoHtml);

                // Creiamo i bottoni specifici per l'Amministratore
                JPanel panelButtons = new JPanel(new GridLayout(1, 2, 5, 0));
                JButton btnGestione = new JButton("Gestisci");
                JButton btnRimuovi = new JButton("Rimuovi");
                panelButtons.add(btnGestione);
                panelButtons.add(btnRimuovi);

                // Listener per la rimozione
                btnRimuovi.addActionListener(e -> {
                    int risposta = JOptionPane.showConfirmDialog(this,
                            "Sei sicuro di voler eliminare permanentemente " + nomeProdotto + " dal catalogo?",
                            "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);

                    if (risposta == JOptionPane.YES_OPTION) {
                        boolean eliminato = ControllerAmministratore.rimuoviProdotto(nomeProdotto);
                        if (eliminato) {
                            JOptionPane.showMessageDialog(this, "Prodotto rimosso con successo.");
                            fillCatalogo(); // Ricarichiamo tutto il catalogo per aggiornare la vista
                        } else {
                            JOptionPane.showMessageDialog(this, "Errore nell'eliminazione.", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                // Listener per la gestione
                btnGestione.addActionListener(e -> {
                    FrameGestisciProdotto frameGestione = new FrameGestisciProdotto(
                            nomeProdotto, categoriaProdotto, prezzoProdotto, descrizioneProdotto, qtaProdotto, isDisponibile, isScontato
                    );
                    frameGestione.setVisible(true);
                    frameGestione.setLocationRelativeTo(null);

                    frameGestione.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent windowEvent) {
                            fillCatalogo(); // Ricarichiamo tutto il catalogo al termine della modifica
                        }
                    });
                });

                panelProdotto.add(lblInfo, BorderLayout.CENTER);
                panelProdotto.add(panelButtons, BorderLayout.EAST);

                CatalogoPane.add(panelProdotto);
                CatalogoPane.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        CatalogoPane.revalidate();
        CatalogoPane.repaint();
    }

    public JFrame apriUIAmm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MainframeAmministratore(emailUtente);
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
        RicercaProdottoButton = new JButton();
        RicercaProdottoButton.setText("Cerca Prodotto");
        panel1.add(RicercaProdottoButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

