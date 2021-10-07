package Aad.manuP;

import Aad.manuP.datos.AccesoADatos;
import Aad.manuP.gui.Ventana;
import Aad.manuP.gui.VentanaController;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());

        // Instanciar Ventana
        Ventana ventana = new Ventana();
        AccesoADatos datos = new AccesoADatos();
        VentanaController controlador = new VentanaController(ventana,datos);

    }
}
