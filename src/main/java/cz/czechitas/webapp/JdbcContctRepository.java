package cz.czechitas.webapp;

import java.sql.*;
import java.util.*;
import org.mariadb.jdbc.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.datasource.lookup.*;
import org.springframework.jdbc.support.*;
import org.springframework.stereotype.*;

@Repository
public class JdbcContctRepository implements InterfaceRepository {

    private MariaDbDataSource dbDataSource;
    private JdbcTemplate template;
    private RowMapper<Contact> contactRowMapper;

    public JdbcContctRepository() {
        try {
            dbDataSource = new MariaDbDataSource();
            dbDataSource.setUserName("student");
            dbDataSource.setPassword("password");
            dbDataSource.setUrl("jdbc:mysql://localhost:3306/SeznamKontaktu");

            template = new JdbcTemplate(dbDataSource);
            contactRowMapper = BeanPropertyRowMapper.newInstance(Contact.class);

        } catch (SQLException e) {
            throw new DataSourceLookupFailureException("nepodarilo se vytvorit Datasource", e);
        }
    }

    /* "select fname as first_name from customer".*/

    @Override
    public List<Contact> findAll() {
        List<Contact> contactList = template.query(
                "select ID, Jmeno as name,TelefonniCislo as phone, Email" +
                        " from Kontakt",
                contactRowMapper);
        return contactList;
    }

    @Override
    public Contact findById(Long id) {
        Contact contact = template.queryForObject(
                "select ID, Jmeno as name,TelefonniCislo as phone, Email" +
                        " from Kontakt where ID = ?",
                contactRowMapper,
                id);
        return contact;
    }

    @Override
    public void save(Contact recordToSave) {
        if (recordToSave.getId() == null) {
            add(recordToSave);
        } else {
            update(recordToSave);
        }
    }

    @Override
    public void deleteById(Long id) {
        template.update(
                "DELETE FROM Kontakt WHERE id = ?",
                id);
    }
    //------------------------------------------------------------

    private void update(Contact recordTosave) {
        template.update(
                "UPDATE Kontakt SET Jmeno = ? , TelefonniCislo = ?, Email = ? WHERE id = ?",
                recordTosave.getName(),
                recordTosave.getPhone(),
                recordTosave.getEmail(),
                recordTosave.getId());
    }

    private void add(Contact recordToSave) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sqlStatement = "INSERT INTO Kontakt (Jmeno, TelefonniCislo, Email) " +
                "VALUES (?, ?, ?)";
        template.update((Connection con) -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, recordToSave.getName());
                    preparedStatement.setString(2, recordToSave.getPhone());
                    preparedStatement.setString(3, recordToSave.getEmail());
                    return preparedStatement;
                },
                generatedKeyHolder);
        recordToSave.setId(generatedKeyHolder.getKey().longValue());

    }
}










