package taxi.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import taxi.exception.AuthenticationException;
import taxi.lib.Injector;
import taxi.model.Driver;
import taxi.service.AuthenticationService;

public class LoginController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("taxi");
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    private final AuthenticationService authenticationService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Method doGet was called");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Method doPost was called");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            Driver driver = authenticationService.login(login, password);
            HttpSession session = req.getSession();
            session.setAttribute("driver_id", driver.getId());
            resp.sendRedirect(req.getContextPath() + "/index");
        } catch (AuthenticationException e) {
            req.setAttribute("errorMsg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            logger.error("Invalid data!");
        }
    }
}
