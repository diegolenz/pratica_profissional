package lib.model.pessoa;

public interface Pessoajuridica {


    public interface PessoaJuridica {
        String getCep();

        String getEmail();

        String getNomeFantasia();

        String getRazaoSocial();

        String getCnpj();

        String getInscricaoEstadual();

        String getLogradouro();

        Integer getNumero();

        String getBairro();

        String getTelefone();

        String getWebsite();

        byte[] getLogomarca();

       // Cidade getCidade();

        String getComplemento();
    }
}