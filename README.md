<div align="center">
    <div style="flex-grow: 1; width: 50vw"> 
<a href="https://www.dominodatalab.com/" alt="Domino Data Lab">
   <img class="spinner" loading="lazy" height="80" width="116" src="https://www.dominodatalab.com/hubfs/NBM/domino-logo-spinner.webp" alt="Domino Data Logo - Graphic part">
   <img loading="lazy" height="80" src="https://www.dominodatalab.com/hubfs/NBM/domino-logo-text.webp" alt="Domino Data Logo - Text part">
</a>
    </div>
 
# Domino Data Lab Command Line Interface
</div>
<br>

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)
[![Java CI](https://img.shields.io/github/actions/workflows/status/ksmpartners/domino-cli/build.yml?branch=main&logo=GitHub&style=for-the-badge)](https://github.com/ksmpartners/domino-cli/actions/workflows/build.yml)

Domino Data Lab Command Line Interface is a client used provision and control Domino.

# Requirements

To use this CLI you will need the following:

- JDK 11+
- Domino API Key to an active Domino instance

# Build and Test

To build the project requires Apache Maven:

```shell
$ mvn clean package
```

To test the CLI:

```shell
$ java -jar ./target/domino-cli.jar --version

    ___                _
   /   \___  _ __ ___ (_)_ __   ___
  / /\ / _ \| '_ ` _ \| | '_ \ / _ \
 / /_// (_) | | | | | | | | | | (_) |
/___,' \___/|_| |_| |_|_|_| |_|\___/

Domino CLI 5.5.1
Copyright 2023, KSM Technology Partners LLC
Java OpenJDK Runtime Environment 11.0.18+10 Oracle Corporation
OS Windows 10 10.0 amd64
```

# Domino Settings

To connect to Domino you will need at least the URL of where your Domino installation is and a personal API key to
access the API. You can set your variables either as environment variables `DOMINO_API_KEY` and `DOMINO_API_URL` or use
the command line parameters `-k` and `-u`.

To add your environment variables in Windows Powershell you can add them like this:

```powershell
[Environment]::SetEnvironmentVariable("DOMINO_API_KEY", "YOUR_KEY", "User")
[Environment]::SetEnvironmentVariable("DOMINO_API_URL", "https://domino.yourcompany.com/v4", "User")
```

To test it is working run the following command to print out your current user information:

```shell
$ java -jar ./target/domino-cli.jar user current
```

If you are not using environment variables it would be:

```shell
$ java -jar ./target/domino-cli.jar -k YOUR_KEY -u https://domino.yourcompany.com/v4 user current
```

```json
{
  "firstName": "Homer",
  "lastName": "Simpson",
  "fullName": "Homer Simpson",
  "id": "6124ffbfa7db86282dde302a",
  "userName": "hs12345",
  "email": "homer.simpson@springfield.org"
}
```

# Help
![image](https://user-images.githubusercontent.com/4399574/155019857-986e31e4-abc0-4eda-9e96-3ed39c746119.png)

### License

***
Licensed under the [MIT](https://en.wikipedia.org/wiki/MIT_License) license.

`SPDX-License-Identifier: MIT`

### Copyright

***
Domino and Domino Data Lab are © 2023 Domino Data Lab, Inc. Made in San Francisco. 
