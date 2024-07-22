import com.google.inject.{AbstractModule, Singleton}
import config.FlywayMigrator

class Module extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[FlywayMigrator]).asEagerSingleton()
  }
}
