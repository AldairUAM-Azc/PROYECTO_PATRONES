package com.restaurante;

import com.restaurante.adminagendareventos.Usuario;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;

public class RestauranteMain {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("src/main/resources/public", Location.EXTERNAL);
            config.fileRenderer(new JavalinThymeleaf());
        }).start(3000);
        // Endpoint de prueba para el login. Implementacion simple e ingenua.
        app.post("/login", ctx -> {
            ctx.sessionAttribute("usuarioLogueado", new Usuario("admin", "admin"));
            ctx.redirect("/");
        });
        
        // Rutas publicas
        app.get("/listado-de-eventos", Server::listadoDeEventos);
        app.get("/evento", Server::evento);
        app.get("/evento/nombre/{nombre}", Server::eventoPorNombre);
        app.get("/estado-sillas/{idEvento}", Server::estadoSillas);
        app.post("/crearEvento", Server::crearEvento);
        app.post("/sembrado/{nombre}", Server::sembrado);
        app.post("/reservar/{idEvento}", Server::reservarSillas);
        
        // Middleware de autorizacion para rutas de administrador
        app.before("/admin/*", ctx -> {
            Usuario usuario = ctx.sessionAttribute("usuarioLogueado");
            if(usuario == null || !usuario.esAdmin()){
                ctx.status(401); // Unauthorized
                ctx.result("Accesso denegado. Se requiere ser administrador");
            }
        });
        
        // Endpoints de administrador
        app.get("/admin/agendarEvento", Server::mostrarFormularioAgendarEvento);
        app.post("/admin/agendarEvento", Server::agendarEvento);

    }

}
