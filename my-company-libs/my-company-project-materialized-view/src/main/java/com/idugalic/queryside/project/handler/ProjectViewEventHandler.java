package com.idugalic.queryside.project.handler;

import com.idugalic.common.project.event.ProjectActivatedEvent;
import com.idugalic.common.project.event.ProjectCreatedEvent;
import com.idugalic.common.project.event.ProjectDeactivatedEvent;
import com.idugalic.common.project.event.ProjectUpdatedEvent;
import com.idugalic.queryside.project.domain.Project;
import com.idugalic.queryside.project.repository.ProjectRepository;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.SequenceNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Event handler for {@link ProjectCreatedEvent}
 * 
 * @author idugalic
 *
 */
@ProcessingGroup("default")
@Component
class ProjectViewEventHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ProjectViewEventHandler.class);

	private ProjectRepository projectRepository;

	@Autowired
	public ProjectViewEventHandler(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	@EventHandler
	public void handle(ProjectCreatedEvent event, @SequenceNumber Long version) {
		projectRepository.save(new Project(event, version));
	}

	@EventHandler
	public void handle(ProjectActivatedEvent event, @SequenceNumber Long version) {
		Project project = projectRepository.findById(event.getId()).get();
		project.setActive(Boolean.TRUE);
		project.setAggregateVersion(version);
		projectRepository.save(project);
	}

	@EventHandler
	public void handle(ProjectDeactivatedEvent event, @SequenceNumber Long version) {
		Project project = projectRepository.findById(event.getId()).get();
		project.setActive(Boolean.FALSE);
        project.setAggregateVersion(version);
        projectRepository.save(project);
	}

	@EventHandler
	public void handle(ProjectUpdatedEvent event, @SequenceNumber Long version) {
		Project project = projectRepository.findById(event.getId()).get();
		project.setDescription(event.getDescription());
		project.setName(event.getName());
		project.setRepoUrl(event.getRepoUrl());
		project.setSiteUrl(event.getSiteUrl());
        project.setAggregateVersion(version);
        projectRepository.save(project);
	}
}
