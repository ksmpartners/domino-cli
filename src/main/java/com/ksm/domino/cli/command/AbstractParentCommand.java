package com.ksm.domino.cli.command;

import com.ksm.domino.cli.Domino;
import picocli.CommandLine;

import static picocli.CommandLine.*;

/**
 * Abstract base command that all parent commands should extend which will print the "help" output
 * if this command is called with no options.
 */
public abstract class AbstractParentCommand implements Runnable {

    @ParentCommand
    Domino domino; // picocli injects reference to parent command

    @Spec
    CommandLine.Model.CommandSpec spec;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Print usage help and exit.")
    private boolean usageHelpRequested;

    /**
     * Print the help if this command is called directly.
     */
    @Override
    public void run() {
        // if the command was invoked without subcommand, show the usage help
        spec.commandLine().usage(System.err);
    }

    /**
     * The root Domino command.
     *
     * @return the roor {@link Domino} command
     */
    public Domino getDomino() {
        return domino;
    }
}
