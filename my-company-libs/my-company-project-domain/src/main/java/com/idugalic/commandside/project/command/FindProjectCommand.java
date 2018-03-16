package com.idugalic.commandside.project.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.model.AuditEntry;

/**
 * A command for finding the project.
 * 
 * @author idugalic
 *
 */
public class FindProjectCommand extends AuditableAbstractCommand {

    @TargetAggregateIdentifier
    private String id;
   

    public FindProjectCommand(String id, AuditEntry auditEntry) {
        super(auditEntry);
        this.id = id;
      
    }

    public String getId() {
        return id;
    }


}
