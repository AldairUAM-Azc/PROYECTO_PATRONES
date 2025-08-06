
let datosEventos = [];
function cargarListaEventos() {


    const listaEventos =
            fetch("/listado-de-eventos")
            .then(response => response.json())
            .then(data => {
                const lista = document.getElementById('lista-de-eventos');
                const eventos = document.querySelector('#lista-eventos');
                data.forEach(evento => {
                    console.log(`id: ${evento.idEvento} nombre: ${evento.nombre} tipo: ${evento.tipo} fecha: ${evento.fecha}`);
                    const ev = {
                        idEvento: evento.idEvento,
                        nombre: evento.nombre,
                        tipo: evento.tipo,
                        fecha: evento.fecha
                    }
                    datosEventos.push(ev);
                    const nuevoEvento = document.createElement('span');
                    const enlace = document.createElement('a');
                    enlace.classList.add('enlace-evento');
                    enlace.innerHTML = `${evento.nombre.toUpperCase()} <br> ${evento.fecha}`;
                    enlace.id = `${evento.idEvento}`;
                    enlace.href = `/${(evento.tipo)}`;

                    // Botón "Reservas"
                    const enlaceReserva = document.createElement('a');
                    enlaceReserva.textContent = 'Reserva';
                    enlaceReserva.id = `${evento.idEvento}`;
                    enlaceReserva.href = `/${(evento.tipo)}`;
                    enlaceReserva.classList.add('boton-reserva');
                    enlaceReserva.dataset.eventoId = evento.idEvento;

                    // enlaceLista.addEventListener('click', (e) => {
                    //     e.preventDefault();
                    //     const idEvento = e.currentTarget.dataset.eventoId;
                    //     window.location.href = `/semprado/${idEvento}`;
                    // });

                    // Botón "Lista"
                    const enlaceLista = document.createElement('a');
                    enlaceLista.textContent = 'Lista';
                    enlaceLista.href = '#';
                    enlaceLista.classList.add('boton-lista');
                    enlaceLista.dataset.eventoId = evento.idEvento;

                    enlaceLista.addEventListener('click', (e) => {
                        e.preventDefault();
                        const idEvento = e.currentTarget.dataset.eventoId;
                        window.location.href = `/lista/${idEvento}`;
                    });

                    // Botón "Editar"
                    const enlaceEditar = document.createElement('a');
                    enlaceEditar.textContent = 'Editar';
                    enlaceEditar.href = '#';
                    enlaceEditar.classList.add('boton-editar');
                    enlaceEditar.dataset.eventoId = evento.idEvento;

                    enlaceEditar.addEventListener('click', (e) => {
                        e.preventDefault();
                        const idEvento = e.currentTarget.dataset.eventoId;
                        window.location.href = `/editar/${idEvento}`;
                    });

                    // Botón "Eliminar"
                    const enlaceBorrar = document.createElement('a');
                    enlaceBorrar.textContent = 'Eliminar';
                    enlaceBorrar.href = '#';
                    enlaceBorrar.classList.add('boton-borrar');
                    enlaceBorrar.dataset.eventoId = evento.idEvento;

                    enlaceBorrar.addEventListener('click', (e) => {
                        e.preventDefault();
                        const idEvento = e.currentTarget.dataset.eventoId;
                        fetch(`/eliminar/${idEvento}`, {
                            method: 'DELETE'
                        })
                                .then(response => {
                                    if (!response.ok)
                                        throw new Error('Error al eliminar');
                                    return response.json();
                                })
                                .then(data => {
                                    console.log(data.message);
                                    // Elimina el elemento del DOM si quieres
                                    // e.currentTarget.parentElement.remove(); (si el enlace está dentro del contenedor del evento)
                                    alert('Evento eliminado correctamente');
                                    location.reload(); // o actualiza solo esa parte del DOM
                                })
                                .catch(error => {
                                    console.error('Error al eliminar el evento:', error);
                                    alert('Hubo un error al eliminar el evento.');
                                });
                    });

                    // Contenedor para botones
                    const botones = document.createElement('div');
                    botones.classList.add('botones');
                    const botonesContainer = document.createElement('div');
                    botonesContainer.classList.add('botones-container');
                    botonesContainer.appendChild(enlaceReserva);
                    botonesContainer.appendChild(enlaceLista);
                    botonesContainer.appendChild(enlaceEditar);
                    // botonesContainer.appendChild(enlaceBorrar); //fondo Rojo
                    botones.appendChild(botonesContainer);
                    botones.appendChild(enlaceBorrar);
                    // Agregamos todo al span
                    nuevoEvento.appendChild(enlace);
                    nuevoEvento.appendChild(botones);

                    eventos.appendChild(nuevoEvento);
                });
            })
            .catch(error => {
                console.error('Error al cargar datos', error);
            });
}
document.addEventListener("DOMContentLoaded", cargarListaEventos());
//console.log(datosEventos);

document.getElementById('lista-eventos').addEventListener('click', function (e) {
    if (e.target.tagName === 'A') {
        e.preventDefault();
        const idEvento = e.target.id;
        const evento = datosEventos.find(obj => obj.idEvento == idEvento);
        const tipo = evento.tipo;
        const form = document.getElementById('jsonform');
        form.action = `/sembrado/${tipo}`;
        document.getElementById('jsonData').value = JSON.stringify(evento);
        form.submit();
    }
})


document.getElementById('agregarEvento').addEventListener('click', () => {
    window.location.href = "Eventos.html";
})



