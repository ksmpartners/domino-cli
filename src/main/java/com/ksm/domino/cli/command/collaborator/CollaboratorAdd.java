package com.ksm.domino.cli.command.collaborator;

import com.dominodatalab.api.model.DominoNucleusProjectModelsCollaborator;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;
import org.apache.commons.lang3.Validate;
import org.apache.commons.text.WordUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(name = "add", header = "%n@|green Adds a user or organization to Project as a collaborator|@")
public class CollaboratorAdd extends AbstractDominoCommand {

    @Parameters(description = "@|blue Required parameters:%n projectId=123%n collaboratorId=hsimpson%n role=Contributor%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        // validate parameters
        String projectId = parameters.get("projectId");
        String collaboratorId = parameters.get("collaboratorId");
        String role = WordUtils.capitalize(parameters.get("role"));
        Validate.notBlank(projectId, "Missing the required parameter 'projectId' when calling collaborator add.");
        Validate.notBlank(collaboratorId, "Missing the required parameter 'collaboratorId' when calling collaborator add.");
        Validate.notBlank(role, "Missing the required parameter 'role' when calling collaborator add.");

        // create the model object
        final DominoNucleusProjectModelsCollaborator collaborator = new DominoNucleusProjectModelsCollaborator();
        collaborator.setCollaboratorId(collaboratorId);
        collaborator.setProjectRole(DominoNucleusProjectModelsCollaborator.ProjectRoleEnum.fromValue(role));

        // execute the API
        final ProjectsApi api = new ProjectsApi(getApiClient());
        final DominoNucleusProjectModelsCollaborator result = api.addCollaborator(projectId, collaborator);
        output(result);
    }
}
