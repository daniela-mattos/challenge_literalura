package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.Author;
import br.com.alura.literalura.model.Book;
import br.com.alura.literalura.model.BookData;
import br.com.alura.literalura.model.GeneralData;
import br.com.alura.literalura.repository.AuthorRepository;
import br.com.alura.literalura.repository.BookRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;


    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    +++ Escolha uma das opções:
                                        
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    4 - Listar autores registrados
                    5 - Listar autores vivos em determinado ano
                    6 - Listar livros em um determinado idioma
                                    
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
        Book book = new Book(bookData.get(0));
        System.out.println(book);
        bookRepository.save(book);

        //mostra dados autor e livros
        List<Author> autores = bookData.stream()
                .limit(1)
                .flatMap(b -> b.autor().stream()
                        .map(d -> new Author(b.titulo(), d))
                ).collect(Collectors.toList());

        autores.forEach(System.out::println);
        Author autor = autores.get(0);
        authorRepository.save(autor);

    }

    private void listarLivrosRegistrados() {
    }

    private void listarAutoresRegistrados() {
    }

    private void listarAutoresVivosData() {
    }

    private void listarLivrosEmIdioma() {
    }
}