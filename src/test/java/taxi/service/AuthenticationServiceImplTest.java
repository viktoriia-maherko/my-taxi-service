package taxi.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import taxi.exception.AuthenticationException;
import taxi.lib.Injector;
import taxi.model.Driver;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceImplTest {
    private static final Injector injector = Injector.getInstance("taxi");
    private static final AuthenticationService authenticationService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);
    private static final DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);
    private static Driver victoriaDriver;

    @BeforeAll
    static void beforeAll() {
        Driver victoria = new Driver("Victoria", "12345", "victoria@gmail.com", "123");
        victoriaDriver = driverService.create(victoria);
    }

    @Test
    void correctData_ok() {
        String login = "victoria@gmail.com";
        String password = "123";
        Driver testDriver = new Driver();
        try {
            testDriver = authenticationService.login(login, password);
        } catch (AuthenticationException e) {
            fail("Can't login driver");
        }
        assertEquals(victoriaDriver.getLogin(), testDriver.getLogin());
    }

    @Test
    void incorrectLogin_notOk() {
        String login = "andriy@gmail.com";
        String password = "123";
        assertThrows(AuthenticationException.class, () -> authenticationService.login(login, password));
    }

        @Test
        void incorrectPassword_notOk() {
            String login = "victoria@gmail.com";
            String password = "321";
            assertThrows(AuthenticationException.class, () -> authenticationService.login(login, password));
        }

    @Test
    void nullLogin_notOk() {
        String login = null;
        String password = "321";
        assertThrows(AuthenticationException.class, () -> authenticationService.login(login, password));
    }

    @Test
    void nullPassword_notOk() {
        String login = "victoria@gmail.com\"";
        String password = null;
        assertThrows(AuthenticationException.class, () -> authenticationService.login(login, password));
    }
}