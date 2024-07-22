package config

import com.google.inject.{Inject, Singleton}
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

@Singleton
class FlywayMigrator @Inject()(flywayRunner: FlywayRunner, lifecycle: ApplicationLifecycle) {
    lifecycle.addStopHook{
      ()=> Future.successful(())
    }
    
    flywayRunner.runMigrations()
}
