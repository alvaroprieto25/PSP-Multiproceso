/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1.psp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 *
 * @author apm25
 */
public class ProcesoPadre {
    public static void main() {
        try {
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + java.io.File.separator + "bin" + java.io.File.separator + "java";
            String classPath = System.getProperty("java.class.path");
            ProcessBuilder pb = new ProcessBuilder(
                javaBin,
                "-cp", classPath, 
                "com.mycompany.practica1.psp.ProcesoHijo"
            );
            Process p = pb.start();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(p.getOutputStream()));
            String textoAEnviar = "hola desde el proceso padre, esto debe verse en mayusculas.";
            pw.println(textoAEnviar);
            
            //vaciado del buffer para que el texto llegue al hijo
            pw.flush(); 
            pw.close();

            BufferedReader lector = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String lineaRecibida;
            
            System.out.println("Respuesta del proceso hijo:");
            while ((lineaRecibida = lector.readLine()) != null) {
                System.out.println(lineaRecibida);
            }
            lector.close();

            //Devuelve el codigo del fin de ejecucion del proceso hijo
            int exitCode = p.waitFor();
            System.out.println("El proceso hijo terminó con código: " + exitCode);

        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
