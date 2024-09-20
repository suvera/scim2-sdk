package dev.suvera.scim2.schema.filter;

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
        throw new RuntimeException("Syntax error at line " + line + " char " + charPositionInLine + ": " + msg);
    }
}
