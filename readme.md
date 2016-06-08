# Compile 

mvn -Pinstall

# Microservice Client with REST Servlet component 

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0/xml/features
features:install micro-camel-service-servlet 
features:install micro-camel-client

# Test service

http http://localhost:7777/camel/client?user=charles
jcurl http://localhost:7777/camel/client?user=charles

http http://localhost:8181/camel/rest/users/charles/hello
jcurl http://localhost:8181/camel/rest/users/charles/hello

# Microservice Client with Jetty Standalone 

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0/xml/features
features:install micro-camel-service-standalone 
features:install micro-camel-client

http http://localhost:7777/camel/client?user=charles
jcurl http://localhost:7777/camel/client?user=charles

http http://localhost:9090/camel/rest/users/charles/hello
jcurl http://localhost:9090/camel/rest/users/charles/hello

# Microservice Client with Jetty Secured (JAAS + SSL) 

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0/xml/features
features:install micro-camel-service-standalone-secured 

* To get the Server certificate 

openssl s_client -showcerts -connect localhost:9191

http --verify=camel-rest-service-standalone-secured/src/main/resources/tls/server.pem https://localhost:9191/camel/rest/users/charles/hello
http --verify=no https://localhost:9191/camel/rest/users/charles/hello
http --verify=no -a admin:admin https://localhost:9191/camel/rest/users/charles/hello

jcurl -k -u admin:admin https://localhost:9191/camel/rest/users/charles/hello

# Using Fabric8 v1 

mvn -Pf8-deploy

./bin/deletefabric8
./bin/fuse

#fabric:create --wait-for-provisioning 
fabric:create -r localip -m 127.0.0.1 --wait-for-provisioning
fabric:profile-edit --pid io.fabric8.elasticsearch-insight/network.host=127.0.0.1 insight-elasticsearch.datastore

fabric:container-create-child --jmx-user admin --jmx-password admin --profile micro-camel-servlet root rest-servlet
fabric:container-create-child --jmx-user admin --jmx-password admin --profile micro-camel-client root rest-client

fabric:container-add-profile root insight-console insight-elasticsearch.datastore insight-logs.elasticsearch insight-metrics.elasticsearch
fabric:container-add-profile rest-servlet insight-camel insight-elasticsearch.node insight-logs.elasticsearch insight-metrics.elasticsearch
fabric:container-add-profile rest-client insight-camel insight-elasticsearch.node insight-logs.elasticsearch insight-metrics.elasticsearch

fabric:container-remove-profile root insight-console insight-elasticsearch.datastore insight-logs.elasticsearch insight-metrics.elasticsearch
fabric:container-remove-profile rest-client insight-camel insight-elasticsearch.node insight-logs.elasticsearch insight-metrics.elasticsearch
fabric:container-remove-profile rest-servlet insight-camel insight-elasticsearch.node insight-logs.elasticsearch insight-metrics.elasticsearch

#fabric:profile-edit --pid parameters/server-port=8182 micro-camel-client

# All - To control/check if the project is working

features:addurl mvn:org.jboss.fuse/camel-assembly/1.0/xml/features
features:install micro-camel-service-servlet
features:install micro-camel-service-standalone
features:install micro-camel-service-standalone-secured

http http://localhost:8181/camel/rest/users/charles/hello
http http://localhost:9090/camel/rest/users/charles/hello
http --verify=no https://localhost:9191/camel/rest/users/charles/hello
http --verify=no -a admin:admin https://localhost:9191/camel/rest/users/charles/hello

# Security with Apiman

rm -rf wildfly-10.0.0.Final
unzip wildfly-10.0.0.Final.zip
unzip -o apiman-distro-wildfly10-1.2.6.Final-overlay.zip -d wildfly-10.0.0.Final
cd wildfly-10.0.0.Final
./bin/standalone.sh -c standalone-apiman.xml


* Without basic Auth

jcurl -k https://localhost:8443/apiman-gateway/demo/users/1.0/charles/hello
jcurl -k https://localhost:8443/apiman-gateway/demo/users/2.0/charles/hello

* Basic Authentication

jcurl -k -u demo:demooo https://localhost:8443/apiman-gateway/demo/users/2.0/charles/hello
jcurl -k -u demo:demo https://localhost:8443/apiman-gateway/demo/users/2.0/charles/hello

* OpenID Connect

export auth_result=$(curl -k -X POST https://localhost:8443/auth/realms/demo/protocol/openid-connect/token -d grant_type=password -d username=admin -d password=admin -d client_id=demo)
export access_token=$(echo -e "$auth_result" | awk -F"," '{print $1}' | awk -F":" '{print $2}' | sed s/\"//g | tr -d ' ')

jcurl -k https://localhost:8443/apiman-gateway/demo/users/3.0/charles/hello -H "Authorization:Bearer $access_token"

* Role

export auth_result=$(curl -k -X POST https://localhost:8443/auth/realms/demo/protocol/openid-connect/token -d grant_type=password -d username=lambda -d password=lambda -d client_id=demo)
export access_token=$(echo -e "$auth_result" | awk -F"," '{print $1}' | awk -F":" '{print $2}' | sed s/\"//g | tr -d ' ')
jcurl -k https://localhost:8443/apiman-gateway/demo/users/4.0/charles/hello -H "Authorization:Bearer $access_token"



