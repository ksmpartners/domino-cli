package com.ksm.domino.cli.command.job;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.dominodatalab.api.model.DominoJobsInterfaceJob;
import com.dominodatalab.api.rest.JobsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "get", header = "%n@|green Retrieves a single job by job id.|@")
public class JobGet extends AbstractDominoCommand {

    @CommandLine.Parameters(description = "@|blue Required parameters:%n jobId=12345%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        String jobId = parameters.get("jobId");
        Validate.notBlank(jobId, "Missing the required parameter 'jobId' when calling job get.");
        JobsApi api = new JobsApi(getApiClient());
        DominoJobsInterfaceJob job = api.getJob(jobId);
        output(job);
    }
}
