/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clase;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author apm25
 */
public class EjerciciosClase {
    final static boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
    private EjerciciosClase(){}
    
    public static void main(){
        ejercicio9();
    }
    
    public static void ejercicio1(){
        ProcessBuilder pb = new ProcessBuilder("ping", "-n", "2", "127.0.0.1");
        
        try{
            Process p = pb.start();
            
            p.waitFor();
        } catch(IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void ejercicio2(){
        File carpeta = new File("./src");
        ProcessBuilder pb = isWindows ? new ProcessBuilder("cmd.exe", "/c", "dir") : new ProcessBuilder("ls");
        pb.directory(carpeta);
        
        try{
            Process p = pb.start();
            
            try(BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()))){
                String linea;
                //Archivos de la carpeta
                for(String archivo : carpeta.list()){
                    System.out.println(archivo);
                }
                
                System.out.println("----------------------------------");
                
                while((linea = bf.readLine()) != null){
                    System.out.println(linea);
                }
            }
            
            p.waitFor();
        } catch(IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void ejercicio3(){
        String programaPython = "import os\n" + "print(\"El valor recibido en el subproceso es: \" + str(os.environ.get('MI_VARIABLE')))";
        ProcessBuilder pb = new ProcessBuilder("python", "-c", programaPython);
        
        Map<String, String> env = pb.environment();
        env.put("MI_VARIABLE", "PSP_2026");
        
        try{
            Process p = pb.start();
            
            try(BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()))){
                String linea;
                while((linea = bf.readLine()) != null){
                    System.out.println(linea);
                }
            }
            
            p.waitFor();
        } catch(IOException | InterruptedException e) {
            System.out.println(e.getMessage());
       }
    }
    
    public static void ejercicio4(){
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "dir", "carpetaFantasma");
        try{
            Process p = pb.start();

            int exitCode = p.waitFor();
            System.out.println("Código de salida: " + exitCode);
        } catch(IOException |InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void ejercicio5(){
        ProcessBuilder pb = new ProcessBuilder("ping", "-n", "10", "127.0.0.1");
        try{
            Process p = pb.start();
            int segundos = 0;
            while(p.isAlive()){
                segundos++;
                Thread.sleep(1000);
            }
            System.out.println(segundos);
        } catch(IOException |InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void ejercicio6(){
        ProcessBuilder pb = new ProcessBuilder("ping", "-n", "10", "127.0.0.1");
        try{
            Process p = pb.start();
            
            
            if (!p.waitFor(3, TimeUnit.SECONDS)) {
                p.destroyForcibly();
                System.out.println("\nProceso cancelado por exceder el tiempo máximo.");
            }
            
        } catch(IOException |InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void ejercicio7(){
        ProcessBuilder pb = new ProcessBuilder("ping", "-n", "2", "127.0.0.1");
        pb.redirectOutput(ProcessBuilder.Redirect.to(new File("ejercicio7.txt")));
        try{
            Process p = pb.start();
            
            p.waitFor();
        } catch(IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    
   public static void ejercicio8(){
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -invalidParam");
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        
        try{
            Process p = pb.start();

            p.waitFor();
        } catch(IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
   }
   
   public static void ejercicio9(){
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "sort");
        pb.redirectInput(ProcessBuilder.Redirect.from(new File("datos.txt")));
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        try{
            Process p = pb.start();
            
            p.waitFor();
        } catch(IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
   }
}
