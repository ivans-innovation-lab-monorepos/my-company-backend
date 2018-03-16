package com.idugalic.commandside.team.aggregate;

/**
 * NOT a public class. It will not be a part of the API. This command is only used in the scope of this 'aggregate' package.
 * 
 */
import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.model.AuditEntry;
import org.axonframework.commandhandling.TargetAggregateIdentifier;


class MarkAssignProjectToTeamFailedCommand extends AuditableAbstractCommand {

	@TargetAggregateIdentifier
	private String id;
	private String projectId;

	public MarkAssignProjectToTeamFailedCommand(AuditEntry auditEntry, String id, String projectId) {
		super(auditEntry);
		this.id = id;
		this.projectId = projectId;
	}

	public String getId() {
		return id;
	}

	public String getProjectId() {
		return projectId;
	}
}
