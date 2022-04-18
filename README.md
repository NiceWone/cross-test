# cross-test
cross-test

Пример 
{
  "type": 2,
  "eventRegExp": ".*ERROR.*",
  "data": {
    "date" : "\\d{4}-\\d{2}-\\d{2}",
    "timestamp": "\\d{2}:\\d{2}:\\d{2}\\.\\d{3}",
    "level": "INFO|ERROR|WARN|TRACE|DEBUG|FATAL",
    "thread": "\\[([^\\]]+)]",
    "class": "\\].([^\\[]+).:",
    "message" : "\\s:([^\\:]).*"
  }
}
