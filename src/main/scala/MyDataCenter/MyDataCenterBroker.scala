package MyDataCenter
import org.cloudbus.cloudsim.DatacenterBroker

//for now just to access protected simentity object
class MyDataCenterBroker extends DatacenterBroker("Default") {

  //get id for broker instance
  def getID(): Int = {

    super.getId
  }

}
