package lib.model.servico;

import lib.model.comercial.VendaServico;
import lib.model.os.OrdemServico;

public class ItemServico extends Servico {
    private Double quantidade;

    private Double descontoUnitario;

    private Double acrescimoUnitario;

    private Double valorUnitario;

    private VendaServico venda;

    private OrdemServico os;

    private Double valorTotal;

    private Double valorRateio;

    public void buildItem(Servico p){
        this.setId(p.getId());
        this.setNome(p.getNome());
        this.setGrupo(p.getGrupo() != null ? p.getGrupo(): null);
        this.setValor(p.getValor());
        this.setAtivo(p.isAtivo());
    }

    public Double getTotaisCustoUn(){
        if (valorUnitario == null || quantidade == null || descontoUnitario==null || acrescimoUnitario == null)
            return 0D;
        Double v = (getValorUnitario() * getQuantidade()) - (getDescontoUnitario() * getQuantidade()) + (getAcrescimoUnitario() * getQuantidade());
        return v;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
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

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public VendaServico getVenda() {
        return venda;
    }

    public void setVenda(VendaServico venda) {
        this.venda = venda;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorRateio() {
        return valorRateio;
    }

    public void setValorRateio(Double valorRateio) {
        this.valorRateio = valorRateio;
    }

    public Double getTotaisDescontos(){
        return getDescontoUnitario() * getQuantidade();
    }

    public Double getTotaisAcrescimos(){
        return getAcrescimoUnitario() * getQuantidade();
    }

    public OrdemServico getOs() {
        return os;
    }

    public void setOs(OrdemServico os) {
        this.os = os;
    }
}