package com.ksm.domino.cli.command.goals;

import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "goal",
            commandListHeading = "%nCommands:%n%nThe most commonly used 'goal' commands are:%n",
            header = "%n@|green Goal related functions|@",
            subcommands = {
                        GoalComplete.class,
                        GoalList.class,
                        GoalLinkJob.class
            })
public class Goal extends AbstractParentCommand {
}
