package com.angel.conversordemonedas.principal;

import com.angel.conversordemonedas.clases.ConsultaMoneda;
import com.angel.conversordemonedas.clases.Moneda;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner tecladoUsuario = new Scanner(System.in); //Objeto de tipo Scanner para capturar la entrada del usuario.
        int opcion = 1; //Variable a utilizar como parámetro del bucle While.
        ConsultaMoneda consulta = new ConsultaMoneda();

        //Inicio del bloque Try-Cath
        try {
            //Inicio del bloque While
            while (opcion != 0) {
                consulta.exhibirMenu();

                consulta.validarOpcion(tecladoUsuario);
                opcion = tecladoUsuario.nextInt();

                //Inicio del bloque Switch
                switch (opcion) {
                    case 1:
                        consulta.mostrarRespuesta(consulta, "USD", "ARS");
                        break;
                    case 2:
                        consulta.mostrarRespuesta(consulta, "ARS", "USD");
                        break;
                    case 3:
                        consulta.mostrarRespuesta(consulta, "USD", "BRL");
                        break;
                    case 4:
                        consulta.mostrarRespuesta(consulta, "BRL", "USD");
                        break;
                    case 5:
                        consulta.mostrarRespuesta(consulta, "USD", "COP");
                        break;
                    case 6:
                        consulta.mostrarRespuesta(consulta, "COP", "USD");
                        break;
                    default:
                        if (opcion != 0) {
                            System.out.println("Opción invalida!!");
                        }
                        break;
                }//Fin del bloque Switch
            }//Fin del bloque While

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }//Fin del bloque Try-Catch

        System.out.println("Finalizando el programa!!");
    }
}
