package com.idugalic.common.team.event;

import com.idugalic.common.event.AuditableAbstractEvent;
import com.idugalic.common.model.AuditEntry;
import com.idugalic.common.team.model.TeamStatus;

public class TeamCreatedEvent extends AuditableAbstractEvent {

    private static final long serialVersionUID = 1594382475023480978L;

    private String name;
    private String description;
    private TeamStatus status;

    public TeamCreatedEvent() {
    }

    public TeamCreatedEvent(String id, AuditEntry auditEntry, String name, String description, TeamStatus status) {
        super(id, auditEntry);
        this.name = name;
        this.description = description;
        this.status = status;
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
