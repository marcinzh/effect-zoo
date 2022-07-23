
###This is ZIO 1.x specific

ZIO's `Has[_]` doesn't seem to work for polymorphic types. This means it's impossible to 
implement standard effects, such as `Reader[R]`, `Writer[W]` and `State[S]`, by following 
official ZIO's [Module Patterns](https://zio.dev/version-1.x/datatypes/contextual/#module-pattern-10).

We can get around this limitation, by altering the pattern, so that the service
is polymorphic at definition site, but it's monomorphised before use, e.g.:

```scala
case object MyState = State[Int]

type MyState = Has[MyState.Service]
```

Note, that this still doesn't allow us to use 2 instances of service at the same time, e.g.:


```scala
case object S1 extends State[Int]
case object S2 extends State[Int]
type S1 = Has[S1.Service]
type S2 = Has[S2.Service]

val prog: UIO[S1 & S2, Unit] =
	for
		n <- S1.get
		_ <- S2.put(n)
	yield ()
```

Will compile. But won't work correctly, because ZIO 1.x runtime can't distinguish `S1` from `S2`.

