package config

import com.google.inject.{Inject, Singleton}
import org.flywaydb.core.Flyway
import play.api.Logger
import play.api.db.Database

@Singleton
class FlywayRunner @Inject(db: Database) {

  private val logger = Logger(this.getClass)

  def runMigrations(): Unit = {
    val flyway = Flyway.configure()
      .dataSource(db.dataSource)
      .load()
    
    logger.info("Running migrations...")
    try {
      flyway.migrate()
      logger.info("Migrations applied successfully")
    } catch {
      case e: Exception =>
        logger.error("Migrations failed", e)
        throw e
    }
      
  }
}
