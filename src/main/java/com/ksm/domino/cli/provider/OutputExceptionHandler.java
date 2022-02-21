package com.ksm.domino.cli.provider;

import org.apache.commons.lang3.exception.ExceptionUtils;

import picocli.CommandLine;

/**
 * Handle CLI exceptions by only printing the core error and not the stack trace.
 */
public class OutputExceptionHandler
            implements CommandLine.IExecutionExceptionHandler {

    @Override
    public int handleExecutionException(Exception e, CommandLine commandLine, CommandLine.ParseResult parseResult) {
        String error = ExceptionUtils.getRootCauseMessage(e);
        commandLine.getErr().println(commandLine.getColorScheme().errorText(error));
        return commandLine.getCommandSpec().exitCodeOnExecutionException();
    }
}
