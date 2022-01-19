package com.ksm.domino.cli.command;

import com.dominodatalab.api.model.DominoCommonUserPerson;
import com.dominodatalab.api.rest.UsersApi;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(name = "list", header = "%n@|green Retrieves a list of users|@")
public class UserList extends AbstractDominoCommand {

    @Parameters(description = "@|blue Optional parameters such as userId=6112, userName=hsimpson, or query=flanders|@", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        String userId = parameters.get("userId");
        List<String> userIds = null;
        if (StringUtils.isNotBlank(userId)) {
            userIds = List.of(parameters.get("userId"));
        }
        String userName = parameters.get("userName");
        String query = parameters.get("query");

        UsersApi api = new UsersApi(getApiClient());
        List<DominoCommonUserPerson> users = api.listUsers(userIds, userName, query);
        output(users);
    }
}
