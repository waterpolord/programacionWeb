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
                    ctx.render("/public/home.html",model);
                });

                get("/comprar/:id", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    Product product =Principal.getInstance().findProductByID(ctx.pathParam("id", Integer.class).get());
                    model.put("title", "Tienda Online");
                    model.put("product",product);
                    model.put("onBuy",true);
                    ctx.render("/public/formproduct.html",model);
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
                    model.put("accion", "/productos/crear");
                    model.put("onBuy",false);
                    ctx.render("/public/formproduct.html",model);
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





            });
        });
    }
}
