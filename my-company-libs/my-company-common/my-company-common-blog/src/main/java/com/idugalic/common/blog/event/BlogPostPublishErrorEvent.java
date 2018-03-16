package com.idugalic.common.blog.event;

import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.event.AbstractErrorEvent;
import com.idugalic.common.model.EventError;

public class BlogPostPublishErrorEvent extends AbstractErrorEvent {

    private static final long serialVersionUID = 1534387425032480970L;

    public BlogPostPublishErrorEvent() {
    }

    public BlogPostPublishErrorEvent(String id, AuditableAbstractCommand auditableAbstractCommand) {
        super(id, auditableAbstractCommand, EventError.BLOGPOST_1);
    }

}
