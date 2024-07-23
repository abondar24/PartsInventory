import com.google.inject.{AbstractModule, Provides, Singleton}
import config.MyBatisConfig
import dao.{PartDao, PartDaoImpl, PartDetailDao, PartDetailDaoImpl}
import org.apache.ibatis.session.SqlSessionFactory
import play.api.db.Database

class Module extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[PartDao]).to(classOf[PartDaoImpl]).in(classOf[Singleton])
    bind(classOf[PartDetailDao]).to(classOf[PartDetailDaoImpl]).in(classOf[Singleton])
  }

  @Provides
  @Singleton
  def provideSqlSessionFactory(myBatisConfig: MyBatisConfig): SqlSessionFactory = {
    myBatisConfig.getSessionFactory
  }
}
