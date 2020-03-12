/**
 * (C) 2015-2018 JL INFORMATICA LTDA ME
 * CNPJ 14.928.793/0001-18
 * www.jlinformatica.net.br
 * (45) 3559-1534
 */
package util;

import java.math.BigDecimal;

/**
 * Created by Lucas on 20/06/2016.
 */
public class DoubleFacade {

    public static Double fixScale(Double input) {
        return fixScale(input, 2);
    }

    /**
     * Método de correção de casas decimais de variaveis double
     *
     * @param input Valor de entrada
     * @param precision Número de casas de precisão
     * @return Valor corrigido
     * @author Lucas Dillmann
     * @since 1.3.0
     */
    public static Double fixScale(Double input, Integer precision) {
        if(input == null) return null;
        return fixScaleAsDecimal(input, precision).doubleValue();
    }

    /**
     * Método de correção de casas decimais de variaveis double
     *
     * @param input Valor de entrada
     * @param precision Número de casas de precisão
     * @return Valor corrigido em BigDecimal
     * @author Lucas Dillmann
     * @since 1.3.1
     */
    public static BigDecimal fixScaleAsDecimal(Double input, Integer precision) {
        if(input == null) return null;
        return BigDecimal.valueOf(input).setScale(precision, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Método de correção de casas decimais de variaveis double
     *
     * @param input Valor de entrada
     * @return Valor corrigido em BigDecimal
     * @author Lucas Dillmann
     * @since 1.3.1
     */
    public static BigDecimal fixScaleAsDecimal(Double input) {
        return fixScaleAsDecimal(input, 2);
    }

    /**
     * Método de corte de casas decimais de variaveis double
     *
     * @param input Valor de entrada
     * @param precision Número de casas de precisão
     * @return Valor corrigido
     * @author Lucas Dillmann
     * @since 1.3.0
     */
    public static Double truncate(Double input, Integer precision) {
        if(input == null) return null;
        if (Double.isNaN(input)) return null;
        return BigDecimal.valueOf(input).setScale(precision, BigDecimal.ROUND_DOWN).doubleValue();
    }

}
