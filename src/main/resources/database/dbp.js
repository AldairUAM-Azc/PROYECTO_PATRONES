const mysql = require('mysql2/promise');

async function conectar() {
  const conexion = await mysql.createConnection({
      host: 'localhost',
      user: 'root',
      // password: 'Lawbin2328.',
      password: 'root',
      database: 'reservacion'
  });
  return conexion;
}

module.exports = conectar();