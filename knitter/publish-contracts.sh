#! /bin/sh
set -x

rm -rf ../farmer/src/test/resources/pacts
mkdir -p ../farmer/src/test/resources/pacts
cp ./target/pacts/* ../farmer/src/test/resources/pacts
