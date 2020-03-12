package lib.model.financeiro;

public interface ModoParcelamento  {

    enum TipoPainel {
        A_VISTA, SIMPLES
    }
/*
    interface Proxy {

        void setParcelasEditaveis(List<ParcelaCobranca> parcelas, boolean permiteAlterarData, boolean permiteAlterarValor);

        void setParcelas(List<ParcelaCobranca> parcelas, TipoPainel tipoPainel);

        void setDadosParcelamento(DadosParcela parcela);

        Pessoa getSacado();

        void setTituloProximaMensalidade(Titulo titulo);

        Cobranca getCobranca();
    }

    void setOpcoesParcelamento(List<DadosParcela> opcoes);

    void calcularParcelas();

    void iniciarListeners();

    boolean isModoValidoParaCartaoMagnetico();

    void notificarAlteracaoSacado();

    void removerParcela(ParcelaCobranca parcela);
    */

}