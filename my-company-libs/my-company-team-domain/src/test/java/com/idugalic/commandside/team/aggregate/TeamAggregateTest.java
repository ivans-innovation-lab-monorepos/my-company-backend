package com.idugalic.commandside.team.aggregate;

import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.JSR303ViolationException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.idugalic.commandside.team.command.ActivateTeamCommand;
import com.idugalic.commandside.team.command.AddMemberToTeamCommand;
import com.idugalic.commandside.team.command.CreateTeamCommand;
import com.idugalic.commandside.team.command.PassivateTeamCommand;
import com.idugalic.commandside.team.command.RemoveMemberFromTeamCommand;
import com.idugalic.common.model.AuditEntry;
import com.idugalic.common.team.event.MemberAddedToTeamEvent;
import com.idugalic.common.team.event.MemberRemovedFromTeamEvent;
import com.idugalic.common.team.event.TeamActivatedEvent;
import com.idugalic.common.team.event.TeamCreatedEvent;
import com.idugalic.common.team.event.TeamPassivatedEvent;
import com.idugalic.common.team.model.TeamStatus;

/**
 * Domain (aggregate) test.
 */
public class TeamAggregateTest {

    private static final String WHO = "john";
    private FixtureConfiguration<TeamAggregate> fixture;
    private AuditEntry auditEntry;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<TeamAggregate>(TeamAggregate.class);
        fixture.registerCommandDispatchInterceptor(new BeanValidationInterceptor());
        auditEntry = new AuditEntry(WHO);
    }

    @Test
    public void createTeamTest() throws Exception {
        CreateTeamCommand command = new CreateTeamCommand(auditEntry, "name", "desc", TeamStatus.ACTIVE);
        fixture.given().when(command).expectEvents(new TeamCreatedEvent(command.getId(), command.getAuditEntry(), command.getName(), command.getDescription(), command
                .getStatus()));
    }

    @Test(expected = JSR303ViolationException.class)
    public void createTeamTestWithBadName() throws Exception {
        CreateTeamCommand command = new CreateTeamCommand(auditEntry, null, "desc", TeamStatus.ACTIVE);
        fixture.given().when(command).expectEvents(new TeamCreatedEvent(command.getId(), command.getAuditEntry(), command.getName(), command.getDescription(), command
                .getStatus()));
    }
    
    @Test
    public void activateTeamTest() throws Exception {
        ActivateTeamCommand command = new ActivateTeamCommand(auditEntry, "id");
        fixture.given(new TeamCreatedEvent(command.getId(), command.getAuditEntry(), "name", "desc", TeamStatus.PASSIVE)).when(command).expectEvents(new TeamActivatedEvent("id", auditEntry));
    }
    
    @Test
    public void passivateTeamTest() throws Exception {
        PassivateTeamCommand command = new PassivateTeamCommand(auditEntry, "id");
        fixture.given(new TeamCreatedEvent(command.getId(), command.getAuditEntry(), "name", "desc", TeamStatus.ACTIVE)).when(command).expectEvents(new TeamPassivatedEvent("id", auditEntry));
    }
    
    @Test
    public void addMemberTest() throws Exception {
    	com.idugalic.common.team.model.Member member = new com.idugalic.common.team.model.Member(WHO, null, null, 1000L);
        AddMemberToTeamCommand command = new AddMemberToTeamCommand(auditEntry, "id2", member);
        fixture.given(new TeamCreatedEvent(command.getId(), command.getAuditEntry(), "name", "desc", TeamStatus.PASSIVE)).when(command).expectEvents(new MemberAddedToTeamEvent("id2", auditEntry, member));
    }
    
    @Test
    public void removeMemberTest() throws Exception {
    	com.idugalic.common.team.model.Member member = new com.idugalic.common.team.model.Member(WHO, null, null, 1000L);
    	RemoveMemberFromTeamCommand command = new RemoveMemberFromTeamCommand(auditEntry, "id2", member);
        fixture.given(new TeamCreatedEvent(command.getId(), command.getAuditEntry(), "name", "desc", TeamStatus.PASSIVE)).when(command).expectEvents(new MemberRemovedFromTeamEvent("id2", auditEntry, member));
    }
}
