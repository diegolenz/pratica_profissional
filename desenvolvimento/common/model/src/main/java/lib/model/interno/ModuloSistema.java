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
    PRODUTOS(CategoriaModuloSistema.ESTOQUE, "Produtos", false),
    SERVICOS(CategoriaModuloSistema.ESTOQUE, "Serviços", false),
    MARCAS(CategoriaModuloSistema.ESTOQUE, "Marcas", false),
    GRUPOS_PRODUTOS_SERVICOS(CategoriaModuloSistema.ESTOQUE, "Grupos de produtos/serviços", false),

    //PESSOAS
    FORNECEDORES(CategoriaModuloSistema.PESSOAS, "Forncededores", false),
    CLIENTES(CategoriaModuloSistema.PESSOAS, "Clientes", false),

    //COMERCIAL
    COMPRAS(CategoriaModuloSistema.COMERCIAL, "Compras", false),
    VENDAS(CategoriaModuloSistema.COMERCIAL, "Vendas", false),

    //FINANCEIRO
    CONTAS_PAGAR(CategoriaModuloSistema.FINANCIRO, "Contas a pagar", false),
    CONTAS_RECEBER(CategoriaModuloSistema.FINANCIRO, "Contas a receber", false),
    ALTERAR_DATA_VENCIMENTO_CONTA_PAGAR(CategoriaModuloSistema.FINANCIRO, "Alterar data de vencimento das contas a pagar", true),
    ALTERAR_FORMA_PAGAMENTO_CONTA_PAGAR(CategoriaModuloSistema.FINANCIRO, "Alterar forma de pagamento das contas a pagar", true),
    ALTERAR_DATA_VENCIMENTO_CONTA_RECEBER(CategoriaModuloSistema.FINANCIRO, "Alterar data de vencimento das contas a receber", true),
    ALTERAR_FORMA_PAGAMENTO_CONTA_RECEBER(CategoriaModuloSistema.FINANCIRO, "Alterar forma de pagamento das contas a receber", true),
    CAIXAS(CategoriaModuloSistema.FINANCIRO, "Caixas", false),
    FORMAS_PAGAMENTO(CategoriaModuloSistema.FINANCIRO, "Formas de pagamentos", false),
    CONDICOES_PAGAMENTO(CategoriaModuloSistema.FINANCIRO, "Condições de pagamento", false),


    //Sistema
    SISTEMA_GRUPO_OPERADORES(CategoriaModuloSistema.SISTEMA, "Grupos de operadores", false),
    SISTEMA_OPERADORES(CategoriaModuloSistema.SISTEMA, "Operadores", false),
    SISTEMA_CONFIG(CategoriaModuloSistema.SISTEMA, "Configuração geral", true),
    // TROCAR_SENHA(CategoriaModuloSistema.SISTEMA, "Trocar senha"),
    CONFIRMACAO(CategoriaModuloSistema.SISTEMA, "Confirmação de senha", true),

    //CADASTROS
    ENDERECO(CategoriaModuloSistema.CADASTROS, "Endereços", false),

    //OS
    OS(CategoriaModuloSistema.OS, "Ordens de serviços", false),
    MODELOS(CategoriaModuloSistema.OS, "Modelos", false);


    private final CategoriaModuloSistema categoria;
    private final String descricao;
    // private final Boolean nescecitaPermissao;
    private final boolean somenteGravacao;
//    private final boolean permiteModoLeituraGravacao;

    ModuloSistema(CategoriaModuloSistema categoria, String descricao, Boolean somenteGravacao) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.somenteGravacao = somenteGravacao;
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
