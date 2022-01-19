package com.ksm.domino.cli.command;

import com.dominodatalab.api.model.DominoCommonUserPerson;
import com.dominodatalab.api.rest.UsersApi;

import static picocli.CommandLine.Command;

@Command(name = "current", header = "%n@|green Retrieves the current user|@")
public class UserCurrent extends AbstractDominoCommand {

    @Override
    public void execute() throws Exception {
        UsersApi api = new UsersApi(getApiClient());
        DominoCommonUserPerson user = api.getCurrentUser();
        output(user);
    }
}
