package com.idugalic.commandside.project.aggregate;

import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfiguration {
	@Autowired
    private org.axonframework.spring.config.AxonConfiguration axonConfiguration;
	
	@Autowired
    private EventBus eventBus;
	
	// Explicit command handlers
    @Bean
    public ProjectCommandHandler projectCommandHandler() {
        return new ProjectCommandHandler(axonConfiguration.repository(ProjectAggregate.class), eventBus);
    }

}
