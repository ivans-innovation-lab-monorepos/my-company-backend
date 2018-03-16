package com.idugalic.queryside.project.repository;

import org.springframework.data.repository.NoRepositoryBean;

import com.idugalic.queryside.project.domain.Project;

/**
 * A JPA repository interface for {@link Project}.
 * 
 * @author idugalic
 *
 */
@NoRepositoryBean
public interface ProjectRepository extends ReadOnlyPagingAndSortingRepository {
}
