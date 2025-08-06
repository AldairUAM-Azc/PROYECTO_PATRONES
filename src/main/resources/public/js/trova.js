
function generateTables(container, totalTables, tablesPerColumn, numFila, zona, chairsPerTable = 4) {
    for (let col = 0; col < Math.ceil(totalTables / tablesPerColumn); col++) {

        // Crear un contenedor para la columna
        const column = document.createElement('div');
        column.style.display = 'grid';
        column.style.gridTemplateRows = `repeat(${tablesPerColumn}, auto)`;
        column.style.gap = '10px';

        for (let i = 0; i < tablesPerColumn; i++) {
            const tableIndex = col * tablesPerColumn + i; // Índice global de la mesa
            if (tableIndex >= totalTables)
                break; // No generar más mesas si excede el total

            const tableWrapper = document.createElement('div');
            tableWrapper.classList.add('table-wrapper');

            const table = document.createElement('div');
            const span = document.createElement('span');
            table.classList.add('table');
            span.classList.add('numMesa');
            table.id = (numFila++);
            span.textContent = numFila - 1;
            table.appendChild(span);
            if (zona === 'centro') {
                //primeras 3 mesas de cada fila del centro son vip
                if (i < 3) {
                    table.classList.add('vip', 'tres');
                    chairsPerTable = 3;
                    span.style.transform = 'rotate(0deg)';
                } else if (i == 3) {
                    table.classList.add('preferente');
                } else {
                    table.classList.add('gral');
                    if (i == 6) {
                        table.classList.add('tres');
                        chairsPerTable = 3;
                        span.style.transform = 'rotate(0deg)';
                    }
                }
                tableWrapper.appendChild(table);
            } else if (zona == 'izq') {
                //primeras 2 mesas pegadas al centro vip
                if (i < 2 && numFila > 330) {
                    table.classList.add('vip');
                } else if (i >= 2 && i < 4 && numFila > 330) {
                    table.classList.add('preferente');
                } else if (numFila > 330) {
                    table.classList.add('gral');
                } else {
                    table.classList.add('laterales');
                }
                tableWrapper.appendChild(table);
            } else if (zona == 'der') {
                //primeras 2 mesas pegadas al centro vip
                if (i < 2 && numFila < 520) {
                    table.classList.add('vip');
                } else if (i >= 2 && i < 4 && numFila < 520) {
                    table.classList.add('preferente');
                } else if (numFila < 520) {
                    table.classList.add('gral');
                } else {
                    table.classList.add('laterales');
                }
                tableWrapper.appendChild(table);
            }
            // Crear el número adecuado de sillas según 'chairsPerTable'
            for (let j = 0; j < chairsPerTable; j++) {
                const chair = document.createElement('div');
                // console.log(table.className);
                switch (j) {
                    case 0:
                        chair.id = 'A';
                        break;
                    case 1:
                        chair.id = 'C';
                        break;
                    case 2:
                        chair.id = 'B';
                        break;
                    case 3:
                        chair.id = 'D';
                        break;
                }
                if (container.className === "centro-mesas" && table.className.includes("tres")) {
                    chair.classList.add('chair3');
                } else {
                    chair.classList.add('chair');
                }
                tableWrapper.appendChild(chair);
            }
            chairsPerTable = 4;
            column.appendChild(tableWrapper);

        }

        // Agregar la columna completa al contenedor principal
        container.appendChild(column);
}
}



function generateTablesHorizontal(container, totalTables, tablesPerRow, numFila, chairsPerTable = 3) {
    // Crear un contenedor para las filas
    const rowWrapper = document.createElement('div');
    rowWrapper.style.display = 'grid';
    rowWrapper.style.gridTemplateColumns = `repeat(${tablesPerRow}, auto)`;
    rowWrapper.style.gap = '30px';
    rowWrapper.classList.add('plataforma');

    for (let i = 0; i < totalTables; i++) {
        const tableIndex = i; // Índice global de la mesa

        // Si la mesa está en los índices vip, añadir la clase 'vip'
        const tableWrapper = document.createElement('div');
        tableWrapper.classList.add('table-wrapper');

        const table = document.createElement('div');
        const span = document.createElement('span');
        table.classList.add('table', 'preferente', 'tres');
        span.classList.add('numMesa');
        table.id = (numFila);
        span.textContent = numFila++;
        span.style.transform = 'rotate(0deg)';
        table.appendChild(span);
        tableWrapper.appendChild(table);
        // Crear el número adecuado de sillas según 'chairsPerTable'
        for (let j = 0; j < chairsPerTable; j++) {
            const chair = document.createElement('div');
            switch (j) {
                case 0:
                    chair.id = 'A';
                    break;
                case 1:
                    chair.id = 'C';
                    break;
                case 2:
                    chair.id = 'B';
                    break;
                case 3:
                    chair.id = 'D';
                    break;
            }
            chair.classList.add('chair3');
            tableWrapper.appendChild(chair);
        }

        // Agregar la mesa y sus sillas a la fila
        rowWrapper.appendChild(tableWrapper);
    }

    // Agregar todas las mesas horizontales al contenedor principal
    container.appendChild(rowWrapper);
}

