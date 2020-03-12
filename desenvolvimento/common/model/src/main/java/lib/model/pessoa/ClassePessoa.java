package lib.model.pessoa;

public enum ClassePessoa {

    CLIENTE("", 0),
    FORNECEDOR("",1),
    CLIENTE_FORNECEDOR("CLIENTE E FORNECEDOR",2);

    private  Integer n ;
    private  String tipo ;

    ClassePessoa(String tipo, Integer n){
        this.n=n;
        this.tipo=tipo;

    }


}
