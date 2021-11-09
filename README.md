## LeJournal
Sample project, exploring ~~yet~~ another approach to implementing a clean architecture for an android application.
LeJournal is a news application based on The Guardian [API](https://open-platform.theguardian.com/documentation/).  It is composed of two screens : List of articles => Details of an article.

<img src="https://github.com/medhdj/LeJournal/blob/main/docs/Screenshot_feed.jpg?raw=true" width="300" style="display:inline">
<img src="https://github.com/medhdj/LeJournal/blob/main/docs/Screenshot_details.jpg?raw=true" width="300" style="display:inline">


## Technologies:

- clean architecture + modularization 
- MVVM
- Kotlin
- Hilt
- RxJava
- Paging library 3
- .... see [BuildSrc](https://github.com/medhdj/LeJournal/blob/main/buildSrc/src/main/kotlin/Dependencies.kt) for more 

## Requirements

- Android Studio
- JDK 11
- The Guardian API Key
you can set the Guardian API key by setting "THEGUARDIAN_API_KEY" env variable or put you key directly in the [data build file](https://github.com/medhdj/LeJournal/blob/main/data/build.gradle). The first option is more secure than the second one :skull:

## Project architecture
**App layer:** 

Hosts all the android platform specifics like views, view models, livedata, this layer is also responsible for dependency injection using hilt

**Business:** 

In this layer we find the different use cases and models that contribute to fulfilling the business requirements, this layer also defines the contracts that the repositories must follow.

**Data:** 

This layer is responsible for fetching data from the sources

**Core:** 

helper layer containing utilities and extensions to simplify the life of developers. :sleeping: :sleeping: :sleeping:
