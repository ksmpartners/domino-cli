package com.ksm.domino.cli.command.user;

import com.ksm.domino.cli.command.AbstractParentCommand;

import static picocli.CommandLine.Command;

@Command(name = "user",
        commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'user'|@ commands are:%n",
        header = "%n@|green User related functions|@",
        subcommands = {
                UserCurrent.class,
                UserList.class,
                UserEnvList.class,
                UserEnvDelete.class,
                UserCredentialList.class,
                UserProjectDependencyGraph.class
        })
public class User extends AbstractParentCommand {

}
