package com.pluralsight;

import com.pluralsight.dao.BookRepository;
import com.pluralsight.dao.BookRepositoryStub;
import com.pluralsight.model.Book;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class BookResourceTest extends JerseyTest {

    public static final String BOOKS_RESOURCE_PATH = "books";

    private Book book1;
    private Book book2;

    @Override
    protected Application configure() {
        final BookRepository bookRepository = new BookRepositoryStub();
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new BookApplication(bookRepository);
    }

    @Before
    public void setupBooks() throws Exception {
        book1 = createBookOnResource("author1", "title1", "1234", new Date()).readEntity(Book.class);
        book2 = createBookOnResource("author2", "title2", "1234", new Date()).readEntity(Book.class);
    }

    @Test
    public void testAddBook() throws Exception {
        // given

        // when
        Date publicationDate = new Date();
        Response response = createBookOnResource("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "978-0345391803", publicationDate);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        Book created = response.readEntity(Book.class);
        assertThat(created.getId()).isNotNull();
        assertThat(created.getTitle()).isEqualTo("The Hitchhiker's Guide to the Galaxy");
        assertThat(created.getAuthor()).isEqualTo("Douglas Adams");
        assertThat(created.getIsbn()).isEqualTo("978-0345391803");
        assertThat(created.getPublished()).isEqualTo(publicationDate);
    }

    @Test
    public void testGetBook() throws Exception {
        // given
        String bookId = book1.getId();

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

    private Response createBookOnResource(String title, String author, String isbn, Date published) {
        Book book = createBook(title, author, isbn, published);
        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON);
        return target(BOOKS_RESOURCE_PATH).request().post(bookEntity);
    }

    private Book createBook(String title, String author, String isbn, Date published) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPublished(published);
        return book;
    }
}
