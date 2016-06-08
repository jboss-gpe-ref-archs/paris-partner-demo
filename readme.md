# Compile 

mvn -Pinstall

# Microservice Client with REST Servlet component 

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0-SNAPSHOT/xml/features
features:install micro-camel-service-servlet 
features:install micro-camel-client

# Test service

http http://localhost:8181/camel/rest/users/charles/hello
jcurl http://localhost:8181/camel/rest/users/charles/hello

# Microservice Client with Jetty Standalone 

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0-SNAPSHOT/xml/features
features:install micro-camel-service-standalone 
features:install micro-camel-client

http http://localhost:9090/camel/rest/users/charles/hello
jcurl http://localhost:9090/camel/rest/users/charles/hello

# Microservice Client with Jetty Secured 

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0-SNAPSHOT/xml/features
features:install micro-camel-service-standalone-secured 
features:install micro-camel-client

http http://localhost:9191/camel/rest/users/charles/hello
http -a admin:admin http://localhost:9191/camel/rest/users/charles/hello
jcurl -u admin:admin http://localhost:9191/camel/rest/users/charles/hello


