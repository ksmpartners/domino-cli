package com.ksm.domino.cli.command.dataset;

import com.dominodatalab.api.model.DominoDatasetrwApiSharedDatasetRwEntryDto;
import com.dominodatalab.api.rest.DatasetRwApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;
import org.apache.commons.lang3.Validate;

import java.util.LinkedHashMap;
import java.util.Map;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(name = "mount", header = "%n@|green Add shared dataset to project|@")
public class DatasetMount extends AbstractDominoCommand {

    @Parameters(description = "@|blue Required parameters:%n projectId=123%n datasetId=456%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(2);

    @Override
    public void execute() throws Exception {
        String projectId = parameters.get("projectId");
        String datasetId = parameters.get("datasetId");
        Validate.notBlank(projectId, "Missing the required parameter 'projectId' when calling dataset mount.");
        Validate.notBlank(datasetId, "Missing the required parameter 'datasetId' when calling dataset mount.");
        DatasetRwApi api = new DatasetRwApi(getApiClient());
        DominoDatasetrwApiSharedDatasetRwEntryDto result = api.addSharedDatasetRwEntry(projectId, datasetId);
        output(result);
    }
}
