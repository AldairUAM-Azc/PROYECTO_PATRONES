

function generateTables(container, totalTables, tablesPerColumn, numFila,zona, chairsPerTable = 4) {
    for (let col = 0; col < Math.ceil(totalTables / tablesPerColumn); col++) {
        
        // Crear un contenedor para la columna
        const column = document.createElement('div');
        column.style.display = 'grid';
        column.style.gridTemplateRows = `repeat(${tablesPerColumn}, auto)`;
        column.style.gap = '10px';

        for (let i = 0; i < tablesPerColumn; i++) {
            const tableIndex = col * tablesPerColumn + i; // Índice global de la mesa
            if (tableIndex >= totalTables) break; // No generar más mesas si excede el total

            const tableWrapper = document.createElement('div');
            tableWrapper.classList.add('table-wrapper');

            const table = document.createElement('div');
            const span = document.createElement('span');
            table.classList.add('table');
            span.classList.add('numMesa');
            table.id = (numFila++); 
            span.textContent = numFila-1;
            table.appendChild(span);
            if(zona ==='centro'){
                //primeras 3 mesas de cada fila del centro son vip
                table.classList.add('vip');                             
                tableWrapper.appendChild(table);
            }else if(zona === 'izq' || zona ==='der'){
                //primeras 2 mesas pegadas al centro vip
                table.classList.add('preferente');                             
                tableWrapper.appendChild(table);
            }
            // Crear el número adecuado de sillas según 'chairsPerTable'
            for (let j = 0; j < chairsPerTable; j++) {
                const chair = document.createElement('div');   
                // console.log(table.className);
                switch(j){
                    case 0: chair.id = 'A'; 
                        break;
                    case 1: chair.id = 'C';
                        break;
                    case 2: chair.id = 'B';
                        break;
                    case 3: chair.id = 'D';
                        break;
                }                    
                if(container.className === "centro-mesas" && table.className.includes("tres")){
                    chair.classList.add('chair3');
                }else{
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

        if(chairsPerTable == 3){
            table.classList.add('table','gral','tres');
            span.classList.add('numMesa');
            table.id = (numFila); 
            span.textContent = numFila++;
            span.style.transform = 'rotate(0deg)'; 
            table.appendChild(span);
            tableWrapper.appendChild(table);


            // Crear el número adecuado de sillas según 'chairsPerTable'
            for (let j = 0; j < chairsPerTable; j++) {
                const chair = document.createElement('div');
                switch(j){
                    case 0: chair.id = 'A'; 
                        break;
                    case 1: chair.id = 'C';
                        break;
                    case 2: chair.id = 'B';
                        break;
                    case 3: chair.id = 'D';
                        break;
                }    
                chair.classList.add('chair3');
                tableWrapper.appendChild(chair);
            }
        } else if(chairsPerTable == 2){
            table.classList.add('table','gral','dos');
            span.classList.add('numMesa');
            table.id = (numFila); 
            span.textContent = numFila++;
            span.style.transform = 'rotate(0deg)'; 
            table.appendChild(span);
            tableWrapper.appendChild(table);


            // Crear el número adecuado de sillas según 'chairsPerTable'
            for (let j = 0; j < chairsPerTable; j++) {
                const chair = document.createElement('div');
                switch(j){
                    case 0: chair.id = 'A'; 
                        break;
                    case 1: chair.id = 'B';
                        break;
                }    
                chair.classList.add('chair2');
                tableWrapper.appendChild(chair);
            }
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
generateTables(vipContainer, 7, 7, 411,"centro"); // Zona Centro fila 1
generateTables(vipContainer, 7, 7, 421,"centro"); // Zona Centro fila 2S
generateTables(vipContainer, 7, 7, 431,"centro"); // Zona Centro fila 3
generateTables(vipContainer, 7, 7, 441,"centro"); // Zona Centro fila 4


// Generar mesas en la sección General Izquierda (tercera columna con 'vip')
const leftContainer = document.querySelector('.izq');
generateTables(leftContainer, 5, 5,311,"izq"); // La primera columna
generateTables(leftContainer, 4, 4,321,"izq"); // La segunda columna
generateTables(leftContainer, 5, 5,331,"izq"); // La tercera columna

// Generar mesas en la sección General Derecha (primera columna con 'vip')
const rightContainer = document.querySelector('.der');
generateTables(rightContainer, 5, 5,511,'der'); // La primera columna
generateTables(rightContainer, 4, 4,521,'der'); // La segunda columna
generateTables(rightContainer, 5, 5,531,'der'); // La tercera columna

//Generar mesas en las nuevas zonas del footer
const footerIzq = document.querySelector('.footer-izq');
generateTablesHorizontal(footerIzq, 2, 2, 201,2); // Mesas con 3 sillas en el footer
generateTablesHorizontal(footerIzq, 3, 3, 203,2); // Mesas con 2 sillas en el footer

const footerDer = document.querySelector('.footer-der');
generateTablesHorizontal(footerDer, 5, 5,214); // Mesas con 3 sillas en el footer


const sillas = document.querySelectorAll('.chair, .chair3, .chair2');

sillas.forEach(silla => {
    silla.addEventListener('click', () => {
        const wrapper = silla.closest('.table-wrapper');
        const mesa = wrapper.querySelector('.table');
        // console.log(`Mesa: ${mesa.id} Silla: ${silla.id}`);
        if(silla.classList.contains('ocupada'))
            alert('Silla ocupada!');
        else
            silla.classList.toggle('activa');
    });
});

const compra = document.querySelector('.compra');
const confirma = document.querySelector('.confirma-compra');
const fondo = document.querySelector('.fondoCompra');

let listaMesaSilla = [];

compra.addEventListener('click', () => {
    listaMesaSilla = [];
    // Obtener todas las sillas activas
    const sillasActivas = document.querySelectorAll('.chair.activa, .chair3.activa, .chair2.activa');
    
    confirma.innerHTML = '';

    if (sillasActivas.length === 0) {
        alert("No hay sillas seleccionadas!");
        return;
    }

    const inicioLista = document.createElement('h2');
    inicioLista.innerHTML = 'Sillas seleccionadas: ';
    confirma.appendChild(inicioLista);
    const lista = document.createElement('ul');
    
    // viendo sillas activas
    sillasActivas.forEach(silla => {
        const wrapper = silla.closest('.table-wrapper');
        const mesa = wrapper.querySelector('.table');
        const item = document.createElement('li');
        item.textContent = `Mesa: ${mesa.id} Silla: ${silla.id}`;
        lista.appendChild(item);
        const MesaSilla = {
            mesa : mesa.id,
            silla : silla.id
        }   
        listaMesaSilla.push(MesaSilla);
    });

    confirma.appendChild(lista);
    const volver = document.createElement('button');
    volver.innerHTML = 'volver';
    volver.className = 'volver';
    confirma.appendChild(volver);
    const confirmar = document.createElement('button');
    confirmar.innerHTML = 'confirmar';
    confirmar.className = 'confirmar';
    confirma.appendChild(volver);
    confirma.appendChild(confirmar);
    confirma.style.display = 'block';
    fondo.style.display = 'block';
    document.querySelector('.main-container').style.pointerEvents = 'none';
})

document.querySelector('.confirma-compra').addEventListener('click', function (event) {
    if (event.target.classList.contains('volver')) {
        this.style.display = 'none';
        fondo.style.display = 'none';
        document.querySelector('.main-container').style.pointerEvents = 'all';
    }
    if (event.target.classList.contains('confirmar')) {
        console.log(listaMesaSilla);
        //ir a formulario de compra o comprar
        this.style.display = 'none';
        fondo.style.display = 'none';
        document.querySelector('.main-container').style.pointerEvents = 'all';
    }
});

console.log('evento recibido: ', window.evento);
console.log('id del Evento: ', window.evento.idEvento);
//nombre que se recibira 
const sembrado = window.evento.idEvento;
const estadoSilla = 
fetch(`/estado-sillas/${sembrado}`)
.then(response => response.json())
.then(data => {
    const lista = document.getElementById('lista-sillas');
    data.forEach(item => {
        // const li = document.createElement('li');
        //console.log(`Mesa: ${item.Mesa}, Silla: ${item.Silla}, Estado: ${item.estado}`);
        if(item.estado){
            const mesa = document.getElementById(`${item.Mesa}`);
            const table = mesa.closest('.table-wrapper');
            switch(item.Silla){
                case 1: table.querySelector('#A').classList.toggle('ocupada');
                    break;
                case 2: table.querySelector('#B').classList.toggle('ocupada');
                    break;
                case 3: table.querySelector('#C').classList.toggle('ocupada');
                    break;
                case 4: table.querySelector('#D').classList.toggle('ocupada');
                    break;
            }
        }
        // lista.appendChild(li);
    });
})
.catch(error => {
    console.error('Error al cargar datos', error);
});

// Para control de sillas
// const allSillas = wrapper.querySelectorAll('.chair');
// allSillas.forEach(s => s.classList.remove('activa'));
// silla.classList.add('activa');

