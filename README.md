# RestAssured

# Basics

Add these in the all the class files

```java
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
```

## Pom

```xml
    <dependencies>
        <!-- https://mvnrepository.com/artifact/io.rest-assured/rest-assured -->
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>4.3.3</version>
            <scope>test</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.7.0</version>
            <scope>test</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.1</version>
        </dependency>
```

## Given When Then

### given()

```java
.given()
	.contentType()
	.body()
```

### when()

```java
.when().get(uri)
```

```java
.when().post(uri)
```

```java
.when().put(uri)//update
```

```java
when().delete(uri)
```

### then()

```java
.then() // this will give a response object
```

```java
.body("path.key", equalTo(value))
```

```java
.statusCode(200)
.log().all();
```

# Requests

## Different ways to create request body

1. Hashmap
2. org.json
3. POJO
4. External Json file
5. String after java 11 with 3 double quotes 

```java
""" {"id" :11, "name":"daya", "courses":["C","C++"]} """
```

### Hashmap (maybe recomended)

```java
**Hashmap data = new HashMap();**
url = "https://sdasdahkbd"

data.put("name","daya");
String courseArr[] ={"C","C++"};
data.put("courses", courseArr)

given()
	.contentType("application/json")
	.body(data)
.when()
	.post(url)
.then()
	.statusCode(201)
	.assertThat()
	.body("name", equalTo("daya"))
	.body("courses[0]", equalTo("C"))
	
```

### org.json (Not recommended because of to string and vernurabalities)

pom

```xml
        <dependency>
            <groupId>org.json</groupId>
            <artifactId>json</artifactId>
            <version>20240303</version>
            <exclusions> <!-- Because of vernarable -->
                <exclusion> 
                    <groupId>com.jayway.jsonpath</groupId> 
                    <artifactId>json-path</artifactId>    
                </exclusion>
            </exclusions>
        </dependency>
        <!-- Added new vesion of excluded version -->
        <dependency>
            <groupId>com.jayway.jsonpath</groupId>
            <artifactId>json-path</artifactId>
            <version>2.9.0</version>
        </dependency>
```

```java
JSONObject data = new JSONObject();
url = "https://sdasdahkbd"

data.put("name","daya");
String courseArr[] ={"C","C++"};
data.put("courses", courseArr)

given()
	.contentType("application/json")
	.body(**data.toString()**) // this is important step thats why dont recomend. 
	//you have to convert the json **toString** by org.json
.when()
	.post(url)
.then()
	.statusCode(201)
	.assertThat()
	.body("name", equalTo("daya"))
	.body("courses[0]", equalTo("C"))
```

### POJO (Recommended)

Create constructor for what body needs 

```java
package models;

public class Product {

    private int id;
    private String name;
    private String courses[];

    public Product(){}

    //Used for POST requests
    public Product(String name, String[] courses){
        setName(name);
        setDescription(courses);
    }

    //Used for PUT requests // this has ID in it// because you need id to find what to update
    public Product(**int id**, String name, String[] courses){
        **setId(id);**
        setName(name);
        setDescription(courses);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }
}
```

```java
String courseArr[] ={"C","C++"};

//Constructor way or
Product **data** = new Product("daya",courseArr);

//or
//manually add all of them
Product **data** = new Product();
data.setName("daya");
String courseArr[] ={"C","C++"};
data.setcourses(courseArr)
// end manual way

url = "https://sdasdahkbd"

given()
	.contentType("application/json")
	.body(**data**)
.when()
	.post(url)
.then()
	.statusCode(201)
	.assertThat()
	.body("name", equalTo("daya"))
	.body("courses[0]", equalTo("C"))
```

### External File ( Not Recommended because of to string and vernurabalities and many files has to be saved)

Json File

![image.png](RestAssured%2011fd1c8e714880cb81d0cc5ababba192/image.png)

![image.png](RestAssured%2011fd1c8e714880cb81d0cc5ababba192/image%201.png)

## Query Params/Path Params

```java
//If the url looks like this https://reqres.in/api/users?page=2

// anything after question mark is query params
String domain = "https://reqres.in/api/"
given()
	.pathParam("myPath", "users") //These are like variables
	.queryParam("page",2)
	.queryParam("id",5)
.when()
	.get(domain+"{myPath}") //Not tested if this works
.then()
	.statusCode(200)
	.log().body();

```

## Cookies and Headers

### Validate a specific cookie / headers

```java
//**Validate** cookie info, this will fail because cookie value 
//changes everytime
Response response = given()
										.when()
											.get("https://www.google.com")
										then()
											.**cookie**("AEC","AsdajnsdlaksddWNIads-z_fakjdsakljn");
											
//**Validate** header info
Response response = given()
										.when()
											.get("https://www.google.com")
										then()
											.header("Content-Type","text/html; charset=ISO-8859-1")
											.header("Content-Encoding","gzip")
											.header("Server","gws");
```

### Get a specific cookie info

```java
// **Get cookie** info
Response response = given()
										.when()
											.get("https://www.google.com");
String aec_cookie = response.**getCookie**("AEC")
sout(aec_cookie)

// **Get header** info
Response response = given()
										.when()
											.get("https://www.google.com");
String type_header = response.**getHeader**("Content-Type");
String encoding_header = response.**getHeader**("Content-Encoding");
sout(aec_cookie + encoding_header);
```

									

### Get All cookies

