package com.idugalic.commandside.team.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.model.AuditEntry;
import com.idugalic.common.team.model.Member;

/**
 * A command for adding member the the team
 * 
 * @author idugalic
 *
 */
public class AddMemberToTeamCommand extends AuditableAbstractCommand {

	@TargetAggregateIdentifier
	private String id;
	private Member member;

	public AddMemberToTeamCommand(AuditEntry auditEntry, String id, Member member) {
		super(auditEntry);
		this.id = id;
		this.member = member;
	}

	public String getId() {
		return id;
	}

	public Member getMember() {
		return member;
	}

}
