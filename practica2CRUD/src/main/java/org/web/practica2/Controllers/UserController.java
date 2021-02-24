package org.web.practica2.Controllers;

import io.javalin.Javalin;
import org.web.practica2.Services.Principal;
import org.web.practica2.models.User;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserController {
    private Javalin app;

    public UserController(Javalin app ){
        this.app = app;
    }

    public void applyRoutes(){


        app.routes(() -> {
            path("/user", () -> {
                get("/", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("title","Tienda Online");
                    ctx.render("public/login.html",model);
                });

                get("/ventas",ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("sales",Principal.getInstance().getSells());
                    model.put("title","Tienda Online");
                    ctx.render("public/sales.html",model);
                });

                //POST
                post("/login",ctx -> {
                    String username = ctx.formParam("username");
                    String password = ctx.formParam("password");
                    assert password != null;
                    User user = Principal.getInstance().loginRequest(username,password);
                    ctx.sessionAttribute("user", user);
                    ctx.redirect("/productos");

                });



            });
        });


    }

}
