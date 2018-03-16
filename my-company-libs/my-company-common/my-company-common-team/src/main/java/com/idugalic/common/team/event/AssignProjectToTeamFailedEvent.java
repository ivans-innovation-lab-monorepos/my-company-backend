package com.idugalic.common.team.event;

import com.idugalic.common.event.AuditableAbstractEvent;
import com.idugalic.common.model.AuditEntry;

public class AssignProjectToTeamFailedEvent extends AuditableAbstractEvent {

    private static final long serialVersionUID = 1594380475023480978L;

    private String projectId;
   

    public AssignProjectToTeamFailedEvent() {
    }

    public AssignProjectToTeamFailedEvent(String id, AuditEntry auditEntry, String projectId) {
        super(id, auditEntry);
        this.projectId = projectId;
    }

	public String getProjectId() {
		return projectId;
	}

}
