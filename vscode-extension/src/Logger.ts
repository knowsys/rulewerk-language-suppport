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

export class Logger {
    /**
     * Logs an error to the console and show a error message to the user.
     *
     * @param message Description of what action or process failed and if known for what reason
     * @param error Full error object, which will get logged to the console. The `error.message` will be shown to the user.
     */
    public logErrorAndShowToUser(message: string, error: any | Error) {
        console.error(message, error);

        if (
            typeof error === "object" &&
            error !== undefined &&
            typeof error.message === "string"
        ) {
            message += ` (${error.message})`;
        }

        vscode.window.showErrorMessage("[Rulewerk] " + message);
    }
}
