package org.web.carritodecompras;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.web.carritodecompras.Services.Connection.DataBaseManager;
import org.web.carritodecompras.Controllers.ProductController;
import org.web.carritodecompras.Controllers.UserController;
import org.web.carritodecompras.Services.Connection.DataBaseManager;
import org.web.carritodecompras.Services.ProductService;
import org.web.carritodecompras.Services.UserService;
import org.web.carritodecompras.models.Product;
import org.web.carritodecompras.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {

        DataBaseManager.startDb();
        //ProductService productService = ProductService.getInstance();
        ArrayList<Product> products =  new ArrayList<>();
        products.add(new Product("Leche",200.0,5,"leche de vaca"));
        products.add(new Product("Queso",225.0,50,""));
        products.add(new Product("Jamon",242.0,4,"jamon induveca"));
        products.add(new Product("Lechuga",2.0,15,""));
        products.add(new Product("Pan",200.0,5,""));
        products.add(new Product("Salami",225.0,50,""));
        products.add(new Product("Ron",100.0,4,""));
        products.add(new Product("Agua",2.0,15,""));
        for(Product product:products){
            ProductService.getInstance().create(product);
        }
        //UserService userService = UserService.getInstance();
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        User user = new User("Robert","admin",passwordEncryptor.encryptPassword("admin"));
        UserService.getInstance().create(user);

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/rutas"));
            config.enableCorsForAllOrigins();
            JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
        }).start(7000);

        app.get("/",ctx -> {
            ctx.redirect("productos");
        });

        new UserController(app).applyRoutes();
        new ProductController(app).applyRoutes();
    }
}
