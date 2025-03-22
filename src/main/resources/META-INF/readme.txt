mvn clean gluonfx:build gluonfx:package -Pandroid
mvn -Pandroid gluonfx:install
mvn -Pandroid -X gluonfx:nativerun

all in one
mvn clean gluonfx:build gluonfx:package gluonfx:install gluonfx:nativerun -Pandroid
