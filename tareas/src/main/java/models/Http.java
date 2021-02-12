package models;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

import java.io.IOException;
import java.util.ArrayList;


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
    public int getLinesResource(){
        return response.body().split("\n").length;
    }
    // b) Indicar la cantidad de párrafos (p) que contiene el documento HTML
    // c) Indicar la cantidad de imágenes (img) dentro de los párrafos que contiene el archivo HTML
    public int getResourceByQuery(String query){
        return document.select(query).size();
    }
    // d)  indicar la cantidad de formularios (form) que contiene el HTML por categorizando por el método implementado POST o GET

    public int getResourceByMethod(String method){
        return document.select("form[method='" + method + "']").size();
    }
    // e )Para cada formulario mostrar los campos del tipo input y su respectivo tipo que contiene en el documento HTML
    public ArrayList<String> getInputsTypes(){
        ArrayList<String> inputs = new ArrayList<>();
        int formInd = 0,inputInd = 0;
        for(FormElement aux : document.getElementsByTag("form").forms()){
            for(Element aux2: aux.getElementsByTag("input")){
                inputs.add("En el formulario #"+formInd+" el input #"+inputInd+" es de tipo "+ aux2.attr("type")+"\n");

                inputInd++;
            }
            formInd++;
        }
        return inputs;
    }







    



}
