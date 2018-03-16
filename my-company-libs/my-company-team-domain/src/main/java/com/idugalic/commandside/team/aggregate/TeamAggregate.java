package com.idugalic.commandside.team.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.HashSet;
import java.util.Set;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idugalic.commandside.team.command.ActivateTeamCommand;
import com.idugalic.commandside.team.command.AddMemberToTeamCommand;
import com.idugalic.commandside.team.command.AssignProjectToTeamCommand;
import com.idugalic.commandside.team.command.CreateTeamCommand;
import com.idugalic.commandside.team.command.PassivateTeamCommand;
import com.idugalic.commandside.team.command.RemoveMemberFromTeamCommand;
import com.idugalic.common.team.event.AssignProjectToTeamFailedEvent;
import com.idugalic.common.team.event.AssignProjectToTeamStartedEvent;
import com.idugalic.common.team.event.AssignProjectToTeamSucceededEvent;
import com.idugalic.common.team.event.MemberAddedToTeamEvent;
import com.idugalic.common.team.event.MemberRemovedFromTeamEvent;
import com.idugalic.common.team.event.TeamActivatedEvent;
import com.idugalic.common.team.event.TeamCreatedEvent;
import com.idugalic.common.team.event.TeamPassivatedEvent;
import com.idugalic.common.team.model.TeamStatus;

/**
 * Team aggregate root
 */
@Aggregate
class TeamAggregate {

	private static final Logger LOG = LoggerFactory.getLogger(TeamAggregate.class);

	/**
	 * Aggregates that are managed by Axon must have a unique identifier.
	 * Strategies similar to GUID are often used. The annotation
	 * 'AggregateIdentifier' identifies the id field as such.
	 */
	@AggregateIdentifier
	private String id;
	private String name;
	private String description;
	private TeamStatus status = TeamStatus.PASSIVE;
	private Project project;
	private Set<Member> members = new HashSet<Member>();

	/**
	 * Default constructor
	 */
	public TeamAggregate() {
	}

	/**
	 * This constructor is marked as a 'CommandHandler' for the
	 * CreateTeamCommand. This command can be used to construct new instances of
	 * the Aggregate. If successful a new TeamAggregate is 'applied' to the
	 * aggregate using the Axon 'apply' method. The apply method appears to also
	 * propagate the Event to any other registered 'Event Listeners', who may
	 * take further action.
	 *
	 * @param command
	 */
	@CommandHandler
	public TeamAggregate(CreateTeamCommand command) {
		apply(new TeamCreatedEvent(command.getId(), command.getAuditEntry(), command.getName(),
				command.getDescription(), command.getStatus()));
	}

	/**
	 * This method is marked as an EventSourcingHandler and is therefore used by
	 * the Axon framework to handle events of the specified type
	 * (TeamCreatedEvent). The TeamCreatedEvent can be raised either by the
	 * constructor during Team(CreateTeamCommand) or by the Repository when
	 * 're-loading' the aggregate.
	 *
	 * @param event
	 */
	@EventSourcingHandler
	public void on(TeamCreatedEvent event) {
		this.id = event.getId();
		this.name = event.getName();
		this.description = event.getDescription();
		this.status = event.getStatus();
		LOG.debug("Event Applied: 'TeamCreatedEvent' [{}]", event.getId());
	}

	@CommandHandler
	public void assignTeamToProject(AssignProjectToTeamCommand command) {
		apply(new AssignProjectToTeamStartedEvent(id, command.getAuditEntry(), command.getProjectId()));
	}

	@CommandHandler
	public void assignTeamToProjectFailed(MarkAssignProjectToTeamFailedCommand command) {
		apply(new AssignProjectToTeamFailedEvent(id, command.getAuditEntry(), command.getProjectId()));
	}

	@EventSourcingHandler
	public void on(AssignProjectToTeamFailedEvent event) {
		this.project = new Project(event.getProjectId(), Status.FAILED);
	}

	@CommandHandler
	public void assignTeamToProjectSuccess(MarkAssignProjectToTeamSucceededCommand command) {
		apply(new AssignProjectToTeamSucceededEvent(id, command.getAuditEntry(), command.getProjectId()));
	}

	@EventSourcingHandler
	public void on(AssignProjectToTeamSucceededEvent event) {
		this.project = new Project(event.getProjectId(), Status.ASSIGNED);
	}

	@CommandHandler
	public void addMemeberToTeam(AddMemberToTeamCommand command) {
		apply(new MemberAddedToTeamEvent(command.getId(), command.getAuditEntry(), command.getMember()));
	}
	
	@EventSourcingHandler
	public void on(MemberAddedToTeamEvent event) {
		Member newMember = new Member(event.getMember().getUserId(), event.getMember().getStartDate(), event.getMember().getEndDate(), event.getMember().getWeeklyHours());
		this.members.add(newMember);
	}
	
	@CommandHandler
	public void removeMemeberFromTeam(RemoveMemberFromTeamCommand command) {
		apply(new MemberRemovedFromTeamEvent(command.getId(), command.getAuditEntry(), command.getMember()));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@EventSourcingHandler
	public void on(MemberRemovedFromTeamEvent event) {
		this.members.remove(event.getMember());
	}
	
	@CommandHandler
	public void activateTeam(ActivateTeamCommand command) {
		apply(new TeamActivatedEvent(command.getId(), command.getAuditEntry()));
	}
	
	@EventSourcingHandler
	public void on(TeamActivatedEvent event) {
		this.status = TeamStatus.ACTIVE;
	}

	@CommandHandler
	public void passivateTeam(PassivateTeamCommand command) {
		apply(new TeamPassivatedEvent(command.getId(), command.getAuditEntry()));
	}
	
	@EventSourcingHandler
	public void on(TeamPassivatedEvent event) {
		this.status = TeamStatus.PASSIVE;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public TeamStatus getStatus() {
		return status;
	}

	public Project getProject() {
		return project;
	}

	public Set<Member> getMembers() {
		return members;
	}

}
