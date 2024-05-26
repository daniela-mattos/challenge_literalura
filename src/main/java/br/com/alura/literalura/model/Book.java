package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    private String nomeAutor;
    private Integer quantidadeDeDownloads;
    private String idioma;

    @ManyToOne
    private Author author;

    public Book() {}

    public Book(BookData bookData) {
        this.titulo = bookData.titulo();
        this.nomeAutor = bookData.autor().get(0).nome();
        this.quantidadeDeDownloads = bookData.quantidadeDeDownloads();
        this.idioma = bookData.idiomas().get(0).toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Integer getQuantidadeDeDownloads() {
        return quantidadeDeDownloads;
    }

    public void setQuantidadeDeDownloads(Integer quantidadeDeDownloads) {
        this.quantidadeDeDownloads = quantidadeDeDownloads;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "\n++++++++++ Livro ++++++++++" +
                "\nTitulo: " + titulo +
                "\nAutor: " + nomeAutor +
                "\nNúmero de Downloads: " + quantidadeDeDownloads +
                "\nIdioma: " + idioma +
                "\n+++++++++++++++++++++++++++\n";
    }

}
