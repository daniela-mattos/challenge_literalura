package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.Author;
import br.com.alura.literalura.model.Book;
import br.com.alura.literalura.model.BookData;
import br.com.alura.literalura.model.GeneralData;
import br.com.alura.literalura.service.BookService;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);
    private BookService bookService = new BookService();
    private Book book = new Book();
    private List<Book> livros = new ArrayList<>();
    private List<Author> autores = new ArrayList<>();

    public Principal(BookService bookService) {
        this.bookService = bookService;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    +++ Escolha uma das opções:
                                        
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em um determinado idioma
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosData();
                    break;
                case 5:
                    listarLivrosEmIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o nome do livro para busca");
        var tituloLivro = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + tituloLivro.replace(" ", "+"));

        //dados desserializados
        GeneralData dados = conversor.obterDados(json, GeneralData.class);

        //pega o campo results e desserializa dados do livro buscado
        List<BookData> bookData = new ArrayList<>();
        bookData = dados.livros();

        //pega o primeiro item da lista de livros devolvida (alguns títulos têm vários itens)
        try {
            book = new Book(bookData.get(0));
            System.out.println(book);

            //mostra dados autor e livros
            List<Author> autores = bookData.stream()
                    .limit(1)
                    .flatMap(b -> b.autor().stream()
                            .map(d -> new Author(b.titulo(), d))
                    ).collect(Collectors.toList());

            autores.forEach(System.out::println);
            Author author = autores.get(0);
            System.out.println(author.getBooks());
            book.setAuthor(author);
            bookService.salvarLivro(book);
        } catch (Exception e) {
            System.out.println("\n+++Livro não encontrado\n");
        }
    }

    private void listarLivrosRegistrados() {
        livros = bookService.findAll();
        livros.stream()
                .sorted(Comparator.comparing(Book::getNomeAutor))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autores = bookService.findAllAuthor();
        autores.stream()
                .sorted(Comparator.comparing(Author::getNome))
                .forEach(System.out::println);
    }

    private void listarAutoresVivosData() {
        System.out.println("Informe o ano de referência: ");
        var anoInformado = leitura.nextInt();
        autores = bookService.autorVivoNoAno(anoInformado);
        autores.stream()
                .sorted(Comparator.comparing(Author::getNome))
                .forEach(System.out::println);
    }

    private void listarLivrosEmIdioma() {
        System.out.println("Informe o idioma: " +
                "\nPortuguês: pt" +
                "\nInglês: en");
        var idiomaBuscado = leitura.nextLine();
        Integer ocorrencias = bookService.contaLivrosEmIdioma(idiomaBuscado);
        System.out.println("\n+++ Tem " + ocorrencias + " livro(s) no idioma " + idiomaBuscado + "\n");
    }

}