// Generar mesas en la sección VIP con 3 sillas por mesa

//Zona Vip naranja
//Zona Preferente blanco
//Zona General Azul

const vipContainer = document.querySelector('.centro-mesas');
generateTables(vipContainer, 7, 7, 411, "centro"); // Zona Centro fila 1
generateTables(vipContainer, 7, 7, 421, "centro"); // Zona Centro fila 2
generateTables(vipContainer, 7, 7, 431, "centro"); // Zona Centro fila 3
generateTables(vipContainer, 7, 7, 441, "centro"); // Zona Centro fila 4
generateTables(vipContainer, 7, 7, 451, "centro"); // Zona Centro fila 5
generateTables(vipContainer, 7, 7, 461, "centro"); // Zona Centro fila 6


// Generar mesas en la sección General Izquierda (tercera columna con 'vip')
const leftContainer = document.querySelector('.izq');
generateTables(leftContainer, 5, 5, 311, "izq"); // La primera columna
generateTables(leftContainer, 4, 4, 321, "izq"); // La segunda columna
generateTables(leftContainer, 5, 5, 331, "izq"); // La tercera columna

// Generar mesas en la sección General Derecha (primera columna con 'vip')
const rightContainer = document.querySelector('.der');
generateTables(rightContainer, 5, 5, 511, 'der'); // La primera columna
generateTables(rightContainer, 4, 4, 521, 'der'); // La segunda columna
generateTables(rightContainer, 5, 5, 531, 'der'); // La tercera columna

// Generar mesas en las nuevas zonas del footer
// const footerIzq = document.querySelector('.footer-izq');
// generateTablesHorizontal(footerIzq, 2, 2, []); // Mesas con 3 sillas en el footer
// generateTablesHorizontal(footerIzq, 3, 3, []); // Mesas con 2 sillas en el footer

const footerDer = document.querySelector('.footer-der');
generateTablesHorizontal(footerDer, 5, 5, 214); // Mesas con 3 sillas en el footer


const sillas = document.querySelectorAll('.chair, .chair3');

sillas.forEach(silla => {
    silla.addEventListener('click', () => {
        const wrapper = silla.closest('.table-wrapper');
        const mesa = wrapper.querySelector('.table');
        if (silla.classList.contains('ocupada')) {
            alert('Silla ocupada');
            return;
        }
        // console.log(`Mesa: ${mesa.id} Silla: ${silla.id}`);
        silla.classList.toggle('activa');
    });
});

const compra = document.querySelector('.compra');
const confirma = document.querySelector('.confirma-compra');

let listaMesaSilla = [];
let precios = [];
let total;

/**
 * Visualizando la compra 
 */
compra.addEventListener('click', () => {
    // Obtener todas las sillas activas
    listaMesaSilla = [];
    const sillasActivas = document.querySelectorAll('.chair.activa, .chair3.activa');
    total = 0;
    confirma.innerHTML = '';

    if (sillasActivas.length === 0) {
        alert("No hay sillas seleccionadas!");
        return;
    }

    const inicioLista = document.createElement('h2');
    inicioLista.innerHTML = 'Sillas seleccionadas: ';
    confirma.appendChild(inicioLista);
    const lista = document.createElement('ul');
    const fondo = document.querySelector('.fondo-compra');
    // viendo sillas activas
    console.log('compra: ');
    sillasActivas.forEach(silla => {
        const wrapper = silla.closest('.table-wrapper');
        const mesa = wrapper.querySelector('.table');
        const item = document.createElement('li');
        const precioSilla = precios.find(b => parseInt(b.mesa) === parseInt(mesa.id) && b.silla === silla.id)
        //console.log(precioSilla.precio);
        //console.log(`Mesa: ${mesa.id} Silla: ${silla.id} Precio: ${2}`);
        item.textContent = `Mesa: ${mesa.id} Silla: ${silla.id} Precio: ${precioSilla.precio}`;
        total += precioSilla.precio;
        lista.appendChild(item);
        //console.log(`Mesa: ${mesa.id} Silla: ${silla.id} Precio: ${p.precio}`);  
        const MesaSilla = {
            mesa: mesa.id,
            silla: silla.id
        }
        listaMesaSilla.push(MesaSilla);
    });

    confirma.appendChild(lista);
    const totalCompra = document.createElement('h3');
    totalCompra.innerHTML = `Total a pagar: ${total}`;
    const volver = document.createElement('button');
    volver.innerHTML = 'volver';
    volver.className = 'volver';
    confirma.appendChild(volver);
    const confirmar = document.createElement('button');
    confirmar.innerHTML = 'confirmar';
    confirmar.className = 'confirmar';
    confirma.appendChild(totalCompra);
    confirma.appendChild(volver);
    confirma.appendChild(confirmar);

    confirma.style.display = 'block';

    fondo.style.position = 'fixed';
    document.querySelector('.main-container').style.pointerEvents = 'none';
})

