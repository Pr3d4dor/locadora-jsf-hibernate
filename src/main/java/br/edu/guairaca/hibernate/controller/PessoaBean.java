package br.edu.guairaca.hibernate.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.guairaca.hibernate.dao.PessoaDAO;
import br.edu.guairaca.hibernate.model.Pessoa;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@SessionScoped
public class PessoaBean extends AbstractController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PessoaDAO dao;

    private Pessoa pessoa;

    private String nomePessoaPesquisa;
    
    private String nome;
    private String cpf;
    private Date dataNascimento;

    private List<Pessoa> pessoas;
    
    @PostConstruct
    private void init() {
        todos();
    }

    public void todos() {
        this.pessoas = dao.findAll();
    }
    
    public void pesquisar() {
        this.pessoas = dao.buscarPorNome(nomePessoaPesquisa);
    }

    public String incluir() {
        this.pessoa = new Pessoa();

        return "/paginas/pessoa/manterPessoa.xhtml?faces-redirect=true";
    }

    public String cancelar() {
        this.pessoa = new Pessoa();

        todos();
        return "/paginas/pessoa/pesquisarPessoa.xhtml?faces-redirect=true";
    }

    public String alterar(Pessoa pessoa) {
        this.pessoa = dao.find(pessoa.getId());
        return "/paginas/pessoa/manterPessoa.xhtml?faces-redirect=true";
    }

    public void excluir(Pessoa pessoa) {
        this.pessoa = dao.find(pessoa.getId());
        this.dao.remove(this.pessoa);
        this.pessoa = new Pessoa();
        todos();
        mensagemInformacao("", "Pessoa Excluida com sucesso.");
    }

    public void salvar() {
        try {
            if (this.pessoa.getId() == null) {
                this.dao.persist(this.pessoa);
                mensagemInformacao("", "Pessoa salva com sucesso.");
                this.pessoa = new Pessoa();
            } else {
                this.dao.merge(this.pessoa);
                mensagemInformacao("", "Pessoa alterada com sucesso.");
            }
        } catch (Exception e) {
            mensagemErroFatal(e, "Erro inesperado", "Erro ao cadastrar pessoa");
        }
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public String getNomePessoaPesquisa() {
        return nomePessoaPesquisa;
    }

    public void setNomePessoaPesquisa(String nomePessoaPesquisa) {
        this.nomePessoaPesquisa = nomePessoaPesquisa;
    }

    public String iniciar() {
        this.pessoa = null;
        this.nome = null;
        this.dataNascimento = null;

        return "/paginas/pessoa/pesquisarPessoa.xhtml?faces-redirect=true";
    }
}
