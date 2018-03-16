package com.idugalic.common.project.event;

import com.idugalic.common.event.AuditableAbstractEvent;
import com.idugalic.common.model.AuditEntry;

public class ProjectActivatedEvent extends AuditableAbstractEvent {

	private static final long serialVersionUID = 1534182475023480008L;

	public ProjectActivatedEvent() {
	}

	public ProjectActivatedEvent(String id, AuditEntry auditEntry) {
		super(id, auditEntry);
	}
}
