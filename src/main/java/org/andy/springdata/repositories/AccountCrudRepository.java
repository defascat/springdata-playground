package org.andy.springdata.repositories;

import java.util.List;
import org.andy.springdata.domain.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author andy
 */
public interface AccountCrudRepository extends PagingAndSortingRepository<Account, Long> {
    Account findByName(String name);
    List<Account> findByNameLike(String name);
}
