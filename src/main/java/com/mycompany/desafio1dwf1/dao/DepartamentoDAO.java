/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.desafio1dwf1.dao;

/**
 *
 * @author ludwi
 */
import com.mycompany.desafio1dwf1.model.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/gestion_rh";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Departamento> listarDepartamentos() throws SQLException {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM departamentos";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Departamento departamento = new Departamento();
                departamento.setId(resultSet.getInt("id"));
                departamento.setNombre(resultSet.getString("nombre"));
                departamentos.add(departamento);
            }
        }
        return departamentos;
    }

    public Departamento obtenerDepartamentoPorId(int id) throws SQLException {
        Departamento departamento = null;
        String sql = "SELECT * FROM departamentos WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    departamento = new Departamento();
                    departamento.setId(resultSet.getInt("id"));
                    departamento.setNombre(resultSet.getString("nombre"));
                }
            }
        }
        return departamento;
    }
}
