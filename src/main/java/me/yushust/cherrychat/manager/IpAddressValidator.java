package me.yushust.cherrychat.manager;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddressValidator implements StringValidator {

    private static final String NUMBER_REGEX = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
    private static final Pattern IP_PATTERN = Pattern.compile(
            NUMBER_REGEX + "\\." + NUMBER_REGEX + "\\." + NUMBER_REGEX + "\\." + NUMBER_REGEX
    );

    @Override
    public boolean isValid(String value) {
        return IP_PATTERN.matcher(value).matches();
    }

    @Override
    public boolean anyValid(String value) {
        return IP_PATTERN.matcher(value).groupCount() > 0;
    }

    @Override
    public String replaceAll(String value, String replaceValue) {
        Matcher matcher = IP_PATTERN.matcher(value);
        for(int i = 0; i < matcher.groupCount(); i++) {
            String group = matcher.group(i);
            value = value.replace(group, Strings.repeat(replaceValue, group.length()));
        }
        return value;
    }
}