console.log('evento recibido: ', window.evento);
console.log('id del Evento: ', window.evento.idEvento);
//nombre que se recibira 
const sembrado = window.evento.idEvento;

/**
 * Verificando el estado de la silla
 */
const estadoSilla =
        fetch(`/estado-sillas/${sembrado}`)
        .then(response => response.json())
        .then(data => {
            const lista = document.getElementById('lista-sillas');
            precios = [];
            data.forEach(item => {
                // const li = document.createElement('li');
                //console.log(`Mesa: ${item.Mesa}, Silla: ${item.Silla}, Estado: ${item.estado} Precio: ${item.precio}`);
                const precioSilla = {
//            mesa: item.Mesa,
//            silla: item.Silla,
                    mesa: item.mesa,
                    silla: item.silla,
                    precio: item.precio
                };
                precios.push(precioSilla);
                if (item.estado) {
                    const mesa = document.getElementById(`${item.mesa}`);
                    const table = mesa.closest('.table-wrapper');
                    switch (item.silla) {
                        case 'A':
                            table.querySelector('#A').classList.toggle('ocupada');
                            break;
                        case 'B':
                            table.querySelector('#B').classList.toggle('ocupada');
                            break;
                        case 'C':
                            table.querySelector('#C').classList.toggle('ocupada');
                            break;
                        case 'D':
                            table.querySelector('#D').classList.toggle('ocupada');
                            break;
                    }
                }
                // lista.appendChild(li);
            });
        })
        .catch(error => {
            console.error('Error al cargar datos', error);
        });

/**
 * Visualizando informacion de compra (carrito)
 * Envia los datos a al formulario para el nombre y telefono del usuario
 */
document.querySelector('.confirma-compra').addEventListener('click', function (event) {
    if (event.target.classList.contains('volver')) {
        this.style.display = 'none';
        //fondo.style.display = 'none';
        document.querySelector('.main-container').style.pointerEvents = 'all';
        document.querySelector('.fondo-compra').style.position = 'unset';
    }
    if (event.target.classList.contains('confirmar')) {
        //ir a formulario de compra para ir a metodo de pago o reservacion
        const tipo = window.evento.tipo;
        const form = document.getElementById('jsonform');
        form.action = `/datos`
        document.getElementById('jsonData').value = JSON.stringify({
            sembrado: sembrado,
            listaMesaSilla: listaMesaSilla,
            tipo: tipo
        });
        //Enviar los datos
        form.submit();
        this.style.display = 'none';
        console.log(`idEvento: ${sembrado}`);
        console.log(listaMesaSilla);
        document.querySelector('.fondo-compra').style.position = 'unset';
        //aqui se hace el update
        // listaMesaSilla.forEach(silla =>{
        //     reservarMesa(silla);
        // })
        // //fondo.style.display = 'none';
        // alert('Reservacion hecha!');
        document.querySelector('.main-container').style.pointerEvents = 'all';
        // location.reload();
    }
});

/**
 * precios
 */

fetch(`/precios/${sembrado}`)
        .then(res => res.json())
        .then(data => {
            const tbody = document.querySelector('#tablaPrecios tbody');
            data.forEach(row => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
    <td>
    <span id = 'bolita${row.tipo}'></span>
    ${row.tipo}
    </td>
    <td>$${row.precio}</td>
    `;
                tbody.appendChild(tr);
            });
        })
        .catch(err => {
            console.error('Error al cargar los precios:', err);
        });

document.getElementById('inicio').addEventListener('click', (e) => {
    e.preventDefault();
    window.location.href = "/";
})
// Para control de sillas
// const allSillas = wrapper.querySelectorAll('.chair');
// allSillas.forEach(s => s.classList.remove('activa'));
// silla.classList.add('activa');

