package br.edu.guairaca.hibernate.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.guairaca.hibernate.dao.PessoaDAO;
import br.edu.guairaca.hibernate.model.Pessoa;

@Named(value = "pessoaConverter")
public class PessoaConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PessoaDAO pessoaDAO;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String str) throws ConverterException {
        if (str == null || str.isEmpty()) {
            return null;
        }

        Pessoa pessoa = this.pessoaDAO.find(Long.parseLong(str));

        return pessoa;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) throws ConverterException {
        if (obj == null) {
            return "";
        }

        if (!(obj instanceof Pessoa)) {
            return "";
        }

        Pessoa pessoa = (Pessoa) obj;

        return pessoa.getId().toString();
    }
}
