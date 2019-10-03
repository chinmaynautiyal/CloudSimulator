package MyDataCenter
import org.cloudbus.cloudsim.DatacenterBroker
class MyDataCenterBroker extends DatacenterBroker("Default") {

  //get id for broker instance
  def getID(): Int = {

    super.getId
  }

}
