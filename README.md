# Weather

## Реализация проекта из [Java Роадмапа Сергея Жукова](https://zhukovsd.github.io/java-backend-learning-course/)

## Суть проекта
Веб-приложение для просмотра текущей погоды в разных городах.

## Техническое задание: [Weather](https://zhukovsd.github.io/java-backend-learning-course/projects/weather-viewer/)

## Запуск проекта

### Предварительные требования
1. Установите [Docker](https://www.docker.com/) и запустите демона
2. Установите [Maven](https://maven.apache.org/) для сборки проекта

### Сборка и запуск
Выполните в терминале:
```bash
mvn clean package  # Сборка проекта
docker compose build && docker compose up -d  # Запуск контейнеров
```

После запуска приложение будет доступно по адресу:  
[http://localhost:8080](http://localhost:8080)

### 🔧 Дополнительные настройки
- Для остановки: `docker compose down`
- Для просмотра логов: `docker compose logs -f`
- Конфигурация портов и других параметров - в файле `docker-compose.yml`

### Особенности реализации
- Используется Spring MVS + Hibernate как backend-фреймворк
- Для получение данных о погоде используется OpenWeatherMap
