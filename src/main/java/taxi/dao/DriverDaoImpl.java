package taxi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import taxi.exception.DataProcessingException;
import taxi.lib.Dao;
import taxi.model.Driver;
import taxi.util.ConnectionUtil;

@Dao
public class DriverDaoImpl implements DriverDao {
    private static final Logger logger = LogManager.getLogger("taxi");

    @Override
    public Driver create(Driver driver) {
        logger.info("Method create in DriverDao was called. Params: driver {}", driver);
        String query = "INSERT INTO drivers (name, license_number, login, password) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenseNumber());
            statement.setString(3, driver.getLogin());
            statement.setString(4, driver.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class));
            }
            return driver;
        } catch (SQLException e) {
            logger.error("Can't create diver in DriverDao. Params: driver {} ", driver, e);
            throw new DataProcessingException("Can't create driver " + driver, e);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        logger.info("Method get in DriverDao was called. Params: driver id {}", id);
        String query = "SELECT * FROM drivers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Driver driver = null;
            if (resultSet.next()) {
                driver = parseDriverFromResultSet(resultSet);
            }
            return Optional.ofNullable(driver);
        } catch (SQLException e) {
            logger.error("Can't get driver in DriverDao. Params: driver id {}", id, e);
            throw new DataProcessingException("Can't get driver by id " + id, e);
        }
    }

    @Override
    public List<Driver> getAll() {
        logger.info("Method getAll in DriverDao was called.");
        String query = "SELECT * FROM drivers WHERE is_deleted = FALSE";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                drivers.add(parseDriverFromResultSet(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            logger.error("Can't get all drivers in DriverDao", e);
            throw new DataProcessingException("Can't get a list of drivers.", e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        logger.info("Method update in DriverDao was called. Params: driver {}", driver);
        get(driver.getId()).orElseThrow(() ->
                new NoSuchElementException("Can't find a driver by id " + driver.getId()));
        String query = "UPDATE drivers "
                + "SET name = ?, license_number = ?, login = ?, password = ? "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement statement
                        = connection.prepareStatement(query)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenseNumber());
            statement.setString(3, driver.getLogin());
            statement.setString(4, driver.getPassword());
            statement.setLong(5, driver.getId());
            statement.executeUpdate();
            return driver;
        } catch (SQLException e) {
            logger.error("Can't update driver in DriverDao. Params: driver {}", driver, e);
            throw new DataProcessingException("Can't update driver" + driver, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Method delete in DriverDao was called. Params: driver id {}", id);
        String query = "UPDATE drivers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Can't delete driver in Driver Dao. Params: driver id {}", id, e);
            throw new DataProcessingException("Can't delete driver with id " + id, e);
        }
    }

    @Override
    public Optional<Driver> findByLogin(String login) {
        logger.info("Method findByLogin in DriverDao was called. Params: login {}", login);
        String query = "SELECT * from drivers WHERE login = ? AND is_deleted = FAlSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            Driver driver = null;
            if (resultSet.next()) {
                driver = parseDriverFromResultSet(resultSet);
            }
            return Optional.ofNullable(driver);
        } catch (SQLException e) {
            logger.error("Can't find driver by login in DriverDao. Params: login {}", login, e);
            throw new DataProcessingException("Can't get driver by login " + login, e);
        }
    }

    private Driver parseDriverFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String licenseNumber = resultSet.getString("license_number");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        Driver driver = new Driver();
        driver.setId(id);
        driver.setName(name);
        driver.setLicenseNumber(licenseNumber);
        driver.setLogin(login);
        driver.setPassword(password);
        return driver;
    }
}
