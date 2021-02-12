import models.Http;

public class Main {
    public static void main(String[] args) {
        Http http = new Http("https://en.wikipedia.org/");
        System.out.println("a) Indicar la cantidad de lineas del recurso retornado: "+http.getLines());
    }
}
