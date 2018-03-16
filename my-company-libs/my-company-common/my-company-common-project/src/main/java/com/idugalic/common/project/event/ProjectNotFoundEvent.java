package com.idugalic.common.project.event;

import com.idugalic.common.event.AuditableAbstractEvent;
import com.idugalic.common.model.AuditEntry;

public class ProjectNotFoundEvent extends AuditableAbstractEvent {

	private static final long serialVersionUID = 1234582475023480998L;

	public ProjectNotFoundEvent() {
	}

	public ProjectNotFoundEvent(String id, AuditEntry auditEntry) {
		super(id, auditEntry);
	}
}
