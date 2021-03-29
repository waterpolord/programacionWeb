package org.web.carritodecompras.Controllers;

import io.javalin.Javalin;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.web.carritodecompras.Services.SaleService;
import org.web.carritodecompras.Services.UserService;
import org.web.carritodecompras.models.User;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserController {
    private Javalin app;
    private UserService userService = UserService.getInstance();
    private SaleService saleService = SaleService.getInstance();

    public UserController(Javalin app ){
        this.app = app;
    }

    public void applyRoutes(){


        app.routes(() -> {
            path("/user", () -> {
                get("/", ctx -> {
                    Map<String, Object> model = new HashMap<>();

                    model.put("title","Tienda Online");
                    ctx.render("public/html/login.html",model);
                });

                get("/ventas",ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("sales",saleService.findAll());
                    model.put("title","Tienda Online");
                    ctx.render("public/html/sales.html",model);
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
                            ctx.cookie("username", user.getUsername(),604800);

                        }
                        ctx.sessionAttribute("user", user);
                    }

                    ctx.redirect("/productos");

                });


            });
        });


    }

}
