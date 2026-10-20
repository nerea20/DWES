package es.dwes.dentista;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/citas/*")
public class AppointmentServlet extends HttpServlet {
    private final ClinicDao dao = new ClinicDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        try {
            if ("/nueva".equals(path)) {
                request.setAttribute("pacientes", dao.findPatientsForSelect());
                request.setAttribute("odontologos", dao.findDentists());
                request.setAttribute("pacienteSeleccionado", request.getParameter("paciente"));
                request.getRequestDispatcher("/WEB-INF/views/formularioCita.jsp").forward(request, response);
                return;
            }
            boolean todas = "1".equals(request.getParameter("todas"));
            request.setAttribute("todas", todas);
            request.setAttribute("citas", dao.findAppointments(todas));
            request.getRequestDispatcher("/WEB-INF/views/listadoCitas.jsp").forward(request, response);
        } catch (SQLException exception) {
            throw new ServletException("No se pudieron cargar los datos de citas", exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String path = request.getPathInfo();
        if ("/eliminar".equals(path)) {
            try {
                dao.deleteAppointment(Integer.parseInt(request.getParameter("id_cita")));
                response.sendRedirect(request.getContextPath() + "/citas?resultado=eliminada");
            } catch (IllegalArgumentException | SQLException exception) {
                boolean todas = "1".equals(request.getParameter("todas"));
                request.setAttribute("error", "No se ha podido anular la cita.");
                request.setAttribute("todas", todas);
                try {
                    request.setAttribute("citas", dao.findAppointments(todas));
                } catch (SQLException loadException) {
                    throw new ServletException("No se pudo cargar el listado de citas", loadException);
                }
                request.getRequestDispatcher("/WEB-INF/views/listadoCitas.jsp").forward(request, response);
            }
            return;
        }
        try {
            dao.addAppointment(request.getParameter("fecha"), request.getParameter("hora"),
                    Integer.parseInt(request.getParameter("id_paciente")),
                    Integer.parseInt(request.getParameter("id_odontologo")), request.getParameter("motivo"));
            response.sendRedirect(request.getContextPath() + "/citas?resultado=creada");
        } catch (IllegalArgumentException | SQLException exception) {
            request.setAttribute("error", "No se ha podido guardar la cita. Compruebe los datos e inténtelo de nuevo.");
            try {
                request.setAttribute("pacientes", dao.findPatientsForSelect());
                request.setAttribute("odontologos", dao.findDentists());
            } catch (SQLException loadException) {
                throw new ServletException("No se pudieron cargar los datos del formulario", loadException);
            }
            request.getRequestDispatcher("/WEB-INF/views/formularioCita.jsp").forward(request, response);
        }
    }
}