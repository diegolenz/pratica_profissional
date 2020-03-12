package lib.model.comercial;

public class ConfigVenda {
    private Integer id;

    private  Integer numProd;

    private Integer numServico;

    private Integer serieServico;

    private Integer serieProduto;

    private String modeloServico;

    private String modeloProduto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumProd() {
        return numProd;
    }

    public void setNumProd(Integer numProd) {
        this.numProd = numProd;
    }

    public Integer getNumServico() {
        return numServico;
    }

    public void setNumServico(Integer numServico) {
        this.numServico = numServico;
    }

    public Integer getSerieServico() {
        return serieServico;
    }

    public void setSerieServico(Integer serieServico) {
        this.serieServico = serieServico;
    }

    public Integer getSerieProduto() {
        return serieProduto;
    }

    public void setSerieProduto(Integer serieProduto) {
        this.serieProduto = serieProduto;
    }

    public String getModeloServico() {
        return modeloServico;
    }

    public void setModeloServico(String modeloServico) {
        this.modeloServico = modeloServico;
    }

    public String getModeloProduto() {
        return modeloProduto;
    }

    public void setModeloProduto(String modeloProduto) {
        this.modeloProduto = modeloProduto;
    }
}
