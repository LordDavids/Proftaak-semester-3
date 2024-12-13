// Import the mysql2 library
import mysql from 'mysql2/promise';
import fs from 'fs';
import dotenv from 'dotenv';

dotenv.config({ path: '.env.development' });

// Create a function to connect to the database and execute a query
export async function runQuery() {
    // Configure the database connection
    const connection = await mysql.createConnection({
        host: process.env.DB_HOST,
        user: process.env.DB_USER,
        password: process.env.DB_PASSWORD,
        database: process.env.DB_DATABASE,
        port: '3306',
        ssl: false,
        multipleStatements: true
    });
    try {
        const file = fs.readFileSync('cypress/scripts/Dump.sql').toString();

        await connection.beginTransaction();

        await connection.query(file);

        await connection.commit();

        return Promise.resolve();
    } catch (error) {
        console.log(error);

        return Promise.reject(error);
    } finally {
        await connection.end();
    }

    return Promise.reject();

}
