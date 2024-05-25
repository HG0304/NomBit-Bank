/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Conexao {
    // realiza a conexao com o banco de dados provisionado na cloud azure
    public Connection getConnection() throws SQLException{
        Connection conexao = DriverManager.getConnection(
        "jdbc:postgresql://postgres-nombit-bank.postgres.database.azure.com:5432/postgres?user=myadmin&password=Fei12345&sslmode=require");
        return conexao;
    }
}
