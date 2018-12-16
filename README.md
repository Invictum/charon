Charon
======

Charon is a Minecraft server side plugin that provides ability to track players PVP statistics and save it to a file in CSV format.

Installation
------------
Just place a Charon jar into servers `plugins` directory and start a server.
Latest Charon pre-build JAR is available in [release](https://github.com/Invictum/charon/releases/latest) section.

Configuration
-------------
On first run plugin will create a default configuration file at `SERVER_ROOT_DIRECTORY/plugins/Charon/config.yml`

There is only one option available for configuration
```
# Path to file to dump data
path: charon-data.csv
```

It defines a path to a file where PVP data should be recorded. Path is relative to the server root directory by default.
But it is possible to provide full absolute path, e. g. `/opt/data/pvp.csv`

Make sure specified path is writable for a user that runs a Minecraft server.
There is no need to create file manually - it will be created on first attempt to log a PVP event.

Data format
-----------
Charon records each player's dead and kill events in a CSV format with `,` symbol separator.
Each line represents one PVP event, two record variants are possible.
```
1544911787779,freeman
```
Record that describes an event of user's death.
Where `1544911787779` is a timestamp in UNIX format and `freeman` is a nickname of player who dead.
```
1544925934804,potato,freeman
```
Record that describes an event of user's kill.
Where `1544925934804` is a timestamp in UNIX format, `potato` is a nickname of player who dead, `freeman` is a nickname of killer player.

Charon unable to detect "accident kills". For example if one player push another into the lava this will be recorded as usual dead event without a killer.

Charon takes into account only the last hit, so if several players attack another the one who made a last hit will be recorded as a killer.

Build plugin manually
---------------------
To build a JAR manually following components must be installed:
 - Java JDK 8+
 - Maven

To build a JAR clone a repository with source code, open it in a terminal and invoke a command
```
mvn clean package
```
This will build sources, run tests and produce plugin JAR file into `target/charon-1.0-SNAPSHOT-jar-with-dependencies.jar`

Sometimes it is necessary to rebuld plugin for specific Minecraft server version. To achieve it edit `dependency` section in `pom.xml` file
```
<dependency>
    <groupId>org.spigotmc</groupId>
    <artifactId>spigot-api</artifactId>
    <version>1.10.2-R0.1-SNAPSHOT</version>
    <scope>provided</scope>
    <type>jar</type>
</dependency>
```
Set suitable version for `spigot-api`, save file and rebuild plugin as usual.

Charon is a free software and available under the GPL license.