

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
})

baile.addEventListener('click', () => {
    baileEvento.style.display = "grid";
    document.getElementById("Agregar").style.display="block";
    trovaEvento.style.display = "none";
    generalEvento.style.display = "none";
})

general.addEventListener('click', () => {
    generalEvento.style.display = "grid";
    document.getElementById("Agregar").style.display="block";
    trovaEvento.style.display = "none";
    baileEvento.style.display = "none";
})

