package com.ksm.domino.cli.command.datasource;

import com.ksm.domino.cli.command.AbstractParentCommand;

import static picocli.CommandLine.Command;

@Command(name = "datasource",
        commandListHeading = "%nCommands:%n%nThe most commonly used @|bold 'datasource'|@ commands are:%n",
        header = "%n@|green DataSource related functions|@",
        subcommands = {
                DataSourceMount.class
        })
public class DataSource extends AbstractParentCommand {
}
