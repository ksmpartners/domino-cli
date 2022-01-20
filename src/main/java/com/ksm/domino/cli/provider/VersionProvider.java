package com.ksm.domino.cli.provider;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import picocli.CommandLine;

public class VersionProvider implements CommandLine.IVersionProvider {

    public String[] getVersion() throws Exception {
        String[] version = new String[6];
        version[0] = String.format("%n@|yellow Domino CLI %s|@", StringUtils.defaultIfEmpty(
                    JarClassLoader.class.getPackage().getImplementationVersion(), "UNKNOWN"));
        version[1] = String.format("@|fg(240) Copyright %s, KSM Technology Partners LLC|@", Calendar.getInstance().get(Calendar.YEAR));
        version[2] = String.format("@|fg(240) Java %s %s %s|@", SystemUtils.JAVA_RUNTIME_NAME, SystemUtils.JAVA_RUNTIME_VERSION,
                    SystemUtils.JAVA_SPECIFICATION_VENDOR);
        version[3] = String.format("@|fg(240) OS %s %s %s|@", SystemUtils.OS_NAME, SystemUtils.OS_VERSION, SystemUtils.OS_ARCH);
        version[4] = "%n";
        return version;
    }

}
