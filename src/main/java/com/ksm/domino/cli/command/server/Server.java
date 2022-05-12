package com.ksm.domino.cli.command.server;

import com.ksm.domino.cli.command.AbstractParentCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "server",
            commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'server'|@ commands are:%n",
            header = "%n@|green Server related functions|@",
            subcommands = {
                        ServerVersion.class
            })
public class Server extends AbstractParentCommand {
}
