## Fabrica para creacion de eventos o Strategy para precio de los asientos
Podriamos aplicar el patron estrategia para calcular los diferentes precios de los asientos al calcular el costo total de la reservacion.
Por ahora, el constructor de Evento necesita de cuatros precios distintos, el prrecioVIP, precioPreferente, precioLaterales, y otro precio.
Podriamos encapsular la construccion de este objeto en una factory.

## Template: para ver si se puede tener una plantilla para los metodos que interactuan con la base de datos.

## Singleton: para la conexion a la base de datos

## DAO: para desacoplar el Server y la DatabaseConnection.

## Adapter: El frontend envía datos de un formulario en formato www-url-encoded, el backend necesista transformar/parsear esa url codificada a json, procesarla y enviar una respuesta en json. Podría funcionar un adaptador que permita que la url-encoded se comporte como json/Map<String, Object>