```java
// **Get ALL cookies** info
Response response = given()
										.when()
											.get("https://www.google.com");
Map<String,String> all_cookies = response.**getCookies()**;
for(String key:all_cookies.keySet())
{
		String all_cookies=response.getCookie(key);
		sout(all_cookies)
}

// **Get ALL Headers** info
Response response = given()
										.when()
											.get("https://www.google.com");
**Headers all_headers = response.getHeaders();**
for(String hd:all_headers)
{
		String all_headers=hd.getName()+hd.getValue();
		sout(all_headers)
}
					
```

# Responses

## Bodies

### Status code

```java
.then()
	.assertThat()
	.statusCode(200)
```

### Logging

```java
.then()
	**.log()**
	.body()
	
.then()
	**.log()**
	.headers()
	
.then()
	**.log()**
	.cookies()
```

```java
.then()
	**.log()**
	.all()
```

### Verify Fields (Matcher)

```java
.then()
	**.assertThat()**
	.statusCode(200)
	**.body("id", equalTo(3))**
	**.body("name",equalTo("someString"))**
```

### Verify Complex bodies

```java
.then()
	.assertThat()
	.statusCode(200)
	.body("records.**size()**", equalTo(19))
	.body("records.**size()**", **greaterThan**(0)).
  body("records.id", **everyItem(notNullValue()**)).
  body("records.name", everyItem(notNullValue())).
  body("records.description", everyItem(notNullValue())).
  body("records.price", everyItem(notNullValue())).
  body("records.category_id", everyItem(notNullValue())).
  body("records.category_name", everyItem(notNullValue())).
	body(**"records.id[0]", equalTo("25")**);
	
```

### Header

```java
.then()
	.assertThat()
	.statusCode(200)
	.body("records.size()", equalTo(19))
	.body("records.size()", greaterThan(0)).
  body("records.id", everyItem(notNullValue())).
  **header**("Content-Type", equalTo("application/json;charset=UTF-8"))
```

### Verify Schema

```java
given().
    when().get("https://reqres.in/api/users?page=2")
    .then()
    .assertThat().body(JsonSchemaValidator.matchesJsonSchema("give full string schema"))
    .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonschema.json"))//jsonschmea presnet in src/stest/resourses/jsonschema.json
    .statusCode(200);
```

## Authentication and Authorization

### Basic

```java
@Test
void authentication(){    
*given*()            
		**.auth()
		.basic("postman","password")**
.when()
		.get("https://postman-echo.com/basic-auth")            
.then()            
		.assertThat()
		.body("authenticated",*equalTo*(true))            
		.statusCode(200);
}
```

### Digest

```java
          
		**.auth()
		.digest("postman","password")**
```

### Preemptive

```java
**.auth()
.preemptive().basic("postman","password")**
```

### api Key( as query param or header depends)

```java
    @Test
    void authentication(){
        String apikey = "jkbdaskjdalsndlaksndlaknsd";
        given().
                **queryParam("appid",**apikey**)**
                .when().get("https://api.openweathermap.org.com/data........")
                .then()
                .statusCode(200).log().body();
    }
```

### OAuth1.0

```java
		**.auth()
		.oauth("consumerkey","consumerSecret","accessToken","tokenSecreat")**
```

### Oauth2.0

```java
   @Test
    void oauth2(){
        String oauth2 = "ghp_dummy";
        given().
                **auth().oauth2(oauth2)**
                .when().get("https://api.github.com/user/repos")
                .then()
                .statusCode(200).log().body();
    }
```

### Bearer ( using header)

```java
    @Test
    void authentication(){
		    //bearer token will be only the token but you need to add Bearer at bigining
        //https://github.com/settings/tokens
        String barerToken = "Bearer ghp_dummy";
        given().
                **headers("Authorization",barerToken)**
                .when().get("https://api.github.com/user/repos")
                .then()
                .statusCode(200).log().body();
    }
```

## Deserializing(Daya’s Understanding - Comparing POJO to POJO)

```java
    @Test
    public void getDeserializedProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        //"id":"2","name":"Cross-Back Training Tank","description":"The most awesome phone of 2013!","price":"299.00","category_id":"2","category_name":"Active Wear - Women"}
        Product **expectedProduct** = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );

        Product **actualProduct** =
            given().
                queryParam("id", "2").
            when().
                **get(endpoint).
                    as(Product.class);// Product.class is the POJO created**

        assertThat(**actualProduct**, **samePropertyValuesAs**(**expectedProduct**));
    }
}
```

# Extras

## Faker

[https://github.com/DiUS/java-faker](https://github.com/DiUS/java-faker)

```xml
<!-- [https://mvnrepository.com/artifact/com.github.javafaker/javafaker](https://mvnrepository.com/artifact/com.github.javafaker/javafaker) -->
<dependency>
	<groupId>com.github.javafaker</groupId>
	<artifactId>javafaker</artifactId>
	<version>1.0.2</version>
</dependency>
```

```java
Faker faker = new Faker();
String firstname = faker.getName();
```

## API Chaining ( ITestContext)

### Test level

```java

//testNG way of setting global variable
ClassA{
		void create_user (ITestContext context)
		{
			context.setAttribute("user_id",id);
		}
}
//testNG way of using variable
ClassB{
			void update_user (ITestContext context)
			{
				int id = (Integer) context.getAttribute("user_id",id);
			}
}
//This will run only by TestNG.xml cannot run a single test
//This will work only within the test
```

### Suite Level

```java

//testNG way of setting global variable
ClassA{
		void create_user (ITestContext context)
		{
			context**.getSuite().**setAttribute("user_id",id);
		}
}
//testNG way of using variable
ClassB{
			void update_user (ITestContext context)
			{
				int id = (Integer) context**.getSuite().**getAttribute("user_id",id);
			}
}

//This will run only by TestNG.xml cannot run a single test
```
