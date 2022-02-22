package com.ksm.domino.cli.command.job;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoJobsInterfaceComputeClusterConfigSpecDto;
import com.dominodatalab.api.model.DominoJobsInterfaceJob;
import com.dominodatalab.api.model.DominoJobsWebStartJobRequest;
import com.dominodatalab.api.model.DominoProjectsApiOnDemandSparkClusterPropertiesSpec;
import com.dominodatalab.api.model.DominoProjectsApiRepositoriesReferenceDTO;
import com.dominodatalab.api.rest.JobsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "start", header = "%n@|green Start a new job.|@")
public class JobStart extends AbstractDominoCommand {

    private static final String NAME = "job start";

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
        request.setDataSetMountConfigName(
                    parameters.get(DominoJobsWebStartJobRequest.JSON_PROPERTY_DATA_SET_MOUNT_CONFIG_NAME));
        request.setEnvironmentRevisionSpec(DominoJobsWebStartJobRequest.JSON_PROPERTY_ENVIRONMENT_REVISION_SPEC);
        
        DominoProjectsApiRepositoriesReferenceDTO mainRepoGitRef = new DominoProjectsApiRepositoriesReferenceDTO();
        mainRepoGitRef.setType(parameters.get(DominoJobsWebStartJobRequest.JSON_PROPERTY_MAIN_REPO_GIT_REF +
                    DominoProjectsApiRepositoriesReferenceDTO.JSON_PROPERTY_TYPE));
        mainRepoGitRef.setType(parameters.get(DominoJobsWebStartJobRequest.JSON_PROPERTY_MAIN_REPO_GIT_REF +
                    DominoProjectsApiRepositoriesReferenceDTO.JSON_PROPERTY_VALUE));
        request.setMainRepoGitRef(mainRepoGitRef);

        // unused
        request.setComputeClusterProperties(new DominoJobsInterfaceComputeClusterConfigSpecDto());
        request.setOnDemandSparkClusterProperties(new DominoProjectsApiOnDemandSparkClusterPropertiesSpec());

        return request;
    }
}
