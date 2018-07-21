package br.edu.guairaca.hibernate.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.guairaca.hibernate.dao.*;
import br.edu.guairaca.hibernate.model.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;

@Named
@SessionScoped
public class LocacaoBean extends AbstractController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private LocacaoDAO locacaoDAO;

    @Inject
    private PessoaDAO pessoaDAO;

    @Inject
    private FilmeDAO filmeDAO;

    private Locacao locacao;

    private String nomeLocacaoPesquisa;

    private Pessoa pessoa;

    private Set<Filme> filmes;

    private Date dataLocacao;

    private List<Locacao> locacoes;

    private List<Pessoa> listaPessoas;

    private List<Filme> listaFilmes;

    @PostConstruct
    public void init() {
        todos();
    }

    public void todos() {
        this.locacoes = locacaoDAO.findAll();
        this.listaFilmes = filmeDAO.findAll();
        this.listaPessoas = pessoaDAO.findAll();
    }

    public void pesquisar() {
        this.locacoes = locacaoDAO.buscarPorNomePessoa(nomeLocacaoPesquisa);
    }

    public String incluir() {
        this.locacao = new Locacao();
        this.pessoa = new Pessoa();
        this.filmes = new HashSet<>();

        this.listaFilmes = filmeDAO.findAll();
        this.listaPessoas = pessoaDAO.findAll();

        return "/paginas/locacao/manterLocacao.xhtml?faces-redirect=true";
    }

    public String cancelar() {
        this.locacao = new Locacao();

        todos();
        return "/paginas/locacao/pesquisarLocacao.xhtml?faces-redirect=true";
    }

    public String alterar(Locacao locacao) {
        this.locacao = locacaoDAO.find(locacao.getId());
        return "/paginas/locacao/manterLocacao.xhtml?faces-redirect=true";
    }

    public void excluir(Locacao locacao) {
        this.locacao = locacaoDAO.find(locacao.getId());
        this.locacao.getFilmes().clear();
        this.locacaoDAO.remove(this.locacao);
        this.locacao = new Locacao();
        todos();
        mensagemInformacao("", "Locacao excluida com sucesso.");
    }

    public void salvar() {
        try {
            if (this.locacao.getId() == null) {
                this.locacaoDAO.persist(this.locacao);
                mensagemInformacao("", "Locacao salvo com sucesso.");
                this.locacao = new Locacao();
            } else {
                this.locacaoDAO.merge(this.locacao);
                mensagemInformacao("", "Locacao alterada com sucesso.");
            }
        } catch (Exception e) {
            mensagemErroFatal(e, "Erro inesperado", "Erro ao cadastrar locacao");
        }
    }

    public Locacao getLocacao() {
        return locacao;
    }

    public void setLocacao(Locacao locacao) {
        this.locacao = locacao;
    }

    public Date getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(Date dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public List<Locacao> getLocacoes() {
        return locacoes;
    }

    public void setLocacoes(List<Locacao> locacoes) {
        this.locacoes = locacoes;
    }

    public String getNomeLocacaoPesquisa() {
        return nomeLocacaoPesquisa;
    }

    public void setNomeLocacaoPesquisa(String nomeLocacaoPesquisa) {
        this.nomeLocacaoPesquisa = nomeLocacaoPesquisa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Set<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(Set<Filme> filmes) {
        this.filmes = filmes;
    }

    public List<Pessoa> getListaPessoas() {
        return listaPessoas;
    }

    public void setListaPessoas(List<Pessoa> listaPessoas) {
        this.listaPessoas = listaPessoas;
    }

    public List<Filme> getListaFilmes() {
        return listaFilmes;
    }

    public void setListaFilmes(List<Filme> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }

    public String iniciar() {
        this.locacao = null;
        this.pessoa = null;
        this.filmes = null;
        this.dataLocacao = null;

        return "/paginas/locacao/pesquisarLocacao.xhtml?faces-redirect=true";
    }
}
