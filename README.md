[![Build Status](https://travis-ci.org/denis-zhdanov/traute.svg?branch=master)](https://travis-ci.org/denis-zhdanov/traute)
[![Maven Central](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/tech/harmonysoft/traute-javac/maven-metadata.xml.svg)](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/tech/harmonysoft/traute-javac/maven-metadata.xml.svg)

## TL;DR

*Traute* is a *javac* plugin which makes bytecode for source code below  

```java
@NotNull
public Integer service(@NotNull Integer i) {
    return adjust(i);
}
```  

look like if it's compiled from this:  

```java
@NotNull
public Integer service(@NotNull Integer i) {
    if (i == null) {
        throw new NullPointerException("Argument 'i' of type Integer (#0 out of 1, zero-based) is marked by @NotNull but got null for it");
    }
    Integer tmpVar1 = adjust(i);
    if (tmpVar1 == null) {
        throw new NullPointerException("Detected an attempt to return null from method MyClass.service() marked by @NotNull");
    }
    return tmpVar1;
}
```  

Couple of notes:
* this is default behavior, the plugin provides a number of [configuration options](core/javac/README.md#7-settings) like an ability to define exception to throw, customize its text etc
* explicitly marking all target method parameters/return types by *@NotNull* might be tedious, so, the plugin can be configured to consider them to be not-*null* by default - see the [NotNullByDefault](core/javac/README.md#72-notnullbydefault-annotations) and [Nullable](core/javac/README.md#73-nullable-annotations)

## Table of Contents

* [1. License](#1-license)
* [2. Rationale](#2-rationale)
* [3. Alternatives](#3-alternatives)
* [4. Name Choice](#4-name-choice)
* [5. Usage](#5-usage)
  * [5.1. Command Line](#51-command-line)
  * [5.2. Gradle](#52-gradle)
  * [5.3. Maven](#53-maven)
  * [5.4. Ant](#54-ant)
* [6. Releases](#6-releases)
* [7. How to Contribute](#7-how-to-contribute)
* [8. Contributors](#8-contributors)
* [9. Evolution](#9-evolution)
* [10. Feedback](#10-feedback)
* [11. Acknowledgments](#11-acknowledgments)

## 1. License

See the [LICENSE](LICENSE.md) file for license rights and limitations (MIT).

## 2. Rationale

Null references are [considered](https://en.wikipedia.org/wiki/Null_pointer#History) to be one of the most expensive mistakes in IT design. It's not surprising that there are numerous efforts to solve it. Here are a couple of examples from the *Java* world:
* [*Kotlin*](https://kotlinlang.org/) fights them at the [language level](https://kotlinlang.org/docs/reference/null-safety.html)
* many tools try to report it as early as possible, for example, here [*IntelliJ IDEA*](https://www.jetbrains.com/idea/) warns us about a possible *NPE*: 

  <img src="/docs/img/warning-intellij.png" height="100px">

* production code often looks like below:

  ```java
  import static com.google.common.base.Preconditions.checkNotNull;

  ...

  public void service(Input input) {
    checkNotNull(input, "'input' argument must not be null");
    // Process the input
  }
  ```

*Kotlin* really solves the problem but *Java* is still a very popular language, so, we have to deal with nullable values. Current tool allows to automate such 'enforce nullability contract' checks generation.

## 3. Alternatives

I found the only alternative which provides similar functionality - [*Project Lombok*](https://projectlombok.org/features/NonNull). Here are pros and cons for using it:
* only [*lombok.NonNull*](https://projectlombok.org/api/lombok/NonNull.html) annotation is supported - there might be problems with *IDE* highlighting possible *NPE*s in source code
* the feature is implemented through a custom [*Annotaton Processing Tool*](https://docs.oracle.com/javase/7/docs/technotes/guides/apt/index.html), which means that there are two set of *\*.class* files after the compilation - one from original code and another one with the tool-added instrumentations. Compiler plugin-based approach is more natural for such task as it's completely transparent for the further assembly construction
* *Lombok* doesn't support [NotNullByDefault](core/javac/README.md#72-notnullbydefault-annotations)
* a solution offered by the current project [works only for the javac8](core/javac/README.md#5-limitations), *Lombok* might operate with *javac6* and *javac7* (as *APT API* is available starting from *java6*, however, I have not verified that)
* *Lombok* is more mature and popular at the moment

## 4. Name Choice

I really like German - how it sounds, language rules, everything, so, wanted to use a german word.  

*Traute* sounds nice and has a good [meaning](http://dictionary.reverso.net/german-english/Traute) - *Trust*. Users trust the tool and the tool enforces trust in application :wink:

## 5. Usage

### 5.1. Command Line

The core functionality is a [*Javac* plugin](core/javac/README.md) which adds *null*-checks into the generated *\*.class* files. It's possible to [use the plugin directly](core/javac/README.md#6-usage) from a command line, however, there are a number of adapters for popular build systems

### 5.2. Gradle

There is a [dedicated Traute plugin](facade/gradle/README.md#3-usage) for the [Gradle](https://gradle.org/) build system

### 5.3. Maven

[This page](facade/maven/README.md#3-usage) contains instructions on how to use *Traute* from [Maven](http://maven.apache.org/)

### 5.4. Ant

[This page](facade/ant/README.md#3-sample) contains instructions on how to use *Traute* from [Ant](https://ant.apache.org/)

## 6. Releases

[Release Notes](RELEASE.md)

*Javac Plugin*  
<a href='https://bintray.com/bintray/jcenter/tech.harmonysoft%3Atraute-javac?source=watch' alt='Get automatic notifications about new "tech.harmonysoft:traute-javac" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>

*Gradle Plugin*  

<a href='https://bintray.com/denis-zhdanov/harmonysoft.tech/traute-gradle?source=watch' alt='Get automatic notifications about new "traute-gradle" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>

You can also subscribe for the new versions notification through [twitter](https://twitter.com/traute_java) and [facebook](https://www.facebook.com/java.traute/).

## 7. How to Contribute

* [report a problem/ask for enhancement](https://github.com/denis-zhdanov/traute/issues)
* [submit a pull request](https://github.com/denis-zhdanov/traute/pulls)
* help me to make small presents to my wife in order to persuade her that spending free time on *OSS* might be not the worst idea :yum: [![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=3GJDPN3TH8T48&lc=RU&item_name=Traute&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted)

## 8. Contributors

* [Denis Zhdanov](https://github.com/denis-zhdanov)

## 9. Evolution

As the project is basically a [*Javac* plugin](core/javac/README.md) and convenient build system-specific adapters to it, new features should be added to the core part. Please check the [corresponding chapter](core/javac/README.md#8-evolution).

## 10. Feedback

Please use any of the channels below to provide your feedback, it's really valuable for me:
* [email](mailto:traute.java@gmail.com)
* [twitter](https://twitter.com/traute_java)
* [facebook](https://www.facebook.com/java.traute/)

## 11. Acknowledgments

<img src="/docs/img/intellij.png" height="70px">

[JetBrains](https://www.jetbrains.com/) helps open source projects by offering free licenses to their awesome products. 

![yourkit](https://www.yourkit.com/images/yklogo.png) 

YourKit supports open source projects with its full-featured Java Profiler.
YourKit, LLC is the creator of <a href="https://www.yourkit.com/java/profiler/">YourKit Java Profiler</a>
and <a href="https://www.yourkit.com/.net/profiler/">YourKit .NET Profiler</a>,
innovative and intelligent tools for profiling Java and .NET applications.  

![jprofiler](https://www.ej-technologies.com/images/product_banners/jprofiler_large.png)  

EJ Technologies supports open source projects by offering a [JProfiler Java profiler](https://www.ej-technologies.com/products/jprofiler/overview.html) license.