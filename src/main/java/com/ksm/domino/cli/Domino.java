package com.ksm.domino.cli;

import com.ksm.domino.cli.command.collaborator.Collaborator;
import com.ksm.domino.cli.command.dataset.Dataset;
import com.ksm.domino.cli.command.project.Project;
import com.ksm.domino.cli.command.user.User;
import com.ksm.domino.cli.provider.EnvironmentVariableDefaultProvider;
import com.ksm.domino.cli.provider.OutputFormat;
import com.ksm.domino.cli.provider.VersionProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import static picocli.CommandLine.*;

@Command(name = "domino",
        header = "%n@|green Domino CLI|@",
        description = "%n@|blue Domino Data Lab Command Line Interface is a client to used provision and control Domino.|@%n",
        mixinStandardHelpOptions = true,
        versionProvider = VersionProvider.class,
        defaultValueProvider = EnvironmentVariableDefaultProvider.class,
        subcommands = {
                Collaborator.class,
                Dataset.class,
                User.class,
                Project.class
        })
public class Domino implements Runnable {

    @Option(names = {"-k", "--key"}, description = "Domino API Key.", defaultValue = "DOMINO_API_KEY", scope = ScopeType.INHERIT)
    public String apiKey;

    @Option(names = {"-u", "--url"}, description = "Domino API URL.", defaultValue = "DOMINO_API_URL", scope = ScopeType.INHERIT)
    public String apiUrl;

    @Option(names = {"-t", "--timeout"}, description = "Timeout in seconds waiting for Domino responses.", defaultValue = "60", scope = ScopeType.INHERIT)
    public long timeoutSeconds;

    @Option(names = {"-o", "--output"}, description = "Output format TEXT, JSON, XML", defaultValue = "JSON", scope = ScopeType.INHERIT)
    public OutputFormat outputFormat;

    @Spec
    CommandLine.Model.CommandSpec spec;

    public static void main(String... args) {
        if (SystemUtils.IS_OS_WINDOWS) {
            AnsiConsole.systemInstall(); // enable colors on Windows
        }

        final CommandLine commandLine = new CommandLine(new Domino());
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);
        commandLine.setSubcommandsCaseInsensitive(true);
        commandLine.setOptionsCaseInsensitive(true);
        int exitCode = commandLine.execute(args);

        if (SystemUtils.IS_OS_WINDOWS) {
            AnsiConsole.systemUninstall(); // cleanup when done
        }
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (StringUtils.isBlank(apiKey) || StringUtils.equalsIgnoreCase("DOMINO_API_KEY", apiKey)) {
            System.err.println("Domino API Key must be set with -k parameter or DOMINO_API_KEY environment variable!");
            return;
        }

        if (StringUtils.isBlank(apiUrl) || StringUtils.equalsIgnoreCase("DOMINO_API_URL", apiUrl)) {
            System.err.println("Domino API URL must be set with -u parameter or DOMINO_API_URL environment variable!");
            return;
        }
        // if the command was invoked without subcommand, show the usage help
        spec.commandLine().usage(System.err);
    }
}