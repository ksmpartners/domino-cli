package com.ksm.domino.cli.command.run;

import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "run",
            commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'run'|@ commands are:%n",
            header = "%n@|green Run related functions|@",
            subcommands = {
                        RunRecent.class,
                        RunList.class
            })
public class Run extends AbstractParentCommand {
}
