package dev.suvera.scim2.schema.filter;

import dev.suvera.scim2.schema.data.ErrorRecord;
import dev.suvera.scim2.schema.ex.ScimException;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class ScimFilterErrorListener extends BaseErrorListener {
    public static final ScimFilterErrorListener INSTANCE = new ScimFilterErrorListener();

    @Override
    public void syntaxError(
            Recognizer<?, ?> recognizer, Object offendingSymbol,
            int line, int charPositionInLine,
            String msg,
            RecognitionException e
    ) {
        ErrorRecord error = new ErrorRecord();
        error.setStatus(400);
        error.setDetail("Filter syntax error at line " + line + " char " + charPositionInLine + ": " + msg + ", caused by " + e.getMessage());
        error.setScimType("invalidFilter");
        throw new ScimException(error);
    }
}
