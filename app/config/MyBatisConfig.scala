package config

import com.google.inject.{Inject, Singleton}
import org.apache.ibatis.session.{SqlSessionFactory, SqlSessionFactoryBuilder}
import org.apache.ibatis.io.Resources

@Singleton
class MyBatisConfig @Inject(){

  private val configStream = Resources.getResourceAsStream("mybatis-config.xml")
  private val sqlSessionFactory: SqlSessionFactory = new SqlSessionFactoryBuilder().build(configStream)


  def getSessionFactory: SqlSessionFactory = sqlSessionFactory
}
