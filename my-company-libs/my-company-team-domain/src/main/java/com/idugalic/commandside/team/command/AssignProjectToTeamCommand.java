package com.idugalic.commandside.team.command;

import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.model.AuditEntry;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * A command for assigning the team to project
 * 
 * @author idugalic
 *
 */
public class AssignProjectToTeamCommand extends AuditableAbstractCommand {

	@TargetAggregateIdentifier
	private String id;
	private String projectId;
	private String reason;

	public AssignProjectToTeamCommand(AuditEntry auditEntry, String id, String projectId, String reason) {
		super(auditEntry);
		this.id = id;
		this.projectId = projectId;
		this.reason = reason;
	}

	public String getId() {
		return id;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getReason() {
		return reason;
	}

}
