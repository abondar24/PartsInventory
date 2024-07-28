import com.google.inject.{AbstractModule, Provides, Singleton}
import config.MyBatisConfig
import dao.{PartDetailMapper, PartMapper}
import org.apache.ibatis.session.SqlSessionFactory
import service.{PartService, PartServiceImpl}

class Module extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[PartService]).to(classOf[PartServiceImpl]).in(classOf[Singleton])
  }

  @Provides
  @Singleton
  def provideSqlSessionFactory(myBatisConfig: MyBatisConfig): SqlSessionFactory = {
    myBatisConfig.getSessionFactory
  }


  @Provides
  @Singleton
  def providePartMapper(sqlSessionFactory: SqlSessionFactory): PartMapper = {
    val session = sqlSessionFactory.openSession()
    try {
      session.getMapper(classOf[PartMapper])
    } finally {
      session.close()
    }
  }

  @Provides
  @Singleton
  def providePartDetailMapper(sqlSessionFactory: SqlSessionFactory): PartDetailMapper = {
    val session = sqlSessionFactory.openSession()
    try {
      session.getMapper(classOf[PartDetailMapper])
    } finally {
      session.close()
    }
  }
}
