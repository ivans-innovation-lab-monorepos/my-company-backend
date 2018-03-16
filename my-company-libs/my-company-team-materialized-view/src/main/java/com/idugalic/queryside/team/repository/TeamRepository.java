package com.idugalic.queryside.team.repository;

import org.springframework.data.repository.NoRepositoryBean;

/**
 * A JPA repository interface.
 */
@NoRepositoryBean
public interface TeamRepository extends ReadOnlyPagingAndSortingRepository {
}
