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

import * as vscode from "vscode";
import * as path from "path";
import { RunInTerminalCommand } from "./commands/RunInTerminalCommand";
import { RulewerkLanguageClient } from "./lsp/RulewerkLanguageClient";
import { Java } from "./Java";
import { Logger } from "./Logger";

let rulewerkLanguageClient: RulewerkLanguageClient | undefined;

export async function activate(extensionContext: vscode.ExtensionContext) {
    const logger = new Logger();

    try {
        console.info(
            "Activating Rulewerk extension",
            extensionContext.extension?.id,
            extensionContext.extension?.packageJSON?.version
        );

        // Path to the Rulewerk client shipped by the extension
        const defaultRulewerkClientPath = extensionContext.asAbsolutePath(
            path.join("libs", "rulewerk-client.jar")
        );
        const rulewerkConfiguration =
            vscode.workspace.getConfiguration("rulewerk");
        const java = new Java(logger, rulewerkConfiguration);

        java.validateInstallation();

        // Register commands
        const commands = [
            new RunInTerminalCommand(
                java,
                rulewerkConfiguration,
                defaultRulewerkClientPath
            ),
        ];

        for (const command of commands) {
            const disposable = vscode.commands.registerCommand(
                command.getIdentifier(),
                () => {
                    try {
                        command.run();
                    } catch (error) {
                        logger.logErrorAndShowToUser(
                            `Error while running command ${command.getIdentifier()}`,
                            error
                        );
                    }
                }
            );
            extensionContext.subscriptions.push(disposable);

            await activateLanguageClient(logger);
        }
    } catch (error) {
        logger.logErrorAndShowToUser(
            "Failed to activate Rulewerk extension",
            error
        );
        // Propagate error
        throw error;
    }
}

async function activateLanguageClient(logger: Logger) {
    try {
        // Start language server and client
        rulewerkLanguageClient = new RulewerkLanguageClient();

        // const languageServerPath = extensionContext.asAbsolutePath(
        //     path.join("libs", "rulewerklanguageserver.jar")
        // );
        // rulewerkLanguageClient = new ExperimentalRulewerkLanguageClient(
        //     languageServerPath
        // );

        await rulewerkLanguageClient.start();
    } catch (error) {
        logger.logErrorAndShowToUser(
            "Failed to start/connect to the langauge server",
            error
        );
    }
}

export async function deactivate(): Promise<void> {
    if (rulewerkLanguageClient !== undefined) {
        rulewerkLanguageClient.stop();
        rulewerkLanguageClient = undefined;
    }
}
