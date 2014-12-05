package org.andy.springdata.repositories;

import java.util.List;
import org.andy.springdata.domain.Account;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author andy
 */
public interface AccountCrudRepository extends CrudRepository<Account, Long> {
    Account findByName(String name);
    List<Account> findByNameLike(String name);
}
