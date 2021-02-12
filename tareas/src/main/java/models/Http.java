package models;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class Http {
    private Document document;
    private Response response;

    public Http(String url) {

        try{
            this.document = Jsoup.connect(url).get();
            this.response = Jsoup.connect(url).execute();
        }catch (IOException e){
            System.out.println("No se ha podido acceder al recurso: "+e);
        }



    }

    // a) Indicar la cantidad de lineas del recurso retornado
    public int getLines(){
        return response.body().split("\n").length;
    }

    



}
