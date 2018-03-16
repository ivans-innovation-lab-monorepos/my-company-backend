package com.idugalic.queryside.team.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import com.idugalic.queryside.team.domain.Team;

/**
 * A read only repository interface - save and delete operations will not be exported as a resource
 *
 */
@NoRepositoryBean
public interface ReadOnlyPagingAndSortingRepository extends PagingAndSortingRepository<Team, String> {

    @Override
    @SuppressWarnings("unchecked")
    @RestResource(exported = false)
    Team save(Team entity);

    @Override
    @RestResource(exported = false)
    void deleteById(String aLong);

    @Override
    @RestResource(exported = false)
    void delete(Team entity);
}
