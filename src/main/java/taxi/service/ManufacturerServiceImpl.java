package taxi.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import taxi.dao.ManufacturerDao;
import taxi.lib.Inject;
import taxi.lib.Service;
import taxi.model.Manufacturer;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private static final Logger logger = LogManager.getLogger("taxi");
    @Inject
    private ManufacturerDao manufacturerDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        logger.info("Method create in ManufacturerService was called. "
                + "Params: manufacturer {}", manufacturer);
        return manufacturerDao.create(manufacturer);
    }

    @Override
    public Manufacturer get(Long id) {
        logger.info("Method get in ManufacturerService was called. Params: manufacturer id {}", id);
        return manufacturerDao.get(id).orElseThrow(() ->
            new NoSuchElementException("Can't get manufacturer by id: " + id)
        );
    }

    @Override
    public List<Manufacturer> getAll() {
        logger.info("Method getAll in ManufacturerService was called.");
        return manufacturerDao.getAll();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        logger.info("Method update in ManufacturerService was called. "
                + "Params: manufacturer {}", manufacturer);
        return manufacturerDao.update(manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Method delete in ManufacturerService was called. "
                + "Params: manufacturer id {}", id);
        return manufacturerDao.delete(id);
    }
}
