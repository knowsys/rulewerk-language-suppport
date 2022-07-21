// Copyright 2022 rulewerk-language-suppport
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import { spawn } from "child_process";
import * as vscode from "vscode";
import { Logger } from "./Logger";

export class Java {
    public constructor(
        private logger: Logger,
        private rulewerkConfiguration: vscode.WorkspaceConfiguration
    ) {}

    /**
     * Gets the configured path to the Java executable
     */
    public getPathToJavaExecutable() {
        const path = this.rulewerkConfiguration.get<string>("javaPath");

        if (typeof path !== "string") {
            throw new Error("Configured Java executable path is not a string.");
        }
        return path;
    }

    /**
     * Validates that Java is installed.
     */
    public validateInstallation() {
        try {
            const process = spawn(
                this.getPathToJavaExecutable(),
                ["--version"],
                {
                    timeout: 10000,
                }
            );

            process.once("error", (error) => {
                this.logger.logErrorAndShowToUser(
                    "Error while checking Java installation",
                    error
                );
            });
            process.once("exit", (code, signal) => {
                if (code === 0) {
                    return;
                }
                this.logger.logErrorAndShowToUser(
                    `Non-zero exit code while checking Java installation: ${code}` +
                        signal ===
                        null
                        ? ""
                        : ` (signal ${signal})`,
                    { code, signal }
                );
            });
        } catch (error) {
            this.logger.logErrorAndShowToUser(
                "Could not start Java, is it installed?",
                error
            );
        }
    }
}
