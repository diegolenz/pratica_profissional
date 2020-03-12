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
    PRODUTOS(CategoriaModuloSistema.ESTOQUE, "Produtos"),
    SERVICOS(CategoriaModuloSistema.ESTOQUE,"Serviços"),
    MARCAS(CategoriaModuloSistema.ESTOQUE, "Marcas"),
    GRUPOS_PRODUTOS_SERVICOS(CategoriaModuloSistema.ESTOQUE, "Grupos de produtos/serviços"),

    //PESSOAS
    FORNECEDORES(CategoriaModuloSistema.PESSOAS, "Forncededores"),
    CLIENTES(CategoriaModuloSistema.PESSOAS, "Clientes"),

    //COMERCIAL
    COMPRAS(CategoriaModuloSistema.COMERCIAL, "Compras"),
    VENDAS(CategoriaModuloSistema.COMERCIAL, "Vendas"),

    //FINANCEIRO
    CONTAS_PAGAR(CategoriaModuloSistema.FINANCIRO, "Contas a pagar"),
    CONTAS_RECEBER(CategoriaModuloSistema.FINANCIRO, "Contas a receber"),
    CAIXAS(CategoriaModuloSistema.FINANCIRO, "Caixas"),
    FORMAS_PAGAMENTO(CategoriaModuloSistema.FINANCIRO, "Formas de pagamentos"),
    CONDICOES_PAGAMENTO(CategoriaModuloSistema.FINANCIRO, "Condições de pagamento"),

    //Sistema
    SISTEMA_GRUPO_OPERADORES(CategoriaModuloSistema.SISTEMA, "Grupos de operadores"),
    SISTEMA_OPERADORES(CategoriaModuloSistema.SISTEMA, "Operadores"),

    //CADASTROS
    ENDERECO(CategoriaModuloSistema.CADASTROS, "Endereços"),

    //OS
    OS(CategoriaModuloSistema.OS, "Ordens de serviços");


    private final CategoriaModuloSistema categoria;
    private final String descricao;
//    private final boolean permiteModoSomenteLeitura;
//    private final boolean permiteModoLeituraGravacao;

    ModuloSistema(CategoriaModuloSistema categoria, String descricao) {
        this.categoria = categoria;
        this.descricao = descricao;
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

    @Override
    public String toString() {
        return getDescricao();
    }

}
