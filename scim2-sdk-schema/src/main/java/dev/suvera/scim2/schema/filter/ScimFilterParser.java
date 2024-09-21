package dev.suvera.scim2.schema.filter;

import dev.suvera.scim2.schema.filter.parser.SCIMFilterLexer;
import dev.suvera.scim2.schema.filter.parser.SCIMFilterParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class ScimFilterParser {

    public static void parse(String input, ScimFilterListener listener) {
        // Create a CharStream from your input
        CharStream charStream = CharStreams.fromString(input, "UTF-8");

        // Create a lexer
        SCIMFilterLexer lexer = new SCIMFilterLexer(charStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ScimFilterErrorListener.INSTANCE);

        // Create a token stream from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create a parser
        SCIMFilterParser parser = new SCIMFilterParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ScimFilterErrorListener.INSTANCE);

        // Start parsing from the 'expression' rule of the grammar, as this is parent of all other rules.
        ParseTree tree = parser.expression();

        // Walk the parse tree using a listener and record the results
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new ScimFilterListenerInternal(listener), tree);
    }

}
