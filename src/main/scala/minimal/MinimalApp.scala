package minimal

import zio.*
import zio.logging.backend.SLF4J

trait ZioLogging {
  this: ZIOApp =>
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = Runtime.removeDefaultLoggers >>> SLF4J.slf4j
}

object MinimalApp extends ZIOAppDefault with ZioLogging {

  override def run = for {
    _ <- ZIO.logInfo("starting")
    _ <- ZIO.logInfo("complete")
  } yield ()
}
