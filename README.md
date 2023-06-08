[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java CI](https://github.com/ksmpartners/domino-cli/actions/workflows/build.yml/badge.svg)](https://github.com/ksmpartners/domino-cli/actions/workflows/build.yml)

# Introduction

Domino Data Lab Command Line Interface is a client used provision and control Domino.

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
Licensed under the MIT License.
