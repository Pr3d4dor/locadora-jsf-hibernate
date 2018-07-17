package br.edu.guairaca.hibernate.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.guairaca.hibernate.dao.FilmeDAO;
import br.edu.guairaca.hibernate.model.Filme;

@Named(value = "filmeConverter")
public class FilmeConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FilmeDAO filmeDAO;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String str) throws ConverterException {
        if (str == null || str.isEmpty()) {
            return null;
        }

        Filme filme = this.filmeDAO.find(Long.parseLong(str));

        return filme;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) throws ConverterException {
        if (obj == null) {
            return "";
        }

        if (!(obj instanceof Filme)) {
            return "";
        }

        Filme filme = (Filme) obj;

        return filme.getId().toString();
    }
}
