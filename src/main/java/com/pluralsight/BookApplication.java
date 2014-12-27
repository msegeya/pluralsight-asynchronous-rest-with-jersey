package com.pluralsight;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.pluralsight.dao.BookRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class BookApplication extends ResourceConfig {

    public BookApplication(final BookRepository bookRepository) {
        packages("com.pluralsight");
        register(new AbstractBinder() {
                     @Override
                     protected void configure() {
                         bind(bookRepository).to(BookRepository.class);
                     }
                 }
        );
        JacksonJsonProvider json = new JacksonJsonProvider()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        register(json);
    }
}
