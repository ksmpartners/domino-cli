package com.ksm.domino.cli.command.run;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.dominodatalab.api.model.DominoScheduledrunApiLegacyScheduledRunDTO;
import com.dominodatalab.api.rest.ScheduledRunsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "list", header = "%n@|green Retrieves a list of runs for a user|@")
public class RunList extends AbstractDominoCommand {

    @CommandLine.Parameters(description = "@|blue Optional parameters:%n userId=hsimpson%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        String userId = parameters.get("userId");
        Validate.notBlank(userId, "Missing the required parameter 'userId' when calling run list.");
        ScheduledRunsApi api = new ScheduledRunsApi(getApiClient());
        List<DominoScheduledrunApiLegacyScheduledRunDTO> runs = api.listScheduledRuns(userId);
        output(runs);
    }
}
