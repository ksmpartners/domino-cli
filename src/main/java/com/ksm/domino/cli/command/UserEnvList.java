package com.ksm.domino.cli.command;

import com.dominodatalab.api.model.DominoCommonModelsEnvironmentVariables;
import com.dominodatalab.api.model.DominoCommonUserPerson;
import com.dominodatalab.api.rest.UsersApi;

import static picocli.CommandLine.Command;

@Command(name = "envlist", header = "%n@|green Retrieves the current user's environment variables|@")
public class UserEnvList extends AbstractDominoCommand {

    @Override
    public void execute() throws Exception {
        UsersApi api = new UsersApi(getApiClient());
        DominoCommonModelsEnvironmentVariables vars = api.listUserEnvironmentVariables();
        output(vars);
    }
}
