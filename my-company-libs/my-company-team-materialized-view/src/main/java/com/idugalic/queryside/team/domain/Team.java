package com.idugalic.queryside.team.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.idugalic.common.team.event.TeamCreatedEvent;
import com.idugalic.common.team.model.TeamStatus;
import com.idugalic.queryside.project.domain.Project;

/**
 * A JPA entity. It represents materialized view of TeamAggregate
 */
@Entity
public class Team {

	@Id
	private String id;
	@Version
	private Long version;
	private Long aggregateVersion;
	private String name;
	private String description;
	private TeamStatus status;
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Member> members = new HashSet<Member>();

	public Team() {
	}

	public Team(String id, Long aggregateVersion, String name, String description, TeamStatus status) {
		super();
		this.id = id;
		this.aggregateVersion = aggregateVersion;
		this.name = name;
		this.description = description;
		this.status = status;
	}

	public Team(TeamCreatedEvent event, Long aggregateVersion) {
		super();
		this.id = event.getId();
		this.aggregateVersion = aggregateVersion;
		this.name = event.getName();
		this.description = event.getDescription();
		this.status = event.getStatus();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return aggregateVersion;
	}

	public void setVersion(Long version) {
		this.aggregateVersion = version;
	}

	public Long getAggregateVersion() {
		return aggregateVersion;
	}

	public void setAggregateVersion(Long aggregateVersion) {
		this.aggregateVersion = aggregateVersion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TeamStatus getStatus() {
		return status;
	}

	public void setStatus(TeamStatus status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<Member> getMembers() {
		return members;
	}

}
