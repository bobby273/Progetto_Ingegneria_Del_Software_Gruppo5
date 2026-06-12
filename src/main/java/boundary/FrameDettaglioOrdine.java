package boundary;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import controller.ControllerAmministratore;
import controller.ControllerCliente;

import entity.Stato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

    //frame che si apre lato cliente quando si preme su altre info

public class FrameDettaglioOrdine extends JFrame {
    //Variabili per il GUI Designer
    private JPanel PanelOrdine;
    private JLabel lblId;
    private JLabel lblTotale;
    private JLabel lblOggettiAcquistati;
    private JLabel lblDataConferma;
    private JLabel lblIndirizzoSpedizione;
    private JLabel lblStato;
    private JButton btnChiudi;
    private JButton btnAnnullaOrdine;

    //Variabili per la logica BCED
    private ControllerCliente controllerCliente;
    private ControllerAmministratore controllerAmministratore; //attualmente questo frame non è eseguibile da amministratore in quanto
    //manca un appropriato metodo che mostra lo storico globale con solo gli ordini effettuati, ma lo implementiamo comunque
    //in modo che in futuro esso sia facilmente instanziabile

    private boolean isAmministratore; //sarà il nostro modo per riconoscere
    // se questa frame è stata aperta da FrameCliente o da FrameAmministratore
    private String id_ordineSelezionato;
    private String stato;
    private float totale;
    private LocalDateTime dataConferma;
    private String indirizzoSpedizione;
    private ArrayList<String> ProdottiEQuantita;
    private Long id_cliente;
    private FrameStoricoOrdiniPersonali frameStoricoOrdiniPersonali;

    //costruttore della boundary
    public FrameDettaglioOrdine(ControllerCliente controller, String id_ordine, FrameStoricoOrdiniPersonali frameStoricoOrdiniPersonali) {
        this.controllerCliente = controller;
        this.isAmministratore = false;
        this.id_ordineSelezionato = id_ordine;
        this.stato = controllerCliente.getStato(id_ordine);
        this.totale = controllerCliente.getTotale(id_ordine);
        this.dataConferma = controllerCliente.getDataConferma(id_ordine);
        this.indirizzoSpedizione = controllerCliente.getIndirizzoSpedizione(id_ordine);
        this.ProdottiEQuantita = controllerCliente.getProdottiEQuantita(id_ordine);
        this.id_cliente = controllerCliente.getIdCliente(id_ordine);
        this.frameStoricoOrdiniPersonali = frameStoricoOrdiniPersonali;

        Object[] infoOrdine = controller.getInfoOrdine(id_ordine);
        popolaDatiDaArray(infoOrdine);
        apriFrame();
    }

    public FrameDettaglioOrdine(ControllerAmministratore controller, String id_ordine) {
        this.controllerAmministratore = controller;
        this.isAmministratore = true;
        this.id_ordineSelezionato = id_ordine;
        this.stato = controller.getStato(id_ordine);
        this.totale = controller.getTotale(id_ordine);
        this.dataConferma = controller.getDataConferma(id_ordine);
        this.indirizzoSpedizione = controller.getIndirizzoSpedizione(id_ordine);
        this.ProdottiEQuantita = controller.getProdottiEQuantita(id_ordine);
        this.id_cliente = controller.getIdCliente(id_ordine);
        apriFrame();
    }

