#!/usr/bin/env sh

exec java \
  ${JAVA_OPTS} \
  -XX:+UseContainerSupport \
  -cp BOOT-INF/classes:BOOT-INF/lib/* com.sourdough.starter.StarterApplication "$@"
