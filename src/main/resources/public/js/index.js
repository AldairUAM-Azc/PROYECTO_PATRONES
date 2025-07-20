
let datosEventos = [];
const listaEventos = 
fetch("/listado-de-eventos")
.then(response => response.json())
.then(data => {
    const lista = document.getElementById('lista-de-eventos');
    const eventos = document.querySelector('#lista-eventos');
    data.forEach(evento => {
        console.log(`id: ${evento.idEvento} nombre: ${evento.nombre} tipo: ${evento.tipo}`);
        const ev = {
            idEvento: evento.idEvento,
            nombre: evento.nombre,
            tipo: evento.tipo
        }
        datosEventos.push(ev);
        const nuevoEvento = document.createElement('span');
        const enlace = document.createElement('a');
        enlace.innerHTML = `${evento.nombre}`;
        enlace.id = `${evento.idEvento}`;
        enlace.href = `/${(evento.tipo)}`;
        nuevoEvento.appendChild(enlace);
        eventos.appendChild(nuevoEvento);
    });
})
.catch(error => {
    console.error('Error al cargar datos', error);
});


//console.log(datosEventos);

document.getElementById('lista-eventos').addEventListener('click',function(e){
    if(e.target.tagName === 'A'){
        e.preventDefault();
        const idEvento = e.target.id;
        const evento = datosEventos.find(obj => obj.idEvento == idEvento);
        const form = document.getElementById('jsonform');
        form.action = `/sembrado/${evento.tipo}`;
        document.getElementById('jsonData').value = JSON.stringify(evento);
        form.submit();
    }
})



