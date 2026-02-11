package com.ksm.domino.cli;

import java.net.http.HttpRequest.Builder;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.SystemUtils;
import org.fusesource.jansi.AnsiConsole;

import com.ksm.domino.cli.command.collaborator.Collaborator;
import com.ksm.domino.cli.command.dataset.Dataset;
import com.ksm.domino.cli.command.datasource.DataSource;
import com.ksm.domino.cli.command.goals.Goal;
import com.ksm.domino.cli.command.job.Job;
import com.ksm.domino.cli.command.project.Project;
import com.ksm.domino.cli.command.run.Run;
import com.ksm.domino.cli.command.server.Server;
import com.ksm.domino.cli.command.user.User;
import com.ksm.domino.cli.provider.EnvironmentVariableDefaultProvider;
import com.ksm.domino.cli.provider.OutputExceptionHandler;
import com.ksm.domino.cli.provider.OutputFormat;
import com.ksm.domino.cli.provider.VersionProvider;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.ScopeType;
import picocli.CommandLine.Spec;

@TopCommand
@Command(name = "domino",
        header = "%n@|green Domino CLI|@",
        description = "%n@|blue Domino Data Lab Command Line Interface is a client to used provision and control Domino.|@%n",
        mixinStandardHelpOptions = true,
        versionProvider = VersionProvider.class,
        defaultValueProvider = EnvironmentVariableDefaultProvider.class,
        subcommands = {
                Collaborator.class,
                Dataset.class,
                DataSource.class,
                Goal.class,
                Job.class,
                User.class,
                Project.class,
                Run.class,
                Server.class
        })
public class Domino {

    /**
     * Either the Domino Workspace built in API key or a user set key checked in order.
     */
    private static final String ENV_API_KEY = "DOMINO_USER_API_KEY,DOMINO_API_KEY";

    /**
     * This token must be provided by user.
     */
    private static final String ENV_API_TOKEN = "DOMINO_API_TOKEN";

    /**
     * Either the Domino Workspace built in API URL or a user set URL checked in order.
     */
    private static final String ENV_API_URL = "DOMINO_API_HOST,DOMINO_API_URL";

    /**
     * The Domino Workspace built in API proxy environment variable. No equivalent for user overriding.
     */
    private static final String ENV_API_PROXY = "DOMINO_API_PROXY";

    @Option(names = {"-k",
            "--key"}, description = "Domino API Key.", defaultValue = ENV_API_KEY, scope = ScopeType.INHERIT)
    public String apiKey;

    @Option(names = {"-a",
            "--access-token"}, description = "Domino service account access token.", defaultValue = ENV_API_TOKEN, scope = ScopeType.INHERIT)
    public String apiToken;

    @Option(names = {"-u",
            "--url"}, description = "Domino API URL.", defaultValue = ENV_API_URL, scope = ScopeType.INHERIT)
    public String apiUrl;

    @Option(names = {"-p",
            "--proxy-url"}, description = "Domino API proxy within a job or workspace.", hidden = true, defaultValue = ENV_API_PROXY, scope = ScopeType.INHERIT)
    public String apiProxy;

    @Option(names = {"-t",
            "--timeout"}, description = "Timeout in seconds waiting for Domino responses.", defaultValue = "60", scope = ScopeType.INHERIT)
    public long timeoutSeconds;

    @Option(names = {"-o",
            "--output"}, description = "Output format TEXT, JSON, XML", defaultValue = "JSON", scope = ScopeType.INHERIT)
    public OutputFormat outputFormat;

    @Spec
    CommandLine.Model.CommandSpec spec;

    public static void main(String... args) {
        if (SystemUtils.IS_OS_WINDOWS) {
            AnsiConsole.systemInstall(); // enable colors on Windows
        }

        Domino domino = new Domino();
        final CommandLine commandLine = new CommandLine(domino);
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);
        commandLine.setSubcommandsCaseInsensitive(true);
        commandLine.setOptionsCaseInsensitive(true);
        commandLine.setExecutionStrategy(domino::executionStrategy);
        commandLine.setExecutionExceptionHandler(new OutputExceptionHandler());
        int exitCode = commandLine.execute(args);

        if (SystemUtils.IS_OS_WINDOWS) {
            AnsiConsole.systemUninstall(); // cleanup when done
        }
        System.exit(exitCode);
    }

    private int executionStrategy(ParseResult parseResult) {
        if (!isTokenConfigured() && !isKeyConfigured()) {
            throw new ParameterException(spec.commandLine(), "Domino access token must be set with -a parameter or DOMINO_API_TOKEN environment variable!");
        }

        if (isKeyConfigured() && !isTokenConfigured()) {
            
            System.err.println("Warning: Domino API Keys are being deprecated. Switch to using Domino service account tokens at your convenience.");
        }

        if ((StringUtils.isBlank(apiUrl) || Strings.CI.equals(ENV_API_URL, apiUrl)) && !isProxiable()) {
            throw new ParameterException(spec.commandLine(), "Domino API URL must be set with -u parameter or DOMINO_API_URL environment variable!");
        }

        return new CommandLine.RunLast().execute(parseResult); // default execution strategy
    }

    /**
     * Returns the proper target Domino URL or proxy address for the request.
     */
    public String getDominoUrl() {
        return isProxiable() ? apiProxy : apiUrl;
    }

    /**
     * attaches token or API key header to HTTP request
     * @return
     */
    public Consumer<Builder> addDominoAuthorization() {
        return builder -> {
            // Do not attach authorization if proxy is set
            if (!isProxiable()) {
                // Prefer token over API key
                if (isTokenConfigured()) {
                    builder.setHeader("Authorization", "Bearer " + apiToken);
                } else {
                    builder.setHeader("X-Domino-Api-Key", apiKey);
                }
            }
        };
    }
    
    private boolean isProxiable() {
        return StringUtils.isNotBlank(apiProxy) && !Strings.CI.equals(ENV_API_PROXY, apiProxy);
    }

    private boolean isKeyConfigured() {
        return StringUtils.isNotBlank(apiKey) && !Strings.CI.equals(ENV_API_KEY, apiKey);
    }

    private boolean isTokenConfigured() {
        return StringUtils.isNotBlank(apiToken) && !Strings.CI.equals(ENV_API_TOKEN, apiToken);
    }
}