package com.ksm.domino.cli.command.user;

import com.dominodatalab.api.model.DominoCommonModelsEnvironmentVariables;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

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
