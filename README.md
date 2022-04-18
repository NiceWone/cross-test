# Сервис регистрации событий

В проекте етсь папки /resource/file-in с примером данных

Описание API с моделями в swagger.

```txt
GET /api/run
POST /api/add
GET /api/receive
```

Входные:

```txt
2022-04-11 13:10:39.283 ERROR 13344 --- [nio-8080-exec-1] ru.nicewone.service.FileService          : java.lang.RuntimeException: runtime exception fatal
2022-04-11 13:08:32.030 ERROR 18696 --- [nio-8080-exec-2] ru.nicewone.service.FileService          : test
```

Выходные:

```txx
{"type":2,"dateTime":"2022-04-11T20:05:56.0967551","data":{"date":"2022-04-11","level":"ERROR","thread":"[nio-8080-exec-1]","message":" : test eeroor lelvel","class":"] ru.nicewone.service.FileService          :","timestamp":"13:10:39.283"}}
{"type":2,"dateTime":"2022-04-11T20:05:56.098754","data":{"date":"2022-04-11","level":"ERROR","thread":"[nio-8080-exec-1]","message":" : java.lang.RuntimeException: runtime exception fatal","class":"] ru.nicewone.service.FileService          : java.lang.RuntimeException:","timestamp":"13:10:39.283"}}
{"type":2,"dateTime":"2022-04-11T20:05:56.1007541","data":{"date":"2022-04-11","level":"ERROR","thread":"[nio-8080-exec-2]","message":" : test","class":"] ru.nicewone.service.FileService          :","timestamp":"13:08:32.030"}}
```

RegExp для запроса:

```json
{
  "type": 2,
  "eventRegExp": ".*ERROR.*",
  "data": {
    "date": "\\d{4}-\\d{2}-\\d{2}",
    "timestamp": "\\d{2}:\\d{2}:\\d{2}\\.\\d{3}",
    "level": "INFO|ERROR|WARN|TRACE|DEBUG|FATAL",
    "thread": "\\[([^\\]]+)]",
    "class": "\\].([^\\[]+).:",
    "message": "\\s:([^\\:]).*"
  }
}
```

