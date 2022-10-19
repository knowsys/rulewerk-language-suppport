package org.semanticweb.rulewerk.language.server;

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

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {

	static int SERVER_PORT = 6060;

	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
			System.out.println("Waiting for LSP clients on port " + SERVER_PORT + ".");

			// Blocking operation
			Socket socketToClient = serverSocket.accept();
			System.out.println("Client connected to LSP server.");

			InputStream in = socketToClient.getInputStream();
			OutputStream out = socketToClient.getOutputStream();

			RulewerkLanguageServer server = new RulewerkLanguageServer();
			Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, in, out);

			LanguageClient client = launcher.getRemoteProxy();
			server.connect(client);

			// Spawns new thread
			launcher.startListening();
		}
	}

}
