package com.idugalic.commandside.blog.aggregate;

import com.idugalic.commandside.blog.aggregate.exception.PublishBlogPostException;
import com.idugalic.commandside.blog.command.CreateBlogPostCommand;
import com.idugalic.commandside.blog.command.PublishBlogPostCommand;
import com.idugalic.commandside.blog.command.UnPublishBlogPostCommand;
import com.idugalic.common.blog.event.BlogPostCreatedEvent;
import com.idugalic.common.blog.event.BlogPostPublishedEvent;
import com.idugalic.common.blog.event.BlogPostUnPublishedEvent;
import com.idugalic.common.blog.model.BlogPostCategory;
import com.idugalic.common.model.AuditEntry;

import java.util.Calendar;
import java.util.Date;

import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.JSR303ViolationException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * Domain (aggregate) test for {@link BlogPostAggregate}.
 * 
 * @author idugalic
 *
 */
public class BlogPostAggregateTest {

    private FixtureConfiguration<BlogPostAggregate> fixture;
    private AuditEntry auditEntry;
    private static final String WHO = "idugalic";
    private Calendar future;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<BlogPostAggregate> (BlogPostAggregate.class);
        fixture.registerCommandDispatchInterceptor(new BeanValidationInterceptor());
        auditEntry = new AuditEntry(WHO);
        future = Calendar.getInstance();
        future.add(Calendar.DAY_OF_YEAR, 1);
    }

    @Test
    public void createBlogPostTest() throws Exception {
        CreateBlogPostCommand command = new CreateBlogPostCommand(auditEntry, "title", "rowContent", "publicSlug", Boolean.TRUE, Boolean.FALSE, future.getTime(), BlogPostCategory.ENGINEERING, WHO);
        fixture.given().when(command).expectEvents(new BlogPostCreatedEvent(command.getId(), command.getAuditEntry(), command.getTitle(), command.getRawContent(), command
                .getPublicSlug(), command.getDraft(), command.getBroadcast(), command.getPublishAt(), BlogPostCategory.ENGINEERING, WHO));
    }
    
    @Test(expected = JSR303ViolationException.class)
    public void createBlogPostWithWrongTitleTest() {
        CreateBlogPostCommand command = new CreateBlogPostCommand(auditEntry, null, null, "publicSlug", Boolean.TRUE, Boolean.FALSE, future.getTime(),
                BlogPostCategory.ENGINEERING, WHO);
        fixture.given().when(command).expectException(JSR303ViolationException.class);
    }

    @Test
    public void publishBlogPostTest() throws Exception {
        PublishBlogPostCommand command = new PublishBlogPostCommand("id", auditEntry, future.getTime());
        fixture.given(new BlogPostCreatedEvent(command.getId(), command.getAuditEntry(), "title", "rawContent", "publicSlug", Boolean.TRUE, Boolean.TRUE, command.getPublishAt(),
                BlogPostCategory.ENGINEERING, WHO)).when(command).expectEvents(new BlogPostPublishedEvent("id", auditEntry, command.getPublishAt()));
    }
    
    @Test
    public void publishBlogPostWithWrongIdTest() throws Exception {
        PublishBlogPostCommand command = new PublishBlogPostCommand(null, auditEntry, future.getTime());
        fixture.given(new BlogPostCreatedEvent(command.getId(), command.getAuditEntry(), "title", "rawContent", "publicSlug", Boolean.TRUE, Boolean.TRUE, command.getPublishAt(),
                BlogPostCategory.ENGINEERING, WHO)).when(command).expectException(IllegalArgumentException.class);
    }

    @Test
    public void unPublishBlogPostTest() throws Exception {
        UnPublishBlogPostCommand command = new UnPublishBlogPostCommand("id", auditEntry);
        fixture.given(new BlogPostCreatedEvent(command.getId(), command.getAuditEntry(), "title", "rawContent", "publicSlug", Boolean.FALSE, Boolean.TRUE, new Date(),
                BlogPostCategory.ENGINEERING, WHO)).when(command).expectEvents(new BlogPostUnPublishedEvent("id", auditEntry));
    }

    @Test
    public void unPublishBlogPostTestWrongDraft() throws Exception {
        UnPublishBlogPostCommand command = new UnPublishBlogPostCommand("id", auditEntry);
        fixture.given(new BlogPostCreatedEvent(command.getId(), command.getAuditEntry(), "title", "rawContent", "publicSlug", Boolean.TRUE, Boolean.TRUE, new Date(),
                BlogPostCategory.ENGINEERING, WHO)).when(command).expectException(PublishBlogPostException.class);
    }

}
