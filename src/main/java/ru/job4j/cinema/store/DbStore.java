package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.AccountCinema;
import ru.job4j.cinema.model.TicketCinema;

public class DbStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbStore.class.getSimpleName());
    private final BasicDataSource pool = new BasicDataSource();

    private DbStore() {
        var fileProperties = "db.properties";
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(new InputStreamReader(
                DbStore.class.getClassLoader().getResourceAsStream(fileProperties)))) {
            cfg.load(io);
        } catch (Exception e) {
            LOGGER.error("Не удалось выполнить операцию");
            throw new IllegalStateException(e);
        }

        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            LOGGER.error("Не удалось выполнить операцию");
            throw new IllegalStateException(e);
        }

        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final DbStore INST = new DbStore();
    }

    public static DbStore instOf() {
        return Lazy.INST;
    }

    public int regAccount(String username, String email, String phone) {
            var req = "INSERT INTO account(username, email, phone) VALUES (?, ?, ?)";
            try (Connection cn = pool.getConnection();
                 PreparedStatement ps = cn.prepareStatement(req, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.execute();
                try (ResultSet id = ps.getGeneratedKeys()) {
                    if (id.next()) {
                        return id.getInt(1);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Не удалось выполнить операцию");
                e.printStackTrace();
            }
            return -1;
    }

    public AccountCinema findAccountByEmail(String email) {
        var req = "SELECT * FROM account WHERE email = ?";
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(req)) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    AccountCinema accountCinema = new AccountCinema(it.getInt("id"), it.getString("username"),
                            it.getString("email"), it.getString("phone"));
                    return accountCinema;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Не удалось выполнить операцию");
            e.printStackTrace();
        }
        return null;
    }

    public AccountCinema findAccountByPhone(String phone) {
        var req = "SELECT * FROM account WHERE phone = ?";
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(req)) {
            ps.setString(1, phone);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    AccountCinema accountCinema = new AccountCinema(it.getInt("id"), it.getString("username"),
                            it.getString("email"), it.getString("phone"));
                    return accountCinema;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Не удалось выполнить операцию");
            e.printStackTrace();
        }
        return null;
    }

    public int regTicket(int sessionId, int row, int cell, int accountId) {
        var req = "INSERT INTO ticket(session_id, row, cell, account_id) VALUES (?, ?, ?, ?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(req, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, sessionId);
            ps.setInt(2, row);
            ps.setInt(3, cell);
            ps.setInt(4, accountId);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    return id.getInt(1);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Не удалось выполнить операцию");
            e.printStackTrace();
        }
        return -1;
    }

    public List<TicketCinema> listTickets(int sessionId) {
        List<TicketCinema> lst = new ArrayList<>();
        var query = "SELECT * FROM ticket WHERE session_id = ? ORDER BY id";
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    lst.add(new TicketCinema(it.getInt("id"), it.getInt("session_id"),
                            it.getInt("row"), it.getInt("cell"), it.getInt("account_id")));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Не удалось выполнить операцию");
            throw new IllegalStateException(e);
        }
        return lst;
    }

    public Boolean findTicket(int sessionId, int row, int cell) {
        var query = "SELECT count(*) FROM ticket WHERE session_id = ? and row = ? and cell = ?";
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ps.setInt(2, row);
            ps.setInt(3, cell);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    return it.getInt("count") > 0;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Не удалось выполнить операцию");
            throw new IllegalStateException(e);
        }
        return false;
    }
}