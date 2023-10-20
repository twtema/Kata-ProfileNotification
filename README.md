# 🚀 МКС ProfileNotification
## 📌 Введение
МКС ProfileNotification - это микросервис, специализирующийся на обработке уведомлений о смене контактных данных пользователей. При получении запроса на изменение контакта, сервис генерирует код подтверждения и отправляет его в Apache Kafka для дальнейшей обработки и уведомления пользователя.

## 🌟 Основные характеристики

**Интеграция с Apache Kafka:** ProfileNotification активно использует Apache Kafka для асинхронной обработки и передачи уведомлений.

**Генерация кодов подтверждения:** При смене контактных данных, сервис автоматически генерирует код подтверждения, который может быть использован для верификации изменений.

## 📚 Документация
Для получения дополнительной информации и деталей об использовании сервиса обратитесь к интерактивной документации Swagger (если доступно).

## 🚀 Начало работы
* Запуск сервиса: Убедитесь, что ProfileNotification запущен и функционирует корректно.
* Настройка Apache Kafka: Убедитесь, что ваш инстанс Apache Kafka запущен и доступен для микросервиса. Также проверьте, что топик contact-update-topic существует и настроен правильно.
* Отправка сообщений: Для инициирования процесса смены контактных данных, отправьте соответствующее сообщение в топик contact-update-topic. ProfileNotification будет слушать этот топик, генерировать коды подтверждения и отправлять их обратно в Kafka.

## 📝 Примечание: Убедитесь, что у вас установлена последняя версия Apache Kafka и что все зависимости и конфигурации настроены правильно.