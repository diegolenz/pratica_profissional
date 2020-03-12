package util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Build {

	public static String builDataSimples(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormat.format(date);
	}

	public static double builDoubleDuasCasasDecimais(double precoDouble) {
		DecimalFormat fmt = new DecimalFormat("0.00");
		String string = fmt.format(precoDouble);
		String[] part = string.split("[,]");
		String string2 = part[0]+"."+part[1];
		double preco = Double.parseDouble(string2);
		return preco;
	}

}
