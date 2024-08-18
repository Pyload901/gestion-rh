package com.mycompany.desafio1dwf1.servlet;

import com.mycompany.desafio1dwf1.dao.DepartamentoDAO;
import com.mycompany.desafio1dwf1.dao.EmpleadoDAO;
import com.mycompany.desafio1dwf1.model.Departamento;
import com.mycompany.desafio1dwf1.model.Empleado;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GestionEmplearServlet extends HttpServlet {

    private EmpleadoDAO empleadoDAO;
    private DepartamentoDAO departamentoDAO;

    @Override
    public void init() {
        empleadoDAO = new EmpleadoDAO();
        departamentoDAO = new DepartamentoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            action = "listar"; // Asignar un valor por defecto si action es nulo
        }

        try {
            switch (action) {
                case "listar":
                    listarEmpleados(request, response);
                    break;
                case "crear":
                    mostrarFormularioCrear(request, response);
                    break;
                case "editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "guardar":
                    guardarEmpleado(request, response);
                    break;
                case "eliminar":
                    eliminarEmpleado(request, response);
                    break;
                default:
                    listarEmpleados(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar la solicitud", e);
        }
    }

    private void listarEmpleados(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Empleado> empleados = empleadoDAO.listarEmpleados();
        request.setAttribute("empleados", empleados);
        request.getRequestDispatcher("empleados.jsp").forward(request, response);
    }

    private void mostrarFormularioCrear(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Departamento> departamentos = departamentoDAO.listarDepartamentos();
        request.setAttribute("departamentos", departamentos);
        request.getRequestDispatcher("crearEmpleado.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Empleado empleado = empleadoDAO.obtenerEmpleadoPorId(id);
        List<Departamento> departamentos = departamentoDAO.listarDepartamentos();
        request.setAttribute("empleado", empleado);
        request.setAttribute("departamentos", departamentos);
        request.getRequestDispatcher("editarEmpleado.jsp").forward(request, response);
    }

    private void guardarEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        int departamentoId = Integer.parseInt(request.getParameter("departamento"));
        String idParam = request.getParameter("id");
        int id = (idParam == null || idParam.isEmpty()) ? 0 : Integer.parseInt(idParam);

        Departamento departamento = departamentoDAO.obtenerDepartamentoPorId(departamentoId);
        Empleado empleado = new Empleado();
        empleado.setId(id);
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setEmail(email);
        empleado.setTelefono(telefono);
        empleado.setDepartamento(departamento);

        if (id > 0) {
            empleadoDAO.actualizarEmpleado(empleado);
        } else {
            empleadoDAO.agregarEmpleado(empleado);
        }
        response.sendRedirect("gestionEmpleados?action=listar");
    }

    private void eliminarEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        empleadoDAO.eliminarEmpleado(id);
        response.sendRedirect("gestionEmpleados?action=listar");
    }

    @Override
    public String getServletInfo() {
        return "Servlet para la gestión de empleados";
    }
}
