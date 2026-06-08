package boundary;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import controller.ControllerAmministratore;

import javax.swing.*;
import java.awt.*;

public class FrameGestisciProdotto extends JFrame {

    public JPanel contentPane;
    private JTextField textNome;
    private JTextField textCategoria;
    private JTextField textPrezzo;
    private JButton modificaNomeButton;
    private JButton modificaCategoriaButton;
    private JButton modificaPrezzoButton;
    private JTextArea areaDescrizione;
    private JButton modificaDescrizioneButton;
    private JButton modificaQtaDisponibileButton;
    private JRadioButton SIRadioButtonDisponibile;
    private JRadioButton NORadioButtonDisponibile;
    private JButton modificaIsDisponibileButton;
    private JRadioButton SIRadioButtonScontato;
    private JRadioButton NORadioButtonScontato;
    private JButton ESCIButton;
    private JButton modificaIsScontatoButton;
    private JLabel nomeLabel;
    private JLabel categoriaLabel;
    private JLabel prezzoLabel;
    private JLabel descrizioneLabel;
    private JLabel qtaDisponibileLabel;
    private JLabel isDisponibileLabel;
    private JLabel isScontatoLabel;
    private JTextField textQtaDisponibile;

    private String nomeProdottoCorrente;

    public FrameGestisciProdotto(String nome, String categoria, String prezzo, String descrizione, String qta, boolean disponibile, boolean scontato) {
        $$$setupUI$$$();
        setContentPane(contentPane);
        setTitle("Gestione Prodotto: " + nome);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);


        this.nomeProdottoCorrente = nome;
        // ==========================================
// 1. POPOLAMENTO INIZIALE DEI CAMPI (Dati dallo Stub)
// ==========================================
        // ... qui sotto c'è il codice della tua griglia che inizializza i componenti ...

        // Assegnazione dei testi ricevuti
        textNome.setText(nome);
        textCategoria.setText(categoria);
        textPrezzo.setText(prezzo);
        areaDescrizione.setText(descrizione);
        textQtaDisponibile.setText(qta);

        // Configurazione RadioButton
        ButtonGroup gruppoDisp = new ButtonGroup();
        gruppoDisp.add(SIRadioButtonDisponibile);
        gruppoDisp.add(NORadioButtonDisponibile);
        if (disponibile) SIRadioButtonDisponibile.setSelected(true);
        else NORadioButtonDisponibile.setSelected(true);

        ButtonGroup gruppoScon = new ButtonGroup();
        gruppoScon.add(SIRadioButtonScontato);
        gruppoScon.add(NORadioButtonScontato);
        if (scontato) SIRadioButtonScontato.setSelected(true);
        else NORadioButtonScontato.setSelected(true);

        // Rendo i campi non editabili direttamente da tastiera per forzare l'uso dei bottoni Modifica
        textNome.setEditable(false);
        textCategoria.setEditable(false);
        textPrezzo.setEditable(false);
        areaDescrizione.setEditable(false);
        textQtaDisponibile.setEditable(false);
        areaDescrizione.setLineWrap(true);
        areaDescrizione.setWrapStyleWord(true);

        // ==========================================
        // 2. LOGICA DEI BOTTONI "MODIFICA"
        // ==========================================

