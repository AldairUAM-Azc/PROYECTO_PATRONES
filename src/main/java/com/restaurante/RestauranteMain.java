package com.restaurante;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class RestauranteMain {

  public static void main(String[] args) {
    Javalin app = Javalin.create(config -> {
      config.staticFiles.add("src/main/resources/public", Location.EXTERNAL);
    }).start(8080);
    app.get("/listado-de-eventos", Server::listadoDeEventos);
    app.get("/evento", Server::evento);
    app.get("/evento/nombre/{nombre}", Server::eventoPorNombre);
    app.get("/estado-sillas/{idEvento}", Server::estadoSillas);
    app.post("/crearEvento", Server::crearEvento);
    
  }
  
  
}
