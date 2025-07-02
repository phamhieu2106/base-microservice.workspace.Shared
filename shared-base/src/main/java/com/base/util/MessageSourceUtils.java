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
    private static String messagesBasename;

    private static final Locale LOCALE_VN = new Locale("vi", "VN");
    private static final Locale LOCALE_EN = new Locale("en", "US");

    @Autowired
    public MessageSourceUtils(MessageSource messageSource,
                              @Value("${spring.messages.basename:vi}") String messagesBasename) {
        MessageSourceUtils.messageSource = messageSource;
        MessageSourceUtils.messagesBasename = messagesBasename;
    }

    public static String getMessage(String key, Object... args) {
        try {
            Locale locale = "en".equalsIgnoreCase(messagesBasename) ? LOCALE_EN : LOCALE_VN;
            return messageSource.getMessage(key, args, locale);
        } catch (Exception ex) {
            return key;
        }
    }
}
