package com.idugalic.commandside.team.aggregate;

import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.idugalic.commandside.project.command.FindProjectCommand;
import com.idugalic.common.project.event.ProjectFoundEvent;
import com.idugalic.common.project.event.ProjectNotFoundEvent;
import com.idugalic.common.team.event.AssignProjectToTeamStartedEvent;
import static org.axonframework.eventhandling.saga.SagaLifecycle.associateWith;

/**
 * Saga for managing transaction in Team - Project aggregates relation
 * 
 * @author idugalic
 *
 */
@Saga
public class TeamProjectManagementSaga {
	
	private static final Logger LOG = LoggerFactory.getLogger(TeamProjectManagementSaga.class);

	private String projectId;
	private String teamId;

	@Autowired
	private transient CommandGateway commandGateway;

	@StartSaga
	@SagaEventHandler(associationProperty = "projectId")
	public void on(AssignProjectToTeamStartedEvent event) {
		this.projectId = event.getProjectId();
		this.teamId = event.getId();
		associateWith("id", this.projectId);
		FindProjectCommand command = new FindProjectCommand(this.projectId, event.getAuditEntry());
		commandGateway.send(command, LoggingCallback.INSTANCE);
	}
	
	@EndSaga
	@SagaEventHandler(associationProperty = "id")
	public void on(ProjectNotFoundEvent event) {
		MarkAssignProjectToTeamFailedCommand command = new MarkAssignProjectToTeamFailedCommand(event.getAuditEntry(), this.teamId, this.projectId);
		commandGateway.send(command, LoggingCallback.INSTANCE);
		
	}
	
	@EndSaga
	@SagaEventHandler(associationProperty = "id")
	public void on(ProjectFoundEvent event) {
		LOG.debug("SagaEventHandler: 'ProjectFoundEvent' received.");
		MarkAssignProjectToTeamSucceededCommand command = new MarkAssignProjectToTeamSucceededCommand(event.getAuditEntry(), this.teamId, this.projectId);
		commandGateway.send(command, LoggingCallback.INSTANCE);
	}
}
