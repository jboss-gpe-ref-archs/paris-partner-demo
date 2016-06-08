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

# Microservice Client with Jetty Secured (JAAS + SSL) 

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0-SNAPSHOT/xml/features
features:install micro-camel-service-standalone-secured 

* To get the Server certificate 

openssl s_client -showcerts -connect localhost:9191

http --verify=camel-rest-service-standalone-secured/src/main/resources/tls/server.pem https://localhost:9191/camel/rest/users/charles/hello
http --verify=no https://localhost:9191/camel/rest/users/charles/hello
http --verify=no -a admin:admin https://localhost:9191/camel/rest/users/charles/hello

jcurl -k -u admin:admin https://localhost:9191/camel/rest/users/charles/hello

# Using Fabric8 v1 

mvn -Pf8-deploy

killall java
rm -rf data
rm -rf instances/

./bin/deletefabric8
fabric:create -r localip -m 127.0.0.1


# All - To control/check if the project is working

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0-SNAPSHOT/xml/features
features:install micro-camel-service-servlet
features:install micro-camel-service-standalone
features:install micro-camel-service-standalone-secured

http http://localhost:8181/camel/rest/users/charles/hello
http http://localhost:9090/camel/rest/users/charles/hello
http http://localhost:9191/camel/rest/users/charles/hello
http -a admin:admin http://localhost:9191/camel/rest/users/charles/hello

