package org.semanticweb.rulewerk.language.server.services;

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
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.semanticweb.rulewerk.language.server.RulewerkDocumentValidator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RulewerkDocumentService extends FullSyncTextDocumentService {

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		super.didOpen(params);

		final TextDocumentItem textDocumentItem = params.getTextDocument();
		validateFullDocumentText(textDocumentItem.getUri());
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		super.didChange(params);

		final VersionedTextDocumentIdentifier textDocumentIdentifier = params.getTextDocument();
		validateFullDocumentText(textDocumentIdentifier.getUri());
	}

	private void validateFullDocumentText(final String documentUri) {
		final TextDocumentItem savedDocument = getDocument(documentUri);
		final List<Diagnostic> diagnostics = new ArrayList<>();
		try {
			diagnostics.addAll(RulewerkDocumentValidator.validate(savedDocument.getText()));
		} catch (FileNotFoundException e) {
			// FIXME handle this case specifically
			// TODO parse Document by uri, take it into consideration for @base
			e.printStackTrace();
		}
		this.client.publishDiagnostics(new PublishDiagnosticsParams(documentUri, diagnostics));
	}

}
