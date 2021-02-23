#!/bin/bash

set -eu

touch ${TOMCAT_BASE}/logs/access_log.log && chown -R ${USERNAME}:${GROUPNAME} ${TOMCAT_BASE} && tail -F ${TOMCAT_BASE}/logs/access_log.log &


cd ${TOMCAT_BASE}
exec su-exec ${USERNAME}:${GROUPNAME} java -jar $JAVA_OPTS ${TOMCAT_BASE}/${SPRINGBOOT_APP_JAR}  --server.port=$SERVER_PORT