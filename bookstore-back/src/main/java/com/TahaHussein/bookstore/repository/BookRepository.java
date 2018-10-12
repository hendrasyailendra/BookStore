package com.TahaHussein.bookstore.repository;

import com.TahaHussein.bookstore.model.Book;
import com.TahaHussein.bookstore.util.NumberGenerator;
import com.TahaHussein.bookstore.util.TextUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;


/**
 * @author Taha Hussein
 */
// check this to know more about transaction annotation http://entjavastuff.blogspot.com/search/label/EJB3
@Transactional(SUPPORTS)
public class BookRepository {

    // ======================================
    // =          Injection Points          =
    // ======================================

    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager em;

    @Inject
    private NumberGenerator numberGenerator;

    @Inject
    private TextUtil textUtil;

    // ======================================
    // =          Business methods          =
    // ======================================

    public Book find(@NotNull Long id) {
        return em.find(Book.class, id);
    }

    @Transactional(REQUIRED) // with write only methods
    public Book create(@NotNull Book book){
         book.setTitle(textUtil.sanitize(book.getTitle()));
         book.setIsbn(numberGenerator.generateNumber());
         em.persist(book);
         return book;
    }

    @Transactional(REQUIRED)
    public void delete(@NotNull Long id){
        em.remove(em.getReference(Book.class, id));
    }

    public List<Book> findAll(){
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ORDER BY b.title", Book.class);
        return query.getResultList();
    }

    public Long countAll(){
        TypedQuery<Long> query = em.createQuery("SELECT count(b) FROM Book b", Long.class); // Note Long
        return query.getSingleResult();
    }
}
