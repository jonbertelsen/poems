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


