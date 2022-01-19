package com.ksm.domino.cli.command;

import com.dominodatalab.api.model.DominoCommonModelsEnvironmentVariables;
import com.dominodatalab.api.rest.UsersApi;

import static picocli.CommandLine.Command;

@Command(name = "envdelete", header = "%n@|green Deletes all of the current user's environment variables|@")
public class UserEnvDelete extends AbstractDominoCommand {

    @Override
    public void execute() throws Exception {
        UsersApi api = new UsersApi(getApiClient());
        DominoCommonModelsEnvironmentVariables vars = api.deleteUserEnvironmentVariables();
        output(vars);
    }
}
