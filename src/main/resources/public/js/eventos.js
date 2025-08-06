// document.addEventListener('DOMContentLoaded', () => {
//     document.querySelector('.inicio').addEventListener('click', (e) => {
//         e.preventDefault();
//         console.log('hola');
//         window.location.href = "/";
//     });
// });

let tipoEvento;

const trova = document.getElementById("Trova");
const trovaEvento = document.querySelector(".evento.trova");

const baile = document.getElementById("Baile");
const baileEvento = document.querySelector(".evento.baile");

const general = document.getElementById("General");
const generalEvento = document.querySelector(".evento.general");

trova.addEventListener('click', () => {
    trovaEvento.style.display = "grid";
    document.getElementById("Agregar").style.display="block";
    baileEvento.style.display = "none";
    generalEvento.style.display = "none";
    tipoEvento = "Trova";
})

baile.addEventListener('click', () => {
    baileEvento.style.display = "grid";
    document.getElementById("Agregar").style.display="block";
    trovaEvento.style.display = "none";
    generalEvento.style.display = "none";
    tipoEvento = "Baile";
})

general.addEventListener('click', () => {
    generalEvento.style.display = "grid";
    document.getElementById("Agregar").style.display="block";
    trovaEvento.style.display = "none";
    baileEvento.style.display = "none";
    tipoEvento = "General";
})


document.getElementById('nuevo-evento').addEventListener('submit', (event) =>{
    event.preventDefault();
    const nombre = document.getElementById('nombre').value;
    const fecha = document.getElementById('fecha').value;
    const tipo = tipoEvento;
    let precios;
    if(tipo == "Trova"){
        const evento = document.querySelector('.evento.trova');    
        precios = {
            VIP: evento.querySelector('input[name="VIP"]').value,
            Preferente: evento.querySelector('input[name="Preferente"]').value,
            General: evento.querySelector('input[name="General"]').value,
            Laterales: evento.querySelector('input[name="Laterales"]').value
        };
        //console.log(`VIP: ${VIP} Preferente: ${Preferente} General: ${General} Laterales: ${Laterales}`);
    }
    if(tipo == "General"){
        const evento = document.querySelector('.evento.general');
        precios = {
            VIP: evento.querySelector('input[name="VIP"]').value,
            Preferente: evento.querySelector('input[name="Preferente"]').value,
            General: evento.querySelector('input[name="General"]').value
        };
        //console.log(`VIP: ${VIP} Preferente: ${Preferente} General: ${General} Laterales: ${Laterales}`);
    }
    console.log(`nombre: ${nombre} fecha: ${fecha} tipo: ${tipo}`);
    console.log(precios);
    fetch('/crearEvento', {
        method: 'POST',
        headers : { 'Content-Type': 'application/json' },
        body: JSON.stringify({nombre,fecha,tipo,precio: precios})
    })
    .then(res => res.text())
    .then(data => {
        console.log(data);
        alert('Evento agregado: ' + data);
    })
    .catch(err => {
        console.error('Error:',err);
        alert('Ocurrio un error al agregar el evento.');
    });
    window.location.href = "/";
});

document.getElementById("inicio").addEventListener('click',()=>{
    window.location.href = "/";
})

//limite de fecha
const inputFecha = document.getElementById('fecha');

// Obtener fecha actual en formato YYYY-MM-DD
const hoy = new Date().toISOString().split('T')[0];

// Establecer el mínimo
inputFecha.min = hoy;
