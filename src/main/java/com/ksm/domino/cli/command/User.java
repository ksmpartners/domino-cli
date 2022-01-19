package com.ksm.domino.cli.command;

import static picocli.CommandLine.Command;

@Command(name = "user",
        commandListHeading = "%nCommands:%n%nThe most commonly used 'user'' commands are:%n",
        header = "%n@|green User related functions|@",
        subcommands = {
                UserCurrent.class,
                UserList.class
        })
public class User extends AbstractParentCommand {

}
