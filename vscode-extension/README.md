# Rulewerk Visual Studio Code Extension

This VS Code extension provides language support for Rulewerk files (`.rls`).

## Features

-   Rulewerk syntax highlighting
-   Run `.rls` files using the interactive Rulewerk shell

## Installation instructions

-   Install the VS Code editor extension
-   Install the Java runtime environment 16 or newer on your system
    -   Minimum required version is Java 16 (Standard Edition)
    -   Ensure the `java` command is available through the `PATH` environment variable, or configure `rulewerk.javaPath` in the extension setting

## Extension Settings

Overview of extension settings (see the settings page of your editor for additional information):

-   `rulewerk.rulewerkClientPath`
    -   Path to an external Rulewerk client JAR file (`org.semanticweb.rulewerk-client`). Used for running Rulewerk files in the terminal. Defaults to the Rulewerk client shipped by the extension.
-   `rulewerk.javaPath`
    -   Path to Java runtime executable. This setting can be used if the directory containing the `java` executable is not in the `PATH` environment variable.

## Development

### Building the extension

-   Ensure the Rulewerk client (`org.semanticweb.rulewerk-client`) from https://github.com/knowsys/rulewerk/releases/ can be found at `vscode-extension/libs/rulewerk-client.jar`
