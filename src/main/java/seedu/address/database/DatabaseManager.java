package seedu.address.database;

import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;

/**
 * Manages reading of module information from database file.
 */
public class DatabaseManager implements Database {

    private static final Logger logger = LogsCenter.getLogger(DatabaseManager.class);
    private String filePath = "database/moduleinfo.json";

    @Override
    public String getDatabaseFilePath() {
        return filePath;
    }

    @Override
    public DbModuleList readDatabase() throws DataLoadingException {
        return readDatabase(this.filePath);
    }

    @Override
    public DbModuleList readDatabase(String filePath) throws DataLoadingException {
        requireNonNull(filePath);
        logger.fine("Attempting to parse module information: " + filePath);

        Optional<JsonAdaptedDbModuleList> jsonDatabaseOptional = JsonUtil.readJsonResource(
                "database/moduleinfo.json", JsonAdaptedDbModuleList.class);

        try {
            return jsonDatabaseOptional.get().toDbModuleList();
        } catch (NoSuchElementException nsee) {
            throw new DataLoadingException(nsee);
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }
}