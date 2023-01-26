/**
 * Copyright 2022 rulewerk-language-suppport
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {
    LanguageClient,
    ServerOptions,
    LanguageClientOptions,
    StreamInfo,
} from "vscode-languageclient/node";
import * as net from "net";

export class RulewerkLanguageClient {
    private languageClient: LanguageClient | undefined;

    constructor() {}

    public async start() {
        // start client-server communication
        const serverPort = 6060;
        const serverIP = "127.0.0.1";

        function getSocketToServer(): Promise<StreamInfo> {
            return new Promise((resolve, reject) => {
                const socketToServer = new net.Socket();

                socketToServer
                    .connect(serverPort, serverIP, function () {
                        console.log("Client connected to LSP server.");
                    })
                    .on("error", (err) => {
                        console.log("Client failed to connect to LSP server!");
                        throw err;
                    });

                resolve({
                    reader: socketToServer,
                    writer: socketToServer,
                });

                socketToServer.on("end", () =>
                    console.log("Client disconnected from LSP server.")
                );
            });
        }

        const clientOptions: LanguageClientOptions = {
            documentSelector: [{ scheme: "file", language: "rulewerk" }],
        };

        // Promise<StreamInfo> is subtype of ServerOptions
        // StreamInfo is object { writer: NodeJS.WritableStream, reader: NodeJS.ReadableStream }
        // net.Socket is subtype of NodeJS.WriteableStream: https://nodejs.org/api/stream.html#writable-streams
        // net.Socket is subtype of NodeJS.ReadableStream: https://nodejs.org/api/stream.html#readable-streams
        const serverOptions: ServerOptions = getSocketToServer;

        this.languageClient = new LanguageClient(
            "rulewerkLanguageClient",
            "Rulewerk Language Client",
            serverOptions,
            clientOptions
        );

        await this.languageClient.start();
        console.log("Starting client-server communication!");
    }

    public async stop() {
        if (this.languageClient === undefined) {
            return;
        }
        await this.languageClient.stop();
        this.languageClient = undefined;
    }
}
