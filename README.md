# Сервис регистрации событий

В проекте етсь папка /resource/file-in с примером данных. Выходные файлы в /resource/file-out.

Описание API с моделями в swagger.

```txt
Запуск
GET /api/run 

Просмотр
GET /api/receive

Добавление 
POST /api/add
```

Модель для добавления через POST:

```json
{
  "type": 0,
  "eventRegExp": "string",
  "data": {
    "additionalRegExp1": "string",
    "additionalRegExp2": "string",
    "additionalRegExp3": "string"
  }
}
```

Пример для добавления через POST:

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

Пример входных данных:

```txt
2022-04-11 13:10:39.283 ERROR 13344 --- [nio-8080-exec-1] ru.nicewone.service.FileService          : java.lang.RuntimeException: runtime exception fatal
2022-04-11 13:08:32.030 ERROR 18696 --- [nio-8080-exec-2] ru.nicewone.service.FileService          : test
```

Пример выходных данных:

```txt
{"type":2,"dateTime":"2022-04-11T20:05:56.0967551","data":{"date":"2022-04-11","level":"ERROR","thread":"[nio-8080-exec-1]","message":" : test eeroor lelvel","class":"] ru.nicewone.service.FileService          :","timestamp":"13:10:39.283"}}
{"type":2,"dateTime":"2022-04-11T20:05:56.098754","data":{"date":"2022-04-11","level":"ERROR","thread":"[nio-8080-exec-1]","message":" : java.lang.RuntimeException: runtime exception fatal","class":"] ru.nicewone.service.FileService          : java.lang.RuntimeException:","timestamp":"13:10:39.283"}}
{"type":2,"dateTime":"2022-04-11T20:05:56.1007541","data":{"date":"2022-04-11","level":"ERROR","thread":"[nio-8080-exec-2]","message":" : test","class":"] ru.nicewone.service.FileService          :","timestamp":"13:08:32.030"}}
```

