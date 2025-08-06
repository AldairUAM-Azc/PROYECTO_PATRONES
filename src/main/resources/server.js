const express = require('express');
const app = express();
const path = require('path');
const conexion = require('./database/db');
const bodyParser = require('body-parser');
const conexionPromise = require('./database/dbp');
const { error } = require('console');



// Para leer application/x-www-form-urlencoded (formularios normales)
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
//para envio de bodyjson
app.use(bodyParser.json());
// Middleware para servir archivos estáticos como index.html
app.use(express.static(path.join(__dirname, 'public')));
//configuracion de ejs
app.set('view engine','ejs');
app.set('views', path.join(__dirname,'views'));


//Eventos 
app.get('/evento',(req,res)=>{
  const query = 'SELECT * FROM evento';
  conexion.query(query,(error,resultado) => {
    if(error){
      console.error('Error en la consulta: ',error);
    }else{
      res.json(resultado);
    }
  })
})

/**
 * Carga de listado de eventos
 * 
 */
app.get('/listado-de-eventos', (req, res) => {
  // Establecer el idioma a español
  conexion.query("SET lc_time_names = 'es_ES'", (error1) => {
    if (error1) {
      console.error('Error al configurar lc_time_names:', error1);
      res.status(500).send('Error interno del servidor');
      return;
    }

    // Consulta de eventos con fecha formateada en español
    const query = `SELECT e.idEvento AS idEvento, e.nombre AS nombre, t.tipo AS tipo, DATE_FORMAT(e.fecha, '%d de %M de %Y') AS fecha
      FROM evento e
      JOIN tipoEvento t ON e.idTipoEVento = t.idTipoEvento
      ORDER BY e.fecha ASC;
    `;

    conexion.query(query, (error2, resultado) => {
      if (error2) {
        console.error('Error en la consulta:', error2);
        res.status(500).send('Error en la consulta');
      } else {
        res.json(resultado);
      }
    });
  });
}); 


//Encontrando evento por nombre
app.get('/evento/nombre/:nombre',(req,res) => {
  const nombre = req.params.nombre;

  const query = 'SELECT * FROM evento WHERE nombre = ?';
  conexion.query(query,[nombre],(error,resultado) => {
    if(error){
      console.error('Error en la consulta: ',error);
      return res.status(500).send('Error en el servidor');
    }

    if(resultado.length === 0){
      return res.status(404).send('Evento no encontrado');
    }

    res.json(resultado);
  })
})


/**
 * Cargando el estado de las sillas de acuerdo al evento
 */
app.get('/estado-sillas/:idEvento',(req,res) => {
  const idEvento = req.params.idEvento;
  
  const query = ` SELECT e.nombre, m.numero AS Mesa, s.letra AS Silla, s.estado, p.precio
                  FROM evento e 
                  JOIN precioEvento p ON e.idEvento = p.idEvento
                  JOIN mesa m ON p.idPrecio = m.idPrecio
                  JOIN silla s ON m.idMesa = s.idMesa
                  WHERE e.idEvento = ?;`;
  conexion.query(query,[idEvento], (error,resultado) => {
    if(error){
      console.error('Error al conectar la  base',error);
      return res.status(500).send('error en el servidor');
    }
    if(resultado.length ===0){
      return res.status(404).send('No se encontro el evento');
    }

    res.json(resultado);
  })
})


