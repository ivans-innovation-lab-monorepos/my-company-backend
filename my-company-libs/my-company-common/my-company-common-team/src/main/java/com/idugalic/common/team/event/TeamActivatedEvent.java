package com.idugalic.common.team.event;

import com.idugalic.common.event.AuditableAbstractEvent;
import com.idugalic.common.model.AuditEntry;

public class TeamActivatedEvent extends AuditableAbstractEvent {

    private static final long serialVersionUID = 1494380375023480978L;
   
    public TeamActivatedEvent() {
    }

    public TeamActivatedEvent(String id, AuditEntry auditEntry) {
        super(id, auditEntry);
    }
}
