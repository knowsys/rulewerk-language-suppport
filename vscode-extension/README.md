# Rulewerk Visual Studio Code Extension

This VS Code extension provides language support for [Rulewerk rule language](https://github.com/knowsys/rulewerk/wiki#rulewerk-rule-language) files with `.rls` extension.

## Features

-   [Rulewerk language](https://github.com/knowsys/rulewerk/wiki/Rule-syntax-grammar) syntax highlighting for `.rls` files
-   [Rulewerk interactive shell](https://github.com/knowsys/rulewerk/wiki/Standalone-client/#rulewerk-interactive-shell) can be launched in the integrated terminal (via the **Run Rulewerk** command or the dedicated "play" button for a specific `.rls` file). Loading the knowledge base of active `.rls` files into the shell.

## Installation instructions

-   Install the VS Code editor extension
-   Install the Java runtime environment on your system
    -   The minimum required version is Java 8 (Standard Edition)
    -   Ensure the `java` command is available through the `PATH` environment variable, or configure `rulewerk.javaPath` in the extension setting

## Extension Settings

Overview of extension settings (see the settings page of your editor for additional information):

-   `rulewerk.rulewerkClientPath`
    -   Path to an executable `org.semanticweb.rulewerk-client` `.jar` file. Used for reasoning over Rulewerk files in the integrated [Rulewerk interactive shell](https://github.com/knowsys/rulewerk/wiki/Standalone-client/#rulewerk-interactive-shell) terminal. Defaults to the [Rulewerk client jar](https://github.com/knowsys/rulewerk/releases/download/v0.9.0/rulewerk-client-0.9.0.jar) shipped by the extension, compatible with [Rulewerk](https://github.com/knowsys/rulewerk) version `v0.9.0`.
-   `rulewerk.javaPath`
    -   Path to Java runtime executable. This setting can be used if the directory containing the `java` executable is not in the `PATH` environment variable.

## Development

### Building the extension

-   Ensure that suitable version of `org.semanticweb.rulewerk-client` can be found at `vscode-extension/libs/rulewerk-client.jar`. This can either be a [released version](https://github.com/knowsys/rulewerk/releases) or a self-built [snaphsot version](https://github.com/knowsys/rulewerk/wiki/Standalone-client#compilation).
