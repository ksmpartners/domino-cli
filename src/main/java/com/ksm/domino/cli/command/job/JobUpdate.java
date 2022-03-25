package com.ksm.domino.cli.command.job;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoJobsInterfaceJob;
import com.dominodatalab.api.model.DominoJobsWebUpdateJobName;
import com.dominodatalab.api.rest.JobsApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "update", header = "%n@|green Updates the values of a job like job name.|@")
public class JobUpdate extends AbstractDominoCommand {

    private static final String NAME = "job update";

    @CommandLine.Parameters(description = "@|blue Required parameters:%n jobId=12345%n name='MyName'%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(3);

    @Override
    public void execute() throws Exception {
        String jobId = getRequiredParam(parameters, "jobId", NAME);
        String name = getRequiredParam(parameters, DominoJobsWebUpdateJobName.JSON_PROPERTY_NAME, NAME);
        JobsApi api = new JobsApi(getApiClient());
        DominoJobsWebUpdateJobName request = new DominoJobsWebUpdateJobName(name);
        DominoJobsInterfaceJob job = api.updateJob(jobId, request);
        output(job);
    }
}