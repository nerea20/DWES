<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Nueva cita</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
  </head>
  <body>
    <h2>Introduzca los datos de la nueva cita:</h2>
    <c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>
    <form method="post" action="${pageContext.request.contextPath}/citas">
      Fecha <input type="date" name="fecha" required><br>
      Hora <input type="time" name="hora" required><br>
      Paciente
      <select name="id_paciente" required>
        <option value="">-- Seleccione un paciente --</option>
        <c:forEach var="paciente" items="${pacientes}">
          <c:choose>
            <c:when test="${paciente.id == pacienteSeleccionado}">
              <option value="${paciente.id}" selected><c:out value="${paciente.surname}"/>, <c:out value="${paciente.name}"/></option>
            </c:when>
            <c:otherwise>
              <option value="${paciente.id}"><c:out value="${paciente.surname}"/>, <c:out value="${paciente.name}"/></option>
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </select><br>
      Odontólogo
      <select name="id_odontologo" required>
        <option value="">-- Seleccione un odontólogo --</option>
        <c:forEach var="odontologo" items="${odontologos}">
          <option value="${odontologo.id}"><c:out value="${odontologo.name}"/> (<c:out value="${odontologo.specialty}"/>)</option>
        </c:forEach>
      </select><br>
      Motivo <input type="text" name="motivo"><br>
      <input type="submit" value="Aceptar">
    </form>
    <p><a href="${pageContext.request.contextPath}/">Volver al inicio</a></p>
  </body>
</html>