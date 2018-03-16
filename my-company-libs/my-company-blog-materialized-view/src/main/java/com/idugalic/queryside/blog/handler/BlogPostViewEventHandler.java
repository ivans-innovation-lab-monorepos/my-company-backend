package com.idugalic.queryside.blog.handler;

import com.idugalic.common.blog.event.BlogPostCreatedEvent;
import com.idugalic.common.blog.event.BlogPostPublishedEvent;
import com.idugalic.common.blog.event.BlogPostUnPublishedEvent;
import com.idugalic.queryside.blog.domain.BlogPost;
import com.idugalic.queryside.blog.repository.BlogPostRepository;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.SequenceNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Event handlers for {@link BlogPostCreatedEvent},
 * {@link BlogPostPublishedEvent}
 * 
 * @author idugalic
 *
 */
@ProcessingGroup("default")
@Component
class BlogPostViewEventHandler {

	private static final Logger LOG = LoggerFactory.getLogger(BlogPostViewEventHandler.class);
	private BlogPostRepository blogPostRepository;

	@Autowired
	public BlogPostViewEventHandler(BlogPostRepository blogPostRepository) {
		this.blogPostRepository = blogPostRepository;
	}

	@EventHandler
	public void handle(BlogPostCreatedEvent event, @SequenceNumber Long version) {
		blogPostRepository.save(new BlogPost(event, version));
	}

	@EventHandler
	public void handle(BlogPostPublishedEvent event, @SequenceNumber Long version) {
		BlogPost post = blogPostRepository.findById(event.getId()).get();
		post.setDraft(Boolean.FALSE);
		post.setPublishAt(event.getPublishAt());
		post.setAggregateVersion(version);
		blogPostRepository.save(post);
	}

	@EventHandler
	public void handle(BlogPostUnPublishedEvent event, @SequenceNumber Long version) {
		BlogPost post = blogPostRepository.findById(event.getId()).get();
		post.setDraft(Boolean.TRUE);
		post.setAggregateVersion(version);
		blogPostRepository.save(post);
	}
}
