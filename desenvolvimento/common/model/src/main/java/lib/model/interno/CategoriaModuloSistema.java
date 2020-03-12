/**
 * (C) 2015-2018 JL INFORMATICA LTDA ME
 * CNPJ 14.928.793/0001-18
 * www.jlinformatica.net.br
 * (45) 3559-1534
 */
package lib.model.interno;

/**
 * Enumeração de categoria de módulo de sistema
 *
 * @author Lucas Dillmann
 * @since 1.3.1
 */
public enum CategoriaModuloSistema {
    PESSOAS("Pessoas"),
    ESTOQUE("Serviços"),
    COMERCIAL("Comercial"),
    FINANCIRO("Financeiro"),
    CADASTROS("Cadastros"),
    OS("Ordem de serviço"),
    SISTEMA("Sistema");



    private final String descricao;

    CategoriaModuloSistema(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

}
