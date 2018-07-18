#!/bin/bash

set -e

LC_ALL=C

# You can run it from any directory.
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

dir=$1
foo=$2
bar=$3

find ${dir} -type f -name "*.properties" ! -path "*/.idea/*" ! -path "*.gradle/*" ! -path "*/build/*" ! -path "*/.git/*" -exec sed -i '' "s/${foo}/${bar}/g" {} \;
find ${dir} -type f -name "*.gradle" ! -path "*/.idea/*" ! -path "*.gradle/*" ! -path "*/build/*" ! -path "*/.git/*" -exec sed -i '' "s/${foo}/${bar}/g" {} \;
find ${dir} -type f -name "*.xml" ! -path "*/.idea/*" ! -path "*.gradle/*" ! -path "*/build/*" ! -path "*/.git/*" -exec sed -i '' "s/${foo}/${bar}/g" {} \;
find ${dir} -type f -name "*.kt" ! -path "*/.idea/*" ! -path "*.gradle/*" ! -path "*/build/*" ! -path "*/.git/*" -exec sed -i '' "s/${foo}/${bar}/g" {} \;
find ${dir} -type f -name "*.java" ! -path "*/.idea/*" ! -path "*.gradle/*" ! -path "*/build/*" ! -path "*/.git/*" -exec sed -i '' "s/${foo}/${bar}/g" {} \;

