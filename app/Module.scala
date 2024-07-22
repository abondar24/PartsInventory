import com.google.inject.{AbstractModule, Singleton, Provides}
import config.MyBatisConfig
import dao.{PartDao, PartDaoImpl}
import org.apache.ibatis.session.SqlSessionFactory
import play.api.db.Database

class Module extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[PartDao]).to(classOf[PartDaoImpl]).in(classOf[Singleton])
  }

  @Provides
  @Singleton
  def provideSqlSessionFactory(myBatisConfig: MyBatisConfig): SqlSessionFactory = {
    myBatisConfig.getSessionFactory
  }
}
