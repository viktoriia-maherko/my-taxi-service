package taxi.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import taxi.exception.DataProcessingException;
import taxi.lib.Injector;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceImplTest {
    private static final Injector injector = Injector.getInstance("taxi");
    private static final CarService carService
            = (CarService) injector.getInstance(CarService.class);
    private static final DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);
    private static final ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);
    private static Driver victoria;
    private static Driver andriy;
    private static Driver bob;
    private static Driver alice;
    private static Manufacturer mazda;
    private static Manufacturer audi;
    private static Manufacturer subaru;

    @BeforeAll
    static void beforeAll() {
        victoria = driverService.create(new Driver("Victoria",
                "1111", "victoria@gmail.com", "123"));
        andriy = driverService.create(new Driver("Andriy",
                "1112", "andriy@gmail.com", "132"));
        bob = driverService.create(new Driver("Bob",
                "1113", "bob@gmail.com", "321"));
        alice = driverService.create(new Driver("Alice",
                "1114", "alice@gmail.com", "332"));
        mazda = manufacturerService.create(new Manufacturer("Mazda", "Japan"));
        audi = manufacturerService.create(new Manufacturer("Audi", "German"));
        subaru = manufacturerService.create(new Manufacturer("Subaru", "Japan"));
    }


    @Test
    void addDriverToCar_carDriver_ok() {
        Car mazdaCx = new Car("Mazda CX-5", mazda);
        Car mazdaCar = carService.create(mazdaCx);
        carService.addDriverToCar(victoria, mazdaCar);
        carService.addDriverToCar(andriy, mazdaCar);
        assertEquals(2, mazdaCar.getDrivers().size());
    }

    @Test
    void addDriverToCar_invalidId_notOk() {
        Car mazdaCx = new Car("Mazda CX-5", mazda);
        Long badId = -1L;
        mazdaCx.setId(badId);
        assertThrows(DataProcessingException.class, () -> carService.addDriverToCar(bob, mazdaCx));
    }

    @Test
    void removeDriverFromCar_carDriver_ok() {
        Car audiA5 = new Car("Audi", audi);
        Car audiCar = carService.create(audiA5);
        carService.addDriverToCar(bob, audiA5);
        carService.removeDriverFromCar(bob, audiA5);
        assertFalse(audiCar.getDrivers().contains(bob));
    }

    @Test
    void getAllByDriver_carDrivers_ok() {
        Car audiA5 = new Car("Audi", audi);
        List<Driver> driversAudi = new ArrayList<>();
        driversAudi.add(bob);
        driversAudi.add(alice);
        driversAudi.add(andriy);
        audiA5.setDrivers(driversAudi);
        Car mazdaCx = new Car("Mazda CX-5", mazda);
        List<Driver> driversMazda = new ArrayList<>();
        driversMazda.add(andriy);
        driversMazda.add(victoria);
        mazdaCx.setDrivers(driversMazda);
        carService.create(audiA5);
        carService.create(mazdaCx);
        List<Car> andriyCars = carService.getAllByDriver(andriy.getId());
        assertEquals(2, andriyCars.size());
    }

    @Test
    void crate_validCar_ok() {
        Car expected = new Car("Mazda CX-5", mazda);
        Car actual = carService.create(expected);
        assertEquals(expected, actual);
    }

    @Test
    void create_nullParameters_notOk() {
        Car car = new Car(null, null);
        assertThrows(NullPointerException.class, () -> {
            carService.create(car);
        });
    }

    @Test
    void get_carById_ok() {
        Car audiA5 = new Car("Audi", audi);
        Car expected = carService.create(audiA5);
        Long audiId = expected.getId();
        Car actual = carService.get(audiId);
        assertEquals(expected, actual);
    }

    @Test
    void get_carByInvalidId_notOk() {
        Long badId = -1L;
        assertThrows(NoSuchElementException.class, () -> carService.get(badId));
    }

    @Test
    void getAll_cars_ok() {
        Car mazdaCx = new Car("Mazda CX-5", mazda);
        Car audiA5 = new Car("Audi", audi);
        carService.create(mazdaCx);
        carService.create(audiA5);
        List<Car> cars = carService.getAll();
        assertTrue(cars.contains(mazdaCx) && cars.contains(audiA5));
    }

    @Test
    void update_car_ok() {
        Car mazdaCx = new Car("Mazda CX-5", mazda);
        Car mazdaCar = carService.create(mazdaCx);
        Long id = mazdaCar.getId();
        Car expected = new Car("Mazda CXQ-5", mazda);
        expected.setId(id);
        Car actual = carService.update(expected);
        assertEquals(expected, actual);
    }

    @Test
    void update_carByInvalidId_notOk() {
        Car mazdaCx = new Car("Mazda CX-5", mazda);
        Car mazdaCar = carService.create(mazdaCx);
        Long id = -1L;
        mazdaCar.setId(id);
        carService.update(mazdaCar);
        assertThrows(NoSuchElementException.class, () -> carService.get(id));
    }

    @Test
    void delete_car_ok() {
        Car audiA5 = new Car("Audi", audi);
        Car audiCar = carService.create(audiA5);
        Long id = audiCar.getId();
        assertTrue(carService.delete(id));
    }

    @Test
    void delete_carByInvalidId_notOk() {
        Long id = -1L;
        assertFalse(carService.delete(id));
    }
}