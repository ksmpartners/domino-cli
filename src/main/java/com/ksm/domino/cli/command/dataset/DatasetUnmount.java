package com.ksm.domino.cli.command.dataset;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoDatasetrwApiSharedDatasetRwEntryDto;
import com.dominodatalab.api.rest.DatasetRwApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

@Command(name = "unmount", header = "%n@|green Removes shared dataset from project|@")
public class DatasetUnmount extends AbstractDominoCommand {

    private static final String NAME = "dataset unmount";

    @Parameters(description = "@|blue Required parameters:%n projectId=123%n datasetId=456%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(2);

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters,
                    DominoDatasetrwApiSharedDatasetRwEntryDto.JSON_PROPERTY_PROJECT_ID, NAME);
        String datasetId = getRequiredParam(parameters, "datasetId", NAME);
        DatasetRwApi api = new DatasetRwApi(getApiClient());
        DominoDatasetrwApiSharedDatasetRwEntryDto result = api.removeSharedDatasetRwEntry(projectId, datasetId);
        output(result);
    }
}
