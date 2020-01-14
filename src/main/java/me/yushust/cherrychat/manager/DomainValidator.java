package me.yushust.cherrychat.manager;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainValidator implements StringValidator {

    private static final Pattern DOMAIN_PATTERN = Pattern.compile("^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$");

    @Override
    public boolean isValid(String value) {
        return DOMAIN_PATTERN.matcher(value).matches();
    }

    @Override
    public boolean anyValid(String value) {
        return DOMAIN_PATTERN.matcher(value).groupCount() > 0;
    }

    @Override
    public String replaceAll(String value, String toReplace) {
        Matcher matcher = DOMAIN_PATTERN.matcher(value);
        for(int i = 0; i < matcher.groupCount(); i++) {
            String group = matcher.group(i);
            value = value.replace(group, Strings.repeat(toReplace, group.length()));
        }
        return value;
    }
}
