package org.web.practica2;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.web.practica2.Controllers.ProductController;
import org.web.practica2.Controllers.UserController;
import org.web.practica2.Services.Connection.DataBaseManager;
import org.web.practica2.Services.ProductService;
import org.web.practica2.Services.UserService;
import org.web.practica2.models.Product;
import org.web.practica2.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/rutas"));
            config.enableCorsForAllOrigins();
            JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
        }).start(7000);

        DataBaseManager.startDb();
        DataBaseManager.createTables();
        ProductService productService = new ProductService();
        ArrayList<Product> products =  new ArrayList<>();
        products.add(new Product(1,"Leche",200.0,5));
        products.add(new Product(2,"Queso",225.0,50));
        products.add(new Product(3,"Jamon",242.0,4));
        products.add(new Product(4,"Lechuga",2.0,15));
        for(Product product:products){
            if(productService.findProductById(product.getId()) == null){
                productService.createProduct(product);

            }
        }
        UserService userService = new UserService();
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        User user = new User("Robert","admin",passwordEncryptor.encryptPassword("admin"));
        if(userService.findUserByUsername(user.getUsername()) == null){
            userService.createUser(user);
        }

        app.get("/",ctx -> {
            ctx.redirect("productos");
        });



        new UserController(app).applyRoutes();
        new ProductController(app).applyRoutes();
    }
}