        // MODIFICA NOME
        modificaNomeButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Inserisci il nuovo Nome:", textNome.getText());
            if (input != null) {
                String nuovoNome = input.trim();

                // Controllo Boundary Locale
                if (nuovoNome.isEmpty() || nuovoNome.length() > 1000) {
                    JOptionPane.showMessageDialog(this, "Errore: Il nome non può essere vuoto o superare i 1000 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Chiamata al controller
                boolean successo = ControllerAmministratore.modificaNomeProdotto(this.nomeProdottoCorrente, nuovoNome);

                if (successo) {
                    textNome.setText(nuovoNome);
                    this.nomeProdottoCorrente = nuovoNome; // Aggiorna il puntatore logico della finestra per i prossimi click!
                    setTitle("Gestione Prodotto: " + this.nomeProdottoCorrente);
                    JOptionPane.showMessageDialog(this, "Nome modificato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Errore: Il prodotto non esiste o il nuovo nome è già utilizzato.", "Errore Modifica", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // MODIFICA CATEGORIA (Con controllo alfabetico stringente)
        // MODIFICA CATEGORIA (Pulito, chiama direttamente aggiornaProdotto)
        modificaCategoriaButton.addActionListener(e -> {
            // 1. Chiediamo prima l'input all'utente
            String input = JOptionPane.showInputDialog(this, "Inserisci la nuova Categoria:", textCategoria.getText());

            if (input != null) {
                String nuovaCat = input.trim();

                // 2. CONTROLLO BOUNDARY LOCALE (Lunghezza e Regex alfabetica)
                if (nuovaCat.isEmpty() || nuovaCat.length() > 100) {
                    JOptionPane.showMessageDialog(this, "Errore: La categoria non può essere vuota o superare i 100 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!nuovaCat.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
                    JOptionPane.showMessageDialog(this, "Errore: La categoria deve contenere solo lettere. Numeri e simboli non consentiti.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 3. CHIAMATA DIRETTA AL METODO AGGIORNA PRODOTTO
                // Passiamo lo stato attuale di tutta la GUI e la nuova categoria
                boolean successo = ControllerAmministratore.modificaCategoriaProdotto(this.nomeProdottoCorrente, nuovaCat);

                // 4. GESTIONE DELLA RISPOSTA (Dal Sequence Diagram)
                if (successo) {
                    textCategoria.setText(nuovaCat); // Aggiorna la grafica solo se il DB dà l'OK
                    JOptionPane.showMessageDialog(this, "Categoria modificata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Se il controller risponde false (perché il prodotto non esiste più nel DB)
                    JOptionPane.showMessageDialog(this, "Errore: Impossibile completare la modifica. Il prodotto potrebbe essere stato rimosso.", "Errore Modifica", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // MODIFICA PREZZO
        modificaPrezzoButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Inserisci il nuovo Prezzo:", textPrezzo.getText());
            if (input != null) {
                String nuovoPrezzoStr = input.trim().replace(",", ".");
                try {
                    float p = Float.parseFloat(nuovoPrezzoStr);
                    if (p < 0) throw new NumberFormatException();

                    boolean successo = ControllerAmministratore.modificaPrezzoProdotto(this.nomeProdottoCorrente, p);

                    if (successo) {
                        textPrezzo.setText(nuovoPrezzoStr);
                        JOptionPane.showMessageDialog(this, "Prezzo modificato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Errore: Impossibile completare la modifica.", "Errore Modifica", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Errore: Inserire un numero decimale valido (>= 0).", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // MODIFICA DESCRIZIONE
        modificaDescrizioneButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Inserisci la nuova Descrizione:", areaDescrizione.getText());
            if (input != null) {
                String nuovaDesc = input.trim();

                if (nuovaDesc.length() > 1000) {
                    JOptionPane.showMessageDialog(this, "Errore: La descrizione non può superare i 1000 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean successo = ControllerAmministratore.modificaDescrizioneProdotto(this.nomeProdottoCorrente, nuovaDesc);

                if (successo) {
                    areaDescrizione.setText(nuovaDesc);
                    JOptionPane.showMessageDialog(this, "Descrizione modificata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Errore: Impossibile completare la modifica.", "Errore Modifica", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // MODIFICA QUANTITÀ DISPONIBILE
        modificaQtaDisponibileButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Inserisci la nuova Quantità:", textQtaDisponibile.getText());
            if (input != null) {
                try {
                    int nuovaQta = Integer.parseInt(input.trim());
                    if (nuovaQta < 0) throw new NumberFormatException();

                    boolean successo = ControllerAmministratore.modificaQuantitaProdotto(this.nomeProdottoCorrente, nuovaQta);

                    if (successo) {
                        textQtaDisponibile.setText(String.valueOf(nuovaQta));
                        JOptionPane.showMessageDialog(this, "Quantità modificata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Errore: Impossibile completare la modifica.", "Errore Modifica", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Errore: Inserire un numero intero valido (>= 0).", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // MODIFICA IS DISPONIBILE (Dialogo a scelta multipla)
        modificaIsDisponibileButton.addActionListener(e -> {
            String[] opzioni = {"SI", "NO"};
            int scelta = JOptionPane.showOptionDialog(this, "Il prodotto è disponibile?", "Modifica Disponibilità",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, opzioni[0]);

            if (scelta != JOptionPane.CLOSED_OPTION) { // Controlla che non abbia chiuso il pop-up con la X
                boolean nuovaDisp = (scelta == 0); // Se sceglie "SI" (indice 0) è true, altrimenti false

                boolean successo = ControllerAmministratore.modificaDisponibilitaProdotto(this.nomeProdottoCorrente, nuovaDisp);


                if (successo) {
                    if (nuovaDisp) SIRadioButtonDisponibile.setSelected(true);
                    else NORadioButtonDisponibile.setSelected(true);
                    JOptionPane.showMessageDialog(this, "Disponibilità modificata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Errore: Impossibile completare la modifica.", "Errore Modifica", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // MODIFICA IS SCONTATO
        modificaIsScontatoButton.addActionListener(e -> {
            String[] opzioni = {"SI", "NO"};
            int scelta = JOptionPane.showOptionDialog(this, "Il prodotto è scontato?", "Modifica Sconto",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, opzioni[0]);

            if (scelta != JOptionPane.CLOSED_OPTION) {
                boolean nuovoSconto = (scelta == 0);

                boolean successo = ControllerAmministratore.modificaScontoProdotto(this.nomeProdottoCorrente, nuovoSconto);

                if (successo) {
                    if (nuovoSconto) SIRadioButtonScontato.setSelected(true);
                    else NORadioButtonScontato.setSelected(true);
                    JOptionPane.showMessageDialog(this, "Stato sconto modificato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Errore: Impossibile completare la modifica.", "Errore Modifica", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ==========================================
        // 3. LOGICA TASTO ESCI
        // ==========================================
        ESCIButton.addActionListener(e -> {
            // Chiude la finestra corrente rilasciando le risorse, lasciando aperto il MainFrame
            dispose();
        });
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
        contentPane.setLayout(new GridLayoutManager(8, 4, new Insets(0, 0, 0, 0), -1, -1));
        nomeLabel = new JLabel();
        nomeLabel.setText("Nome");
        contentPane.add(nomeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textNome = new JTextField();
        contentPane.add(textNome, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        categoriaLabel = new JLabel();
        categoriaLabel.setText("Categoria");
        contentPane.add(categoriaLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textCategoria = new JTextField();
        contentPane.add(textCategoria, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textPrezzo = new JTextField();
        contentPane.add(textPrezzo, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        prezzoLabel = new JLabel();
        prezzoLabel.setText("Prezzo");
        contentPane.add(prezzoLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificaNomeButton = new JButton();
        modificaNomeButton.setText("Modifica");
        contentPane.add(modificaNomeButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificaCategoriaButton = new JButton();
        modificaCategoriaButton.setText("Modifica");
        contentPane.add(modificaCategoriaButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificaPrezzoButton = new JButton();
        modificaPrezzoButton.setText("Modifica");
        contentPane.add(modificaPrezzoButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descrizioneLabel = new JLabel();
        descrizioneLabel.setText("Descrizione");
        contentPane.add(descrizioneLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        areaDescrizione = new JTextArea();
        contentPane.add(areaDescrizione, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        modificaDescrizioneButton = new JButton();
        modificaDescrizioneButton.setText("Modifica");
        contentPane.add(modificaDescrizioneButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        qtaDisponibileLabel = new JLabel();
        qtaDisponibileLabel.setText("qtaDisponibile");
        contentPane.add(qtaDisponibileLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificaQtaDisponibileButton = new JButton();
        modificaQtaDisponibileButton.setText("Modifica");
        contentPane.add(modificaQtaDisponibileButton, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isDisponibileLabel = new JLabel();
        isDisponibileLabel.setText("isDisponibile");
        contentPane.add(isDisponibileLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SIRadioButtonDisponibile = new JRadioButton();
        SIRadioButtonDisponibile.setText("SI");
        contentPane.add(SIRadioButtonDisponibile, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NORadioButtonDisponibile = new JRadioButton();
        NORadioButtonDisponibile.setText("NO");
        contentPane.add(NORadioButtonDisponibile, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificaIsDisponibileButton = new JButton();
        modificaIsDisponibileButton.setText("Modifica");
        contentPane.add(modificaIsDisponibileButton, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isScontatoLabel = new JLabel();
        isScontatoLabel.setText("isScontato");
        contentPane.add(isScontatoLabel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SIRadioButtonScontato = new JRadioButton();
        SIRadioButtonScontato.setText("SI");
        contentPane.add(SIRadioButtonScontato, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NORadioButtonScontato = new JRadioButton();
        NORadioButtonScontato.setText("NO");
        contentPane.add(NORadioButtonScontato, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(7, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ESCIButton = new JButton();
        ESCIButton.setText("ESCI");
        panel1.add(ESCIButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificaIsScontatoButton = new JButton();
        modificaIsScontatoButton.setText("Modifica");
        contentPane.add(modificaIsScontatoButton, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textQtaDisponibile = new JTextField();
        contentPane.add(textQtaDisponibile, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
