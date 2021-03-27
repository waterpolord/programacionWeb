package org.web.carritodecompras.Controllers;

import io.javalin.Javalin;
import org.hibernate.Session;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.web.carritodecompras.Services.*;
import org.web.carritodecompras.Services.Connection.DataBaseManager;
import org.web.carritodecompras.models.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ProductController {

    private Javalin app;
    private ProductService productService = ProductService.getInstance();
    private ClientService clientService = ClientService.getInstance();
    private SaleService saleService = SaleService.getInstance();
    private UserService userService = UserService.getInstance();
    private PhotoService photoService = PhotoService.getInstance();
    private CommentService commentService = CommentService.getInstance();
    private Map<String, Object> model = new HashMap<>();

    public ProductController(Javalin app ){
        this.app = app;
    }

    public void applyRoutes(){
        app.routes(() -> {
            path("/productos", () -> {

                before("/*",ctx -> {
                    String username = ctx.cookie("username");
                    User user = null;


                    if(username != null){
                        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

                        user = userService.findUserByUsername(username);

                    }


                    if(user != null){
                        model.put("logged",true);
                    }
                    else{
                        model.put("logged",false);
                    }
                });

                get("/", ctx -> {


                    model.put("title", "Tienda Online");
                    model.put("products",productService.findAll());
                    User user = ctx.sessionAttribute("user");


                    if(ctx.sessionAttribute("cart") == null){
                        model.put("car",false);
                    }
                    else{
                        model.put("car",true);
                    }

                    ctx.render("/public/home.html",model);
                });

                /*post("/procesarFoto", ctx -> {
                    ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                        try {
                            byte[] bytes = uploadedFile.getContent().readAllBytes();
                            String encodedString = Base64.getEncoder().encodeToString(bytes);
                            Photo foto = new Photo(uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                            photoService.create(foto);
                            Product product = productService.find(ctx.pathParam("id", Integer.class).get());
                            product.addPhoto(foto);
                            productService.update(product);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ctx.redirect("/fotos/listar");
                    });
                });*/

                get("/comprar/:id", ctx -> {

                    Product product = productService.find(ctx.pathParam("id", Integer.class).get());
                    product = productService.update(product);
                    model.put("title", "Tienda Online");
                    model.put("product",product);
                    model.put("accion", "/productos/comprar/"+product.getId());
                    model.put("onBuy",true);
                    System.out.println("comentarios: ");
                    System.out.println(product.getComments());
                    ctx.render("/public/formproduct.html",model);
                });

                post("/comprar/:id", ctx -> {

                    Product product = productService.find(ctx.pathParam("id", Integer.class).get());
                    int quantity = ctx.formParam("compra", Integer.class).get();
                    ArrayList<Product> selectedproducts = new ArrayList<>();
                    product.setQuantity(product.getQuantity() - quantity);
                    //selectedproducts.add(product);
                    productService.update(product);
                   // product.setQuantity(quantity);
                    Product product1 = new Product(product,quantity);
                    selectedproducts.add(product1);
                    String mail = ctx.formParam("email");
                    if(clientService.findClientByMail(mail) == null){
                        Client client =  new Client(ctx.formParam("cliente"),mail,selectedproducts);
                        clientService.create(client);
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
                        clientService.update(client);
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
                            ctx.formParam("cantidad", Integer.class).get(),
                            ctx.formParam("descripcion", String.class).get()
                    );
                    var files = ctx.uploadedFiles("foto");
                    ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                        try {

                            byte[] bytes = uploadedFile.getContent().readAllBytes();
                            String encodedString = Base64.getEncoder().encodeToString(bytes);
                            Photo foto = new Photo(uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                            photoService.create(foto);
                            //product = productService.find(ctx.pathParam("id", Integer.class).get());
                            product.addPhoto(foto);
                            //productService.update(product);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        productService.create(product);
                    });


                    ctx.redirect("/productos");

                });

                get("/crear", ctx -> {
                   // Map<String, Object> model = new HashMap<>();
                    model.put("title", "Tienda Online");
                    model.put("accion", "/productos/crear");
                    model.put("onBuy",false);
                    model.put("photos",new ArrayList<Photo>());
                    model.put("comments",new ArrayList<Comment>());
                    ctx.render("public/formproduct.html",model);
                });


                get("/editar/:id", ctx -> {
                   // Map<String, Object> model = new HashMap<>();
                    Product product = productService.find(ctx.pathParam("id", Integer.class).get());
                    model.put("title", "Tienda Online");
                    model.put("product",product);
                    model.put("onBuy",false);
                    model.put("accion", "/productos/editar/"+product.getId());
                    ctx.render("/public/formproduct.html",model);
                });

                post("/editar/:id", ctx -> {


                    ctx.redirect("/productos");
                    Product product = new Product(
                            ctx.formParam("nombre"),
                            ctx.formParam("precio", Double.class).get(),
                            ctx.formParam("cantidad", Integer.class).get(),
                            ctx.formParam("descripcion", String.class).get()
                    );
                    ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                        try {

                            byte[] bytes = uploadedFile.getContent().readAllBytes();
                            String encodedString = Base64.getEncoder().encodeToString(bytes);
                            Photo foto = new Photo(uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                            product.addPhoto(foto);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    productService.update(product);

                });


                get("/eliminar/:id", ctx -> {

                    productService.delete(ctx.pathParam("id", Integer.class).get());

                    ctx.redirect("/productos");

                });

                get("/:id/comentario/:idcomment/eliminar", ctx -> {

                    Product product = productService.find(ctx.pathParam("id", Integer.class).get());
                    product.deleteCommentById(commentService.find(ctx.pathParam("idcomment", Integer.class).get()));
                    Comment comment = commentService.find(ctx.pathParam("idcomment", Integer.class).get());
                    comment.setActive(false);
                    commentService.update(comment);
                    productService.update(product);


                    ctx.redirect("/");
                    //ctx.render("/public/formproduct.html",model);

                });

                post("/comentar/:id", ctx -> {


                    //ctx.redirect("/productos");
                    Product product = productService.find(ctx.pathParam("id", Integer.class).get());
                    Comment comment = new Comment(ctx.formParam("mensaje", String.class).get(),
                            ctx.formParam("namecomment", String.class).get());
                    commentService.create(comment);
                    product.addComment(comment);
                    productService.update(product);
                    ctx.redirect("/");
                });

                get("/carrito", ctx -> {
                 //   Map<String, Object> model = new HashMap<>();

                    List<Product> cart = ctx.sessionAttribute("cart");
                    model.put("products",cart);
                    model.put("title", "Tienda Online");
                    ctx.render("/public/cart.html",model);
                });

                get("/carrito/eliminar/:id", ctx -> {


                    Client client = ctx.sessionAttribute("client");
                    assert client != null;

                    Product product = productService.find(ctx.pathParam("id", Integer.class).get());
                    Product productClient = client.getProductById(product);
                    product.setQuantity( product.getQuantity() + productClient.getQuantity());
                    client.deleteProductFromKartById(ctx.pathParam("id", Integer.class).get());
                    clientService.update(client);

                    ctx.redirect("/productos");

                });



                get("/carrito/comprar", ctx -> {


                    Client client = ctx.sessionAttribute("client");
                    assert client != null;


                    saleService.create(new Sale(client,client.getKart(),LocalDate.now()));
                    client.setKart(new ArrayList<>());
                    clientService.update(client);
                    ctx.sessionAttribute("cart",client.getKart());
                    ctx.redirect("/productos");

                });

                get("/carrito/clean", ctx -> {


                    Client client = ctx.sessionAttribute("client");
                    assert client != null;

                    client.setKart(new ArrayList<>());
                    clientService.update(client);
                    ctx.sessionAttribute("cart",client.getKart());

                    ctx.redirect("/productos");

                });





            });
        });
    }

}
