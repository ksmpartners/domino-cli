package com.ksm.domino.cli.command.project;

import com.ksm.domino.cli.command.AbstractParentCommand;
import com.ksm.domino.cli.command.goals.GoalList;

import picocli.CommandLine;

@CommandLine.Command(name = "project",
            commandListHeading = "%nCommands:%n%nThe most commonly used 'project' commands are:%n",
            header = "%n@|green Project related functions|@",
            subcommands = {
                        ProjectCreate.class,
                        GoalList.class
            })
public class Project extends AbstractParentCommand {
}
