package org.semanticweb.rulewerk.language.server.services;

import java.util.HashMap;
import java.util.Map;

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
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;

public class FullSyncTextDocumentService implements TextDocumentService {

	protected final Map<String, TextDocumentItem> documentsByUri = new HashMap<>();
	protected LanguageClient client;

	public void setClient(LanguageClient client) {
		this.client = client;
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		TextDocumentItem textDocument = params.getTextDocument();
		final String documentUri = textDocument.getUri();
		this.documentsByUri.put(documentUri, textDocument);
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		for (TextDocumentContentChangeEvent changeEvent : params.getContentChanges()) {
			validateFullSync(changeEvent);

			final String documentUri = params.getTextDocument().getUri();
			getDocument(documentUri).setText(changeEvent.getText());
		}
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		final String documentUri = params.getTextDocument().getUri();
		this.documentsByUri.remove(documentUri);
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
	}

	/**
	 * TextDocumentSyncKind is configured as Full during server initialization,
	 * therefore given {@code changeEvent} is expected to not have a range
	 * 
	 * @param changeEvent of a Text Document Change
	 */
	private void validateFullSync(TextDocumentContentChangeEvent changeEvent) {
		// TextDocumentSyncKind is configured as Full during server initialization
		if (changeEvent.getRange() != null) {
			throw new UnsupportedOperationException("Only full content updates are supported!");
		}
	}

	protected TextDocumentItem getDocument(final String documentUri) {
		return this.documentsByUri.get(documentUri);
	}

}