// Puerto en que correrá el servidor
const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`);
});

/**
 * Enviar creacion de evento
 */

app.post('/crearEvento', (req, res) => {
  console.log('Recibido:', req.body);
  const { nombre, fecha , tipo , precio } = req.body;
  if(tipo == "Trova"){
    const query = `CALL evento${tipo}( ? , ? , ? , ? , ? , ? , ? );`;
    conexion.query(query, [nombre,tipo,fecha, parseFloat(precio.VIP),parseFloat(precio.Preferente),
    parseFloat(precio.General),parseFloat(precio.Laterales)], (err, result) => {
    if (err) {
      console.error('Error al insertar:', err);
      return res.status(500).send('Error en el servidor');
    }
    //agregar condiciones para crear otros tipos de eventos

      res.send(`Evento de ${nombre} agregado`);
    });
  }
  if(tipo == "General"){
    const query = `CALL evento${tipo}( ? , ? , ? , ? , ? , ? );`;
    conexion.query(query, [nombre,tipo,fecha, parseFloat(precio.VIP),parseFloat(precio.Preferente),
    parseFloat(precio.General)], (err, result) => {
    if (err) {
      console.error('Error al insertar:', err);
      return res.status(500).send('Error en el servidor');
    }
    //agregar condiciones para crear otros tipos de eventos

      res.send(`Evento de ${nombre} agregado`);
    });
  }
});

/**
 * cargar pagina de sembrado
 */

app.post('/sembrado/:nombre',(req,res) => {
  const raw = req.body.data;
  if (!raw) {
    return res.status(400).send("No se recibió 'data' en el cuerpo del formulario.");
  }

  const evento = JSON.parse(req.body.data);
  //console.log(`Recibido en /${req.params.nombre}:`, evento);
  res.render(evento.tipo, { evento });
  //console.log(evento.tipo);
})

/**
 * Reservacion de sillas
 */
app.put('/reservar/:idEvento', (req, res) => {
  const { idEvento } = req.params; // Obtenemos el ID de los parámetros
  const { silla , codigo} = req.body; // Obtenemos los datos del cuerpo del JSON
  const mesa = silla.mesa;
  const sil = silla.silla;

  console.log(`mesa: ${mesa}`);
  console.log(`silla: ${sil}`);
  console.log(codigo);
  // Validación básica
  if (!mesa || !sil || isNaN(mesa) || typeof sil !== 'string') {
    return res.status(400).json({ error: 'Los parámetros mesa y silla deben ser números válidos' });
  }

  const query = ` UPDATE silla s
                  JOIN mesa m ON m.idMesa = s.idMesa
                  JOIN precioEvento p ON p.idPrecio = m.idPrecio
                  JOIN evento e ON e.idEvento = p.idEvento
                  SET s.estado = true,
                      s.codigo = ?
                  WHERE m.numero = ? 
                  AND e.idEvento = ?
                  AND s.letra = ?;`;

  // Ejecutar la consulta con parámetros
  conexion.query(query, [codigo, mesa, parseInt(idEvento), sil], (err, result) => {
    if (err) {
      console.error('Error al actualizar:', err);
      return res.status(500).json({ error: 'Error al actualizar los datos' });
    }

    if (result.affectedRows === 0) {
      return res.status(404).json({ message: 'No se encontró el registro con los datos proporcionados' });
    }
    res.status(200).json({ message: 'Reserva realizada correctamente' });
  });
});

/**
 * Enviando los datos para el formulario de los datos del usuario
 */
app.post('/datos', (req,res) => {
    const reserva = JSON.parse(req.body.jsonData);
    // console.log(reserva.sembrado);
    // console.log(reserva.listaMesaSilla);
    // console.log(reserva.tipo);
    sembrado = reserva.sembrado;
    listaMesaSilla = reserva.listaMesaSilla;
    tipo = reserva.tipo;
    res.render( 'datos' , { sembrado, listaMesaSilla, tipo } );
})

/**
 * Obtencion del conteo de forma asyncrona
 */
async function obtenerConteo(idEvento) {
  const conexionConteo = await conexionPromise
  const [rows] = await conexionConteo.execute(
    `SELECT COUNT(*) AS numero
     FROM silla s
     JOIN mesa m ON m.idMesa = s.idMesa
     JOIN precioEvento p ON p.idPrecio = m.idPrecio
     JOIN evento e ON e.idEvento = p.idEvento
     WHERE estado = true AND e.idEvento = ?`,
    [idEvento]
  );
  return rows[0].numero;
}

/**
 * consulta para la creacion del codigo
 */

app.get('/conteo/:idEvento', async (req, res) => {
  const { idEvento } = req.params;
  try {
    const conteo = await obtenerConteo(idEvento);
    res.json({ conteo }); // <-- Respuesta en formato JSON
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

/**
 * Insercion del codigo y el nombre de quien reserva
 */
app.post('/codigo',(req,res) =>{
    const {codigo,nombreCompleto,telefono} = req.body;
    console.log(`codigo: ${codigo}`);
    console.log(`nombre: ${nombreCompleto}`);
    console.log(`telefono: ${telefono}`);

    if( isNaN(codigo) || !nombreCompleto || !telefono){
      return res.status(400).json({ error: 'Parametros invalidos' });
    }

    const query = `INSERT INTO Reserva VALUES( ? , ? , ?);`;

    conexion.query(query,[codigo,nombreCompleto,telefono],(err,result) =>{
      if (err) {
      console.error('Error al actualizar:', err);
        return res.status(500).json({ error: 'Error al actualizar los datos' });
      }

      if (result.affectedRows === 0) {
        return res.status(404).json({ message: 'No se encontró el registro con los datos proporcionados' });
      }
      res.status(200).json({ message: 'Reserva realizada correctamente' });
    })
})

// app.get(`/lista/:idEvento`,(req,res) => {
//     const { idEvento } = req.params;

//   const query = `SELECT DISTINCT r.nombre , r.telefono, r.codigo 
//                   FROM reserva r
//                   JOIN silla s ON s.codigo = r.codigo
//                   JOIN mesa m ON m.idMesa = s.idMesa
//                   JOIN precioEvento p ON p.idPrecio = m.idPrecio
//                   JOIN evento e ON e.idEvento = p.idEvento
//                   WHERE e.idEvento = ?;`;

