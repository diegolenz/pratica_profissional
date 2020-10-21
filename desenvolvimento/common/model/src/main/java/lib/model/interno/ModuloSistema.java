/**
 * (C) 2015-2018 JL INFORMATICA LTDA ME
 * CNPJ 14.928.793/0001-18
 * www.jlinformatica.net.br
 * (45) 3559-1534
 */
package lib.model.interno;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enumerçao de controle de módulos e permissoes do sistema
 *
 * @author Lucas Dillmann
 * @since 1.0
 */
public enum ModuloSistema implements Serializable {

    //produtos
    PRODUTOS(CategoriaModuloSistema.ESTOQUE, "Produtos", false, true),
    SERVICOS(CategoriaModuloSistema.ESTOQUE, "Serviços", false, true),
    MARCAS(CategoriaModuloSistema.ESTOQUE, "Marcas", false, true),
    GRUPOS_PRODUTOS_SERVICOS(CategoriaModuloSistema.ESTOQUE, "Grupos de produtos/serviços", false, true),

    //PESSOAS
    FORNECEDORES(CategoriaModuloSistema.PESSOAS, "Forncededores", false, true),
    CLIENTES(CategoriaModuloSistema.PESSOAS, "Clientes", false, true),

    //COMERCIAL
    COMPRAS(CategoriaModuloSistema.COMERCIAL, "Compras", false, true),
    VENDAS(CategoriaModuloSistema.COMERCIAL, "Vendas", false, true),

    //FINANCEIRO
    CONTAS_PAGAR(CategoriaModuloSistema.FINANCIRO, "Contas a pagar", false, true),
    CONTAS_RECEBER(CategoriaModuloSistema.FINANCIRO, "Contas a receber", false, true),
    ALTERAR_DATA_VENCIMENTO_CONTA_PAGAR(CategoriaModuloSistema.FINANCIRO, "Alterar data de vencimento das contas a pagar", true, true),
    ALTERAR_FORMA_PAGAMENTO_CONTA_PAGAR(CategoriaModuloSistema.FINANCIRO, "Alterar forma de pagamento das contas a pagar", true, true),
    ALTERAR_DATA_VENCIMENTO_CONTA_RECEBER(CategoriaModuloSistema.FINANCIRO, "Alterar data de vencimento das contas a receber", true, true),
    ALTERAR_FORMA_PAGAMENTO_CONTA_RECEBER(CategoriaModuloSistema.FINANCIRO, "Alterar forma de pagamento das contas a receber", true, true),
    CAIXAS(CategoriaModuloSistema.FINANCIRO, "Caixas", false, false), //alterar nescecita permissao caso for implementado os caixas
    FORMAS_PAGAMENTO(CategoriaModuloSistema.FINANCIRO, "Formas de pagamentos", false, true),
    CONDICOES_PAGAMENTO(CategoriaModuloSistema.FINANCIRO, "Condições de pagamento", false, true),


    //Sistema
    SISTEMA_GRUPO_OPERADORES(CategoriaModuloSistema.SISTEMA, "Grupos de operadores", false, true),
    SISTEMA_OPERADORES(CategoriaModuloSistema.SISTEMA, "Operadores", false, true),
    SISTEMA_CONFIG(CategoriaModuloSistema.SISTEMA, "Configuração geral", true, true),
    // TROCAR_SENHA(CategoriaModuloSistema.SISTEMA, "Trocar senha"),
    CONFIRMACAO(CategoriaModuloSistema.SISTEMA, "Confirmação de senha", true, false),
    SWING(CategoriaModuloSistema.SISTEMA, "SWING", true, false),


    //CADASTROS
    ENDERECO(CategoriaModuloSistema.CADASTROS, "Endereços", false, true),

    //OS
    OS(CategoriaModuloSistema.OS, "Ordens de serviços", false, true),
    MODELOS(CategoriaModuloSistema.OS, "Modelos", false, true);


    private final CategoriaModuloSistema categoria;
    private final String descricao;

    private final boolean somenteGravacao;
    private final Boolean nescecitaPermissao;
//    private final boolean permiteModoLeituraGravacao;

    ModuloSistema(CategoriaModuloSistema categoria, String descricao, Boolean somenteGravacao, Boolean nescecitaPermissao) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.somenteGravacao = somenteGravacao;
        this.nescecitaPermissao = nescecitaPermissao;
//        this.permiteModoSomenteLeitura = permiteModoSomenteLeitura;
//        this.permiteModoLeituraGravacao = permiteModoLeituraGravacao;
    }

//    public boolean isVirtual() {
//        return categoria == CategoriaModuloSistema.VIRTUAL;
//    }

    public CategoriaModuloSistema getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return categoria.getDescricao() + " - " + descricao;
    }

    public String getDescricaoSimplificada() {
        return descricao;
    }

    public boolean isSomenteGravacao() {
        return somenteGravacao;
    }

    public Boolean getNescecitaPermissao() {
        return nescecitaPermissao;
    }

    //    public boolean isModoSomenteLeituraPermitido() {
//        return permiteModoSomenteLeitura;
//    }
//
//    public boolean isModoLeituraGravacaoPermitido() {
//        return permiteModoLeituraGravacao;
//    }

    public static final List<ModuloSistema> getAll() {
        return Arrays.stream(values())
                // .filter(modulo -> !modulo.isVirtual())
                .sorted(Comparator.comparing(ModuloSistema::getDescricao))
                .collect(Collectors.toList());
    }

}
