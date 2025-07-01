package com.base.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageSourceUtils {
    private static MessageSource messageSource;

    private static final Locale LOCALE_VN = new Locale("vi", "VN");
    private static final Locale LOCALE_EN = new Locale("en", "US");
    private static String locale = "vi";

    @Autowired
    public void setMessageSource(MessageSource messageSource, @Value("${spring.messages.basename:vi}") String locale) {
        MessageSourceUtils.locale = locale;
        MessageSourceUtils.messageSource = messageSource;
    }

    public static String getMessage(String key, Object... args) {
        try {
            return messageSource.getMessage(key, args, "en".equalsIgnoreCase(locale) ? LOCALE_EN : LOCALE_VN);
        } catch (Exception ex) {
            return key;
        }
    }
}
