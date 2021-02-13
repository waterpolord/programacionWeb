import models.Http;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner scanf= new Scanner(System.in);
        System.out.println("enter url: ");
        String url = scanf.nextLine();

        Http http = new Http(url);
        System.out.println("a) Indicar la cantidad de lineas del recurso retornado: "+http.getLinesResource()+"\n");

        System.out.println("b) Indicar la cantidad de párrafos (p) que contiene el documento HTML: "+http.getResourceByQuery("p")+"\n");

        System.out.println("c) Indicar la cantidad de imágenes (img) dentro de los párrafos que contiene el archivo HTML: "+http.getResourceByQuery("p img")+"\n");

        System.out.println("d) metodo post:  indicar la cantidad de formularios (form) que contiene el HTML por\n" +
                        "categorizando por el método implementado POST o GET: ");
        System.out.println("metodo post: "+http.getResourceByMethod("post")+"\n");
        System.out.println("metodo get: "+http.getResourceByMethod("get")+"\n");

        System.out.println("e) Para cada formulario mostrar los campos del tipo input y su respectivo tipo que contiene en el documento HTML: \n");
        for(String aux:http.getInputsTypes()){
            System.out.println(aux);
        }
        System.out.println("f) Para cada formulario parseado, identificar que el método de envío\n" +
                "del formulario sea POST y enviar una petición al servidor con el\n" +
                "parámetro llamado asignatura y valor practica1 y un header llamado\n" +
                "matricula con el valor correspondiente a matrícula asignada. Debe\n" +
                "mostrar la respuesta por la salida estándar: ");
        http.Post();








    }
}
