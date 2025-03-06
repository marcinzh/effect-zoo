# Effect Zoo for Scala 3

Inspired by [Effect Zoo](https://github.com/ocharles/effect-zoo) for Haskell.

Effect Zoo provides:

1. Code gallery, where we can demonstrate and compare the syntax of different effect systems,
when they are assigned the same task.
The sources are located [here](https://github.com/marcinzh/effect-zoo/tree/master/modules/core/src/main/scala/effect_zoo/contests).

2. Microbenchmark suite. See running instruction [below](#running-microbenchmarks).


### Included scenarios (contests):

- `cdown`: The CountDown scenario from Haskell's Effect Zoo. Uses single `State` effect.

- `sumh`: In the spirit of CountDown, but uses more effects: `Reader`, `Writer`, `State`, etc.

- `mulst`: Multiple instances of `State` effects used at the same time.
Compares overhead of effect stack size, by running the same number of `State` operations,
spread over a varying number `State` effects (1 to 5).

- `reint`: The Reinterpretation scenario from Haskell's Effect Zoo.
Demonstrates use of custom-defined effects as application modules, and effect reinterpretation as DI mechanism.


### Included effect systems:

- [Cats Core](https://github.com/typelevel/cats). Plain monad transformers, used with manual lifting.

- [Cats MTL](https://github.com/typelevel/cats-mtl).

- Cats IO. Typically, the same solution as Cats MTL, but with `IO` from [Cats Effect](https://github.com/typelevel/cats-effect) as the base monad.

- [Cats Eff](https://github.com/atnos-org/eff). Don't confuse with [Cats Effect](https://github.com/typelevel/cats-effect).

- [ZIO](https://github.com/zio/zio).
ZIO doesn't come with predefined standard effects such as`Reader`, `Writer` and `State`.
In this project we explore [several ways](https://github.com/marcinzh/effect-zoo/tree/main/modules/core/src/main/scala/effect_zoo/auxx/zio_/rws) of implementing them.

- ZPure from [ZIO Prelude](https://github.com/zio/zio-prelude).

- [Turbolift](https://github.com/marcinzh/turbolift).

- [Kyo](https://github.com/fwbrasil/kyo).

There are many more effect systems for Scala (e.g. 3 more implementations of the Eff monad alone),
but they are unmaintained and unavailable for Scala 2.13 or 3.x.

# Running Microbenchmarks

> [!IMPORTANT]
> Some effect systems require **Java 11** or newer.

- **Step 0:** Ensure you have a modern terminal.

  Without support for Unicode characters and True Color, charts won't be displayed properly.
  If you are on Windows, old `cmd.exe` or PowerShell will get you garbage on the screen.
  The [New Terminal](https://github.com/Microsoft/Terminal) might work (unconfirmed).

- **Step 1:** Have sbt installed.

  https://www.scala-sbt.org/download.html

- **Step 2:** Get a local copy of this repo and launch sbt:

  ```
  git clone https://github.com/marcinzh/effect-zoo.git
  cd effect-zoo
  sbt
  ```

- **Step 3:** From sbt's command prompt, pick **one** method:

  - Run JMH by using predefined alias (takes ~20 minutes to complete):  
    ```
    runbench
    ```
  
  - Run JMH directly, with your own parameters, e.g:  
    ```
    bench/Jmh/run -i 3 -wi 3 -f1 -t1 -r 3 -w 3 .*Cdown
    ```
  
  - Run Effect-Zoo's own, simple microbenchmark tool.
  It's less accurate than JMH, but displays charts live:
    ```
    diy/run --all
    ```
