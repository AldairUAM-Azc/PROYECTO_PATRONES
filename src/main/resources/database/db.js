const mysql = require('mysql2');

// Crear conexión
const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  // password: 'Lawbin2328.',
  password: 'root',
  database: 'reservacion'
});

// Conectar
connection.connect((err) => {
  if (err) {
    console.error('Error al conectar a la base de datos:', err);
    return;
  }
  console.log('Conectado a la base de datos MySQL');
});

module.exports = connection;
