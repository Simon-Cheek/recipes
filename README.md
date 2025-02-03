# recipes

Recipes app backend - utilizes Java Spring Boot, JPARepository, MySQL, and more.

- Currently primarily designed to fetch entire users at once (including recipes / categories)
- Supports modification and deletion of individual recipes / categories in addition to users

## Database Information

Database Name = recipes_app
Tables:
- Users (One to Many with Recipes and Categories)
- Recipes (Many to Many with Categories)
- Categories (Many to Many with Recipes)
