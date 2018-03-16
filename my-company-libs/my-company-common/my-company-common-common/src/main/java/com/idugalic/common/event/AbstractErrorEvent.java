package com.idugalic.common.event;

import com.idugalic.common.command.AuditableAbstractCommand;
import com.idugalic.common.model.EventError;

/**
 * A base, error event class.
 * 
 * @author idugalic
 *
 */
public abstract class AbstractErrorEvent extends AbstractEvent {

	private static final long serialVersionUID = -5385959139760065159L;

	private AuditableAbstractCommand auditableAbstractCommand;
	private EventError error;

	public AbstractErrorEvent() {

	}

	public AbstractErrorEvent(String id, AuditableAbstractCommand auditableAbstractCommand, EventError error) {
		super(id);
		this.setAuditableAbstractCommand(auditableAbstractCommand);
		this.setError(error);
	}

	public AuditableAbstractCommand getAuditableAbstractCommand() {
		return this.auditableAbstractCommand;
	}

	public void setAuditableAbstractCommand(AuditableAbstractCommand auditableAbstractCommand) {
		this.auditableAbstractCommand = auditableAbstractCommand;
	}

	public EventError getError() {
		return this.error;
	}

	public void setError(EventError error) {
		this.error = error;
	}

}
