package com.ksm.domino.cli.command.collaborator;

import com.ksm.domino.cli.command.AbstractParentCommand;

import static picocli.CommandLine.Command;

@Command(name = "collaborator",
        commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'collaborator'|@ commands are:%n",
        header = "%n@|green Collaborator related functions|@",
        subcommands = {
                CollaboratorAdd.class,
                CollaboratorRemove.class
        })
public class Collaborator extends AbstractParentCommand {
}
