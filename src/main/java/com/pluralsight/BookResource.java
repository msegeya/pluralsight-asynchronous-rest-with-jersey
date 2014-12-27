package com.pluralsight;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.pluralsight.dao.BookRepository;
import com.pluralsight.model.Book;
import org.glassfish.jersey.server.ManagedAsync;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/books")
public class BookResource {

    // HK2 injection in Jersey (object must be bound in ResourceConfig)
    @Context
    private BookRepository bookRepository;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getBook(@PathParam("id") String id, @Suspended final AsyncResponse response) {
        ListenableFuture<Book> bookFuture = bookRepository.getBookAsync(id);
        Futures.addCallback(bookFuture, new FutureCallback<Book>() {
            @Override
            public void onSuccess(Book book) {
                response.resume(book);
            }

            @Override
            public void onFailure(Throwable throwable) {
                response.resume(throwable);
            }
        });
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getBooks(@Suspended final AsyncResponse response) {
        ListenableFuture<Collection<Book>> booksFuture = bookRepository.getBooksAsync();
        Futures.addCallback(booksFuture, new FutureCallback<Collection<Book>>() {
            public void onSuccess(Collection<Book> result) {
                response.resume(new GenericEntity<Collection<Book>>(result){});
            }

            public void onFailure(Throwable throwable) {
                response.resume(throwable);
            }
        });
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void addBook(Book book, @Suspended final AsyncResponse response) {
        ListenableFuture<Book> bookFuture = bookRepository.addBookAsync(book);
        Futures.addCallback(bookFuture, new FutureCallback<Book>() {
            public void onSuccess(Book addedBook) {
                response.resume(addedBook);
            }

            public void onFailure(Throwable throwable) {
                response.resume(throwable);
            }
        });
    }
}
