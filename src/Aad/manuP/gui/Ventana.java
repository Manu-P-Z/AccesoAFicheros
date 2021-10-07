package Aad.manuP.gui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Ventana {

    //Elementos de la ventana(swing)

    private JPanel panel1;

    public JTextField textFieldMatricula;
    public JTextField textFieldModelo;
    public JTextField textFieldFecha;
    public JTextField textFieldPotencia;
    public JTextField textFieldCombustible;
    public JTextField textFieldBuscar;

    public JButton nuevoButton;
    public JButton guardarButton;

    public JButton modificarButton;

    public JButton eliminarButton;
    public JCheckBox hibridoCheckBox;
    public JButton buscarButton;
    public JTextPane buscarTextPane;

    public JTextField textFieldRuta;
    public JButton buttonRuta;

    public Ventana() {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        textFieldPotencia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
               char c = e.getKeyChar();
               if(!Character.isDigit(c)) {
                   e.consume();
               }
            }
        });
    }
}
