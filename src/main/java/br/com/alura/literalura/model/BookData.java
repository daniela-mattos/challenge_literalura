package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(@JsonAlias("title") String titulo,
                       @JsonAlias("authors") List<AuthorData> autor,
                       @JsonAlias("download_count") Integer quantidadeDeDownloads,
                       @JsonAlias("languages") List<String> idiomas) {

}
