/**
 * (C) 2015-2018 JL INFORMATICA LTDA ME
 * CNPJ 14.928.793/0001-18
 * www.jlinformatica.net.br
 * (45) 3559-1534
 */

package lib.model.interno;

import java.io.Serializable;

/**
 *
 * @author Lucas Dillmann
 */


public enum NivelAcessoModulo implements Serializable {
    
    SOMENTE_LEITURA("Somente leitura"),
    LEITURA_GRAVACAO("Leitura e gravação");
    
    private final String descricao;

    NivelAcessoModulo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return getDescricao();
    }
    
}
