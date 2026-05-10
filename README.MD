# Workshop Booking System

Система управления бронированием мастер-классов.

## Технологический стек
- Django 4.x
- Django REST Framework
- SQLite (можно заменить на PostgreSQL)
- JWT аутентификация

## Установка

1. Клонируйте репозиторий
2. Создайте виртуальное окружение: `python -m venv venv`
3. Активируйте: `venv\Scripts\activate` (Windows) или `source venv/bin/activate` (Mac/Linux)
4. Установите зависимости: `pip install -r requirements.txt`
5. Примените миграции: `python manage.py migrate`
6. Запустите сервер: `python manage.py runserver`

## API Endpoints

### Аутентификация
- POST /api/token/ - получение токена
- POST /api/token/refresh/ - обновление токена
- POST /api/register/ - регистрация пользователя

### Мастер-классы
- GET /api/workshops/ - список мастер-классов
- GET /api/workshops/{id}/ - детали мастер-класса
- POST /api/workshops/ - создание (только админ)
- PUT /api/workshops/{id}/ - обновление (только админ)
- DELETE /api/workshops/{id}/ - удаление (только админ)

### Бронирования
- GET /api/bookings/ - мои бронирования
- POST /api/bookings/ - создать бронирование
- DELETE /api/bookings/{id}/ - отмена бронирования
- GET /api/bookings/my_bookings/ - мои активные бронирования

## Коллекция Postman

Импортируйте файл `workshop_booking.postman_collection.json` в Postman.