    private void apriFrame() {
        setTitle("Ecco l'ordine selezionato:");
        setContentPane(PanelOrdine);
        setSize(700, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblId.setText(id_ordineSelezionato != null ? id_ordineSelezionato : "N/D");
        lblStato.setText(stato != null ? stato : "Sconosciuto");
        lblTotale.setText(String.format("%.2f €", totale));
        lblDataConferma.setText(dataConferma != null ? dataConferma.toString() : "N/D");
        lblIndirizzoSpedizione.setText(indirizzoSpedizione != null ? indirizzoSpedizione : "N/D");

        if (ProdottiEQuantita != null && !ProdottiEQuantita.isEmpty()) {
            StringBuilder sb = new StringBuilder("<html>");

            // Scorriamo l'array a passi di 2 per accoppiare Nome e Quantità
            for (int i = 0; i < ProdottiEQuantita.size(); i += 2) {
                String nomeProdotto = ProdottiEQuantita.get(i);
                // Prende la quantità (che sta nella posizione successiva)
                String quantita = (i + 1 < ProdottiEQuantita.size()) ? ProdottiEQuantita.get(i + 1) : "?";
                // Crea la stringa formattata: "<li>NomeProdotto (Quantità: X)</li>"
                sb.append("• ").append(nomeProdotto).append(" (Quantità: ").append(quantita).append(")<br>");            }
            sb.append("</html>");
            lblOggettiAcquistati.setText(sb.toString());
        } else {
            lblOggettiAcquistati.setText("Nessun dettaglio prodotti");
        }



        // Blocca il tasto se l'ordine non è più annullabile
        if (stato != null && (stato.equals("ANNULLATO") || stato.equals("CONSEGNATO") || stato.equals("SPEDITO"))) {
            btnAnnullaOrdine.setEnabled(false);
        }

        // --- ATTIVAZIONE DEI BOTTONI ---
        addListenerBottoni();

    }

    private void popolaDatiDaArray(Object[] info) {
        if (info != null) {
            // L'indice 0 è l'ID, ma lo abbiamo già assegnato sopra
            this.totale = (float) info[1];
            this.stato = (String) info[2];
            this.dataConferma = (LocalDateTime) info[3];
            this.indirizzoSpedizione = (String) info[4];
            this.ProdottiEQuantita = (ArrayList<String>) info[5];
            this.id_cliente = (Long) info[6];
        }
    }

    private void addListenerBottoni() {
        btnChiudi.addActionListener(e -> dispose());

        btnAnnullaOrdine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int conferma = JOptionPane.showConfirmDialog(
                        PanelOrdine,
                        "Vuoi annullare l'ordine?",
                        "Conferma",
                        JOptionPane.YES_NO_OPTION);

                if (conferma == JOptionPane.YES_OPTION) {
                    boolean successo;

                    // CONTROLLO DEL RUOLO: Scegliamo a quale controller chiedere l'annullamento
                    if (isAmministratore) {
                        successo = controllerAmministratore.annullaOrdine(id_ordineSelezionato);
                    } else {
                        successo = controllerCliente.annullaOrdine(id_ordineSelezionato);
                    }

                    if (successo) {
                        lblStato.setText(Stato.ANNULLATO.toString());
                        btnAnnullaOrdine.setEnabled(false);
                        JOptionPane.showMessageDialog(PanelOrdine, "Ordine annullato. Avvenuto rimborso:");
                        dispose();

                        //aggiungo un controllo di sicurezza per aggiornare la tabella dello storico ordini personali nel caso essa sia vuota.
                        if(frameStoricoOrdiniPersonali != null) {
                            frameStoricoOrdiniPersonali.aggiornaTabella();
                        }
                    } else { //scriviamo questa casistica per puri motivi di sicurezza
                        //in teoria non è mai accessibile dalla GUI per codice in riga 118-120.
                        JOptionPane.showMessageDialog(PanelOrdine, "Errore: hai già annullato questo ordine!", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
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
        PanelOrdine = new JPanel();
        PanelOrdine.setLayout(new GridLayoutManager(7, 2, new Insets(2, 2, 2, 2), -1, -1));
        lblDataConferma = new JLabel();
        lblDataConferma.setText("Label");
        PanelOrdine.add(lblDataConferma, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblIndirizzoSpedizione = new JLabel();
        lblIndirizzoSpedizione.setText("Label");
        PanelOrdine.add(lblIndirizzoSpedizione, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblId = new JLabel();
        lblId.setText("Label");
        PanelOrdine.add(lblId, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTotale = new JLabel();
        lblTotale.setText("Label");
        PanelOrdine.add(lblTotale, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Totale (€):");
        PanelOrdine.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Data Conferma Ordine:");
        PanelOrdine.add(label2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Indirizzo Spedizione");
        PanelOrdine.add(label3, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("ID Ordine:");
        PanelOrdine.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Oggetti Acquistati:");
        PanelOrdine.add(label5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblOggettiAcquistati = new JLabel();
        lblOggettiAcquistati.setText("Label");
        PanelOrdine.add(lblOggettiAcquistati, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Stato Ordine");
        PanelOrdine.add(label6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblStato = new JLabel();
        lblStato.setText("Label");
        PanelOrdine.add(lblStato, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnChiudi = new JButton();
        btnChiudi.setText("Chiudi");
        PanelOrdine.add(btnChiudi, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAnnullaOrdine = new JButton();
        btnAnnullaOrdine.setText("Annulla Ordine");
        PanelOrdine.add(btnAnnullaOrdine, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return PanelOrdine;
    }

}
