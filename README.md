# Android sample app FinMarket

FinMarket is a sample app (pet-project). Written in Kotlin aiming to demonstrate Android app architecture using Android development best practices.

### Introduction
The app is being built using Clean Architecture based on the MVVM pattern. Architecture principles follow Google recommended Guide to app architecture.

### Libraries and features used
- Android KTX
- LiveData
- ViewModel
- Room Persistence Library
- View Binding feature
- Paging Library
- Navigation component
- Retrofit 2 http://square.github.io/retrofit
- Dagger 2 https://github.com/google/dagger
- Dagger Android https://dagger.dev/android.html
- RxJava 2 https://github.com/ReactiveX/RxJava
- RxAndroid 2 https://github.com/ReactiveX/RxAndroid
- Glide https://github.com/bumptech/glide

### Youtube-like UX pattern
One of the UX patterns of nowadays is youtube or instagram-like patterns. In this UX each section or page has its own back stack, so when going from page to page your back stack saves. For example, on Instagram you can go inside "Home" section and dive in a couple of screens and then go to the "Search" page and then back to Home and the state of the "Home" page still remains.
To implement this kind of UX I used ViewPager and create in MainActivity my own back stack for it.
```Kotlin
private val viewPagerBackStack = Stack<Int>()
```

Then each of ViewPager Fragments is a Host Fragment in Android Navigation Component. So each of them has its own back stack controlled by Navigation Controller.

### Single Source of Truth
The sample app implements a single source of truth principle via Room persistence framework. This was done by the following. The repository layer always returns observable data (LiveData) from SQL database (Room). Depending on data (for example if it is empty) and on a timestamp of last request repository decides whether do update or not database. And each update of the database will trigger the emitting of LivaData to ViewModels.

### TODO
- Tests
- Kotlin Coroutines
- Work manager

### UI
A sample app consist of 3 screens: Stock List, Stock Details including News section and Favorites screen.
![](https://raw.githubusercontent.com/makzimi/finmarket/master/screenshots/Screenshot_01.png)
![](https://raw.githubusercontent.com/makzimi/finmarket/master/screenshots/Screenshot_02.png)
![](https://raw.githubusercontent.com/makzimi/finmarket/master/screenshots/Screenshot_03.png)

### API Keys
To run the app please get api keys from
https://financialmodelingprep.com/developer/docs/
and
https://newsapi.org/

And put them in local.properties file:
```Groovy
stocksapi.token=a024a6d3ba39313cee81093d2e69cf04
newsapi.token=92d610963da14182bccb8fe180cc993d
```


### License
Copyright 2019 Kachinkin Maxim

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
