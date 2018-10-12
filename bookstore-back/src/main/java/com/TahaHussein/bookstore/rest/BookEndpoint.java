package com.TahaHussein.bookstore.rest;

import com.TahaHussein.bookstore.model.Book;
import com.TahaHussein.bookstore.repository.BookRepository;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * @author Taha Hussein
 */

@SwaggerDefinition(
        info = @Info(
                title = "BookStore APIs",
                description = "BookStore APIs exposed from a Java EE back-end to an Angular front-end",
                version = "V1.0.0",
                contact = @Contact(
                        name = "Taha Hussein",
                        email = "taha.hussein.12.6.95@gmail.com"
                )
        ),
        host = "localhost:8080",
        basePath = "/bookstore-back-1.0/api",
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        tags = {
                @Tag(name = "Book", description = "Tag used to denote operations as private")
        }
)
@Path("/books")
@Api("Book")
public class BookEndpoint {

    // ======================================
    // =          Injection Points          =
    // ======================================

    @Inject
    private BookRepository bookRepository;

    // ======================================
    // =          Business methods          =
    // ======================================

    @POST
    @Consumes(APPLICATION_JSON)
    @ApiOperation("Creates a book given a JSon Book representation")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The book is created"),
            @ApiResponse(code = 415, message = "Format is not JSon")
    })
    public Response createBook(@ApiParam(value = "Book to be created", required = true) Book book, @Context UriInfo uriInfo) {
        book = bookRepository.create(book);
        URI createdURI = uriInfo.getBaseUriBuilder().path(book.getId().toString()).build();
        return Response.created(createdURI).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .header("Access-Control-Allow-Credentials",true).build(); //https://stackoverflow.com/questions/28306103/angular-js-no-access-control-allow-origin-header-is-present-on-the-requested
        // very important also https://stackoverflow.com/questions/35588699/response-to-preflight-request-doesnt-pass-access-control-check
    }

    @GET
    @Path("/{id : \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Returns a book given an id", response = Book.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Book found"),
            @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Book book = bookRepository.find(id);

        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .header("Access-Control-Allow-Credentials",true).build();

        return Response.ok(book).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .header("Access-Control-Allow-Credentials",true).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation("Deletes a book given an id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Book has been deleted"),
            @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
            @ApiResponse(code = 500, message = "Book not found")
    })
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        bookRepository.delete(id);
        return Response.noContent().header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .header("Access-Control-Allow-Credentials",true).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Returns all the books", response = Book.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Books found"),
            @ApiResponse(code = 204, message = "No books found"),
    })
    public Response getBooks() {
        List<Book> books = bookRepository.findAll();

        if (books.size() == 0)
            return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .header("Access-Control-Allow-Credentials",true).build();

        return Response.ok(books).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .header("Access-Control-Allow-Credentials",true).build();
    }

    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @ApiOperation(value = "Returns the number of books", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Number of books found"),
            @ApiResponse(code = 204, message = "No books found"),
    })
    public Response countBooks() {
        Long nbOfBooks = bookRepository.countAll();

        if (nbOfBooks == 0)
            return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .header("Access-Control-Allow-Credentials",true).build();

        return Response.ok(nbOfBooks).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .header("Access-Control-Allow-Credentials",true).build();
    }
}