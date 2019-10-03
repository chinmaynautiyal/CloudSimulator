package config
import scala.beans.BeanProperty
import scala.jdk.CollectionConverters._


class ConfigDataCenter {



  @BeanProperty var architecture: String = _

  @BeanProperty var dcName: String = _

  @BeanProperty var vmm: String = _

  @BeanProperty var os: String = _

  @BeanProperty var timeZone: Double = _

  @BeanProperty var schedulingInterval: Double = _


  @BeanProperty var vmAllocationPolicy: String = _

  var hosts: List[ConfigHost] = _

  @BeanProperty var cost_second: Double = _

  @BeanProperty var cost_memory: Double = _

  @BeanProperty var cost_storage: Double = _

  @BeanProperty var cost_bandwidth: Double = _

  @BeanProperty var rootSwitches: ConfigSwitch = _

  @BeanProperty var aggregateSwitches: ConfigSwitch = _

  @BeanProperty var edgeSwitches: ConfigSwitch = _


  def getHosts: java.util.List[ConfigHost] = hosts.asJava

  def setHosts(hosts: java.util.List[ConfigHost]): Unit = {
    this.hosts = hosts.asScala.toList
  }

}

