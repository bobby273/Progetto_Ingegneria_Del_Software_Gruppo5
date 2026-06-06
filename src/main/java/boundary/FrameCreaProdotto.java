package boundary;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class FrameCreaProdotto extends JFrame {
    public JPanel contentPane;
    private JTextField textNome;
    private JLabel nomeLabel;
    private JTextField textCategoria;
    private JLabel categoriaLabel;
    private JTextField textPrezzo;
    private JLabel prezzoLabel;
    private JTextArea areaDescrizione;
    private JLabel descrizioneLabel;
    private JTextField textQtaDisponibile;
    private JLabel qtaDisponibileLabel;
    private JLabel isDisponibileLabel;
    private JLabel isScontatoLabel;
    private JButton confermaButton;
    private JRadioButton SIRadioButtonDisponibile;
    private JRadioButton NORadioButtonDisponibile;
    private JRadioButton SIRadioButtonScontato;
    private JRadioButton NORadioButtonScontato;

    public FrameCreaProdotto() {
        setTitle("Creazione Prodotto");
        setContentPane(contentPane);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        // --- 1. GRUPPO PER IS DISPONIBILE ---
        ButtonGroup groupDisponibile = new ButtonGroup();
// Assumi che questi siano i nomi delle tue variabili per i tasti SI/NO di isDisponibile
        groupDisponibile.add(SIRadioButtonDisponibile);
        groupDisponibile.add(NORadioButtonDisponibile);

// --- 2. GRUPPO PER IS SCONTATO ---
        ButtonGroup groupScontato = new ButtonGroup();
// Assumi che questi siano i nomi delle tue variabili per i tasti SI/NO di isScontato
        groupScontato.add(SIRadioButtonScontato);
        groupScontato.add(NORadioButtonScontato);

        confermaButton.addActionListener(e -> {
            // 1. Recuperiamo i testi inseriti eliminando spazi vuoti all'inizio e alla fine (.trim())
            String nome = textNome.getText().trim();
            String categoria = textCategoria.getText().trim();
            String prezzoStr = textPrezzo.getText().trim();
            String descrizione = areaDescrizione.getText().trim();
            String qtaStr = textQtaDisponibile.getText().trim();

            // ==========================================
            // VALIDAZIONE: NOME
            // ==========================================
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Errore: Il campo Nome non può essere vuoto.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return; // Blocca l'esecuzione
            }
            if (nome.length() > 1000) {
                JOptionPane.showMessageDialog(this, "Errore: Il Nome non può superare i 1000 caratteri.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ==========================================
            // VALIDAZIONE: CATEGORIA
            // ==========================================
            if (categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Errore: Il campo Categoria non può essere vuoto.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (categoria.length() > 100) {
                JOptionPane.showMessageDialog(this, "Errore: La Categoria non può superare i 100 caratteri.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!categoria.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
                JOptionPane.showMessageDialog(this, "Errore: La Categoria deve contenere solo lettere. Numeri e simboli non sono consentiti.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ==========================================
            // VALIDAZIONE: DESCRIZIONE
            // ==========================================
            // Può essere vuota, quindi controlliamo la lunghezza solo se l'utente ha scritto qualcosa
            if (descrizione.length() > 1000) {
                JOptionPane.showMessageDialog(this, "Errore: La Descrizione non può superare i 1000 caratteri.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Se è vuota, la trasformiamo in null per fare un lavoro pulito sul DB
            if (descrizione.isEmpty()) {
                descrizione = null;
            }

            // ==========================================
            // VALIDAZIONE: PREZZO
            // ==========================================
            double prezzoParsed = 0.0;
            try {
                // Sostituiamo l'eventuale virgola con il punto per evitare crash di formattazione
                prezzoStr = prezzoStr.replace(",", ".");
                prezzoParsed = Double.parseDouble(prezzoStr);
            } catch (NumberFormatException ex) {
                // Se l'utente scrive lettere o simboli strani, parseDouble si schianta ed entra qui
                JOptionPane.showMessageDialog(this, "Errore: Il Prezzo deve essere un numero decimale valido (es. 19.99).", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (prezzoParsed < 0) {
                JOptionPane.showMessageDialog(this, "Errore: Il Prezzo non può essere un valore negativo.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int qtaParsed = 0;
            try {
                qtaParsed = Integer.parseInt(qtaStr);
            } catch (NumberFormatException ex) {
                // Entra qui se l'utente scrive lettere, simboli o numeri con la virgola
                JOptionPane.showMessageDialog(this, "Errore: La Quantità Disponibile deve essere un numero intero valido (es. 10).", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (qtaParsed < 0) {
                JOptionPane.showMessageDialog(this, "Errore: La Quantità Disponibile non può essere un valore negativo.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!SIRadioButtonDisponibile.isSelected() && !NORadioButtonDisponibile.isSelected()) {
                JOptionPane.showMessageDialog(this, "Errore: Devi specificare se il prodotto è disponibile (Seleziona SI o NO).", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ==========================================
            // VALIDAZIONE: SELEZIONE OBBLIGATORIA SCONTO
            // ==========================================
            if (!SIRadioButtonScontato.isSelected() && !NORadioButtonScontato.isSelected()) {
                JOptionPane.showMessageDialog(this, "Errore: Devi specificare se il prodotto è scontato (Seleziona SI o NO).", "Errore Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ==========================================
            // SE IL CODICE ARRIVA QUI, TUTTI I DATI SONO CORRETTI!
            // ==========================================

            // Recuperiamo i booleani dai tuoi JRadioButton
            boolean disponibile = SIRadioButtonDisponibile.isSelected();
            boolean scontato = SIRadioButtonScontato.isSelected();

            // TODO: Qui inserirai la logica JPA per salvare il prodotto
            JOptionPane.showMessageDialog(this, "Prodotto validato con successo! Pronto per il salvataggio.", "Successo", JOptionPane.INFORMATION_MESSAGE);

            // Chiude la finestra corrente tornando al pannello amministratore
            this.dispose();
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new FrameCreaProdotto();
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
        contentPane.setLayout(new GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        textNome = new JTextField();
        contentPane.add(textNome, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nomeLabel = new JLabel();
        nomeLabel.setText("Nome");
        contentPane.add(nomeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textCategoria = new JTextField();
        contentPane.add(textCategoria, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        categoriaLabel = new JLabel();
        categoriaLabel.setText("Categoria");
        contentPane.add(categoriaLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textPrezzo = new JTextField();
        contentPane.add(textPrezzo, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        prezzoLabel = new JLabel();
        prezzoLabel.setText("Prezzo");
        contentPane.add(prezzoLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        areaDescrizione = new JTextArea();
        areaDescrizione.setText("");
        contentPane.add(areaDescrizione, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        descrizioneLabel = new JLabel();
        descrizioneLabel.setText("Descrizione");
        contentPane.add(descrizioneLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textQtaDisponibile = new JTextField();
        contentPane.add(textQtaDisponibile, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        qtaDisponibileLabel = new JLabel();
        qtaDisponibileLabel.setText("qtaDisponibile");
        contentPane.add(qtaDisponibileLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isDisponibileLabel = new JLabel();
        isDisponibileLabel.setText("isDisponibile?");
        contentPane.add(isDisponibileLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isScontatoLabel = new JLabel();
        isScontatoLabel.setText("isScontato?");
        contentPane.add(isScontatoLabel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(7, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        confermaButton = new JButton();
        confermaButton.setText("Conferma");
        panel1.add(confermaButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SIRadioButtonDisponibile = new JRadioButton();
        SIRadioButtonDisponibile.setText("SI");
        contentPane.add(SIRadioButtonDisponibile, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NORadioButtonDisponibile = new JRadioButton();
        NORadioButtonDisponibile.setText("NO");
        contentPane.add(NORadioButtonDisponibile, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SIRadioButtonScontato = new JRadioButton();
        SIRadioButtonScontato.setText("SI");
        contentPane.add(SIRadioButtonScontato, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NORadioButtonScontato = new JRadioButton();
        NORadioButtonScontato.setText("NO");
        contentPane.add(NORadioButtonScontato, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
