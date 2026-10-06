 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.practica1.psp;

import clase.EjerciciosClase;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author apm25
 */
public class Practica1Psp {
    final static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    public static void main(String[] args) {
       EjerciciosClase.main();
       //ejercicioPadreHijo();
       //ejercicio1();
       //ejercicio2();
       //ejercicio3();
       //ejercicio4();
       //ejercicio5();
       //ejercicio6();
       //ejercicio7();
       //ejercicio8();
       //ejercicio9();
       //ejercicio10();
       //ejercicio11();
       //ejercicio12();
       //ejercicio13();
       //ejercicio14();
       
    }
    
    public static void ejercicio1() {
        ProcessBuilder pb = new ProcessBuilder("ping", "-n", "2", "127.0.0.1");
        try {
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            /*
            for(String line : br.readAllLines()){
                System.out.println(line);
            }*/
            for(int i = 0; i < 8; i++){
                linea = br.readLine();
                if(linea != null){
                    System.out.println(linea);
                } else {
                    break;
                }
            }
            /*
            int count = 1;
            while((linea = br.readLine()) != null && count <= 5){
                System.out.println(linea);
                count++;
            }
            */
            //Recojo el codigo de finalizacion del programa, es decir si ha ido bien o mal
            int exitCode = p.waitFor();
            System.out.println("Proceso finalizado con código: " + exitCode);
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void ejercicioPadreHijo(){
        ProcesoPadre.main();
    }
    
    public static void ejercicio2() {
        File dir = new File("./src");
        ProcessBuilder pb = isWindows ? new ProcessBuilder("cmd.exe", "/c", "dir") : new ProcessBuilder("ls");
        
        pb.directory(dir);
        try {
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String linea;
            System.out.println("Contenido del directorio " + dir.getPath() + ": ");
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void ejercicio3(){
        ProcessBuilder pb = new ProcessBuilder("python", "scripts/verificador.py");
        
        //con esto obtenemos las variables de entorno pero es una copia de las reales, podemos modificarlo sin cambiar
        //nada de nuestro sistema operativo
        Map<String, String> variablesEntorno = pb.environment();
        variablesEntorno.put("MI_VARIABLE", "PSP_2026");

        try {
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String linea;

            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error durante la ejecución del proceso.");
            e.printStackTrace();
        }
    }
    
    public static void ejercicio4(){
        String rutaInexistente = "carpeta_fantasma_404";
        ProcessBuilder pb = isWindows ?  new ProcessBuilder("cmd.exe", "/c", "dir", rutaInexistente) : new ProcessBuilder("ls", rutaInexistente);
        try {
            Process p = pb.start();
            int exitCode = p.waitFor();
            
            System.out.println("Código de salida: " + exitCode);

            if (exitCode == 0) {
                System.out.println("El proceso terminó con éxito (código 0).");
            } else {
                System.out.println("El proceso terminó con error (distinto de 0).");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static void ejercicio5(){
        ProcessBuilder pb = isWindows ? new ProcessBuilder("cmd.exe", "/c", "timeout", "/t", "3", "/nobreak") : new ProcessBuilder("sleep", "3");

        try {
            Process p = pb.start();
            while (p.isAlive()) {
                System.out.print(".");
                Thread.sleep(500); //pausa para no saturar el procesador con el bucle, descansa medio segundo entre puntos.
            }
            System.out.println("El proceso ha finalizado");
            System.out.println("Código de salida: " + p.exitValue());

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void ejercicio6() {
        ProcessBuilder pb = isWindows ? new ProcessBuilder("cmd.exe", "/c", "timeout", "/t", "10", "/nobreak") : new ProcessBuilder("sleep", "10");      
        try{
            Process p = pb.start();
            if (!p.waitFor(3, TimeUnit.SECONDS)) {
                p.destroyForcibly();
                System.out.println("Proceso cancelado por exceder el tiempo máximo.");
            }
        } catch(IOException | InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void ejercicio7(){
        ProcessBuilder pb = new ProcessBuilder(isWindows ? "ipconfig" : "ifconfig");
        pb.redirectOutput(ProcessBuilder.Redirect.to(new File("salida_red.txt")));
        try{
            Process p = pb.start();
        } catch(IOException e) {
            e.getMessage();
        }
    }
    
    public static void ejercicio8(){
        ProcessBuilder pb = isWindows ? 
                new ProcessBuilder("cmd.exe", "/c", "ping 127.0.0.1 && ping -invalidParam") : 
                new ProcessBuilder("bash", "-c", "ping -c 1 127.0.0.1 && ping --invalid-param");
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        try{
            Process p = pb.start();
        } catch(IOException e) {
            e.getMessage();
        }
    }
    
    public static void ejercicio9(){
        ProcessBuilder pb = isWindows ? 
                new ProcessBuilder("cmd.exe", "/c", "sort") : 
                new ProcessBuilder("sort", "-n");
        pb.redirectInput(ProcessBuilder.Redirect.from(new File("datos.txt")));
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        try{
            Process p = pb.start();
            
        } catch(IOException e) {
            e.getMessage();
        }
    }
    
    public static void ejercicio10(){
        ProcessBuilder pb = new ProcessBuilder(isWindows ? "tasklist" : "ps aux");
        try{
            Process p = pb.start();
            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int numeroProcesos = 0;
            String linea;
            while((linea = bf.readLine()) != null) {
                //System.out.println(linea);
                numeroProcesos++;
            }
            
            System.out.println("El numero de procesos en el sistema en este momento es: " + numeroProcesos);
        } catch(IOException e) {
            e.getMessage();
        }
    }
    
    public static void ejercicio11(){
        String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + java.io.File.separator + "bin" + java.io.File.separator + "java";
            String classPath = System.getProperty("java.class.path");
            ProcessBuilder pb = new ProcessBuilder(
                javaBin,
                "-cp", classPath, 
                "com.mycompany.practica1.psp.ProcesoHijo"
            );
        try{
            Process p = pb.start();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(p.getOutputStream()));
            pw.println("Linea de ejemplo 1");
            pw.println("Linea de ejemplo 2");
            pw.println("Linea de ejemplo 3");
            pw.flush();
            pw.close();
            
            //Importante sin esto no ves nada
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();
        } catch(IOException e) {
            e.getMessage();
        }
    }
    
    public static void ejercicio12() {
        ProcessBuilder pb = isWindows ? new ProcessBuilder("cmd.exe", "/c", "dir", "carpeta_falsa"): new ProcessBuilder("ls", "carpeta_falsa");
        try{
            Process p = pb.start();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream()))){
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
                String lineaError;
                while ((lineaError = brError.readLine()) != null) {
                    System.out.println(lineaError);
                }
            }
        } catch(IOException e) {
            e.getMessage();
        }
    }
    
    public static void ejercicio13(){
        ProcessBuilder pb1, pb2;
        if (isWindows) {
            //el primer proceso tira el listado de procesos
            pb1 = new ProcessBuilder("tasklist");
            //este proceso filtra las lineas que tienen la palabra java
            pb2 = new ProcessBuilder("findstr", "java");
        } else {
            pb1 = new ProcessBuilder("ps", "aux");
            pb2 = new ProcessBuilder("grep", "java");
        }
        //listado de procesos que lanzaremos a la vez
        //la salida del primer proceo sera la entrada del segundo
        List<ProcessBuilder> builders = Arrays.asList(pb1, pb2);

        try {
            List<Process> pipeline = ProcessBuilder.startPipeline(builders);

            //Obtengo el ultimo proceso, de ahi el size-1
            Process ultimoProceso = pipeline.get(pipeline.size() - 1);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(ultimoProceso.getInputStream()))) {
                String linea;
                //imprimira todos los procesos de que sean de java.
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            for (Process p : pipeline) {
                p.waitFor();
            }

        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void ejercicio14(){
        ProcessBuilder pb1, pb2, pb3;
        if (isWindows) {
            pb1 = new ProcessBuilder("cmd.exe", "/c", "dir", "/b");
            pb2 = new ProcessBuilder("findstr", ".java");
            pb3 = new ProcessBuilder("sort");
        } else {
            pb1 = new ProcessBuilder("ls", "-1");
            pb2 = new ProcessBuilder("grep", ".java");
            pb3 = new ProcessBuilder("sort");
        }
        
        File archivoSalida = new File("resultado_ordenado.txt");
        pb3.redirectOutput(ProcessBuilder.Redirect.to(archivoSalida));
        
        List<ProcessBuilder> builders = Arrays.asList(pb1, pb2, pb3);
        
        try{
            List<Process> pipeline = ProcessBuilder.startPipeline(builders);
            
            for (Process p : pipeline) {
                p.waitFor();
            }
            
        } catch(IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
