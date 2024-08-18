package com.mycompany.desafio1dwf1.dao;

import com.mycompany.desafio1dwf1.model.Empleado;
import com.mycompany.desafio1dwf1.model.Departamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/gestion_rh";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = ""; // 

    public EmpleadoDAO() {
        // Constructor vacío
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Empleado> listarEmpleados() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT e.id, e.nombre, e.apellido, e.email, e.telefono, d.id AS departamento_id, d.nombre AS departamento_nombre " +
                       "FROM empleados e JOIN departamentos d ON e.departamento_id = d.id";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(resultSet.getInt("id"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setApellido(resultSet.getString("apellido"));
                empleado.setEmail(resultSet.getString("email"));
                empleado.setTelefono(resultSet.getString("telefono"));

                Departamento departamento = new Departamento();
                departamento.setId(resultSet.getInt("departamento_id"));
                departamento.setNombre(resultSet.getString("departamento_nombre"));

                empleado.setDepartamento(departamento);
                empleados.add(empleado);
            }
        }

        return empleados;
    }

    public void agregarEmpleado(Empleado empleado) throws SQLException {
        String query = "INSERT INTO empleados (nombre, apellido, email, telefono, departamento_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, empleado.getNombre());
            preparedStatement.setString(2, empleado.getApellido());
            preparedStatement.setString(3, empleado.getEmail());
            preparedStatement.setString(4, empleado.getTelefono());
            preparedStatement.setInt(5, empleado.getDepartamento().getId());

            preparedStatement.executeUpdate();
        }
    }

    public Empleado obtenerEmpleadoPorId(int id) throws SQLException {
        Empleado empleado = null;
        String query = "SELECT e.id, e.nombre, e.apellido, e.email, e.telefono, d.id AS departamento_id, d.nombre AS departamento_nombre " +
                       "FROM empleados e JOIN departamentos d ON e.departamento_id = d.id WHERE e.id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    empleado = new Empleado();
                    empleado.setId(resultSet.getInt("id"));
                    empleado.setNombre(resultSet.getString("nombre"));
                    empleado.setApellido(resultSet.getString("apellido"));
                    empleado.setEmail(resultSet.getString("email"));
                    empleado.setTelefono(resultSet.getString("telefono"));

                    Departamento departamento = new Departamento();
                    departamento.setId(resultSet.getInt("departamento_id"));
                    departamento.setNombre(resultSet.getString("departamento_nombre"));

                    empleado.setDepartamento(departamento);
                }
            }
        }

        return empleado;
    }

    public void actualizarEmpleado(Empleado empleado) throws SQLException {
        String query = "UPDATE empleados SET nombre = ?, apellido = ?, email = ?, telefono = ?, departamento_id = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, empleado.getNombre());
            preparedStatement.setString(2, empleado.getApellido());
            preparedStatement.setString(3, empleado.getEmail());
            preparedStatement.setString(4, empleado.getTelefono());
            preparedStatement.setInt(5, empleado.getDepartamento().getId());
            preparedStatement.setInt(6, empleado.getId());

            preparedStatement.executeUpdate();
        }
    }

    public void eliminarEmpleado(int id) throws SQLException {
        String query = "DELETE FROM empleados WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
