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
            table.classList.add('table');
            table.id = (numFila++); 
            if(zona ==='centro'){
                //primeras 3 mesas de cada fila del centro son vip
                if (i < 3) {
                    table.classList.add('vip','tres');                             
                    chairsPerTable = 3;
                } else if( i == 3){
                    table.classList.add('preferente');
                } else {
                    table.classList.add('gral');
                    if(i == 6){
                        table.classList.add('tres');
                        chairsPerTable = 3;
                    }
                }
                tableWrapper.appendChild(table);
            }else if(zona == 'izq'){
                //primeras 2 mesas pegadas al centro vip
                if (i < 2 && numFila > 330) {
                    table.classList.add('vip');                             
                } else if( i >= 2 && i < 4 && numFila > 330){
                    table.classList.add('preferente');
                } else if(numFila > 330){
                    table.classList.add('gral');
                } else{
                    table.classList.add('laterales');
                }
                tableWrapper.appendChild(table);
            }else if(zona == 'der'){
                //primeras 2 mesas pegadas al centro vip
                if (i < 2 && numFila < 520) {
                    table.classList.add('vip');                             
                } else if( i >= 2 && i < 4 && numFila < 520){
                    table.classList.add('preferente');
                } else if(numFila < 520){
                    table.classList.add('gral');
                } else{
                    table.classList.add('laterales');
                }
                tableWrapper.appendChild(table);
            }
            
            // Crear el número adecuado de sillas según 'chairsPerTable'
            for (let j = 0; j < chairsPerTable; j++) {
                const chair = document.createElement('div');   
                console.log(table.className);
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



function generateTablesHorizontal(container, totalTables, tablesPerRow, vipIndices = [], chairsPerTable = 4) {
    // Crear un contenedor para las filas
    const rowWrapper = document.createElement('div');
    rowWrapper.style.display = 'grid';
    rowWrapper.style.gridTemplateColumns = `repeat(${tablesPerRow}, auto)`;
    rowWrapper.style.gap = '10px';

    for (let i = 0; i < totalTables; i++) {
        const tableIndex = i; // Índice global de la mesa

        // Si la mesa está en los índices vip, añadir la clase 'vip'
        const tableWrapper = document.createElement('div');
        tableWrapper.classList.add('table-wrapper');

        const table = document.createElement('div');
        table.classList.add('table');
        if (i <= 3) {
            if(container.className === "footer-der"){
                table.classList.add('vip');
                chairsPerTable = 3;
            }else{
                table.classList.add('vip');
            }  
        }
        if(container.className === "footer-izq"){
            table.classList.add('vipfi');
            chairsPerTable = 2;
        }

        tableWrapper.appendChild(table);
        // Crear el número adecuado de sillas según 'chairsPerTable'
        for (let j = 0; j < chairsPerTable; j++) {
            const chair = document.createElement('div');
            if(container.className === "footer-der" && table.className === "table vipfd"){
                chair.classList.add('chair3');
            }else if(container.className === "footer-izq" && table.className === "table vipfi"){
                chair.classList.add('chair2');
            }else{
                chair.classList.add('chair');
            }
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
generateTables(vipContainer, 7, 7, 411,"centro"); // Zona Centro fila 1
generateTables(vipContainer, 7, 7, 421,"centro"); // Zona Centro fila 2
generateTables(vipContainer, 7, 7, 431,"centro"); // Zona Centro fila 3
generateTables(vipContainer, 7, 7, 431,"centro"); // Zona Centro fila 4
generateTables(vipContainer, 7, 7, 451,"centro"); // Zona Centro fila 5
generateTables(vipContainer, 7, 7, 461,"centro"); // Zona Centro fila 6


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

// Generar mesas en las nuevas zonas del footer
// const footerIzq = document.querySelector('.footer-izq');
// generateTablesHorizontal(footerIzq, 2, 2, []); // Mesas con 3 sillas en el footer
// generateTablesHorizontal(footerIzq, 3, 3, []); // Mesas con 2 sillas en el footer

const footerDer = document.querySelector('.footer-der');
generateTablesHorizontal(footerDer, 5, 5,[0,1,2,3,4]); // Mesas con 3 sillas en el footer