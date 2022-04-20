package com.ksm.domino.cli.command.user;

import com.dominodatalab.api.model.DominoCommonUserPerson;
import com.dominodatalab.api.model.DominoServerAccountApiGitCredentialAccessorDto;
import com.dominodatalab.api.rest.GitCredentialsApi;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "credentials", header = "%n@|green Retrieves the current user's git credentials|@")
public class UserCredentialList extends AbstractDominoCommand {

    @Override
    public void execute() throws Exception {
        UsersApi api = new UsersApi(getApiClient());
        DominoCommonUserPerson user = api.getCurrentUser();

        GitCredentialsApi gitApi = new GitCredentialsApi(getApiClient());
        List<DominoServerAccountApiGitCredentialAccessorDto> credentials = gitApi.getGitCredentials(user.getId());
        output(credentials);
    }
}
