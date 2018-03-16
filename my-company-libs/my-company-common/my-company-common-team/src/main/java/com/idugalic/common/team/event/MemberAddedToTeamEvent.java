package com.idugalic.common.team.event;

import com.idugalic.common.event.AuditableAbstractEvent;
import com.idugalic.common.model.AuditEntry;
import com.idugalic.common.team.model.Member;

public class MemberAddedToTeamEvent extends AuditableAbstractEvent {

    private static final long serialVersionUID = 7894380375023480978L;
    private Member member;
   
    public MemberAddedToTeamEvent() {
    }

    public MemberAddedToTeamEvent(String id, AuditEntry auditEntry, Member member) {
        super(id, auditEntry);
        this.member = member;
    }

	public Member getMember() {
		return member;
	}
    
}
