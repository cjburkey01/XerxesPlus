package com.cjburkey.xerxesplus.util;

import java.text.DecimalFormat;

public class TextHelper {
	
	private static DecimalFormat decFormatter;
	
	public static String getFormatted(int value) {
		if (decFormatter == null) {
			decFormatter = new DecimalFormat("###,###");
		}
		String output = decFormatter.format(value);
		return output;
	}
	
}