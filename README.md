# Effect Zoo for Scala 3

Inspired by [Effect Zoo](https://github.com/ocharles/effect-zoo) for Haskell.

Effect Zoo provides:

1. Code gallery, where we can demonstrate and compare, how different effect systems solve the same task.
The sources are located [here](https://github.com/marcinzh/effect-zoo/tree/main/modules/core/src/main/scala/effect_zoo/contests).

2. Microbenchmark suite. See running instruction [below](README.md#running-microbenchmarks). Currently, in Scala community,
the microbenchmark discourse is almost exclusively oriented about `IO` performance. 
That's unlike in Effect Zoo, where (currently) included scenarios don't involve `IO`.


### Included scenarios (contests):

- `cdown`: The CountDown scenario from Haskell's Effect Zoo. Uses single `State` effect.

- `sumh`: In the spirit of CountDown, but uses more effects: `Reader`, `Writer`, `State`, etc.

- `mulst`: Multiple instances of `State` effects used at the same time. Compares overhead of effect stack size, by running the same number of `State` operations,  spread over a varying number `State` effects (1 to 5).

- `reint`: The Reinterpretation scenario from Haskell's Effect Zoo. Demonstrates use of custom-defined effects as application modules, and effect reinterpretation as DI mechanism.


### Included effect systems:

- [Cats Core](https://github.com/typelevel/cats). Plain monad transformers, used with manual lifting.

- [Cats MTL](https://github.com/typelevel/cats-mtl).

- [Cats Eff](https://github.com/atnos-org/eff). Don't confuse with [Cats Effect](https://github.com/typelevel/cats-effect).

- [ZIO](https://github.com/zio/zio). ZIO doesn't come with predefined standard effects such as
`Reader`, `Writer` and `State`. In this project we explore [several ways](https://github.com/marcinzh/effect-zoo/tree/main/modules/core/src/main/scala/effect_zoo/auxx/zio_/rws) of implementing them.

- ZPure from [ZIO Prelude](https://github.com/zio/zio-prelude).

- [Turbolift](https://github.com/marcinzh/turbolift).

There are many more effect systems for Scala (e.g. 3 more implementations of the Eff monad alone), but they are unmaintained and unavailable for Scala 2.13 or 3.x.

# Running Microbenchmarks
