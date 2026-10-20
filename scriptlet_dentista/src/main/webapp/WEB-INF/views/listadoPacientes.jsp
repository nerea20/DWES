<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Listado de pacientes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
  </head>
  <body>
    <h1>Listado de pacientes</h1>
    <c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>
    <c:if test="${param.resultado eq 'creado'}"><p>Paciente dado de alta.</p></c:if>
    <c:if test="${param.resultado eq 'eliminado'}"><p>Paciente eliminado. Sus citas asociadas también se han eliminado.</p></c:if>
    <table>
      <tr>
        <th>Id</th><th>Nombre</th><th>Apellidos</th><th>DNI</th><th>Fecha nacimiento</th>
        <th>Teléfono</th><th>Email</th><th>Dirección</th><th>Alergias</th><th>Citas</th><th>Acciones</th>
      </tr>
      <c:forEach var="paciente" items="${pacientes}">
        <tr>
          <td><c:out value="${paciente.id}"/></td>
          <td><c:out value="${paciente.name}"/></td>
          <td><c:out value="${paciente.surname}"/></td>
          <td><c:out value="${paciente.dni}"/></td>
          <td><c:out value="${paciente.birthDate}"/></td>
          <td><c:out value="${paciente.phone}"/></td>
          <td><c:out value="${paciente.email}"/></td>
          <td><c:out value="${paciente.address}"/></td>
          <td><c:out value="${paciente.allergies}"/></td>
          <td><c:out value="${paciente.appointmentCount}"/></td>
          <td>
            <a href="${pageContext.request.contextPath}/citas/nueva?paciente=${paciente.id}">Nueva cita</a>
            <form method="post" action="${pageContext.request.contextPath}/pacientes/eliminar">
              <input type="hidden" name="id_paciente" value="${paciente.id}">
              <input type="submit" value="Borrar">
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>
    <p><a href="${pageContext.request.contextPath}/pacientes/nuevo">Nuevo paciente</a> | <a href="${pageContext.request.contextPath}/">Volver al inicio</a></p>
  </body>
</html>