package util;

public class CpfCnpjValidator {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isCpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        cpf = cpf.replaceAll("\\D+", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (isDigitosIguais(cpf)) {
            return false;
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    public static boolean isCnpjValido(String cnpj) {
        if (cnpj == null) {
            return false;
        }

        cnpj = cnpj.replaceAll("\\D+", "");

        if (cnpj.length() != 14) {
            return false;
        }

        if (isDigitosIguais(cnpj)) {
            return false;
        }

        Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    private static boolean isDigitosIguais(String input) {
        char[] digitos = input.toCharArray();
        boolean digitosIguais = true;
        char digitoAnterior = digitos[0];

        for (int indice = 1; indice < digitos.length; indice++) {
            if (digitos[indice] != digitoAnterior) {
                digitosIguais = false;
                break;
            }

            digitoAnterior = digitos[indice];
        }

        return digitosIguais;
    }
}
