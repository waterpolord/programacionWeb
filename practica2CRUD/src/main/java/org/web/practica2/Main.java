package org.web.practica2;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.web.practica2.Controllers.ProductController;
import org.web.practica2.Controllers.UserController;
import org.web.practica2.Services.Connection.DataBaseManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/rutas"));
            config.enableCorsForAllOrigins();
            JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
        }).start(7777);

        DataBaseManager.startDb();
        DataBaseManager.createTables();

        app.get("/",ctx -> {
            ctx.redirect("productos");
        });



        new UserController(app).applyRoutes();
        new ProductController(app).applyRoutes();
    }
}
