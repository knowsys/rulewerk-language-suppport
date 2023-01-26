package org.semanticweb.rulewerk.language.server;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/*-
 * #%L
 * rulewerk-language-server
 * %%
 * Copyright (C) 2018 - 2022 Rulewerk Language Server Developers
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Range;
import org.semanticweb.rulewerk.core.reasoner.KnowledgeBase;
import org.semanticweb.rulewerk.language.server.util.DocumentUtil;
import org.semanticweb.rulewerk.parser.input.InputAwareParsingException;
import org.semanticweb.rulewerk.parser.input.InputAwareRuleParser;

public class RulewerkDocumentValidator {

	public static final String RULEWERK_LANGUAGE_DIAGNOSTIC_SOURCE = "rulewerk";

	public static List<Diagnostic> validate(final String documentText) throws FileNotFoundException {
		final List<Diagnostic> diagnostics = new ArrayList<>();

		final KnowledgeBase knowledgeBase = new KnowledgeBase();

		try {
			InputAwareRuleParser.parse(knowledgeBase, documentText);

		} catch (InputAwareParsingException e) {

			final Diagnostic diagnostic = toDiagnostic(e);
			diagnostics.add(diagnostic);
		}

		return diagnostics;
	}

	private static Diagnostic toDiagnostic(final InputAwareParsingException e) {
		final Diagnostic diagnostic = new Diagnostic();
		diagnostic.setSource(RULEWERK_LANGUAGE_DIAGNOSTIC_SOURCE);
		diagnostic.setSeverity(DiagnosticSeverity.Error);

		diagnostic.setMessage(e.getMessage());
		final Range range = DocumentUtil.toRange(e.getToken());
		diagnostic.setRange(range);
		return diagnostic;
	}

}
