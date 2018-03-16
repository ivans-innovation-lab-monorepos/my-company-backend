package com.idugalic.common.project.event;

import com.idugalic.common.event.AuditableAbstractEvent;
import com.idugalic.common.model.AuditEntry;

public class ProjectFoundEvent extends AuditableAbstractEvent {

	private static final long serialVersionUID = 1534182475023480998L;

	public ProjectFoundEvent() {
	}

	public ProjectFoundEvent(String id, AuditEntry auditEntry) {
		super(id, auditEntry);
	}
}
