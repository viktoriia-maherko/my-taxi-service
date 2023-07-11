package taxi.service;

import org.junit.jupiter.api.Test;
import taxi.lib.Injector;
import taxi.model.Manufacturer;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerServiceImplTest {
    Injector injector = Injector.getInstance("taxi");
    ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Test
    void create_manufacturer_ok() {
        Manufacturer audi = new Manufacturer("Audi", "German");
        Manufacturer audiManufacturer = manufacturerService.create(audi);
        Long audiId = audiManufacturer.getId();
        audiManufacturer.setId(audiId);
        assertEquals(audi, audiManufacturer);
    }

    @Test
    void get_manufacturerById_ok() {
        Manufacturer mazda = new Manufacturer("Mazda", "Japan");
        Manufacturer expected = manufacturerService.create(mazda);
        Long id = mazda.getId();
        Manufacturer actual = manufacturerService.get(id);
        assertEquals(expected, actual);
    }

    @Test
    void get_manufacturerByInvalidId_notOk() {
        Manufacturer mazda = new Manufacturer("Mazda", "Japan");
        mazda.setId(-1L);
        assertThrows(NoSuchElementException.class,
                () -> {manufacturerService.get(mazda.getId());});
    }

    @Test
    void getAll_manufacturers_ok() {
        Manufacturer mazda = new Manufacturer("Mazda", "Japan");
        Manufacturer audi = new Manufacturer("Audi", "German");
        Manufacturer mazdaManufacturer = manufacturerService.create(mazda);
        Manufacturer audiManufacturer = manufacturerService.create(audi);
        List<Manufacturer> manufacturers = manufacturerService.getAll();
        assertTrue(manufacturers.contains(audiManufacturer)
                && manufacturers.contains(mazdaManufacturer));
    }

    @Test
    void update_manufacturer_ok() {
        Manufacturer mazda = new Manufacturer("Mazda", "Japan");
        Manufacturer mazdaManufacturer = manufacturerService.create(mazda);
        Long id = mazdaManufacturer.getId();
        Manufacturer expected = new Manufacturer("Mazda", "Korea");
        expected.setId(id);
        Manufacturer actual = manufacturerService.update(expected);
        assertEquals(expected, actual);
    }

    @Test
    void update_manufacturerInvalidId_notOk() {
        Manufacturer audi = new Manufacturer("Audi", "German");
        Long id = -1L;
        audi.setId(id);
        manufacturerService.update(audi);
        assertThrows(NoSuchElementException.class, () -> {manufacturerService.get(id);});
    }

    @Test
    void delete_manufacturer_ok() {
        Manufacturer audi = new Manufacturer("Audi", "German");
        Manufacturer audiManufacturer = manufacturerService.create(audi);
        Long id = audiManufacturer.getId();
        assertTrue(manufacturerService.delete(id));
    }

    @Test
    void delete_manufacturerInvalidId_notOk() {
        Long id = -1L;
        assertFalse(manufacturerService.delete(id));
    }
}