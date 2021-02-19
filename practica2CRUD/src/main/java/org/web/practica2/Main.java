package org.web.practica2;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.web.practica2.Controllers.ProductController;
import org.web.practica2.Controllers.UserController;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.enableCorsForAllOrigins();
            JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
        }).start(7777);



        app.get("/",ctx -> {
            ctx.redirect("productos");
        });

        new UserController(app).applyRoutes();
        new ProductController(app).applyRoutes();
    }
}
