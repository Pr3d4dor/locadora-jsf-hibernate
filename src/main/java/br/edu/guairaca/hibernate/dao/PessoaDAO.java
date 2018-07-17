package br.edu.guairaca.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.edu.guairaca.hibernate.model.Pessoa;

@Transactional
public class PessoaDAO extends GenericDAO<Pessoa, Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    protected PessoaDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<Pessoa> buscarPorNome(String nome) {
        if (nome != null && nome.isEmpty()) {
            nome = null;
        }

        StringBuffer hql = new StringBuffer();
        hql.append("select p from Pessoa p ");
        hql.append(" where p.nome = coalesce(:nome, p.nome) ");

        Query query = entityManager.createQuery(hql.toString());
        query.setParameter("nome", nome);

        return query.getResultList();
    }
}
