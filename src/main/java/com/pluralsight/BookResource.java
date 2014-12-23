package com.pluralsight;

import com.pluralsight.dao.BookRepository;
import com.pluralsight.dao.BookRepositoryStub;
import com.pluralsight.model.Book;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/books")
public class BookResource {

    private BookRepository bookRepository = new BookRepositoryStub();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Book> getBooks() {
        return bookRepository.getBooks();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBooks(@PathParam("id") String id) {
        return bookRepository.getBook(id);
    }
}
