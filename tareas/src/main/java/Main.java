import models.Http;

public class Main {
    public static void main(String[] args) {
        Http http = new Http("https://www.abrirllave.com/cmd/cambiar-de-unidad.php");
        System.out.println("a) Indicar la cantidad de lineas del recurso retornado: "+http.getLinesResource()+"\n");

        System.out.println("b) Indicar la cantidad de párrafos (p) que contiene el documento HTML: "+http.getResourceByQuery("p")+"\n");

        System.out.println("c) Indicar la cantidad de imágenes (img) dentro de los párrafos que contiene el archivo HTML: "+http.getResourceByQuery("p img")+"\n");

        System.out.println("d)  indicar la cantidad de formularios (form) que contiene el HTML por\n" +
                "categorizando por el método implementado POST o GET: "+http.getResourceByQuery("p img")+"\n");



    }
}
