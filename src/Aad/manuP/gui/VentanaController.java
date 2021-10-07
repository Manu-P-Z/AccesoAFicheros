package Aad.manuP.gui;

import Aad.manuP.clases.Coche;
import Aad.manuP.datos.AccesoADatos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import static javax.swing.JOptionPane.showMessageDialog;

public class VentanaController implements ActionListener {

    //Variables para la ventana y manejador de datos
    private final Ventana ventana;
    private final AccesoADatos datos;
    //Método para instanciar ventana y datos desde el main

    public VentanaController(Ventana ventana, AccesoADatos datos) {
        this.datos = datos;
        this.ventana = ventana;

        //Busca la ruta en el archivo data\ruta y la muestra en el campo de texto de ruta
        ventana.textFieldRuta.setText(datos.cargarRuta());

        //Almacena los datos del fichero en el hashmap
        datos.cargarDesdeFichero();

        //Muestra los coches en el campo de texto lateral
        buscarTodos();

        //Añadir todos los actionListeners(Código abajo del todo)
        addEventListeners(this);
    }


    //Codigo generado por la implementacion de ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        String accion = e.getActionCommand();
        Coche coche;

        switch (accion) {
            case "Nuevo":

                //Ponte todos los campos en blanco, y el check a false
                resetInput();

                break;
            case "Guardar":

                //Asigna valores a un nuevo objeto coche
                coche = new Coche();
                coche = asignarAtributos(coche);


                if (coche.getMatricula().isEmpty()) {
                    showMessageDialog(null, "La matrícula no puede estar vacía");

                } else {
                    datos.guardar(coche);
                    showMessageDialog(null, "Se ha guardado el coche");
                    buscarTodos();
                }
                break;

            case "Modificar":

                //Modifica objeto segun matricula
                coche = new Coche();
                coche = asignarAtributos(coche);
                datos.modificar(coche.getMatricula(), coche);
                showMessageDialog(null, "Se ha modificado la entrada ");

                break;

            case "Eliminar":

                //elimina el objeto con matricula especificada
                if(ventana.textFieldMatricula.getText().isEmpty()) {
                    showMessageDialog(null, "Escriba la matrícula que quiera eliminar en el campo Matrícula*");

                } else {
                    datos.eliminar(ventana.textFieldMatricula.getText());
                    showMessageDialog(null, "Se ha eliminado el coche");

                    buscarTodos();
                    resetInput();
                }
                break;

            case "Buscar":

                //Busca en el hashmap segun matricula y lo carga en los campos de texto(si existe)
                coche = datos.buscar(ventana.textFieldBuscar.getText());
                if (coche != null) {
                    cargar(coche);
                } else {
                    showMessageDialog(null, "No se ha encontrado un coche con esta matrícula");
                }
                break;
            case "Ruta":
                //cambia la ruta. Se selecciona aunque no se especifique un archivo. simplemente cancela o cierra la ventana una vez estes en el directorio que quieras
                elegirRuta();
                break;

        }
    }



    private void buscarTodos() {

        Map<String, Coche> coches = datos.buscarTodos();
        ventana.buscarTextPane.setText(null);
        for (Object i : coches.keySet()) {
            String matr = i.toString();
            if (ventana.buscarTextPane.getText().isEmpty()) {
                ventana.buscarTextPane.setText(matr);
            } else {
                ventana.buscarTextPane.setText(ventana.buscarTextPane.getText() + "\n" + matr);

            }
        }

    }

    private void cargar(Coche coche) {

        ventana.textFieldMatricula.setText(coche.getMatricula());
        ventana.textFieldModelo.setText(coche.getModelo());
        ventana.textFieldFecha.setText(coche.getFechaCompra());
        ventana.textFieldPotencia.setText(Integer.toString(coche.getPotencia()));
        ventana.textFieldCombustible.setText(coche.getCombustible());
        ventana.hibridoCheckBox.setSelected(coche.isHibrido());
    }


    private Coche asignarAtributos(Coche coche) {

        coche.setMatricula(ventana.textFieldMatricula.getText());
        coche.setModelo(ventana.textFieldModelo.getText());
        coche.setFechaCompra(ventana.textFieldFecha.getText());
        if (!ventana.textFieldPotencia.getText().isEmpty()) {
            coche.setPotencia(Integer.parseInt(ventana.textFieldPotencia.getText()));
        }
        coche.setCombustible(ventana.textFieldCombustible.getText());
        coche.setHibrido(ventana.hibridoCheckBox.isSelected());
        return coche;
    }

    //Añadir actionListeners para cada boton
    private void addEventListeners(ActionListener listener) {

        ventana.nuevoButton.addActionListener(listener);
        ventana.guardarButton.addActionListener(listener);
        ventana.modificarButton.addActionListener(listener);
        ventana.eliminarButton.addActionListener(listener);
        ventana.buscarButton.addActionListener(listener);
        ventana.buttonRuta.addActionListener(listener);

    }

    private void resetInput() {
        ventana.textFieldMatricula.setText("");
        ventana.textFieldModelo.setText("");
        ventana.textFieldFecha.setText("");
        ventana.textFieldPotencia.setText("");
        ventana.textFieldCombustible.setText("");
        ventana.hibridoCheckBox.setSelected(false);
        ventana.textFieldBuscar.setText("");
    }
    private void elegirRuta() {

        JFileChooser jfc = new JFileChooser();

        jfc.setCurrentDirectory(new File("."));
        jfc.showDialog(null,"Cierre esta pestaña cuando esté en la ruta deseada");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setAcceptAllFileFilterUsed(true);
        ventana.textFieldRuta.setText(jfc.getCurrentDirectory().toString());
        datos.cambiarRuta(jfc.getCurrentDirectory().toString());
    }
}
