package me.yushust.cherrychat.manager;

import lombok.Getter;
import me.yushust.cherrychat.util.Texts;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

/*
 * Code based on AuthMe command logging filter
 */
@Getter
public class CommandLoggingFilter implements Filter {

    private Result onMismatch = Result.NEUTRAL;
    private Result onMatch = Result.NEUTRAL;

    public Result filter(LogEvent record) {
        try {
            if(record != null && record.getMessage() != null) {
                String npe = record.getMessage().getFormattedMessage().toLowerCase();
                return getResult(npe);
            } else {
                return Result.NEUTRAL;
            }
        } catch (NullPointerException var3) {
            return Result.NEUTRAL;
        }
    }

    private Result getResult(String npe) {
        if(!npe.contains(" issued server command: ")) return Result.NEUTRAL;
        if(Texts.containsAny(npe, "/message ", "/msg", "/tell", "/whisper")) {
            return Result.DENY;
        }
        return Result.NEUTRAL;
    }

    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object... arg4) {
        try {
            if(message == null) {
                return Result.NEUTRAL;
            } else {
                String npe = message.toLowerCase();
                return getResult(npe);
            }
        } catch (NullPointerException var7) {
            return Result.NEUTRAL;
        }
    }

    public Result filter(Logger arg0, Level arg1, Marker arg2, Object message, Throwable arg4) {
        try {
            if(message == null) {
                return Result.NEUTRAL;
            } else {
                String npe = message.toString().toLowerCase();
                return getResult(npe);
            }
        } catch (NullPointerException var7) {
            return Result.NEUTRAL;
        }
    }

    public Result filter(Logger arg0, Level arg1, Marker arg2, Message message, Throwable arg4) {
        try {
            if(message == null) {
                return Result.NEUTRAL;
            } else {
                String npe = message.getFormattedMessage().toLowerCase();
                return getResult(npe);
            }
        } catch (NullPointerException var7) {
            return Result.NEUTRAL;
        }
    }

}
