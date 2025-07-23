package com.ksm.domino.cli.command.user;

import com.dominodatalab.api.model.DominoCommonUserPersonWithRoles;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "current", header = "%n@|green Retrieves the current user|@")
public class UserCurrent extends AbstractDominoCommand {

    @ParentCommand
    private User parent;

    @Override
    public void execute() throws Exception {
        UsersApi api = new UsersApi(getApiClient(parent.domino));
        DominoCommonUserPersonWithRoles user = api.getCurrentUser();
        output(user, parent.domino);
    }
}
