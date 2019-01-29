mvn spring-boot:run

wget https://search.maven.org/remotecontent?filepath=io/swagger/codegen/v3/swagger-codegen-cli/3.0.3/swagger-codegen-cli-3.0.3.jar -o tools/

java -jar ./tools/swagger-codegen-cli.jar generate -l typescript-fetch -i http://localhost:8888/v2/api-docs -o frontend/src/apis/


http://localhost:8888/frontend/swagger-ui.html#/

# spring data rest 
http://localhost:8888/api/data/xUsers/1