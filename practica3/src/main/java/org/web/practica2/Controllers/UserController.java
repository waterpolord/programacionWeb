package org.web.practica2.Controllers;

import io.javalin.Javalin;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.web.practica2.Services.Principal;
import org.web.practica2.Services.SaleService;
import org.web.practica2.Services.UserService;
import org.web.practica2.models.User;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserController {
    private Javalin app;
    private UserService userService = new UserService();
    private SaleService saleService = new SaleService();

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
                    model.put("sales",saleService.findAllSales());
                    model.put("title","Tienda Online");
                    ctx.render("public/sales.html",model);
                });

                //POST
                post("/login",ctx -> {

                    String username = ctx.formParam("username");
                    String password = ctx.formParam("password");
                    assert password != null;
                    User user = userService.loginRequest(username,password);
                    if(user != null){
                        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
                        if(ctx.formParam("session") != null ){
                            ctx.cookie("username",user.getUsername(),604800);

                        }
                        ctx.sessionAttribute("user", user);
                    }

                    ctx.redirect("/productos");

                });


            });
        });


    }

}
