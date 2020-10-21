package lib.model.pessoa;

/**
 * (C) 2015-2018 JL INFORMATICA LTDA ME
 * CNPJ 14.928.793/0001-18
 * www.jlinformatica.net.br
 * (45) 3559-1534
 */


import java.io.Serializable;

/**
 * @author Lucas Dillmann
 */
public enum TipoPessoa implements Serializable {

    FISICA("Pessoa física"),
    JURIDICA("Pessoa jurídica"),
    ESTRANGEIRO("Estrangeiro");


    private final String descricao;

    TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public boolean isPessoaFisica() {
        return equals(FISICA);
    }

    public boolean isPessoaJuridica() {
        return equals(JURIDICA);
    }

    public boolean isPessoaEstrangeira() {
        return equals(ESTRANGEIRO);
    }

    /**
     * Método para identificar o tipo de documento que foi cadastrado para a pessoa
     *
     * @return String com a representação do tipo de documento da pessoa
     * @author Cesar Both
     * @since 1.4.2
     */
    public String getTipoDocumentoPessoa() {
        if (isPessoaFisica()) {
            return "CPF";
        } else if (isPessoaJuridica()) {
            return "CNPJ";
        } else
            return "Documento";
    }

    public TipoPessoa getTipo(Integer i){
        switch (i){
            case 0: return TipoPessoa.FISICA;
            case 1: return TipoPessoa.JURIDICA;
            case 2: return TipoPessoa.ESTRANGEIRO;
            default: return TipoPessoa.FISICA;
        }
}

}
