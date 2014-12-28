package com.pluralsight;

import com.pluralsight.dao.BookRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class BookApplication extends ResourceConfig {

    public BookApplication(final BookRepository bookRepository) {
        super(ObjectMapperProvider.class, org.glassfish.jersey.jackson.JacksonFeature.class);
        packages("com.pluralsight");
        register(new AbstractBinder() {
                     @Override
                     protected void configure() {
                         bind(bookRepository).to(BookRepository.class);
                     }
                 }
        );
    }
}
