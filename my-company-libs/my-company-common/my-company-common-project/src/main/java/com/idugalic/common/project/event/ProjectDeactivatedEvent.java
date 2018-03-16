package com.idugalic.common.project.event;

import com.idugalic.common.event.AuditableAbstractEvent;
import com.idugalic.common.model.AuditEntry;

public class ProjectDeactivatedEvent extends AuditableAbstractEvent {

	private static final long serialVersionUID = 1534182475023400008L;

	public ProjectDeactivatedEvent() {
	}

	public ProjectDeactivatedEvent(String id, AuditEntry auditEntry) {
		super(id, auditEntry);
	}
}
