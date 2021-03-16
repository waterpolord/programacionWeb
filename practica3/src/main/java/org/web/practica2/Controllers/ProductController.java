package org.web.practica2.Controllers;

import io.javalin.Javalin;
import org.web.practica2.Services.*;
import org.web.practica2.models.Client;
import org.web.practica2.models.Product;
import org.web.practica2.models.Sale;
import org.web.practica2.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ProductController {

    private Javalin app;
    private ProductService productService = new ProductService();
    private ClientService clientService = new ClientService();
    private SaleService saleService = new SaleService();
    private UserService userService = new UserService();

    public ProductController(Javalin app ){
        this.app = app;
    }

    public void applyRoutes(){
        app.routes(() -> {
            path("/productos", () -> {
                get("/", ctx -> {

                    Map<String, Object> model = new HashMap<>();
                    model.put("title", "Tienda Online");
                    model.put("products",productService.findAllProducts());
                    User user = ctx.sessionAttribute("user");
                    String username = ctx.cookie("username");
                    if(username != null){
                       user = userService.findUserByUsername(username);

                    }


                    if(user != null){
                        model.put("logged",true);
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
                    Product product = productService.findProductById(ctx.pathParam("id", Integer.class).get());
                    model.put("title", "Tienda Online");
                    model.put("product",product);
                    model.put("accion", "/productos/comprar/"+product.getId());
                    model.put("onBuy",true);
                    ctx.render("/public/formproduct.html",model);
                });

                post("/comprar/:id", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    Product product = productService.findProductById(ctx.pathParam("id", Integer.class).get());
                    int quantity = ctx.formParam("compra", Integer.class).get();
                    ArrayList<Product> selectedproducts = new ArrayList<>();
                    product.setQuantity(product.getQuantity() - quantity);
                    //selectedproducts.add(product);
                    productService.updateProduct(product);
                   // product.setQuantity(quantity);
                    Product product1 = new Product(product,quantity);
                    selectedproducts.add(product1);
                    String mail = ctx.formParam("email");
                    if(clientService.findClientByMail(mail) == null){
                        Client client =  new Client(ctx.formParam("cliente"),mail,selectedproducts);
                        clientService.createClient(client);
                        /*Principal.getInstance().addSell(
                                new Sell(client,selectedproducts, LocalDate.now())
                        );*/
                        ctx.sessionAttribute("cart",client.getKart());
                        ctx.sessionAttribute("client",client);
                    }
                    else{
                        Client client = clientService.findClientByMail(mail);
                        for(Product aux:selectedproducts){
                            client.addToKart(aux);
                        }
                        clientService.updateClient(client);
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

                    productService.createProduct(product);
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
                    Product product = productService.findProductById(ctx.pathParam("id", Integer.class).get());
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
                    productService.updateProduct(product);
                    ctx.redirect("/productos");

                });


                get("/eliminar/:id", ctx -> {

                    productService.deleteProductById(ctx.pathParam("id", Integer.class).get());

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

                    Product product = productService.findProductById(ctx.pathParam("id", Integer.class).get());
                    Product productClient = client.getProductById(product);
                    product.setQuantity( product.getQuantity() + productClient.getQuantity());
                    client.deleteProductFromKartById(ctx.pathParam("id", Integer.class).get());
                    clientService.updateClient(client);

                    ctx.redirect("/productos");

                });

                get("/carrito/comprar", ctx -> {


                    Client client = ctx.sessionAttribute("client");
                    assert client != null;


                    saleService.createSale(new Sale(client,client.getKart(),LocalDate.now()));
                    client.setKart(new ArrayList<>());
                    clientService.updateClient(client);
                    ctx.sessionAttribute("cart",client.getKart());
                    ctx.redirect("/productos");

                });

                get("/carrito/clean", ctx -> {


                    Client client = ctx.sessionAttribute("client");
                    assert client != null;

                    client.setKart(new ArrayList<>());
                    clientService.updateClient(client);
                    ctx.sessionAttribute("cart",client.getKart());

                    ctx.redirect("/productos");

                });





            });
        });
    }
}
