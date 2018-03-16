package com.idugalic.common.blog.event;

import com.idugalic.common.event.AuditableAbstractEvent;
import com.idugalic.common.model.AuditEntry;

public class BlogPostUnPublishedEvent extends AuditableAbstractEvent {

    private static final long serialVersionUID = 1534382475032480979L;

    public BlogPostUnPublishedEvent() {
    }

    public BlogPostUnPublishedEvent(String id, AuditEntry auditEntry) {
        super(id, auditEntry);
    }
}
