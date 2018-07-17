package br.edu.guairaca.hibernate.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.guairaca.hibernate.dao.*;
import br.edu.guairaca.hibernate.model.*;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@SessionScoped
public class FilmeBean extends AbstractController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FilmeDAO dao;

    private Filme filme;

    private String nomeFilmePesquisa;
    
    private String nome;
    private String genero;
    private String faixaEtaria;
    private Date dataLancamento;

    private List<Filme> filmes;
    
    @PostConstruct
    private void init() {
        todos();
    }

    public void todos() {
        this.filmes = dao.findAll();
    }
    
    public void pesquisar() {
        this.filmes = dao.buscarPorNome(nomeFilmePesquisa);
    }

    public String incluir() {
        this.filme = new Filme();

        return "/paginas/filme/manterFilme.xhtml?faces-redirect=true";
    }

    public String cancelar() {
        this.filme = new Filme();

        todos();
        return "/paginas/filme/pesquisarFilme.xhtml?faces-redirect=true";
    }

    public String alterar(Filme filme) {
        this.filme = dao.find(filme.getId());
        return "/paginas/filme/manterFilme.xhtml?faces-redirect=true";
    }

    public void excluir(Filme filme) {
        this.filme = dao.find(filme.getId());
        this.dao.remove(this.filme);
        this.filme = new Filme();
        todos();
        mensagemInformacao("", "Filme Excluido com sucesso.");
    }

    public void salvar() {
        try {
            if (this.filme.getId() == null) {
                this.dao.persist(this.filme);
                mensagemInformacao("", "Filme salvo com sucesso.");
                this.filme = new Filme();
            } else {
                this.dao.merge(this.filme);
                mensagemInformacao("", "Filme alterado com sucesso.");
            }
        } catch (Exception e) {
            mensagemErroFatal(e, "Erro inesperado", "Erro ao cadastrar filme");
        }
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }

    public String getNomeFilmePesquisa() {
        return nomeFilmePesquisa;
    }

    public void setNomeFilmePesquisa(String nomeFilmePesquisa) {
        this.nomeFilmePesquisa = nomeFilmePesquisa;
    }

    public String iniciar() {
        this.filme = null;
        this.nome = null;
        this.faixaEtaria = null;
        this.dataLancamento = null;

        return "/paginas/filme/pesquisarFilme.xhtml?faces-redirect=true";
    }
}
