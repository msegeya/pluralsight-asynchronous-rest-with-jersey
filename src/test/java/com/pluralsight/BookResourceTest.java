package com.pluralsight;

import com.pluralsight.model.Book;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class BookResourceTest extends JerseyTest {

    public static final String BOOKS_RESOURCE_PATH = "books";

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig().packages("com.pluralsight");
    }

    @Test
    public void testGetBook() throws Exception {
        // given
        String bookId = "1";

        // when
        Book book = target(BOOKS_RESOURCE_PATH).path(bookId).request().get(Book.class);

        // then
        assertThat(book).isNotNull();
    }

    @Test
    public void testGetBooksCollection() throws Exception {
        // given

        // when
        Collection<Book> books = target(BOOKS_RESOURCE_PATH).request().get(new GenericType<Collection<Book>>() {});

        // then
        assertThat(books).isNotNull().isNotEmpty();
        assertThat(books).hasSize(2);
    }
}
