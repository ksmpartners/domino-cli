package com.ksm.domino.cli.command.project;

import com.dominodatalab.api.model.DominoNucleusProjectModelsNewProject;
import com.dominodatalab.api.model.DominoNucleusProjectModelsProject;
import com.dominodatalab.api.model.DominoProjectsApiCollaboratorDTO;
import com.dominodatalab.api.model.DominoProjectsApiNewTagsDTO;
import com.dominodatalab.api.model.DominoProjectsApiProjectGitRepositoryTemp;
import com.dominodatalab.api.model.DominoProjectsApiRepositoriesReferenceDTO;
import com.dominodatalab.api.rest.ProjectsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@CommandLine.Command(name = "create", header = "%n@|green Creates a new project|@")
public class ProjectCreate extends AbstractDominoCommand {

    private static final String REPOSITORY_PROVIDER = "githubEnterprise";

    @CommandLine.Parameters(description = "@|blue Required Parameters:%nname=projectName%nownerId=6125....%nOptional Parameters:%ndescription:\"A project description\"%nmainRepoUrl:https://mygithub.gsk.com/mudid/repo-name%ncredentialId:61de.....%nrepoProvider=githubEnterprise%ncollaborators=123,456,789|@")
    private final Map<String, String> parameters = new LinkedHashMap<>();

    @Override
    public void execute() throws Exception {
        String projectName = parameters.get("name");
        Validate.notBlank(projectName, "Missing the required parameter 'name' when calling project create.");
        String ownerId = parameters.get("ownerId");
        Validate.notBlank(ownerId, "Missing the required parameter 'ownerId' when calling project create.");
        String description = parameters.getOrDefault("description", "");

        DominoProjectsApiNewTagsDTO tags = new DominoProjectsApiNewTagsDTO();
        tags.setTagNames(new ArrayList<>());

        List<DominoProjectsApiCollaboratorDTO> collaboratorDTOS = new ArrayList<>();

        String collaboratorId = parameters.get("collaboratorIds");
        List<String> collaboratorIds;
        if (StringUtils.isNotBlank(collaboratorId)) {
            collaboratorIds = List.of(StringUtils.split(parameters.get("collaboratorIds"), ','));
            collaboratorIds.forEach(id -> {
                DominoProjectsApiCollaboratorDTO collaborator = new DominoProjectsApiCollaboratorDTO();
                collaborator.setCollaboratorId(id);
                collaborator.setProjectRole(DominoProjectsApiCollaboratorDTO.ProjectRoleEnum.CONTRIBUTOR);
                collaboratorDTOS.add(collaborator);
            });
        }

        DominoNucleusProjectModelsNewProject newProject = DominoNucleusProjectModelsNewProject.builder()
                .name(projectName)
                .description(description)
                .visibility(DominoNucleusProjectModelsNewProject.VisibilityEnum.PRIVATE)
                .ownerId(ownerId)
                .tags(tags)
                .collaborators(collaboratorDTOS)
                .build();

        if (parameters.containsKey("mainRepoUrl")) {
            String mainRepoUrl = parameters.get("mainRepoUrl");
            String repoProvider = parameters.getOrDefault("repoProvider", REPOSITORY_PROVIDER);
            String credentialId = parameters.get("credentialId");
            Validate.notBlank(credentialId, "Parameter 'credentialId' is required if 'mainRepoUrl' is specified");

            DominoProjectsApiRepositoriesReferenceDTO defaultRef = new DominoProjectsApiRepositoriesReferenceDTO();
            defaultRef.setType("head");

            DominoProjectsApiProjectGitRepositoryTemp gitRepositoryTemp = new DominoProjectsApiProjectGitRepositoryTemp();
            gitRepositoryTemp.setUri(mainRepoUrl);
            gitRepositoryTemp.setServiceProvider(repoProvider);
            gitRepositoryTemp.setCredentialId(credentialId);
            gitRepositoryTemp.setDefaultRef(defaultRef);

            newProject.setMainRepository(gitRepositoryTemp);
        }

        ProjectsApi projectsApi = new ProjectsApi(getApiClient());
        DominoNucleusProjectModelsProject project = projectsApi.createProject(newProject);

        output(project);
    }
}
