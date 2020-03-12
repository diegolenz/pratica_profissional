package lib.model.comercial;

import lib.model.os.OrdemServico;
import lib.model.produto.Produto;

public class ItemProduto extends Produto{

    private Double quantidade ;

    private Double descontoUnitario;

    private Double acrescimoUnitario;

    private Double valorUnitario;

    private Compra compra;

    private VendaProduto venda;

    private Double valorTotal ;

    private Double valorRateio ;

    private OrdemServico os;

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double qtd) {
        this.quantidade = qtd;
    }

    public Double getDescontoUnitario() {
        return descontoUnitario;
    }

    public void setDescontoUnitario(Double descontoUnitario) {
        this.descontoUnitario = descontoUnitario;
    }

    public Double getAcrescimoUnitario() {
        return acrescimoUnitario;
    }

    public void setAcrescimoUnitario(Double acrescimoUnitario) {
        this.acrescimoUnitario = acrescimoUnitario;
    }

    public Double getValorTotal() {
        return (getValorUnitario() * getQuantidade()) + (getAcrescimoUnitario() * getQuantidade()) - (getDescontoUnitario() * getQuantidade()) ;
    }

    public Double getTotaisAcrescimos(){
        return getAcrescimoUnitario() * getQuantidade();
    }

    public Double getTotaisCustoUn(){
        if (valorUnitario == null || quantidade == null || descontoUnitario==null || acrescimoUnitario == null)
            return 0D;
        Double v = (getValorUnitario() * getQuantidade()) - (getDescontoUnitario() * getQuantidade()) + (getAcrescimoUnitario() * getQuantidade());
        return v;
    }

    public Double getTotal(){
        return getTotaisCustoUn() - getTotaisDescontos() + getTotaisAcrescimos();
    }

    public Double getTotaisDescontos(){
        return getDescontoUnitario() * getQuantidade();
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public void buildItem(Produto p){
        this.setId(p.getId());
        this.setNome(p.getNome());
        this.setAtivo(p.getAtivo());
        this.setMarca(p.getMarca() != null ? p.getMarca(): null);
        this.setGrupo(p.getGrupo() != null ? p.getGrupo(): null);
        this.setDataCadastro(p.getDataCadastro());
        this.setDataUltimaAlteracao(p.getDataUltimaAlteracao());
        this.setValor(p.getValor());
        this.setPrecoCompra(p.getPrecoCompra());
      // this.setValorUnitario(p.getPrecoCompra());
        this.setUnidadeMedida(p.getUnidadeMedida());
        this.setCodigoBarras(p.getCodigoBarras());
        this.setQuantidadeEstoque(p.getQuantidadeEstoque());
        this.setQuantidadeMinima(p.getQuantidadeMinima());
        this.setAtivo(p.getAtivo());
        this.setReferencia(p.getReferencia());
        this.setDataUltimaAlteracao(p.getDataUltimaAlteracao());
    }

    public Double getValorRateio() {
        return valorRateio;
    }

    public void setValorRateio(Double valorRateio) {
        this.valorRateio = valorRateio;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public VendaProduto getVenda() {
        return venda;
    }

    public void setVenda(VendaProduto venda) {
        this.venda = venda;
    }

    public OrdemServico getOs() {
        return os;
    }

    public void setOs(OrdemServico os) {
        this.os = os;
    }

    public ItemProduto() {
        this.quantidade = 0D;
        this.descontoUnitario = 0D;
        this.acrescimoUnitario = 0D;
        this.valorUnitario = 0D;
        this.compra = null;
        this.venda = null;
        this.valorTotal = 0D;
        this.valorRateio = 0D;
        this.os = null;
    }
}
