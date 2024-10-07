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

1. The whole `security` package
2. The pom.xml dependencies used for security.
    - jbcrypt
    - jitpack.io repository
    - TokenSecurity
3. `config.properties` with the secret key for jwt should be in the resources folder. Since we don't want to expose the file on Github, you need to to create it yourself and add this into it:
  
   ```terminal
   SECRET_KEY=4c9f92b04b1e85fa56e7b7b0a34f2de4f5b08cd9bb4dfe8ac4d73b4f7f6ef37b
   ISSUER="Dit navn"
   TOKEN_EXPIRE_TIME="1800000"
   ```

4. The utils package
5. The startServer method in the `Application` class
6. The `generalExceptionHandler` and `apiExceptionHandler` method in the `ApplicationConfig` class
7. Add the User and Role entity classes to the HibernateConfig file
8. Lastly, you need to add Role.EVERYONE, ROLE.USER, and ROLE.ADMIN to the endpoints. Check how it is done in the `SecurityRoutes` class.
