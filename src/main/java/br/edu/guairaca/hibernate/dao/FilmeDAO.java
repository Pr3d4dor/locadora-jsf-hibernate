package br.edu.guairaca.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.edu.guairaca.hibernate.model.Filme;

@Transactional
public class FilmeDAO extends GenericDAO<Filme, Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    protected FilmeDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<Filme> buscarPorNome(String nome) {
        if (nome != null && nome.isEmpty()) {
            nome = null;
        }

        StringBuffer hql = new StringBuffer();
        hql.append("select f from Filme f ");
        hql.append(" where f.nome = coalesce(:nome, f.nome) ");

        Query query = entityManager.createQuery(hql.toString());
        query.setParameter("nome", nome);

        return query.getResultList();
    }
}
