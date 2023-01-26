package org.semanticweb.rulewerk.language.server.util;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.semanticweb.rulewerk.parser.javacc.Token;

public final class DocumentUtil {

	private DocumentUtil() {
	}

	public static Range toRange(final Token token) {
//		A range in a text document expressed as (zero-based) start and end positions. 
		final Position startPosition = new Position(toZeroBasedPosition(token.beginLine),
				toZeroBasedPosition(token.endColumn));
//		The end position is exclusive. If you want to specify a range that contains a line including the line ending character(s) 
//		then use an end position denoting the start of the next line.
		final Position endPosition = new Position(toZeroBasedPosition(token.endLine),
				toZeroBasedPosition(toExclusiveEndPosition(token.endColumn)));
		return new Range(startPosition, endPosition);
	}

	private static int toZeroBasedPosition(int oneBasedPosition) {
		return oneBasedPosition - 1;
	}

	private static int toExclusiveEndPosition(int inclusiveEndPosition) {
		return inclusiveEndPosition + 1;
	}

}
