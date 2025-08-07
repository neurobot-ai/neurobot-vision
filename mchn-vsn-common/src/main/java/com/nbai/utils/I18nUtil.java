package com.nbai.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
public class I18nUtil {

    @Autowired
    private final MessageSource messageSource;

    public I18nUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String msgKey, Object[] args) {
        return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
    }

    public String getMessage(String msgKey) {
//        Locale locale = LocaleContextHolder.getLocale();
        Locale locale = Locale.getDefault();
//        Locale locale=new Locale("zh","CN");
        return messageSource.getMessage(msgKey, null, locale);
    }

    public Integer getCode(String msgKey) {
//        Locale locale = LocaleContextHolder.getLocale();
        Locale locale = Locale.getDefault();
//        Locale locale=new Locale("zh","CN");
        return Integer.valueOf(messageSource.getMessage(msgKey, null, locale));
    }

    /**
     * 返回language
     */
    public static String getLanguage(){
        return Locale.getDefault().getLanguage();
    }
}