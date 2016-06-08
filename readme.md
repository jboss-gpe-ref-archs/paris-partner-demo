# Compile 

mvn -Pinstall

# Microservice Client with REST Servlet component 

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0-SNAPSHOT/xml/features
features:install micro-camel-service-servlet 
features:install micro-camel-client

# Test service

http http://localhost:8181/camel/rest/users/charles/hello
jcurl http://localhost:8181/camel/rest/users/charles/hello

# Microservice Client with REST Servlet component 

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0-SNAPSHOT/xml/features
features:install micro-camel-service-standalone 
features:install micro-camel-client

http -a admin:admin  http://localhost:9090/camel/rest/users/charles/hello
curl -u admin:admin http://localhost:9090/camel/rest/users/charles/hello


