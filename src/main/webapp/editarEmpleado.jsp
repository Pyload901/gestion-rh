<%-- 
    Document   : editarEmpleado
    Created on : 18 ago. 2024, 07:29:44
    Author     : ludwi
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Empleado</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1>Editar Empleado</h1>
        <form action="empleados?action=guardar" method="post">
            <div class="form-group">
                <label for="nombre">Nombre:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="${empleado.nombre}" required>
            </div>
            <div class="form-group">
                <label for="apellido">Apellido:</label>
                <input type="text" class="form-control" id="apellido" name="apellido" value="${empleado.apellido}" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="${empleado.email}" required>
            </div>
            <div class="form-group">
                <label for="telefono">Teléfono:</label>
                <input type="text" class="form-control" id="telefono" name="telefono" value="${empleado.telefono}" required>
            </div>
            <div class="form-group">
                <label for="departamento">Departamento:</label>
                <select class="form-control" id="departamento" name="departamento" required>
                    <c:forEach var="departamento" items="${departamentos}">
                        <option value="${departamento.id}" ${departamento.id == empleado.departamento.id ? 'selected' : ''}>
                            ${departamento.nombre}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <input type="hidden" name="id" value="${empleado.id}">
            <button type="submit" class="btn btn-primary">Guardar</button>
        </form>
    </div>
</body>
</html>
