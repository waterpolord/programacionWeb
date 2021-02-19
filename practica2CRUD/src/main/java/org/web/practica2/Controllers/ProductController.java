package org.web.practica2.Controllers;

import io.javalin.Javalin;
import org.web.practica2.Services.Principal;
import org.web.practica2.models.Product;

import java.util.HashMap;
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
                    ctx.render("/public/home.vm",model);
                });

                get("/comprar/:id", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    Product product =Principal.getInstance().findProductByID(ctx.pathParam("id", Integer.class).get());
                    model.put("title", "Tienda Online");
                    model.put("product",product);
                    ctx.render("/public/formproduct.vm",model);
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

                get("/crear/form", ctx -> {
                    Map<String, Object> model = new HashMap<>();

                    ctx.render("/public/formproduct.vm");
                });


                get("/editar/:id", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    Product product =Principal.getInstance().findProductByID(ctx.pathParam("id", Integer.class).get());
                    model.put("title", "Tienda Online");
                    model.put("product",product);
                    ctx.render("/public/formproduct.vm",model);
                });

                put("/editar", ctx -> {
                    Product product = new Product(
                            ctx.formParam("nombre"),
                            ctx.formParam("precio", Double.class).get(),
                            ctx.formParam("cantidad", Integer.class).get()
                    );
                    Principal.getInstance().updateProduct(product);
                    ctx.redirect("/productos");

                });






            });
        });
    }
}
