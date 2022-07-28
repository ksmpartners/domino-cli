package com.ksm.domino.cli.command.dataset;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoDatasetrwApiDatasetRwSnapshotSummaryDto;
import com.dominodatalab.api.rest.DatasetRwApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

@Command(name = "snapshot", header = "%n@|green Retrieves a snapshot of a dataset|@")
public class DatasetSnapshot extends AbstractDominoCommand {

    private static final String NAME = "dataset snapshot";

    @Parameters(description = "@|blue Required parameters:%n snapshotId=456%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(1);

    @Override
    public void execute() throws Exception {
        String snapshotId = getRequiredParam(parameters, "snapshotId", NAME);
        DatasetRwApi api = new DatasetRwApi(getApiClient());
        DominoDatasetrwApiDatasetRwSnapshotSummaryDto result = api.getSnapshot(snapshotId);
        output(result);
    }
}
