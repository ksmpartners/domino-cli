package com.ksm.domino.cli.command.project;

import com.ksm.domino.cli.command.AbstractParentCommand;
import picocli.CommandLine;

@CommandLine.Command(name = "project",
        commandListHeading = "%nCommands:%n%nThe most commonly used 'project' commands are:%n",
        header = "%n@|green Project related functions|@",
        subcommands = {
                ProjectCreate.class
        })
public class Project extends AbstractParentCommand {
}
