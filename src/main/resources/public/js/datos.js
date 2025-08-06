console.log("Datos recibidos");
console.log(window.sembrado);
console.log(window.listaMesaSilla);

const sembrado = parseInt(window.sembrado);
const listaMesaSilla = window.listaMesaSilla;
const tipo = window.tipo;

/**
 * Confirmando la compra
 */
const reservarMesa = async (silla,codigo) => {
  silla.mesa = parseInt(silla.mesa);
  console.log(silla);
  try {
    //sembrado tiene el id del evento)
    const response = await fetch(`/reservar/${sembrado}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json' // Indicamos que el cuerpo de la solicitud es JSON
      },
      body: JSON.stringify({
        silla,
        codigo
      }) // Convertimos el objeto 'datos' a JSON
    });

    // Verificamos si la respuesta es exitosa
    if (!response.ok) {
      throw new Error(`Error al reservar: ${response.statusText}`);
    }

    // Convertimos la respuesta en JSON (si es necesario)
    const result = await response.json();
    
    //console.log('Reserva exitosa:', result.message);
    //alert(result.message); // Por ejemplo, alertamos el mensaje

  } catch (error) {
    console.error('Error en la solicitud:', error);
    alert('Hubo un problema al intentar hacer la reserva.');
  }
};

/**
 * Obteniendo los valores del formulario para reservar
 */
document.getElementById('reservar').addEventListener('click', async function() {
    // Obtener los valores de los campos del formulario
    const nombre = document.getElementById('nombre').value;
    const apellidos = document.getElementById('apellidos').value;
    const telefono = document.getElementById('telefono').value;

    // Verificar si todos los campos están llenos
    if (nombre && apellidos && telefono) {
        // Mostrar los datos en consola
        console.log('Nombre:', nombre);
        console.log('Apellidos:', apellidos);
        console.log('Teléfono:', telefono);
        const res = await fetch(`/conteo/${sembrado}`);
        const data = await res.json();
        const conteo = data.conteo;
        const nombreCompleto = nombre + " " + apellidos;
        console.log('Conteo recibido con fetch:', conteo);
        // Aquí puedes hacer lo que desees con los datos (enviarlos al servidor, etc.)
        // Por ejemplo, podrías enviar los datos a un servidor usando fetch
        const codigoCadena = `${sembrado}${conteo}`;
        const codigo = parseInt(codigoCadena);
        fetch('/codigo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ codigo, nombreCompleto, telefono })
        })
        .then(response => response.json())
        .then(data => console.log('Datos enviados correctamente:', data))
        .catch(err => console.error('Error al enviar los datos:', err));
        

        listaMesaSilla.forEach(silla =>{
            const cod = codigo;
            reservarMesa(silla,cod);
        })
        alert('Reservacion hecha!');
        window.location.href = "/";
    } else {
        alert('Por favor, complete todos los campos.');
    }
});

document.getElementById('volver').addEventListener('click', () =>{
  history.back();
})















// 
// codigo util para despues
// 

// async function procesarReserva(codigo, nombreCompleto, telefono) {
//   try {
//     const response = await fetch('/codigo', {
//       method: 'POST',
//       headers: {
//         'Content-Type': 'application/json'
//       },
//       body: JSON.stringify({ codigo, nombreCompleto, telefono })
//     });

//     const data = await response.json();
//     console.log('Datos enviados correctamente:', data);

//     // Ahora sí, reservar cada silla
//     for (const silla of listaMesaSilla) {
//       await reservarMesa(silla, codigo); // O sin await si querés paralelismo
//     }

//     alert('¡Reservación hecha!');
//     window.location.href = "/";
    
//   } catch (err) {
//     console.error('Error en la reserva:', err);
//     alert('Hubo un problema al intentar hacer la reserva.');
//   }
// }

// document.getElementById('reservar').addEventListener('click', function () {
//   const nombre = document.getElementById('nombre').value;
//   const apellidos = document.getElementById('apellidos').value;
//   const telefono = document.getElementById('telefono').value;
//   const nombreCompleto = `${nombre} ${apellidos}`;

//   if (nombre && apellidos && telefono) {
//     const codigo = generarCodigo(); // o donde lo estés generando

//     // 👇 Pasás los parámetros a la función async
//     procesarReserva(codigo, nombreCompleto, telefono);
//   } else {
//     alert('Por favor, complete todos los campos.');
//   }
// });