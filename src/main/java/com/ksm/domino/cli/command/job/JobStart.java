package com.ksm.domino.cli.command.job;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import com.dominodatalab.api.model.DominoJobsInterfaceJob;
import com.dominodatalab.api.model.DominoJobsWebStartJobRequest;
import com.dominodatalab.api.model.DominoProjectsApiRepositoriesReferenceDTO;
import com.dominodatalab.api.model.DominoWorkspacesWebLaunchWorkspaceInputsEnvironmentRevisionSpec;
import com.dominodatalab.api.rest.JobsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "start", header = "%n@|green Start a new job.|@")
public class JobStart extends AbstractDominoCommand {

    private static final String NAME = "job start";
    private static final String MAIN_REPO_REF_TYPE = "mainRepoGitRefType";
    private static final String MAIN_REPO_REF_VALUE = "mainRepoGitRefValue";

    @CommandLine.Parameters(description = "@|blue Parameters:%n projectId=12345%n environmentId=456%n mainRepoGitRefType=head%n mainRepoGitRefValue=xxx%n commandToRun='test.sh'%n commitId=abe5g43%n overrideHardwareTierId=xxx%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(6);

    @Override
    public void execute() throws Exception {
        JobsApi api = new JobsApi(getApiClient());
        DominoJobsWebStartJobRequest request = createRequest();
        DominoJobsInterfaceJob job = api.startJob(request);
        output(job);
    }

    private DominoJobsWebStartJobRequest createRequest() {
        DominoJobsWebStartJobRequest request = new DominoJobsWebStartJobRequest();
        // required
        request.setProjectId(getRequiredParam(parameters, DominoJobsWebStartJobRequest.JSON_PROPERTY_PROJECT_ID, NAME));
        request.setCommandToRun(
                    getRequiredParam(parameters, DominoJobsWebStartJobRequest.JSON_PROPERTY_COMMAND_TO_RUN, NAME));

        // optional
        request.setCommitId(parameters.get(DominoJobsWebStartJobRequest.JSON_PROPERTY_COMMIT_ID));
        request.setEnvironmentId(parameters.get(DominoJobsWebStartJobRequest.JSON_PROPERTY_ENVIRONMENT_ID));
        request.setOverrideHardwareTierId(
                    parameters.get(DominoJobsWebStartJobRequest.JSON_PROPERTY_OVERRIDE_HARDWARE_TIER_ID));
        request.setEnvironmentRevisionSpec(new DominoWorkspacesWebLaunchWorkspaceInputsEnvironmentRevisionSpec("ActiveRevision"));
        
        DominoProjectsApiRepositoriesReferenceDTO mainRepoGitRef = new DominoProjectsApiRepositoriesReferenceDTO();
        mainRepoGitRef.setType(parameters.get(MAIN_REPO_REF_TYPE));
        mainRepoGitRef.setValue(parameters.get(MAIN_REPO_REF_VALUE));
        // type and value are required values of mainRepoGitRef - if DNE, override with null
        if (mainRepoGitRef.getType() == null && mainRepoGitRef.getValue() == null) {
            mainRepoGitRef = null;
        } else {

            Validate.notBlank(mainRepoGitRef.getType(),
                    String.format("Missing the required parameter '%s' when calling '%s' and '%s' is defined.", MAIN_REPO_REF_TYPE, NAME, MAIN_REPO_REF_VALUE));

            Validate.notBlank(mainRepoGitRef.getValue(),
                    String.format("Missing the required parameter '%s' when calling '%s' and '%s' is defined.", MAIN_REPO_REF_VALUE, NAME, MAIN_REPO_REF_TYPE));
        }
        request.setMainRepoGitRef(mainRepoGitRef);

        // unused
        request.setComputeClusterProperties(null);
        request.setOnDemandSparkClusterProperties(null);

        return request;
    }
}
