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

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.services.WorkspaceService;

public class RulewerkWorkspaceService implements WorkspaceService {
// TODO implement workspace functionality 
	
	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
//		params.getChanges().forEach(fileEvent -> {
//			// TODO remove Syso
//			System.out
//					.println(fileEvent.getType() + " Even triggered for changes on watch file: " + fileEvent.getUri());
//
//		});
		// TODO implement functionality
	}

}
