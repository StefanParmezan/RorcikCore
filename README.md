<div align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&height=160&color=0:36BCF7,50:6C63FF,100:FF4F9A&text=RorcikCore&fontSize=46&fontColor=ffffff&fontAlignY=38&desc=Core%20library%20for%20Rorcik%20mods&descAlignY=60&descSize=16&animation=fadeIn" alt="RorcikCore" width="100%" />

![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-3C8527?style=for-the-badge)
![Forge](https://img.shields.io/badge/Forge-47.4.10-E04E14?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![License](https://img.shields.io/badge/License-All_Rights_Reserved-B22222?style=for-the-badge)

</div>

## О проекте

**RorcikCore** — базовая библиотека для модов Rorcik и модпака **The Last Dawn**.
Мод не добавляет игровой контент: он предоставляет общие абстракции и API, которые используют **Rorcik World Generation** и **Rorcik Structures**.

Основные задачи библиотеки:

- единая система свойств для предметов;
- реестры и события регистрации;
- городские и климатические зоны;
- утилиты для сериализации и идентифицируемых объектов.

## Возможности

| Модуль | Что делает |
|:---|:---|
| `property` | Типизированные ключи и дескрипторы свойств, валидация, значения по умолчанию, копирование контейнера |
| `capability` | Динамическая capability `RorPropertyContainer` для `ItemStack` с тегом `rorcik_core:has_properties` |
| `city` | Геометрия городских зон: границы в чанках и блоках, пересечения, расширение границ |
| `zone` | Биомные и температурные зоны, правила размещения, `SavedData` |
| `registry` | Реестр идентифицируемых объектов |
| `event` | События регистрации свойств и идентифицируемых объектов для модов-зависимостей |
| `util` | Хелперы для `ResourceLocation` и сериализации |

## Стек

| Компонент | Версия |
|:---|:---|
| Minecraft | 1.20.1 |
| Forge | 47.4.10 |
| Java | 17 |
| Gradle | wrapper проекта |
| Тесты | JUnit 5 |

## Структура проекта

```text
src/main/java/ru/rorcik/core/
├── capability/    # Capability-based свойства ItemStack
├── city/          # Городские зоны и провайдеры
├── event/         # События регистрации
├── property/      # Система свойств и реестр дескрипторов
├── registry/      # Реестр идентифицируемых объектов
├── util/          # Утилиты
├── zone/          # Биомные и температурные зоны
└── RorcikCore.java
```

## Сборка и запуск

Требования:

- JDK 17;
- Forge 1.20.1;
- Gradle Wrapper из репозитория.

```bash
./gradlew build
./gradlew test
./gradlew runClient
./gradlew runServer
./gradlew runData
```

`RorcikCore` — библиотека, поэтому отдельного игрового контента в ней нет. Для проверки интеграции используйте соседние проекты `RorcikWorldGeneration` и `RorcikStructuresMod`.

## Подключение к другим модам

`RorcikWorldGeneration` и `RorcikStructuresMod` подключают ядро через composite build:

```groovy
includeBuild('../RorcikCore')
```

Поэтому репозитории должны лежать рядом:

```text
RorcikCore/
RorcikWorldGeneration/
RorcikStructuresMod/
```

В коде доступны:

- `RorPropertyContainer` — capability для хранения свойств;
- `RorPropertyRegistry` — регистрация и поиск дескрипторов;
- `RegisterPropertiesEvent` и `RegisterIdentifiableEvent` — точки расширения;
- `CityZone`, `ZoneRules`, `ZoneRegistrySavedData` — общие модели зон.

## Тесты

```bash
./gradlew test
```

Сейчас покрыта геометрия городских зон: границы чанков и блоков, пересечения, расширение границ и валидация некорректных границ.

## Лицензия

Код мода распространяется на условиях **All Rights Reserved** (см. `gradle.properties`).
В `LICENSE.txt` находятся лицензионные уведомления Minecraft Forge и MCP, относящиеся к MDK.

## Авторы

- Rorcik
- [StefanParmezan](https://github.com/StefanParmezan)

Вопросы и предложения — Telegram: [@StefanParmezan](https://t.me/StefanParmezan).
