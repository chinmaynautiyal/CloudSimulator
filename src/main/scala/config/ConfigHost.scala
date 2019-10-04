package config

import scala.beans.BeanProperty


//configuration object for host within the data center
class ConfigHost {

  @BeanProperty var number: Int = _
  @BeanProperty var ram: Long = _
  @BeanProperty var storage: Long = _
  @BeanProperty var bandwidth: Long = _
  @BeanProperty var mips: Double = _
  @BeanProperty var cores: Int = _
  @BeanProperty var vmScheduler: String = _





}
