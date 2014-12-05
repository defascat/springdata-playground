package org.andy.springdata.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author andy
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByName(String name);
    List<Account> findByNameLike(String name);
}
