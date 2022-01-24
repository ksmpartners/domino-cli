package com.ksm.domino.cli.command.collaborator;

import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;
import org.apache.commons.lang3.Validate;

import java.util.LinkedHashMap;
import java.util.Map;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(name = "remove", header = "%n@|green Remove a collaborator from a Project|@")
public class CollaboratorRemove extends AbstractDominoCommand {

    @Parameters(description = "@|blue Required parameters:%n projectId=123%n collaboratorId=hsimpson%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        // validate parameters
        String projectId = parameters.get("projectId");
        String collaboratorId = parameters.get("collaboratorId");
        Validate.notBlank(projectId, "Missing the required parameter 'projectId' when calling collaborator remove.");
        Validate.notBlank(collaboratorId, "Missing the required parameter 'collaboratorId' when calling collaborator remove.");

        // execute the API
        final ProjectsApi api = new ProjectsApi(getApiClient());
        api.removeCollaborator(projectId, collaboratorId);
        output(String.format("Collaborator %s successfully removed from project %s", collaboratorId, projectId));
    }
}
