package com.ksm.domino.cli.command.datasource;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dominodatalab.api.model.DominoDatasetrwApiSharedDatasetRwEntryDto;
import com.dominodatalab.api.model.DominoDatasourceApiDataSourceDto;
import com.dominodatalab.api.rest.DataSourceApi;
import com.ksm.domino.cli.command.AbstractDominoCommand;

@Command(name = "mount", header = "%n@|green Add DataSource to project|@")
public class DataSourceMount extends AbstractDominoCommand {

    private static final String NAME = "datasource mount";

    @Parameters(description = "@|blue Required parameters:%n projectId=123%n datasourceId=456%n|@%n", mapFallbackValue = "")
    private final Map<String, String> parameters = new LinkedHashMap<>(2);

    @Override
    public void execute() throws Exception {
        String projectId = getRequiredParam(parameters,
                    DominoDatasetrwApiSharedDatasetRwEntryDto.JSON_PROPERTY_PROJECT_ID, NAME);
        String datasetId = getRequiredParam(parameters, "datasourceId", NAME);
        DataSourceApi api = new DataSourceApi(getApiClient());
        DominoDatasourceApiDataSourceDto result = api.addProjectToDataSource(datasetId, projectId);
        output(result);
    }
}
