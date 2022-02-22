package com.ksm.domino.cli.command.job;

import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "job",
            commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'job'|@ commands are:%n",
            header = "%n@|green Job related functions|@",
            subcommands = {
                        JobGet.class,
                        JobStart.class
            })
public class Job extends AbstractParentCommand {
}
