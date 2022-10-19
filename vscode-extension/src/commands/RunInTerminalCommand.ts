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
import { Java } from "../Java";
import { Logger } from "../Logger";
import { Command } from "./Command";

export class RunInTerminalCommand extends Command {
    constructor(
        private java: Java,
        private rulewerkConfiguration: vscode.WorkspaceConfiguration,
        private defaultRulewerkClientPath: string
    ) {
        super();
    }

    public getIdentifier() {
        return "rulewerk.runInTerminal";
    }

    public run() {
        const rulewerkClientPath = this.getRulewerkClientPath();

        const shellPath = this.java.getPathToJavaExecutable();
        const shellArgs = ["-jar", rulewerkClientPath];
        const message = `Starting the Rulewerk interactive shell:\r\n${shellPath} ${shellArgs[0]} '${shellArgs[1]}'\r\n`;

        console.log("Running Rulewerk client in terminal", {
            shellPath,
            shellArgs,
        });

        const terminal = vscode.window.createTerminal({
            name: "Rulewerk",
            shellPath,
            shellArgs,
            message,
        });

        const activeEditor = vscode.window.activeTextEditor;
        if (activeEditor !== undefined) {
            // Load current file in Rulewerk reasoner
            this.loadRulewerkFileInTerminal(activeEditor, terminal);
        }

        terminal.show();
    }

    /**
     * Gets the Rulewerk client path configured by the user or the default path
     */
    private getRulewerkClientPath() {
        const configuredPath = this.rulewerkConfiguration.get<string | null>(
            "rulewerkClientPath"
        );

        if (typeof configuredPath !== "string") {
            return this.defaultRulewerkClientPath;
        }
        return configuredPath;
    }

    /**
     * Runs Rulewerk `@load` command on the given Rulewerk file
     */
    private loadRulewerkFileInTerminal(
        editor: vscode.TextEditor,
        terminal: vscode.Terminal
    ) {
        const activeFilePath = editor.document.uri.path;

        // TODO: Properly escape characters
        // const escapedPath=escapePathCharacters(activeFilePath);
        terminal.sendText(`@load "${activeFilePath}"`);
    }

    // private  escapePathCharacters(filePath: String) {
    //     // TODO escape "
    //     // TODO escape \
    //     // TODO escape \n
    //     // TODO escape \t
    // }
}
