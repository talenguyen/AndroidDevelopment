#!/usr/bin/env python3

import os
import sys

SNAPSHOT = "-SNAPSHOT"
GRADLE_PROPERTIES = "gradle.properties"


def change_to_non_snapshot_version():
    # Get remove '-SNAPSHOT' from version
    non_snapshot_version_content = open(GRADLE_PROPERTIES, "r").read().replace(SNAPSHOT, "")

    # Write non-SNAPSHOT version to `gradle.properties` file
    gradle_properties_file = open(GRADLE_PROPERTIES, "w")
    gradle_properties_file.write(non_snapshot_version_content)
    gradle_properties_file.close()
    return


def get_version():
    gradle_properties_file = open(GRADLE_PROPERTIES, "r")
    for line in gradle_properties_file.readlines():
        if line.startswith("VERSION_NAME="):
            return line.replace("VERSION_NAME=", "").replace("\n", "")
    return


def get_next_version():
    current_version = get_version()
    version_parts = current_version.split('.')
    version_major = version_parts[0]
    version_minor = version_parts[1]
    version_patch = version_parts[2]
    next_version_path = int(version_patch) + 1
    return ".".join([version_major, version_minor, str(next_version_path)])


def change_to_next_development_version():
    gradle_properties_file = open(GRADLE_PROPERTIES, "r")
    content = ""
    for line in gradle_properties_file.readlines():
        if line.startswith("VERSION_NAME="):
            content += "VERSION_NAME=" + get_next_version() + "-SNAPSHOT\n"
        else:
            content += line

    gradle_properties_file = open(GRADLE_PROPERTIES, "w")
    gradle_properties_file.write(content)
    gradle_properties_file.close()
    return


def run(command):
    # print(command)
    os.system(command)
    return


if __name__ == '__main__':
    print("1. Change the version in `gradle.properties` to a non-SNAPSHOT version.")
    change_to_non_snapshot_version()

    print("2. `git commit -am \"Prepare for release X.Y.Z.\"` (where X.Y.Z is the new version)")
    module_name = os.path.basename(os.getcwd()) if len(sys.argv) == 1 else len(sys.argv[1])
    version = get_version()
    tag = module_name + "-" + str(version)
    message = "Prepare for release " + tag
    command = "git commit -am \"" + message + "\""
    run(command)

    print("3. `git tag -a X.Y.Z -m \"Version X.Y.Z\"` (where X.Y.Z is the new version)")
    message = "Version " + tag
    command = "git tag -a " + tag + " -m \"" + message + "\""
    run(command)

    print("4. `./gradlew clean uploadArchives`")
    command = "./gradlew clean bintrayUpload"
    run(command)

    print("5. Update the `gradle.properties` to the next SNAPSHOT version.")
    change_to_next_development_version()

    print("6. `git commit -am \"Prepare next development version.\"`")
    command = "git commit -am \"Prepare next development version.\""
    run(command)

    print("7. `git push && git push --tags`")
    command = "git push && git push --tags"
    run(command)
