package lib.model.comercial.frete;

public enum TipoFrete {

    CIF(),
    FOB();

    TipoFrete(){};

    public static TipoFrete getByOrdinal(Integer i) {
        switch (i) {
            case 0:
                return CIF;
            case 1:
                return FOB;
        }
        return CIF;
    }

}
