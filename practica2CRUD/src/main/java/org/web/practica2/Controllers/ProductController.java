package org.web.practica2.Controllers;

import io.javalin.Javalin;
import org.web.practica2.Services.Principal;
import org.web.practica2.models.Client;
import org.web.practica2.models.Product;
import org.web.practica2.models.Sell;
import org.web.practica2.models.User;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ProductController {

    private Javalin app;

    public ProductController(Javalin app ){
        this.app = app;
    }

    public void applyRoutes(){
        app.routes(() -> {
            path("/productos", () -> {
                get("/", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("title", "Tienda Online");
                    model.put("products",Principal.getInstance().getProducts());
                    User user = ctx.sessionAttribute("user");
                    if(user != null){
                        model.put("logged",user.getLogged());
                    }
                    else{
                        model.put("logged",false);
                    }

                    if(ctx.sessionAttribute("cart") == null){
                        model.put("car",false);
                    }
                    else{
                        model.put("car",true);
                    }

                    ctx.render("/public/home.html",model);
                });

                get("/comprar/:id", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    Product product =Principal.getInstance().findProductByID(ctx.pathParam("id", Integer.class).get());
                    model.put("title", "Tienda Online");
                    model.put("product",product);
                    model.put("accion", "/productos/comprar/"+product.getId());
                    model.put("onBuy",true);
                    ctx.render("/public/formproduct.html",model);
                });

                post("/comprar/:id", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    Product product =Principal.getInstance().findProductByID(ctx.pathParam("id", Integer.class).get());
                    int quantity = ctx.formParam("compra", Integer.class).get();
                    ArrayList<Product> selectedproducts = new ArrayList<>();
                    product.setQuantity(product.getQuantity() - quantity);
                    //selectedproducts.add(product);
                    Principal.getInstance().updateProduct(product);
                   // product.setQuantity(quantity);
                    Product product1 = new Product(product,quantity);
                    selectedproducts.add(product1);
                    String mail = ctx.formParam("email");
                    if(!Principal.getInstance().clientExistByEmail(mail)){
                        Client client =  new Client(ctx.formParam("cliente"),mail,selectedproducts);
                        Principal.getInstance().addClient(client);
                        /*Principal.getInstance().addSell(
                                new Sell(client,selectedproducts, LocalDate.now())
                        );*/
                        ctx.sessionAttribute("cart",client.getKart());
                        ctx.sessionAttribute("client",client);
                    }
                    else{
                        Client client = Principal.getInstance().findClientByEmail(mail);
                        for(Product aux:selectedproducts){
                            client.addToKart(aux);
                        }
                        ctx.sessionAttribute("cart",client.getKart());
                        ctx.sessionAttribute("client",client);


                    }



                    //Principal.getInstance().addProduct(product);
                    ctx.redirect("/productos");

                });


                post("/crear", ctx -> {
                    Product product = new Product(
                            ctx.formParam("nombre"),
                            ctx.formParam("precio", Double.class).get(),
                            ctx.formParam("cantidad", Integer.class).get()
                    );

                    Principal.getInstance().addProduct(product);
                    ctx.redirect("/productos");

                });

                get("/crear", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("title", "Tienda Online");
                    model.put("accion", "/productos/crear");
                    model.put("onBuy",false);
                    ctx.render("public/formproduct.html",model);
                });


                get("/editar/:id", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    Product product =Principal.getInstance().findProductByID(ctx.pathParam("id", Integer.class).get());
                    model.put("title", "Tienda Online");
                    model.put("product",product);
                    model.put("onBuy",false);
                    model.put("accion", "/productos/editar/"+product.getId());
                    ctx.render("/public/formproduct.html",model);
                });

                post("/editar/:id", ctx -> {
                    Product product = new Product(
                            ctx.pathParam("id", Integer.class).get(),
                            ctx.formParam("nombre"),
                            ctx.formParam("precio", Double.class).get(),
                            ctx.formParam("cantidad", Integer.class).get()
                    );
                    Principal.getInstance().updateProduct(product);
                    ctx.redirect("/productos");

                });


                get("/eliminar/:id", ctx -> {

                    Principal.getInstance().deleteProductById(ctx.pathParam("id", Integer.class).get());

                    ctx.redirect("/productos");

                });

                get("/carrito", ctx -> {
                    Map<String, Object> model = new HashMap<>();

                    List<Product> cart = ctx.sessionAttribute("cart");
                    model.put("products",cart);
                    model.put("title", "Tienda Online");
                    ctx.render("/public/cart.html",model);
                });

                get("/carrito/eliminar/:id", ctx -> {


                    Client client = ctx.sessionAttribute("client");
                    assert client != null;

                    Product product = Principal.getInstance().findProductByID(ctx.pathParam("id", Integer.class).get());
                    Product productClient = client.getProductById(product);
                    product.setQuantity( product.getQuantity() + productClient.getQuantity());
                    client.deleteProductFromKartById(ctx.pathParam("id", Integer.class).get());
                    Principal.getInstance().updateClient(client);

                    ctx.redirect("/productos");

                });

                get("/carrito/comprar", ctx -> {


                    Client client = ctx.sessionAttribute("client");
                    assert client != null;


                    Principal.getInstance().addSell(new Sell(client,client.getKart(),LocalDate.now()));
                    client.setKart(new ArrayList<>());
                    Principal.getInstance().updateClient(client);
                    ctx.sessionAttribute("cart",client.getKart());
                    ctx.redirect("/productos");

                });

                get("/carrito/clean", ctx -> {


                    Client client = ctx.sessionAttribute("client");
                    assert client != null;

                    client.setKart(new ArrayList<>());
                    Principal.getInstance().updateClient(client);
                    ctx.sessionAttribute("cart",client.getKart());

                    ctx.redirect("/productos");

                });





            });
        });
    }
}
