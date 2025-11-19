package com.angel.conversordemonedas.clases;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConsultaMoneda {

    /*
    Variables que se utilizaran para mostrar los datos obtenidos de la API ExchangeRate
    al usuario.
    */
    private String monedaBase;
    private String monedaObjetivo;
    private double tasaDeConversion;

    /*
    Constructor que sirve para capturar los datos de la API desde el record Moneda.java
    y manipularlos en esta clase.
     */
    public ConsultaMoneda(Moneda monedas) {
        this.monedaBase = monedas.base_code();
        this.monedaObjetivo = monedas.target_code();
        this.tasaDeConversion = Double.valueOf(monedas.conversion_rate());
    }

    /*
    Constructor explícito de esta clase, generado para evitar un error en la línea (Pendiente de agregar la línea)
    de la clase principal.
     */
    public ConsultaMoneda() {
    }

    //Método que muestra el menú principal de la app
    public void exhibirMenu (){

        System.out.println("""
                ****************************************************************************
                !!!Bienvenido al conversor de monedas!!!
                
                1) Dólar =>> Peso Argentino.
                2) Peso Argentino =>> Dólar.
                3) Dólar =>> Real Brasileño.
                4) Real Brasileño =>> Dólar.
                5) Dólar =>> Peso Colombiano.
                6) Peso Colombiano =>> Dólar.
                0) Salir.
                ****************************************************************************
                Ingrese una opción valida ó 0 si desea salir:
                """);
    }

    //Esta clase muestra la respuesta al usuario según la opción elegida del menú principal
    public void mostrarRespuesta (ConsultaMoneda consulta, String monedaBase, String monedaObjetivo) {
        Scanner tecladoDelUsuario = new Scanner(System.in);
        double cantidadAConvertir = 0;

        System.out.println("Ingrese la cantidad de " + monedaBase);
        consulta.validarCantidadDeMoneda(tecladoDelUsuario);
        cantidadAConvertir = Double.valueOf(tecladoDelUsuario.nextDouble());

        Moneda monedas = consulta.buscaMoneda(monedaBase, monedaObjetivo);
        ConsultaMoneda miMoneda = new ConsultaMoneda(monedas);

        //System.out.println(monedas);
        System.out.println(miMoneda.toString(cantidadAConvertir));
    }

    //Este método sirve para validar que la opción que ingrese el usuario solo sean números enteros.
    public void validarOpcion(Scanner opcionElegida) {
        while (!opcionElegida.hasNextInt()) {
            System.out.println("Error: debe de ingresar solo números enteros!!");
            opcionElegida.next(); //Limpia la entrada incorrecta.
            System.out.println("Ingrese una opción valida!!");
        }
    }

    //Este método sirve para validar que el valor que ingrese el usuario solo sean números de tipo double.
    public void validarCantidadDeMoneda(Scanner valorIngresado) {
        while (!valorIngresado.hasNextDouble()) {
            System.out.println("Error: debe de ingresar solo números enteros o decimales!!");
            valorIngresado.next(); //Limpia la entrada incorrecta.
            System.out.println("Ingrese una valor valido!!");
        }
    }

    //Esta clase se encarga de realizar la solicitud al servidor de la API
    public Moneda buscaMoneda (String monedaBase, String monedaObjetivo) {
        //Objeto de la clase URI creada para poder buscar el tipo de moneda que el usuario elija
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/92df69f598851f8f1bf06a7a/pair/"
                                    + monedaBase + "/" + monedaObjetivo + "/");

        //Objetos de la clase HTTP para crear el cliente y la solicitud de este.
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        /*
        Bloque Try-Catch para poder capturar los posibles errores de la respuesta del servidor de la API
         */
        try {
            //Objeto de la clase HTTP para capturar la respuesta del servidor de la API
            HttpResponse<String> respuesta = cliente
                    .send(solicitud, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(respuesta.body(), Moneda.class); //Convierte el JSON recibido en el cuerpo de
                                                                        //la respuesta HTTP a un objeto de tipo Moneda
                                                                        //usando la librería Gson.
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Método para estructurar la respuesta que recibirá el usuario, recibe como parámetro
    la cantidad de dinero que el usuario desea convertir.
     */
    public String toString(Double cantidadBase) {
        double tasaDeConversionTotal = this.tasaDeConversion * cantidadBase;

        //estos fragmentos """String.format("%.2f", cantidadBase)""" hace que formatee las variables para
        //mostrar únicamente 2 decimales.
        return "El valor de " + String.format("%.2f", cantidadBase) + " " + this.monedaBase + " corresponde al valor final de =>>> "
                + String.format("%.2f", tasaDeConversionTotal) + " " + this.monedaObjetivo ;
    }
}
