package com.idugalic.commandside.blog.command;

import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.model.AuditEntry;

import java.util.Date;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 * A command for publishing a blog post.
 * 
 * @author idugalic
 *
 */
public class PublishBlogPostCommand extends AuditableAbstractCommand {

    @TargetAggregateIdentifier
    private String id;
    @Future(message = "'Publish at' date must be in the future")
    @NotNull
    private Date publishAt;

    public PublishBlogPostCommand(String id, AuditEntry auditEntry, Date publishAt) {
        this.id = id;
        this.publishAt = publishAt;
        this.setAuditEntry(auditEntry);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPublishAt() {
        return publishAt;
    }
}
