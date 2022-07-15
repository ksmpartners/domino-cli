package com.ksm.domino.cli.command.user;

import org.apache.commons.io.FileUtils;
import com.dominodatalab.api.model.DominoCommonUserPerson;
import com.dominodatalab.api.model.DominoServerAccountApiGitCredentialAccessorDto;
import com.dominodatalab.api.rest.GitCredentialsApi;
import com.dominodatalab.api.rest.UsersApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

@CommandLine.Command(name = "add-credentials", header = "%n@|green Adds git credentials to the current user's account|@")
public class UserCredentialAdd extends AbstractDominoCommand {

    private static final String NAME = "user add-credentials";
    private static final String REPOSITORY_PROVIDER = "githubEnterprise";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nname=credentialsName%n%nOptional Parameters:%nkeyFile=~/.ssh/id_ed25519%ntoken=ghp_ieaf...%nproviderUrl=ghe.foo.com%nrepoProvider=githubEnterprise|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    private static final String TYPE_SSH_KEY = "key";
    private static final String TYPE_TOKEN_KEY = "token";

    private static final String SSH_DTO_TYPE = "SshGitCredentialDto";
    private static final String TOKEN_DTO_TYPE = "TokenGitCredentialDto";

    @Override
    public void execute() throws Exception {
        Map<String, String> request = new LinkedHashMap<>();
        String credentialsName = getRequiredParam(parameters, "name", NAME);
        request.put("name", credentialsName);

        String repoProvider = parameters.getOrDefault("repoProvider", REPOSITORY_PROVIDER);
        request.put("gitServiceProvider", repoProvider);

        if (repoProvider.equals(REPOSITORY_PROVIDER)) {
            String providerUrl = parameters.get("providerUrl");
            Validate.notBlank(providerUrl, "Parameter 'providerUrl' is required if 'repoProvider' is type 'githubEnterprise'");

            request.put("domain", providerUrl);
        }

        request.put("accessType", getCredentialsType());

        if (parameters.containsKey("token")) {
            request.put("type", TOKEN_DTO_TYPE);

            String credentialsToken = parameters.get("token");
            request.put("token", credentialsToken);
        } else if (parameters.containsKey("keyFile")) {
            request.put("type", SSH_DTO_TYPE);

            String credentialsKeyFile = parameters.get("keyFile");
            File file = new File(credentialsKeyFile);
            String privateKey = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            request.put("key", privateKey);
        }

        UsersApi api = new UsersApi(getApiClient());
        DominoCommonUserPerson user = api.getCurrentUser();

        GitCredentialsApi gitApi = new GitCredentialsApi(getApiClient());

        DominoServerAccountApiGitCredentialAccessorDto result = gitApi.addGitCredential(user.getId(), request);

        output(result);
    }

    /**
     * Domino has two types of git credentials, "key" (SSH private key) and "token" (Personal Access Token)
     */
    private String getCredentialsType() {
        if (parameters.containsKey("token")) {
            return TYPE_TOKEN_KEY;
        } else if (parameters.containsKey("keyFile")) {
            return TYPE_SSH_KEY;
        } else {
            throw new IllegalArgumentException("Must provide token or keyFile");
        }
    }
}
