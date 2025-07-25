package com.restaurante;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;

public class RestauranteMain {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("src/main/resources/public", Location.EXTERNAL);
            config.fileRenderer(new JavalinThymeleaf());
        }).start(3000);
        app.get("/listado-de-eventos", Server::listadoDeEventos);
        app.get("/evento", Server::evento);
        app.get("/evento/nombre/{nombre}", Server::eventoPorNombre);
        app.get("/estado-sillas/{idEvento}", Server::estadoSillas);
        app.post("/crearEvento", Server::crearEvento);
        app.post("/sembrado/{nombre}", Server::sembrado);
        app.post("/reservar/{idEvento}", Server::reservarSillas);

    }

}
