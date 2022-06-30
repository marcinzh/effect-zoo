
This is an attempt to encode `Reader[R]`, `Writer[W]` and `State[S]` as layers.

It failes to work in ZIO 1.x, even though it compiles. See [README.md](../mono/README.md) in sibling directory.

It does work in ZIO 2.x, after minor updates (such as removing `Has`).
