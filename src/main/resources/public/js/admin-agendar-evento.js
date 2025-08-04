const $formulario = document.getElementById('formulario-evento');

$formulario.addEventListener('submit', ev => {
    ev.preventDefault();
    const form = ev.target;

    const eventoData = {
        tipo: form.elements['tipo'].value,
        nombre: form.elements['nombre'].value,
        descripcion: form.elements['descripcion'].value,
        fecha: form.elements['fecha'].value
    };

    fetch('/admin/agendarEvento', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(eventoData)
    }).then(response => {
        response.text();
    }).then(data => {
        console.log(data);
    }).catch(error => {
        console.error('Error:', error);
    });
});