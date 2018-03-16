package com.idugalic.common.team.event;

import com.idugalic.common.event.AuditableAbstractEvent;
import com.idugalic.common.model.AuditEntry;

public class TeamPassivatedEvent extends AuditableAbstractEvent {

    private static final long serialVersionUID = 1594380375023480978L;
   
    public TeamPassivatedEvent() {
    }

    public TeamPassivatedEvent(String id, AuditEntry auditEntry) {
        super(id, auditEntry);
    }
}
