package i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class Execute_i18n {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String language;
        String country;

            language = new String("de");
            country = new String("DE");

        Locale currentLocale;
        ResourceBundle messages;

        currentLocale = new Locale(language, country);

        messages = ResourceBundle.getBundle("MessagesBundle",
                                           currentLocale);
        System.out.println(messages.getString("hello"));
	}

}
