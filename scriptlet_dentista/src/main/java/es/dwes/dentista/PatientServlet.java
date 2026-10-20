package es.dwes.dentista;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pacientes/*")
public class PatientServlet extends HttpServlet {
    private final ClinicDao dao = new ClinicDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if ("/nuevo".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/views/formularioPaciente.jsp").forward(request, response);
            return;
        }
        try {
            request.setAttribute("pacientes", dao.findPatients());
            request.getRequestDispatcher("/WEB-INF/views/listadoPacientes.jsp").forward(request, response);
        } catch (SQLException exception) {
            throw new ServletException("No se pudo cargar el listado de pacientes", exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String path = request.getPathInfo();
        if ("/eliminar".equals(path)) {
            try {
                dao.deletePatient(Integer.parseInt(request.getParameter("id_paciente")));
                response.sendRedirect(request.getContextPath() + "/pacientes?resultado=eliminado");
            } catch (IllegalArgumentException | SQLException exception) {
                request.setAttribute("error", "No se ha podido eliminar el paciente.");
                try {
                    request.setAttribute("pacientes", dao.findPatients());
                } catch (SQLException loadException) {
                    throw new ServletException("No se pudo cargar el listado de pacientes", loadException);
                }
                request.getRequestDispatcher("/WEB-INF/views/listadoPacientes.jsp").forward(request, response);
            }
            return;
        }
        try {
            dao.addPatient(request.getParameter("nombre"), request.getParameter("apellidos"),
                    request.getParameter("dni"), request.getParameter("fecha_nacimiento"),
                    request.getParameter("telefono"), request.getParameter("email"),
                    request.getParameter("direccion"), request.getParameter("alergias"));
            response.sendRedirect(request.getContextPath() + "/pacientes?resultado=creado");
        } catch (IllegalArgumentException | SQLException exception) {
            request.setAttribute("error", "No se ha podido guardar el paciente. Compruebe los datos e inténtelo de nuevo.");
            request.getRequestDispatcher("/WEB-INF/views/formularioPaciente.jsp").forward(request, response);
        }
    }
}