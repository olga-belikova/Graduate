
### Запуск тестов

Для запуска тестов необходимо выполнить следующие шаги:
1. Запустить приложение Docker Desktop на локальном компьютере.
2. Склонировать репозиторий https://github.com/olga-belikova/Graduate на локальный компьютер. 
3. Открыть проект в IntelliJ IDEA.
4. Запустить контейнеры, введя в терминале команду: `docker-compose up --build`.
5. Запустить SUT, введя в новой вкладке терминала команду: `java -jar artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app`
  Приложение запускается на порту 8080, по умолчанию используется БД MySQL.
6. Для подключения к БД PostgreSQL необходимо в файле `build.gradle` заменить `db.url` на `systemProperty 'db.url', System.getProperty('db.url', 'jdbc:postgresql://localhost:5432/app')` и запустить SUT командой в терминале `java -jar artifacts/aqa-shop.jar -Dspring.datasource-postgresql.url=jdbc:postgresql://localhost:5432/app`.
7. Запустить тесты, введя в новой вкладке терминала команду:
- `./gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app` - для БД MySQL

- `./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app` - для БД PostgreSQL
8. Запустить формирование отчета Allure командой в терминале: `./gradlew allureserve`. Отчет открывается автоматически в браузере.

