package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book getByTitulo(String titulo);

    List<Book> findAll();

    @Query("SELECT COUNT(b) FROM Book b WHERE b.idioma LIKE %:idioma%")
    long contaLivrosEmIdioma(String idioma);

}
