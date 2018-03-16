package com.idugalic.commandside.team.aggregate;

import org.axonframework.test.saga.FixtureConfiguration;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import com.idugalic.commandside.project.command.FindProjectCommand;
import com.idugalic.common.model.AuditEntry;
import com.idugalic.common.project.event.ProjectFoundEvent;
import com.idugalic.common.project.event.ProjectNotFoundEvent;
import com.idugalic.common.team.event.AssignProjectToTeamStartedEvent;

public class TeamProjectManagementSagaTest {
	private FixtureConfiguration testFixture;
	private static final String WHO = "john";
	private AuditEntry auditEntry;

	@Before
	public void setUp() throws Exception {
		testFixture = new SagaTestFixture<>(TeamProjectManagementSaga.class);
		auditEntry = new AuditEntry(WHO);
	}

	@Test
	public void testAssigneTeamToProject() throws Exception {
		String projectId = "projectId";
		String teamId = "teamId";

		testFixture.givenNoPriorActivity()
		           .whenAggregate(teamId)
				   .publishes(new AssignProjectToTeamStartedEvent(teamId, auditEntry, projectId))
				   .expectActiveSagas(1)
				   .expectDispatchedCommands(new FindProjectCommand(projectId, auditEntry));
	}
	
	@Test
	public void testAssigneProjectToTeamSuccess() throws Exception {
		String projectId = "projectId";
		String teamId = "teamId";

		testFixture.givenAggregate(teamId)
		           .published(new AssignProjectToTeamStartedEvent (teamId, auditEntry, projectId))
		           .whenPublishingA(new ProjectFoundEvent(projectId, auditEntry))
		           .expectActiveSagas(0)
		           .expectDispatchedCommands(new MarkAssignProjectToTeamSucceededCommand(auditEntry,teamId, projectId));
	}
	
	@Test
	public void testAssigneProjectToTeamFailed() throws Exception {
		String projectId = "projectId";
		String teamId = "teamId";

		testFixture.givenAggregate(teamId)
		           .published(new AssignProjectToTeamStartedEvent (teamId, auditEntry, projectId))
		           .whenPublishingA(new ProjectNotFoundEvent(projectId, auditEntry))
		           .expectActiveSagas(0)
		           .expectDispatchedCommands(new MarkAssignProjectToTeamFailedCommand(auditEntry,teamId, projectId));
	}

}
