package com.ksm.domino.cli.command;

import static picocli.CommandLine.Option;
import static picocli.CommandLine.ParentCommand;

import java.net.http.HttpClient;
import java.time.Duration;

import org.openapitools.jackson.nullable.JsonNullableModule;

import com.dominodatalab.api.invoker.ApiClient;
import com.dominodatalab.api.invoker.ApiException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ksm.domino.cli.Domino;
import com.ksm.domino.cli.provider.TrustAllManager;

/**
 * Abstract base class that any command that needs to access Domino should extend.
 */
public abstract class AbstractDominoCommand implements Runnable {

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Print usage help and exit.")
    private boolean usageHelpRequested;
    /**
     * Parent command
     */
    @ParentCommand
    private AbstractParentCommand parent; // picocli injects reference to parent command
    /**
     * Cached API client for calling Domino.
     */
    private ApiClient apiClient;

    /**
     * Method that executes this command.
     *
     * @throws Exception if any error occurs
     */
    public abstract void execute() throws Exception;

    @Override
    public void run() {
        try {
            execute();
        }
        catch (ApiException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Create the API Client for accessing Domino over HTTP.
     *
     * @return the {@@link ApiClient}
     */
    public ApiClient getApiClient() {
        if (apiClient == null) {
            Domino domino = parent.getDomino();
            HttpClient.Builder httpClient = HttpClient.newBuilder().sslContext(TrustAllManager.createSslContext());
            ApiClient client = new ApiClient();
            client.setHttpClientBuilder(httpClient);
            client.setReadTimeout(Duration.ofSeconds(domino.timeoutSeconds));
            client.updateBaseUri(domino.apiUrl);
            client.setRequestInterceptor(builder -> builder.setHeader("X-Domino-Api-Key", domino.apiKey));
            apiClient = client;
        }
        return apiClient;
    }

    /**
     * Output this result to the console.
     *
     * @param o object to output to console
     * @throws JsonProcessingException if any error occurs
     */
    public void output(Object o) throws JsonProcessingException {
        if (o == null) {
            return;
        }

        if (o instanceof String) {
            System.out.println(o);
            return;
        }

        ObjectMapper mapper;
        switch (parent.getDomino().outputFormat) {
            case TEXT:
                mapper = new TomlMapper();
                break;
            case XML:
                mapper = new XmlMapper();
                break;
            case JSON:
            default:
                // default to JSON mapper
                mapper = new ObjectMapper();
                break;
        }
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new JsonNullableModule());
        String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        System.out.println(result);
    }
}
