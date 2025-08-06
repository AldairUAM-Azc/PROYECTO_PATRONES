window.addEventListener('DOMContentLoaded', () => {
  const { nombreEvento, fechaEvento, precios} = window.eventoData;

  document.getElementById('nombre').value = nombreEvento;
  document.getElementById('fecha').value = fechaEvento;

  const contenedor = document.getElementById('precios-contenedor');
  contenedor.innerHTML = ''; // limpiar antes si acaso

  precios.forEach(({ tipo, precio }, index) => {
    const fila = document.createElement('div');
    fila.classList.add('precio-row');

    const label = document.createElement('label');
    label.textContent = tipo;
    label.htmlFor = `precio-${index}`;
    label.classList.add('tipo-mesa');

    const input = document.createElement('input');
    input.type = 'number';
    input.min = 0;
    input.step = 'any';
    input.id = `precio-${index}`;
    input.name = `precio-${index}`;
    input.value = precio;
    input.required = true;

    fila.appendChild(label);
    fila.appendChild(input);
    contenedor.appendChild(fila);
  });
});


/**
 * Enviar los cambios de evento
 */

document.getElementById('editar-evento').addEventListener('submit', (event) =>{
    event.preventDefault();
    const nombre = document.getElementById('nombre').value;
    const fecha = document.getElementById('fecha').value;
    const tipo = window.eventoData.tipoEvento;
    const idEvento = window.eventoData.idEvento;
    console.log(`tipo de evento:${tipo} idEvento: ${idEvento}`);
    console.log(idEvento)
    let precios;
    if(tipo === "Trova"){
        precios = {
            vip: document.querySelector('input[name="precio-0"]').value,
            preferente: document.querySelector('input[name="precio-1"]').value,
            general: document.querySelector('input[name="precio-2"]').value,
            laterales: document.querySelector('input[name="precio-3"]').value
        };
        console.log(`VIP: ${precios.vip} Preferente: ${precios.preferente} General: ${precios.general} Laterales: ${precios.laterales}`);
    }
    if(tipo === "General"){
        precios = {
            vip: document.querySelector('input[name="precio-0"]').value,
            preferente: document.querySelector('input[name="precio-1"]').value,
            general: document.querySelector('input[name="precio-2"]').value,
        };
        console.log(`VIP: ${precios.vip} Preferente: ${precios.preferente} General: ${precios.general}`);
    }

    console.log(`nombre: ${nombre} fecha: ${fecha} tipo: ${tipo}`);
    console.log(precios);
    
    fetch(`/cambio/${idEvento}`, {
        method: 'PUT',
        headers : { 'Content-Type': 'application/json' },
        body: JSON.stringify({tipo,nombre,fecha,precio: precios})
    })
    .then(res => res.text())
    .then(data => {
        console.log(data);
        alert('Evento modificado: ' + data);
    })
    .catch(err => {
        console.error('Error:',err);
        alert('Ocurrio un error al modificar el evento.');
    });
    window.location.href = "/";
});