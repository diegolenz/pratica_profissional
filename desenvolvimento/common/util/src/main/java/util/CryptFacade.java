/**
 * (C) 2015-2018 JL INFORMATICA LTDA ME
 * CNPJ 14.928.793/0001-18
 * www.jlinformatica.net.br
 * (45) 3559-1534
 */

package util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.security.spec.KeySpec;

/**
 *
 * @author Lucas Dillmann
 */
public class CryptFacade {

    static {
        try {
            Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, Boolean.FALSE);
        } catch (Exception ex) {
        }
    }

    //private static final Logger logger = LoggerFactory.getLogger(CryptFacade.class);

    private static final String LEGACY_SALT_2048 = "{0S73T$40{.^|(S9/7M/-9#3%/1*~h}97|0fPNhi449263ipB]y<Va%_8g0h4vr3}w'im4\"8" +
            "2'@6QQc2uL#K1{21%5681_5k504=R7X8\"48W_9\"N(ouA4_7aJp82]aVF{4{c2_|2PCE\\lPeMvK! $6613898e4Z{l0P>19o#]4,&+" +
            " 9mq*K31?^36582k9#0),4l6'(5?5200M\\S4?S674A}v1<SjL^P21$7#]8t!A2w462w8017bnu-SDNC370l";

    private static final String CHAVE_2048 = ";?2[@+Jj*:a78?&752; !7,|(*4}08:,$187?}79$9B\\jP70''860$WF}s,%-_2B/#$32<8" +
            "'255w+&3z>\"|0&9z9u {)!D~|<:(T355=\"/h)D eT1>=G.5%668$/2^53gQ.(CiU,J18@8)72/5a60|2H>%]&4#1%6y6=5U)Z;:% 20" +
            "5il6,|$^k).a9?oc~Ii/4B+kgC[B]]!U}0\"-k\\.631k=#+}\"]>9?]}Z!ak52,[||0OXS2!~6:|:n2.{58P";

    private static boolean use2048 = true;
    private static SecretKey legacySecretKey;

    public static void use2048BitKey() {
        use2048 = true;
    }

    public static void use128BitKey() {
        use2048 = false;
    }

    public static String encrypt(String input) {
        return encrypt(input, false);
    }

    public static String encrypt(String input, boolean forceLegacy) {
        try {

            String salt = forceLegacy ? LEGACY_SALT_2048 : RandomStringUtils.randomAlphanumeric(15);

            // Obtem chave secreta de 512 bits
            SecretKey secretKey = getSecretKey(salt);

            // Instancia cipher e chave-secreta utilizando o algoritmo AES
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Configura para o modo de encriptação
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));

            // Retorna um Base64 do input criptografado
            String value = Base64.encodeBase64String(
                    cipher.doFinal(input.getBytes("UTF-8"))
            );

            if(forceLegacy) return value;
            else return "2$" + Base64.encodeBase64String(
                    (salt + value).getBytes("UTF-8")
            );

        } // Em caso de alguma falha (das muitas possiveis) registra o erro e retorna nulo
        catch (Throwable ex) {
//            logger.error("Falha ao criptografar entrada", ex);
            return null;
        }
    }

    public static String decrypt(String input) {
        try {

            if(input.startsWith("2$")) {
                String inputPart = input.substring(2);
                String decodedInput = new String(Base64.decodeBase64(inputPart));
                String salt = decodedInput.substring(0, 15);
                String value = decodedInput.substring(15);

                // Obtem chave secreta de 512 bits
                SecretKey secretKey = getSecretKey(salt);

                // Instancia cipher e chave-secreta utilizando o algoritmo AES
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

                // Configura para o modo de encriptação
                cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));

                // Retorna um Base64 do input criptografado
                return new String(cipher.doFinal(
                        Base64.decodeBase64(value)
                ), "UTF-8");

            }

            else return legacyDecrypt(input);


        } // Em caso de alguma falha registra o erro e retorna nulo
        catch (Throwable ex) {
//            logger.error("Falha ao descriptografar entrada", ex);
            return null;
        }
    }

    private static String legacyDecrypt(String input) {
        try {

            // Obtem chave secreta de 512 bits
            SecretKey secretKey = getSecretKey(LEGACY_SALT_2048);

            // Instancia cipher e chave-secreta utilizando o algoritmo AES
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Configura para o modo de encriptação
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));

            // Retorna um Base64 do input criptografado
            return new String(cipher.doFinal(
                    Base64.decodeBase64(input)
            ), "UTF-8");

        } // Em caso de alguma falha registra o erro e retorna nulo
        catch (Throwable ex) {
            return null;
        }
    }

    public static String md5(String input) {
        return DigestUtils.md5Hex(input);
    }

    private static SecretKey getSecretKey(String salt) {

        if(LEGACY_SALT_2048.equals(salt) && legacySecretKey != null)
            return legacySecretKey;

        try {
            // Obtem instância da fábrica de chaves
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            // Configura as especificações para chave de 512 bits
            KeySpec spec = new PBEKeySpec(CHAVE_2048.toCharArray(), salt.getBytes("UTF-8"), 1024, 256);

            // Gera chave secreta e retorna
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            if(LEGACY_SALT_2048.equals(salt)) legacySecretKey = secret;
            return secret;
        } // Em caso de alguma falha (das muitas possiveis) registra o erro e retorna nulo
        catch (Throwable ex) {
            //logger.error("Erro ao gerar chave secreta", ex);
            return null;
        }
    }

}
