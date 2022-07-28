package com.ksm.domino.cli.command.dataset;

import com.ksm.domino.cli.command.AbstractParentCommand;

import static picocli.CommandLine.Command;

@Command(name = "dataset",
        commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'dataset'|@ commands are:%n",
        header = "%n@|green Dataset related functions|@",
        subcommands = {
                DatasetMount.class,
                DatasetSnapshot.class,
                DatasetUnmount.class
        })
public class Dataset extends AbstractParentCommand {
}
