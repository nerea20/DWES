<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Nuevo paciente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
  </head>
  <body>
    <h2>Introduzca los datos del nuevo paciente:</h2>
    <c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>
    <form method="post" action="${pageContext.request.contextPath}/pacientes">
      Nombre <input type="text" name="nombre" required><br>
      Apellidos <input type="text" name="apellidos" required><br>
      DNI <input type="text" name="dni" maxlength="9" required><br>
      Fecha de nacimiento <input type="date" name="fecha_nacimiento"><br>
      Teléfono <input type="text" name="telefono"><br>
      Correo electrónico <input type="email" name="email"><br>
      Dirección <input type="text" name="direccion"><br>
      Alergias o notas <input type="text" name="alergias"><br>
      <input type="submit" value="Aceptar">
    </form>
    <p><a href="${pageContext.request.contextPath}/">Volver al inicio</a></p>
  </body>
</html>