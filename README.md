## Цель работы
Изучить способ использования репозитория в Spring Data JPA. Реализовать CRUD-приложение
на Spring Boot с использованием встроенной СУБД H2, шаблонизатора Thymeleaf и репозитория
`CrudRepository` для управления сущностью «Покупатель» (создание, чтение, редактирование,
удаление, отображение списка и детальной информации).

## Архитектура проекта
- **Стек:** Java 17+, Spring Boot 4.1.1, Maven, Spring MVC, Spring Data JPA, Thymeleaf, Bootstrap 5
- **СУБД:** H2 (in-memory, режим `jdbc:h2:mem:institut`)
- **Основные слои:**
  - **Entity** (`ru.lab2.kafpin.Buyer`) — JPA-сущность, отображается на таблицу `buyers`
  - **Repository** (`ru.lab2.kafpin.repository.BuyerRepository`) — наследуется от `CrudRepository<Buyer, Long>`, обеспечивает доступ к данным
  - **Controller** (`ru.lab2.kafpin.BuyerController`) — обрабатывает HTTP-запросы, взаимодействует с репозиторием и возвращает имена Thymeleaf-шаблонов
  - **View** (`src/main/resources/templates/*.html`) — Thymeleaf-шаблоны: `main.html`, `details.html`, `create-buyer.html`, `edit_buyer.html`
  - **Config / Data** — `application.properties` (настройки БД и H2-консоли), `data.sql` (инициализация тестовыми данными)

- **Ключевые аннотации:** Перечислить ключевые аннотации текущей работы. 
`@SpringBootApplication` `KafpinApplication`
`@Entity` `Buyer`
`@Table(name = "buyers")` `Buyer`
`@Id` `id`
`@GeneratedValue(strategy = IDENTITY)` `id`
`@Column(name = "...")` `Buyer`
`@Controller` `BuyerController`
`@RequestMapping("/buyers")` `BuyerController`
`@GetMapping(...)`
`@PostMapping(...)`
`@PathVariable("id")`
`@ModelAttribute` `Buyer`

## Алгоритм работы

1. **Запуск приложения.** Запускается класс `KafpinApplication` (`@SpringBootApplication`).
   Spring Boot поднимает встроенный Tomcat на порту `8080`, автоматически конфигурирует
   DataSource для H2, создаёт EntityManagerFactory и сканирует репозитории Spring Data.

2. **Создание схемы.** Hibernate по аннотациям `@Entity` / `@Table` / `@Column` генерирует
   таблицу `buyers`. Благодаря свойству `spring.jpa.defer-datasource-initialization=true`
   после генерации схемы выполняется `data.sql`, который наполняет таблицу тестовыми
   записями (Иванов, Петрова, Сидоров).

3. **Просмотр списка покупателей.** Пользователь открывает `GET /buyers/main`.
   Контроллер вызывает `buyerRepository.findAll()`, помещает список в модель
   (`model.addAttribute("buyers", allBuyers)`) и возвращает шаблон `main.html`.
   Thymeleaf в цикле `th:each` строит таблицу со всеми записями.

4. **Просмотр детальной информации.** Переход по ссылке `Details` открывает
   `GET /buyers/details/{id}`. Контроллер вызывает `buyerRepository.findById(id)`,
   проверяет наличие записи через `Optional.isEmpty()`, и либо редиректит на список
   (если запись не найдена), либо передаёт объект в `details.html`.

5. **Создание нового покупателя.**
   - `GET /buyers/create` — показывает форму `create-buyer.html` с пустым объектом `Buyer`.
   - `POST /buyers/create` — принимает данные формы через `@ModelAttribute`, вызывает
     `buyerRepository.save(buyer)` и редиректит на `/buyers/main`.

6. **Редактирование.**
   - `GET /buyers/update/{id}` — находит запись по id, кладёт её в модель и открывает
     `edit_buyer.html`. В форме присутствует **скрытое поле** `id`, чтобы при отправке
     `POST /buyers/update` можно было понять, какую именно запись обновлять.
   - `POST /buyers/update` — проверяет существование записи через `existsById(id)`,
     затем вызывает `save(buyer)` (Hibernate выполнит UPDATE, т.к. id уже задан),
     редиректит на список.

7. **Удаление.** `GET /buyers/delete/{id}` — контроллер проверяет существование записи
   через `existsById(id)`, и если запись есть — вызывает `deleteById(id)`.
   Далее — редирект на `/buyers/main`.

8. **Консоль H2.** Включена через `spring.h2.console.enabled=true`, доступна по адресу
   `http://localhost:8080/h2-console`. Параметры подключения:
   - JDBC URL: `jdbc:h2:mem:institut`
   - User Name: `sa`
   - Password: `password`
