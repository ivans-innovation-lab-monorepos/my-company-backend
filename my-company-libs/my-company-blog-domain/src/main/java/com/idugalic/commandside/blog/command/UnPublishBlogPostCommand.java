package com.idugalic.commandside.blog.command;

import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.model.AuditEntry;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Date;

/**
 * A command for un publishing a blog post.
 * 
 * @author idugalic
 *
 */
public class UnPublishBlogPostCommand extends AuditableAbstractCommand {

    @TargetAggregateIdentifier
    private String id;

    public UnPublishBlogPostCommand(String id, AuditEntry auditEntry) {
        this.id = id;
        this.setAuditEntry(auditEntry);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
