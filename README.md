# Poem Codelab exercise

This is a solution to part of the [Codelab exercise](https://dat3cph.github.io/material/rest-intro/exercises/codelab/)

Includes:

- IntelliJ setup
- JPA dependencies
- Lombok
- Junit 5
- Postgres drivers
- Test containers

Is created for Java 17 corretto

## Endpoints

[Overview](http://localhost:7070/routes)

## Architecture branch

The `architecture` branch has been refactored to use a layered architecture. 
This can offer a better separation of concerns and make the code more maintainable.
However, it could easily be even more elaborate, but this is a good start. For one thing, it needs
tests. DAO test and Rest endpoint tests would be nice.

If you want to clone the `architecture` branch, you can do so with the following command:

```bash
  git clone --branch architecture https://github.com/jonbertelsen/poems.git
```

## Helpful links and resources

1. [The Poem API Security Exercise](https://dat3cph.github.io/material/rest-test-security/exercises/poems-security/)
2.  [Useful snippets](https://dat3cph.github.io/material/tools/security/api)
3. [Token Security Library](https://github.com/Hartmannsolution/TokenSecurity)
4. [Thomas' solution that we can copy/paste from](https://github.com/Hartmannsolution/poemsolution)

## Security branch

This branch has been refactored to include security. It uses the TokenSecurity library.
This can be used to secure other APIs as well. You will need to copy these files:

- The whole `security` package
- The pom.xml dependencies used for security.
    - jbcrypt
    - jitpack.io repository
    - TokenSecurity
- config.properties with the secret key for jwt should be in the resources folder
- The `accessHandler` in the `ApplicationConfig` class
- The `setGeneralExceptionHandling` method in the `ApplicationConfig` class
- Add the User and Role entity classes to the HibernateConfig file
- Lastly, you need to add Role.EVERYONE, ROLE.USER, and ROLE.ADMIN to the endpoints. Check how it is done in the `SecurityRoutes` class.
  - Remember to check for a valid token in the `SecurityRoutes` class. The `before` is doing the job:
  
      ```java
        public static EndpointGroup getSecuredRoutes(){
            return ()->{
                path("/protected", ()->{
                    before(securityController.authenticate()); // check if there is a valid token in the header
                    get("/user_demo", (ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from USER Protected")), Role.USER);
                    get("/admin_demo", (ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from ADMIN Protected")), Role.ADMIN);
                });
        };
    }
    ```