//   const queryNombreEvento = `SELECT nombre FROM evento WHERE idEvento = ?;`;

//   conexion.query(query, [idEvento], (err, resultado) => {
//     if (err) {
//       console.error('Error en la consulta:', err);
//       return res.status(500).send('Error en el servidor');
//     }

//     conexion.query(queryNombreEvento, [idEvento], (err, resultadoEvento) => {
//       if (err) {
//         console.error('Error al obtener el nombre del evento:', err);
//         return res.status(500).send('Error en el servidor al obtener el evento');
//       }

//       if (resultadoEvento.length === 0) {
//         return res.status(404).send('Evento no encontrado');
//       }

//       const nombreEvento = resultadoEvento[0].nombre;

//     res.render('lista', { reservas: resultado, nombreEvento });
//     }) 
//   })
    
// })

app.get('/lista/:idEvento', async (req, res) => {
  const { idEvento } = req.params;

  const queryReservas = `
    SELECT DISTINCT r.nombre, r.telefono, r.codigo 
    FROM reserva r
    JOIN silla s ON s.codigo = r.codigo
    JOIN mesa m ON m.idMesa = s.idMesa
    JOIN precioEvento p ON p.idPrecio = m.idPrecio
    JOIN evento e ON e.idEvento = p.idEvento
    WHERE e.idEvento = ?;
  `;

  const queryNombreEvento = `SELECT nombre FROM evento WHERE idEvento = ?;`;

  try {
    const conexion = await conexionPromise;

    // Obtener las reservas
    const [reservas] = await conexion.execute(queryReservas, [idEvento]);

    // Obtener el nombre del evento
    const [eventoResultado] = await conexion.execute(queryNombreEvento, [idEvento]);

    if (eventoResultado.length === 0) {
      return res.status(404).send('Evento no encontrado');
    }

    const nombreEvento = eventoResultado[0].nombre;

    // Obtener el conteo de boletos para cada reserva
    const reservasConBoletos = await Promise.all(
      reservas.map(async (reserva) => {
        const [conteo] = await conexion.execute(
          `SELECT COUNT(*) AS boletos
           FROM reserva r
           JOIN silla s ON r.codigo = s.codigo
           JOIN mesa m ON m.idMesa = s.idMesa
           JOIN precioEvento p ON p.idPrecio = m.idPrecio
           JOIN evento e ON e.idEvento = p.idEvento
           WHERE e.idEvento = ? AND r.codigo = ?;`,
          [idEvento, reserva.codigo]
        );

        return {
          ...reserva,
          boletos: conteo[0].boletos
        };
      })
    );

    // Renderizar la vista con los datos
    res.render('lista', {
      reservas: reservasConBoletos,
      nombreEvento
    });

  } catch (error) {
    console.error('Error en el servidor:', error);
    res.status(500).send('Error interno del servidor');
  }
});


