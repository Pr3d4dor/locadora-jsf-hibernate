package br.edu.guairaca.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.edu.guairaca.hibernate.model.Locacao;

@Transactional
public class LocacaoDAO extends GenericDAO<Locacao, Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    protected LocacaoDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<Locacao> buscarPorNomePessoa(String nome) {
        if (nome != null && nome.isEmpty()) {
            nome = null;
        }

        StringBuffer hql = new StringBuffer();
        hql.append("select l from Locacao l");
        hql.append(" where l.pessoa.nome = coalesce(:nome, l.nome) ");

        Query query = entityManager.createQuery(hql.toString());
        query.setParameter("nome", nome);

        return query.getResultList();
    }
}
