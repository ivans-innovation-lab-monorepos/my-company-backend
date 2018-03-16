package com.idugalic.commandside.project.aggregate;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;

import com.idugalic.commandside.project.command.FindProjectCommand;
import com.idugalic.common.project.event.ProjectFoundEvent;
import com.idugalic.common.project.event.ProjectNotFoundEvent;


class ProjectCommandHandler {
	
	private Repository<ProjectAggregate> repository;
	private EventBus eventBus;
	
	public ProjectCommandHandler(Repository<ProjectAggregate> repository, EventBus eventBus) {
		super();
		this.repository = repository;
		this.eventBus = eventBus;
	}
	
	@CommandHandler
	public void handle (FindProjectCommand command){
		try {
			@SuppressWarnings("unused")
			Aggregate<ProjectAggregate> projectAggregate = repository.load(command.getId());
			eventBus.publish(asEventMessage(new ProjectFoundEvent(command.getId(), command.getAuditEntry())));
		} catch (AggregateNotFoundException e) {
			eventBus.publish(asEventMessage(new ProjectNotFoundEvent(command.getId(), command.getAuditEntry())));
		}
		
	}

}
