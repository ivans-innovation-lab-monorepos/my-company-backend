package com.idugalic.commandside.team.command;

import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.model.AuditEntry;
import com.idugalic.common.team.model.TeamStatus;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * A command for creating an aggregate.
 */
public class CreateTeamCommand extends AuditableAbstractCommand {

	@TargetAggregateIdentifier
	private String id;
	@NotNull(message = "Name is mandatory")
	@NotBlank(message = "Name is mandatory")
	private String name;
	private String description;
	private TeamStatus status;

	public CreateTeamCommand(AuditEntry auditEntry, String name, String description, TeamStatus status) {
		super(auditEntry);
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.description = description;
		this.status = status;
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

}
