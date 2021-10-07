package Aad.manuP.datos;

import Aad.manuP.clases.Coche;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class AccesoADatos implements Serializable {
    public String nombreFichero = "coches.txt";
    public String ruta;
    private Map<String, Coche> coches = new HashMap<>();
    private final Properties configuracion = new Properties();


    public String  cargarRuta() {

        try {
            configuracion.load(new FileInputStream("data\\ruta.txt"));
            ruta = configuracion.getProperty("ruta") + "\\";

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return ruta;
    }

    public void guardar(Coche coche) {
        coches.put(coche.getMatricula(), coche);
        guardarAFichero();
    }

    public void modificar(String matricula, Coche coche) {
        coches.replace(matricula, coche);
        guardarAFichero();
    }

    public Coche buscar(String matricula) {
        try {
            return coches.get(matricula);
        } catch (Exception e) {
            return null;
        }
    }

    public void eliminar(String matricula) {
        coches.remove(matricula);
        guardarAFichero();
    }

    public Map<String, Coche> buscarTodos() {
        return coches;
    }

    private void guardarAFichero() {

        try {

            FileOutputStream flOut;
            flOut = new FileOutputStream(ruta + nombreFichero);
            ObjectOutputStream out = new ObjectOutputStream(flOut);

            out.writeObject(coches);

            out.close();
            flOut.close();

        } catch (IOException e) {
            System.out.println(e);

            e.printStackTrace();
        }
    }

    public void cargarDesdeFichero() {
        if (ruta == null) {
            ruta = "data\\";
        }
        try {
            FileInputStream flIn = new FileInputStream(ruta + nombreFichero);
            ObjectInputStream in = new ObjectInputStream(flIn);
            coches = (Map<String, Coche>) in.readObject();
            in.close();
            flIn.close();
            System.out.println("Se ha accedido al fichero");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    public void cambiarRuta(String ruta) {
        File og = new File(this.ruta +"\\" + nombreFichero);
        File destino = new File(ruta + "\\" + nombreFichero);
        try {
            Files.copy(og.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            this.ruta = ruta;

            configuracion.setProperty("ruta", ruta);
            try {
                configuracion.store(new FileOutputStream("data\\ruta.txt"),
                        "Fichero de configuracion");
            } catch (FileNotFoundException fnfe ) {
                fnfe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            System.out.println("Se ha movido el archivo");
            System.out.println(this.ruta);
            og.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
