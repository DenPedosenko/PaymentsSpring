# Платежі

1. Клієнт реєструється в системі і має:
   - одну або кілька Кредитних карт, кожна з яких відповідає певному Рахунку в системі;
   - можливість за допомогою Рахунку здійснити Платіж;
2. Платіж має один з двох статусів:

   - підготовлений;
   - відправлений.
     (За бажанням: реалізувати можливість генерації pdf-звіту про платіж).

3. Клієнт має особистий кабінет, в якому може переглядати інформацію про свої платежі та рахунки. Необхідно реалізувати можливість сортування:
   - платежів:
   1. за номером;
   2. за датою (від старих до нових, від нових до старих);
   - рахунків:
   1. за номером;
   2. за найменуванням рахунку;
   3. за розміром залишку коштів на рахунку.
4. Клієнт може поповнити або заблокувати один зі своїх рахунків. Для розблокування рахунку клієнт повинен зробити запит до на розблокування.
5. Адміністратор системи володіє правами:
   - блокування / розблокування користувача;
   - блокування / розблокування одного з рахунків користувача.

# Завдання фінального проекту

Розробити веб-застосунок, що підтримує функціональність відповідно до варіанту завдання.

## Вимоги до реалізації

1. На основі сутностей предметної області створити класи, які їм відповідають.

2. Класи і методи повинні мати назви, що відображають їх функціональність, і повинні бути рознесені по пакетам.

3. Оформлення коду має відповідати [Java Code Convention](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf).

4. Інформацію щодо предметної області зберігати у реляційній базі даних (в якості СУБД рекомендується використовувати [MySQL](https://www.mysql.com/) або [PostgreSQL](https://www.postgresql.org/)).

5. Застосунок повинен буду структурованим за архітектурою _MVC_ та _Spring Boot_.

> Дозволено використання Project Lombok.

6. Застосунок має підтримувати роботу з кирилицею (бути багатомовним), в тому числі при зберіганні інформації в базі даних:

   - повинна бути можливість перемикання мови інтерфейсу;
   - повинна бути підтримка введення, виведення і зберігання інформації (в базі даних), записаної на різних мовах;
   - в якості мов обрати мінімум дві: одна на основі кирилиці (українська або російська), інша на основі латиниці (англійська).
   - дати повинні бути реалізовані через DataTime бібліотеку (Java8).

7. Використання [Spring Resource Bundle](https://www.baeldung.com/java-resourcebundle) заохочується.
8. При реалізації бізнес-логіки необхідно використовувати шаблони проектування: Команда, Стратегія, Фабрика, Будівельник, Сінглтон, Фронт-контролер, Спостерігач, Адаптер та ін.

> Використання шаблонів повинно бути обґрунтованим.

9. Повинно бути застосована _Spring Authorization_.

10. Для доступу до даних використовувати _JPA_ ([Spring Data](https://spring.io/projects/spring-data) та/або [Hibernate](<https://en.wikipedia.org/wiki/Hibernate_(framework)>)).

11. Реалізувати захист від повторної відправки даних на сервер при оновленні сторінки (реалізувати [PRG](https://en.wikipedia.org/wiki/Post/Redirect/Get)).

12. Обробка виключних ситуацій за допомогою Exception Handling with Spring for REST API заохочується.

13. У застосунку повинні бути реалізовані аутентифікація і авторизація, розмежування прав доступу користувачів системи до компонентів програми. Шифрування паролів заохочується.

14. Код повинен містити коментарі документації (всі класи верхнього рівня, нетривіальні методи і конструктори).

15. Використання ThymeLeaf заохочується.

16. Використання додаткових фреймворків ([Swager](https://swagger.io/) та інші) заохочується

17. Застосунок має бути покритим модульними тестами (мінімальний відсоток покриття - **40%**). Написання інтеграційних тестів заохочуються.

18. Реалізувати механізм пагінації сторінок з даними.

19. Всі поля введення повинні бути із валідацією даних.

20. Застосунок має коректно реагувати на помилки та виключні ситуації різного роду (кінцевий користувач не повинен бачити _stack trace_ на стороні клієнта).

21. Самостійне розширення постановки задачі по функціональності заохочується (додавання капчі, формування звітів у різних форматах, тощо)!

22. Використання _HTML, CSS, JS_ фреймворків для інтерфейсу користувача ([Bootstrap](https://getbootstrap.com/), [Materialize](https://materializecss.com/) та ін.) заохочується!