/**
 * Obtencion de precios
 */

app.get('/precios/:idEvento', (req, res) => {
  const { idEvento } = req.params;

  const query = `
    SELECT t.tipo, p.precio
    FROM precioEvento p
    JOIN tipoMesa t ON t.idTipoMesa = p.idTipoMesa
    JOIN evento e ON p.idEvento = e.idEvento
    WHERE e.idEvento = ?;
  `;

  conexion.query(query, [idEvento], (err, resultado) => {
    if (err) {
      console.error('Error en la consulta:', err);
      return res.status(500).json({ error: 'Error en el servidor' });
    }

    res.json(resultado); // ← enviamos los resultados al cliente
  });
});

/**
 * Edicion de un evento
 */

app.get('/editar/:idEvento', (req, res) => {
  const { idEvento } = req.params;

  const query = `
    SELECT m.tipo, p.precio, e.fecha, e.nombre, t.tipo AS tipoEvento
    FROM evento e 
    JOIN tipoEvento t ON e.idTipoEvento = t.idTipoEvento
    JOIN precioEvento p ON e.idEvento = p.idEvento
    JOIN tipoMesa m ON m.idTipoMesa = p.idTipoMesa 
    WHERE e.idEvento = ?;
  `;

  conexion.query(query, [idEvento], (err, resultado) => {
    if (err) {
      console.error('Error al consultar evento:', err);
      return res.status(500).send('Error en el servidor');
    }
    if (resultado.length === 0) {
      return res.status(404).send('Evento no encontrado');
    }
    
    const nombreEvento = resultado[0].nombre;
    const fechaEvento = resultado[0].fecha;
    const tipoEvento = resultado[0].tipoEvento;

    // Preparamos los datos de precios y tipos de mesa para la vista
    // Para que sea más cómodo en el EJS, agrupamos precios por tipo
    const precios = resultado.map(row => ({
      tipo: row.tipo,
      precio: row.precio
    }));

    res.render('editarEvento', { idEvento, nombreEvento, fechaEvento, precios , tipoEvento });
  });
});


/**
 * Edicion del evento
 */
