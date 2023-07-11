package taxi.service;

import org.junit.jupiter.api.Test;
import taxi.exception.DataProcessingException;
import taxi.lib.Injector;
import taxi.model.Driver;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DriverServiceImplTest {
    Injector injector = Injector.getInstance("taxi");
    DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);

    @Test
    void crate_validDriver_ok() {
        Driver expected = new Driver("Bob",
                "1234", "bob@gmail.com", "123");
        Driver actual = driverService.create(expected);
        Long expectedId = expected.getId();
        Long actualId = actual.getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    void create_driverWithNullName_notOk() {
        Driver driver = new Driver(null,
                "1234", "bob@gmail.com", "123");
        assertThrows(DataProcessingException.class, () -> driverService.create(driver));
    }

    @Test
    void create_driverWithNullLicense_notOk() {
        Driver driver = new Driver("Bob",
                null, "bob@gmail.com", "123");
        assertThrows(DataProcessingException.class, () -> driverService.create(driver));
    }

    @Test
    void create_driverWithNullLogin_notOk() {
        Driver driver = new Driver("Bob",
                "1234", null, "123");
        assertThrows(DataProcessingException.class, () -> driverService.create(driver));
    }

    @Test
    void create_driverWithNullPassword_notOk() {
        Driver driver = new Driver("Bob",
                "1234", "bob@gmail.com", null);
        assertThrows(DataProcessingException.class, () -> driverService.create(driver));
    }

    @Test
    void update_validDriver_ok() {
        Driver bob = new Driver("Bob",
                "1234", "bob@gmail.com", "123");
        Driver bobDriver = driverService.create(bob);
        Long bobId = bobDriver.getId();
        Driver newBob = new Driver("Bob",
                "1235", "bob123@gmail.com", "1234");
        newBob.setId(bobId);
        Driver newBoboDriver = driverService.update(newBob);
        assertEquals(newBob, newBoboDriver);
    }
    @Test
    void update_driverWithNullName_notOk() {
        Driver bob = new Driver("Bob",
                "1234", "bob@gmail.com", "123");
        Driver bobDriver = driverService.create(bob);
        Long bobId = bobDriver.getId();
        Driver newBob = new Driver(null,
                "1235", "bob123@gmail.com", "1234");
        newBob.setId(bobId);
        assertThrows(DataProcessingException.class, () -> driverService.update(newBob));
    }

    @Test
    void update_driverWithNullLicense_notOk() {
        Driver bob = new Driver("Bob",
                "1234", "bob@gmail.com", "123");
        Driver bobDriver = driverService.create(bob);
        Long bobId = bobDriver.getId();
        Driver newBob = new Driver("Bob",
                null, "bob123@gmail.com", "1234");
        newBob.setId(bobId);
        assertThrows(DataProcessingException.class, () -> driverService.update(newBob));
    }

    @Test
    void update_driverWithNullLogin_notOk() {
        Driver bob = new Driver("Bob",
                "1234", "bob@gmail.com", "123");
        Driver bobDriver = driverService.create(bob);
        Long bobId = bobDriver.getId();
        Driver newBob = new Driver("Bob",
                "1235", null, "1234");
        newBob.setId(bobId);
        assertThrows(DataProcessingException.class, () -> driverService.update(newBob));
    }

    @Test
    void update_driverWithNullPassword_notOk() {
        Driver bob = new Driver("Bob",
                "1234", "bob@gmail.com", "123");
        Driver bobDriver = driverService.create(bob);
        Long bobId = bobDriver.getId();
        Driver newBob = new Driver("Bob",
                "1235", "bob123@gmail.com", null);
        newBob.setId(bobId);
        assertThrows(DataProcessingException.class, () -> driverService.update(newBob));
    }

    @Test
    void update_driverWithInvalidId_notOk() {
        Driver driver = new Driver("Victoria", "333", "vic@gmail.com", "456");
        driver.setId(-1L);
        assertThrows(NoSuchElementException.class, () -> driverService.update(driver));
    }

    @Test
    void get_driverByValidId_ok() {
        Driver driver = new Driver("Victoria", "333", "vic@gmail.com", "456");
        Driver expected = driverService.create(driver);
        Long driverId = expected.getId();
        Driver actual = driverService.get(driverId);
        assertEquals(expected, actual);
    }

    @Test
    void get_driverByInvalidId_notOk() {
        Long badId = -1L;
        assertThrows(NoSuchElementException.class,
                () -> driverService.get(badId));
    }

    @Test
    void get_deletedDriver_notOk() {
        Driver andriy = new Driver("Andriy", "111", "and@gmail.com", "678");
        Driver andriyDriver = driverService.create(andriy);
        Long andriyId = andriyDriver.getId();
        driverService.delete(andriyId);
        assertThrows(NoSuchElementException.class,
                () -> driverService.get(andriyId));
    }

    @Test
    void get_allDrivers_ok() {
        Driver andriy = new Driver("Andriy", "111", "and@gmail.com", "678");
        Driver victoria = new Driver("Victoria", "333", "vic@gmail.com", "456");
        driverService.create(andriy);
        driverService.create(victoria);
        List<Driver> drivers = driverService.getAll();
        assertTrue(drivers.contains(andriy) && drivers.contains(victoria));
    }
}