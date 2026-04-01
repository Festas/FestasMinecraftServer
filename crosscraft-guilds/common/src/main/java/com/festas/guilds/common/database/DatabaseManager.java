package com.festas.guilds.common.database;

import com.festas.guilds.common.config.GuildConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Verwaltet den HikariCP Connection-Pool zur MySQL-Datenbank.
 */
public class DatabaseManager {

    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);

    private HikariDataSource dataSource;
    private final GuildConfig config;

    public DatabaseManager(GuildConfig config) {
        this.config = config;
    }

    /** Initialisiert den Connection-Pool und erstellt das Schema falls nötig. */
    public void initialize() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + config.getDatabaseHost() + ":" +
                config.getDatabasePort() + "/" + config.getDatabaseName() +
                "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        hikariConfig.setUsername(config.getDatabaseUsername());
        hikariConfig.setPassword(config.getDatabasePassword());
        hikariConfig.setMaximumPoolSize(config.getDatabasePoolSize());
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setConnectionTimeout(30_000);
        hikariConfig.setIdleTimeout(600_000);
        hikariConfig.setMaxLifetime(1_800_000);
        hikariConfig.setPoolName("CrossCraftGuilds-Pool");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(hikariConfig);
        log.info("DatabaseManager initialisiert - verbunden mit {}:{}/{}",
                config.getDatabaseHost(), config.getDatabasePort(), config.getDatabaseName());

        runSchemaSetup();
    }

    private void runSchemaSetup() {
        try (Connection conn = getConnection()) {
            // Schema wird über schema.sql ausgeführt
            var stream = getClass().getClassLoader().getResourceAsStream("schema.sql");
            if (stream == null) {
                log.warn("schema.sql nicht gefunden - Schema wird nicht automatisch erstellt");
                return;
            }
            String sql = new String(stream.readAllBytes());
            // Statements aufteilen und einzeln ausführen
            for (String statement : sql.split(";")) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    try (var stmt = conn.prepareStatement(trimmed)) {
                        stmt.execute();
                    }
                }
            }
            log.info("Datenbankschema erfolgreich initialisiert");
        } catch (Exception e) {
            log.error("Fehler beim Initialisieren des Datenbankschemas", e);
            throw new RuntimeException("Schema-Setup fehlgeschlagen", e);
        }
    }

    /**
     * Gibt eine Datenbankverbindung aus dem Pool zurück.
     * Muss nach Verwendung via try-with-resources geschlossen werden.
     */
    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            throw new SQLException("DataSource ist nicht initialisiert oder bereits geschlossen");
        }
        return dataSource.getConnection();
    }

    /** Schließt den Connection-Pool. */
    public void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            log.info("DatabaseManager heruntergefahren");
        }
    }

    public boolean isConnected() {
        return dataSource != null && !dataSource.isClosed();
    }
}
