package mx.unam.ciencias.edd.proyecto3;
import java.io.BufferedInputStream;
import java.io.IOException;
import mx.unam.ciencias.edd.Lista;

/** 
 * Proyecto 3: Generador de laberintos
 */
public class Proyecto3 {

    private static void usoBandera(){
        System.err.println("Uso: java -jar target/proyecto3 -g ...\n La bandera \"-g\" es necesaria para generar el laberinto.");
        System.exit(1);
    }

    private static void usoNumeros(){
        System.err.println("Las banderas \"-h\", \"-w\" y \"-s\" deben ser seguidas de un numero");
        System.exit(1);
    }

    private static void uso(){
        System.err.println("Uso:\n java -jar target/proyecto3 -g -w N -h N\n java -jar target/proyecto3 -g s N -w N -h N");
        System.exit(1);
    }
    public static void main(String[] args){
        /* Lectura del archivo */
        if (args.length == 0){
            leerArchivo();
            return;
        }
        /* Generar laberinto */
        String s = null, w = null, h = null;
        Boolean banderaG = false;
        try{
            for (int i = 0; i < args.length; i++){
                switch (args[i]) {
                    case "-s":
                        s = args[i+1];   
                        break;
                    case "-w":
                        w = args[i+1];
                        break;
                    case "-h":
                        h = args[i+1];
                        break;
                    default:
                        break;
            }
            if (args[i].equals("-g"))
                banderaG = true;
            }
        } catch (IndexOutOfBoundsException iobe){
            usoNumeros();
        }
        if(!banderaG)
            usoBandera();
        if(w == null || h == null)
            uso();
        long semilla = System.currentTimeMillis();
        int columnas = 0, renglones = 0;
        try{
            if (s != null)
                semilla = Integer.parseInt(s);
            columnas = Integer.parseInt(w);
            renglones = Integer.parseInt(h);
        } catch(NumberFormatException nfe){
            usoNumeros();
        }

        if (columnas < 2 || renglones < 2  || columnas > 255 || renglones > 255){
            System.err.println("El minimo de columnas y de renglones que puede tener un laberinto es de 2 y maximo 255");
            System.exit(1);
        }

        GenerarArchivo archivo = new GenerarArchivo(columnas, renglones, semilla);
        archivo.creaArchivo();
    }

    /* Metodo auxiliar para leer uin archivo por bytes */
    private static void leerArchivo(){
        Lista<Integer> numeros = new Lista<>();
        try{
            BufferedInputStream out = new BufferedInputStream(System.in);
            int n;
            while ((n = out.read()) != -1){
                numeros.agrega(n);
            }
            out.close();
        } catch (IOException ioe){
            System.err.println("Ocurrio un error durante la lectura del archivo");
            System.exit(1);
        }
        GeneradorSVG svg = new GeneradorSVG(numeros);
        svg.ejecuta();
    }
}
