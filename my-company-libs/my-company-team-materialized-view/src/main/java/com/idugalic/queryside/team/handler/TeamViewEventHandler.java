package com.idugalic.queryside.team.handler;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.SequenceNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idugalic.common.team.event.AssignProjectToTeamSucceededEvent;
import com.idugalic.common.team.event.MemberAddedToTeamEvent;
import com.idugalic.common.team.event.MemberRemovedFromTeamEvent;
import com.idugalic.common.team.event.TeamActivatedEvent;
import com.idugalic.common.team.event.TeamCreatedEvent;
import com.idugalic.common.team.event.TeamPassivatedEvent;
import com.idugalic.common.team.model.TeamStatus;
import com.idugalic.queryside.project.repository.ProjectRepository;
import com.idugalic.queryside.team.domain.Team;
import com.idugalic.queryside.team.repository.TeamRepository;

/**
 * Event handlers
 *
 */
@ProcessingGroup("default")
@Component
public class TeamViewEventHandler {

    @Autowired
    private TeamRepository myAggregateRepository;
    
    @Autowired
    private ProjectRepository projectRepository;

    @EventHandler
    public void handle(TeamCreatedEvent event, @SequenceNumber Long version) {
        myAggregateRepository.save(new Team(event, version));
    }
    
    @EventHandler
    public void handle(AssignProjectToTeamSucceededEvent event, @SequenceNumber Long version){
    	 Team team = myAggregateRepository.findById(event.getId()).get();
    	 team.setProject(projectRepository.findById(event.getProjectId()).get());
    	 team.setAggregateVersion(version);
		 myAggregateRepository.save(team);
    }
    
    @EventHandler
    public void handle(TeamActivatedEvent event, @SequenceNumber Long version){
    	 Team team = myAggregateRepository.findById(event.getId()).get();
    	 team.setStatus(TeamStatus.ACTIVE);
    	 team.setAggregateVersion(version);
    	 myAggregateRepository.save(team);
    }
    
    @EventHandler
    public void handle(TeamPassivatedEvent event, @SequenceNumber Long version){
    	 Team team = myAggregateRepository.findById(event.getId()).get();
    	 team.setStatus(TeamStatus.PASSIVE);
    	 team.setAggregateVersion(version);
    	 myAggregateRepository.save(team);
    }
    
    @EventHandler
    public void handle(MemberAddedToTeamEvent event, @SequenceNumber Long version){
    	 Team team = myAggregateRepository.findById(event.getId()).get();
    	 
    	 com.idugalic.queryside.team.domain.Member newMember = new com.idugalic.queryside.team.domain.Member();
    	 newMember.setEndDate(event.getMember().getEndDate());
    	 newMember.setStartDate(event.getMember().getStartDate());
    	 newMember.setTeam(team);
    	 newMember.setUserId(event.getMember().getUserId());
    	 newMember.setWeeklyHours(event.getMember().getWeeklyHours());
    	 
    	 team.getMembers().add(newMember);
    	 team.setAggregateVersion(version);
    	 myAggregateRepository.save(team);
    }
    
    @EventHandler
    public void handle(MemberRemovedFromTeamEvent event, @SequenceNumber Long version){
    	 Team team = myAggregateRepository.findById(event.getId()).get();

    	 team.getMembers().removeIf(member -> member.getUserId().equals(event.getMember().getUserId()));
    	 team.setAggregateVersion(version);
    	 myAggregateRepository.save(team);
    }
}
