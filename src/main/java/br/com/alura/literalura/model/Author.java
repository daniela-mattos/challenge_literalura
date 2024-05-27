package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    private Integer anoDeNascimento;
    private Integer anoDeFalecimento;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<Book>();

    public Author() {}

    public Author(String titulo, AuthorData authorData) {
        this.nome = authorData.nome();
        this.anoDeNascimento = authorData.anoDeNascimento();
        this.anoDeFalecimento = authorData.anoDeFalecimento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Integer anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public Integer getAnoDeFalecimento() {
        return anoDeFalecimento;
    }

    public void setAnoDeFalecimento(Integer anoDeFalecimento) {
        this.anoDeFalecimento = anoDeFalecimento;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBooks(Book book) {
        book.setAuthor(this);
        this.books.add(book);
    }

    public void setBooks(Book book) {
        this.books.add(book);
    }

    @Override
    public String toString() {
        String livrosStr = books.stream()
                .map(Book::getTitulo)
                .collect(Collectors.joining(", "));

        return  "\n++++++++++ Autor +++++++++" +
                "\nNome: " + nome +
                "\nAno de Nascimento: " + anoDeNascimento +
                "\nAno de Falecimento: " + anoDeFalecimento +
                "\nLivros: " + livrosStr +
                "\n+++++++++++++++++++++++++++\n";
    }
}
