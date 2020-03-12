package lib.model.produto;

import lib.model.comercial.ItemProduto;
import lib.model.grupo.Grupo;
import lib.model.marca.Marca;
import lib.model.pessoa.fornecedor.Fornecedor;

import java.util.Date;

public class Produto {

    Integer id;

    private String nome;

    private Double quantidadeMinima;

    private Double quantidadeEstoque;

    private Double valor;

    private Date dataUltimaAlteracao;

    private Date dataCadastro;

    private Date dataUltimaCompra;

    private Date dataUltimaVenda;

    private String codigoBarras;

    private Marca marca;

    private Grupo grupo;

    private String unidadeMedida;

    private String referencia;

    private Double precoCompra;

    private Fornecedor ultimoFornecedor;

    private Boolean ativo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(Double quantidade_minima) {
        this.quantidadeMinima = quantidade_minima;
    }

    public Double getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Double quantidade) {
        this.quantidadeEstoque = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public Produto buildProduto(ItemProduto itemProduto){
        Produto p = new Produto();
        p.setId(itemProduto.getId());
        p.setNome(itemProduto.getNome());
        p.setUnidadeMedida(itemProduto.getUnidadeMedida());
        p.setMarca(itemProduto.getMarca());
        p.setGrupo(itemProduto.getGrupo());
        p.setValor(itemProduto.getValor());
        p.setPrecoCompra(itemProduto.getPrecoCompra());
        p.setReferencia(itemProduto.getReferencia());
        p.setDataCadastro(itemProduto.getDataCadastro());
        p.setCodigoBarras(itemProduto.getCodigoBarras());
        p.setQuantidadeMinima(itemProduto.getQuantidadeMinima());
        p.setQuantidadeEstoque(itemProduto.getQuantidadeEstoque());
        p.setDataCadastro(itemProduto.getDataCadastro());
        p.setDataUltimaAlteracao(itemProduto.getDataUltimaAlteracao());
        p.setAtivo(itemProduto.getAtivo());
        return p;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }


    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Fornecedor getUltimoFornecedor() {
        return ultimoFornecedor;
    }

    public void setUltimoFornecedor(Fornecedor ultimoFornecedor) {
        this.ultimoFornecedor = ultimoFornecedor;
    }

    public Date getDataUltimaCompra() {
        return dataUltimaCompra;
    }

    public void setDataUltimaCompra(Date dataUltimaCompra) {
        this.dataUltimaCompra = dataUltimaCompra;
    }

    public Date getDataUltimaVenda() {
        return dataUltimaVenda;
    }

    public void setDataUltimaVenda(Date dataUltimaVenda) {
        this.dataUltimaVenda = dataUltimaVenda;
    }
}
