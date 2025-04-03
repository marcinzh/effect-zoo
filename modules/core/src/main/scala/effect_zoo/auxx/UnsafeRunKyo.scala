package effect_zoo.auxx
import kyo._


object UnsafeRunKyo:
  extension [A](thiz: A < IO)
    def unsafeRunKyo: A =
      import AllowUnsafe.embrace.danger
      KyoApp.Unsafe.runAndBlock(1.minute)(thiz).getOrThrow