```txt
Файл 1 - 39098 строк
Файл 2 - 10860 строк
Файл 3 - 10860 стро
---
2022-04-18 19:51:23.568  INFO 20220 --- [nio-8080-exec-7] ru.nicewone.service.FileService          : Current File is : 08.04.2022_10_10.txt
2022-04-18 19:51:23.568  INFO 20220 --- [onPool-worker-1] ru.nicewone.service.FileService          : Current File is : 09.04.2022_12_12.txt
2022-04-18 19:51:23.568  INFO 20220 --- [onPool-worker-7] ru.nicewone.service.FileService          : Current File is : 07.04.2022_10_10.txt
2022-04-18 19:51:23.574  INFO 20220 --- [onPool-worker-7] ru.nicewone.service.FileService          : Erase all data in file :D:\dev\pet\cross-test\build\resources\main\files-out\07.04.2022_10_10.json
2022-04-18 19:51:23.574  INFO 20220 --- [onPool-worker-1] ru.nicewone.service.FileService          : Erase all data in file :D:\dev\pet\cross-test\build\resources\main\files-out\09.04.2022_12_12.json
2022-04-18 19:51:23.575  INFO 20220 --- [nio-8080-exec-7] ru.nicewone.service.FileService          : Erase all data in file :D:\dev\pet\cross-test\build\resources\main\files-out\08.04.2022_10_10.json
2022-04-18 19:51:25.654  INFO 20220 --- [onPool-worker-1] ru.nicewone.service.FileService          : Current File is ended : 09.04.2022_12_12.txt
2022-04-18 19:51:25.741  INFO 20220 --- [nio-8080-exec-7] ru.nicewone.service.FileService          : Current File is ended : 08.04.2022_10_10.txt
2022-04-18 19:51:29.887  INFO 20220 --- [onPool-worker-7] ru.nicewone.service.FileService          : Current File is ended : 07.04.2022_10_10.txt
2022-04-18 19:51:29.887  INFO 20220 --- [nio-8080-exec-7] ru.nicewone.service.FileService          : All time is 6319575800
2022-04-18 19:51:29.887  INFO 20220 --- [nio-8080-exec-7] ru.nicewone.service.FileService          : All time is PT6.3196776S
---
2022-04-18 19:51:44.524  INFO 20220 --- [onPool-worker-1] ru.nicewone.service.FileService          : Current File is : 09.04.2022_12_12.txt
2022-04-18 19:51:44.524  INFO 20220 --- [onPool-worker-7] ru.nicewone.service.FileService          : Current File is : 07.04.2022_10_10.txt
2022-04-18 19:51:44.524  INFO 20220 --- [nio-8080-exec-9] ru.nicewone.service.FileService          : Current File is : 08.04.2022_10_10.txt
2022-04-18 19:51:44.530  INFO 20220 --- [nio-8080-exec-9] ru.nicewone.service.FileService          : Erase all data in file :D:\dev\pet\cross-test\build\resources\main\files-out\08.04.2022_10_10.json
2022-04-18 19:51:44.531  INFO 20220 --- [onPool-worker-1] ru.nicewone.service.FileService          : Erase all data in file :D:\dev\pet\cross-test\build\resources\main\files-out\09.04.2022_12_12.json
2022-04-18 19:51:44.531  INFO 20220 --- [onPool-worker-7] ru.nicewone.service.FileService          : Erase all data in file :D:\dev\pet\cross-test\build\resources\main\files-out\07.04.2022_10_10.json
2022-04-18 19:51:46.502  INFO 20220 --- [nio-8080-exec-9] ru.nicewone.service.FileService          : Current File is ended : 08.04.2022_10_10.txt
2022-04-18 19:51:46.541  INFO 20220 --- [onPool-worker-1] ru.nicewone.service.FileService          : Current File is ended : 09.04.2022_12_12.txt
2022-04-18 19:51:50.480  INFO 20220 --- [onPool-worker-7] ru.nicewone.service.FileService          : Current File is ended : 07.04.2022_10_10.txt
2022-04-18 19:51:50.480  INFO 20220 --- [nio-8080-exec-9] ru.nicewone.service.FileService          : All time is 5957260600
2022-04-18 19:51:50.480  INFO 20220 --- [nio-8080-exec-9] ru.nicewone.service.FileService          : All time is PT5.9573242S
---
2022-04-18 19:52:00.493  INFO 20220 --- [onPool-worker-1] ru.nicewone.service.FileService          : Current File is : 09.04.2022_12_12.txt
2022-04-18 19:52:00.493  INFO 20220 --- [onPool-worker-7] ru.nicewone.service.FileService          : Current File is : 07.04.2022_10_10.txt
2022-04-18 19:52:00.493  INFO 20220 --- [nio-8080-exec-1] ru.nicewone.service.FileService          : Current File is : 08.04.2022_10_10.txt
2022-04-18 19:52:00.499  INFO 20220 --- [nio-8080-exec-1] ru.nicewone.service.FileService          : Erase all data in file :D:\dev\pet\cross-test\build\resources\main\files-out\08.04.2022_10_10.json
2022-04-18 19:52:00.499  INFO 20220 --- [onPool-worker-1] ru.nicewone.service.FileService          : Erase all data in file :D:\dev\pet\cross-test\build\resources\main\files-out\09.04.2022_12_12.json
2022-04-18 19:52:00.500  INFO 20220 --- [onPool-worker-7] ru.nicewone.service.FileService          : Erase all data in file :D:\dev\pet\cross-test\build\resources\main\files-out\07.04.2022_10_10.json
2022-04-18 19:52:02.393  INFO 20220 --- [onPool-worker-1] ru.nicewone.service.FileService          : Current File is ended : 09.04.2022_12_12.txt
2022-04-18 19:52:02.682  INFO 20220 --- [nio-8080-exec-1] ru.nicewone.service.FileService          : Current File is ended : 08.04.2022_10_10.txt
2022-04-18 19:52:06.464  INFO 20220 --- [onPool-worker-7] ru.nicewone.service.FileService          : Current File is ended : 07.04.2022_10_10.txt
2022-04-18 19:52:06.464  INFO 20220 --- [nio-8080-exec-1] ru.nicewone.service.FileService          : All time is 5971411600
2022-04-18 19:52:06.464  INFO 20220 --- [nio-8080-exec-1] ru.nicewone.service.FileService          : All time is PT5.9714962S
---
С файлами на 5 Мб,38 Мб(300к строк),2 Мб
2022-04-18 20:02:51.189  INFO 14976 --- [nio-8080-exec-4] ru.nicewone.service.FileService          : All time is 50033626700
2022-04-18 20:02:51.189  INFO 14976 --- [nio-8080-exec-4] ru.nicewone.service.FileService          : All time is PT50.034475S
---
```

![alt text for screen readers](https://raw.githubusercontent.com/NiceWone/cross-test/master/src/main/resources/image_2022-04-18_19-52-18.png "Text to show on mouseover")
