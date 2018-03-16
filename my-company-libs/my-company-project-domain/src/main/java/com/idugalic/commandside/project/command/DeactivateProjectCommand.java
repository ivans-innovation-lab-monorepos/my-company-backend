package com.idugalic.commandside.project.command;

import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.model.AuditEntry;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * A command for deactivating the project.
 * 
 * @author idugalic
 *
 */
public class DeactivateProjectCommand extends AuditableAbstractCommand {

    @TargetAggregateIdentifier
    private String id;


    public DeactivateProjectCommand(String id, AuditEntry auditEntry) {
        super(auditEntry);
        this.id = id;
      
    }

    public String getId() {
        return id;
    }


}
