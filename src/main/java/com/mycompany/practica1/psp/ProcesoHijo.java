/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1.psp;
import java.util.Scanner;

/**
 *
 * @author apm25
 */
public class ProcesoHijo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String linea = sc.nextLine();
            System.out.println(linea);
            //System.out.println(linea.toUpperCase());
        }
        sc.close();
    }
}