package com.idugalic.commandside.team.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.model.AuditEntry;

/**
 * A command for passivating/deactivating the team
 * 
 * @author idugalic
 *
 */
public class PassivateTeamCommand extends AuditableAbstractCommand {

	@TargetAggregateIdentifier
	private String id;

	public PassivateTeamCommand(AuditEntry auditEntry, String id) {
		super(auditEntry);
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
