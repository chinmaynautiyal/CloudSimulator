package config
import scala.beans.BeanProperty

class ConfigSwitch {
  @BeanProperty var number : Int = _
  @BeanProperty var ports : Int = _
  @BeanProperty var bandwidth : Long = _
  @BeanProperty var switchingDelay : Double = _
}
