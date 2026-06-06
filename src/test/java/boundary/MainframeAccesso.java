package boundary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainframeAccesso {
    private JPanel MainPanel;
    private JLabel Welcome;
    private JButton RegistrazioneButton;
    private JButton AccessoButton;

    private JFrame FrameRegistrazione;
    private JFrame FrameAccesso;

    public MainframeAccesso() {
        RegistrazioneButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(FrameRegistrazione == null || !FrameRegistrazione.isDisplayable()){
                   FrameRegistrazione formreg=new FrameRegistrazione();
                   FrameRegistrazione=formreg.apriFormReg();
                   FrameRegistrazione.setLocationRelativeTo(null);
                   FrameRegistrazione.setVisible(true);
               } else {
                   FrameRegistrazione.toFront();
                   FrameRegistrazione.requestFocus();
               }
           }
        });

        AccessoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(FrameAccesso == null || !FrameAccesso.isDisplayable()) {
                    FrameAccesso formacc = new FrameAccesso();
                    FrameAccesso = formacc.apriFormAcc();
                    FrameAccesso.setLocationRelativeTo(null);
                    FrameAccesso.setVisible(true);
                } else {
                    FrameAccesso.toFront();
                    FrameAccesso.requestFocus();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Homepage");
        MainframeAccesso mainframe= new MainframeAccesso();
        frame.setContentPane(mainframe.MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
