package lib.model.pessoa;


import java.io.Serializable;

/**
 *
 * @author Lucas Dillmann
 */
public enum Sexo implements Serializable {

    MASCULINO("Masculino", "Masc", 0),
    FEMININO("Feminino", "Fem",1),
    OUTROS("Outros ou não especificar", "Outr",2);



    Sexo(String descricao, String abreviacao, Integer n) {

    }



}
