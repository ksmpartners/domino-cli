package com.ksm.domino.cli.command.project;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoCommonModelsEnvironmentVariables;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "get-env", header = "%n@|green Retrieves all environment variables for a project|@")
public class ProjectGetEnv extends AbstractDominoCommand {

    private static final String NAME = "project get-env";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nprojectId=1234.....|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters, "projectId", NAME);
        parameters.remove("projectId");

        ProjectsApi projectsApi = new ProjectsApi(getApiClient());
        DominoCommonModelsEnvironmentVariables result = projectsApi.listProjectEnvironmentVariables(projectId);

        output(result);
    }

}