app.put(`/cambio/:idEvento`, (req, res) => {
  const { idEvento } = req.params;
  const { tipo, nombre, fecha, precio } = req.body;

  // Actualización del evento
  const queryEvento = `UPDATE evento 
                  SET nombre = ?, fecha = ? 
                  WHERE idEvento = ?`;

  conexion.query(queryEvento, [nombre, fecha, idEvento], (err, result) => {
    if (err) {
      console.log("Error al actualizar el evento: " + err);
      return res.status(500).json({ error: 'Error al actualizar los datos del evento' });
    }

    if (result.affectedRows === 0) {
      return res.status(404).json({ message: 'No se encontró el registro con los datos proporcionados' });
    }

    // Si la actualización del evento es exitosa, proceder con la actualización de precios.
    if (tipo == "Trova") {
      const VIP = parseFloat(precio.VIP);
      const Preferente = parseFloat(precio.Preferente);
      const General = parseFloat(precio.General);
      const Laterales = parseFloat(precio.Laterales);
      const queryPrecio = `UPDATE precioEvento
                          SET precio = 
                              CASE
                                WHEN idTipoMesa = 1 THEN ?
                                WHEN idTipoMesa = 2 THEN ?
                                WHEN idTipoMesa = 3 THEN ?
                                WHEN idTipoMesa = 4 THEN ?
                              END
                          WHERE idEvento = ?`;

      conexion.query(queryPrecio, [VIP, Preferente, General, Laterales, parseInt(idEvento)], (err, result) => {
        if (err) {
          console.log('Error al actualizar el precio ', err);
          return res.status(500).json({ error: 'Error al actualizar los datos del precio' });
        }

        if (result.affectedRows == 0) {
          return res.status(404).json({ message: 'No se encontró el registro de precio para el evento' });
        }

        // Finalmente, si todo fue exitoso, responder con un mensaje de éxito.
        res.status(200).json({ message: 'Reserva realizada correctamente' });
      });
    } else if(tipo == "General") {
          const VIP = parseFloat(precio.VIP);
          const Preferente = parseFloat(precio.Preferente);
          const General = parseFloat(precio.General);
          const queryPrecio = `UPDATE precioEvento
                            SET precio = 
                                CASE
                                  WHEN idTipoMesa = 1 THEN ?
                                  WHEN idTipoMesa = 2 THEN ?
                                  WHEN idTipoMesa = 3 THEN ?
                                END
                            WHERE idEvento = ?`;

        conexion.query(queryPrecio, [VIP, Preferente, General, parseInt(idEvento)], (err, result) => {
          if (err) {
            console.log('Error al actualizar el precio ', err);
            return res.status(500).json({ error: 'Error al actualizar los datos del precio' });
          }

          if (result.affectedRows == 0) {
            return res.status(404).json({ message: 'No se encontró el registro de precio para el evento' });
          }

          // Finalmente, si todo fue exitoso, responder con un mensaje de éxito.
          res.status(200).json({ message: 'Reserva realizada correctamente' });
      });
      } else{
      // Si el tipo no es "Trova", responder inmediatamente después de la actualización del evento
      res.status(200).json({ message: 'Evento actualizado correctamente' });
    }
  });
});

app.delete(`/eliminar/:idEvento`, (req,res) =>{
  const { idEvento } = req.params;
  
  const query = `DELETE FROM evento WHERE idEvento = ?`

  conexion.query(query,[idEvento],(err,result) =>{
    if(err){
      console.log('Error al eliminar el evento', err);
      res.status(500).json({error: 'Error al eliminar el evento'});
    }
    if(result.affectedRows === 0){
        return res.status(404).json({ message: 'No se elimino ningun evento' });
    }
    res.status(200).json({ message: 'Evento eliminado correctamente!' });
  })
})


/**
 * Conteo de reservas de forma asyncrona
 */
async function conteoReservas(idEvento,codigo) {
  const conexionConteo = await conexionPromise
  const [rows] = await conexionConteo.execute(
    `SELECT COUNT(*) AS boletos
    FROM Reserva r
    JOIN silla s ON r.codigo = s.codigo
    JOIN mesa m ON m.idMesa = s.idMesa
    JOIN precioEvento p ON p.idPrecio = m.idPrecio
    JOIN evento e ON e.idEvento = p.idEvento
    WHERE e.idEvento = 4 AND r.codigo = 52;`,
    [idEvento,codigo]
  );
  return rows[0].boletos;
}

/**
 * consulta para la creacion del codigo
 */

app.get('/conteoReservas/:idEvento', async (req, res) => {
  const { idEvento,codigo} = req.params;
  try {
    const conteo = await conteoReservas(idEvento,codigo);
    res.json({ conteo }); // <-- Respuesta en formato JSON
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});