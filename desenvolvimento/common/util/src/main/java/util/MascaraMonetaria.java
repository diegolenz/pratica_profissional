package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MascaraMonetaria extends PlainDocument {
    private Integer maxLength;
    public void NumerosDecimais(Integer maxLength) {
        this.maxLength = maxLength;
    }
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        String texto = getText(0, getLength());
        if (texto.length() < this.maxLength) {
            remove(0, getLength());
            StringBuffer strBuf = new StringBuffer(texto.replaceAll(",", "") + str);
            if (strBuf.length() < 3) {
                strBuf.insert(0, ",");
            } else {
                strBuf.insert(strBuf.length() - 2, ",");
            }
            super.insertString(0, strBuf.toString(), a);
        }
    }
}