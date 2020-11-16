package cz.czechitas.webapp;

import java.util.*;

public interface InterfaceRepository {

    List<Contact> findAll();

    Contact findById(Long id);

    void save(Contact recordToSave);

    void deleteById(Long id);

}
