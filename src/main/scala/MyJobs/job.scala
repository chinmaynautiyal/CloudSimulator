package MyJobs

import org.cloudbus.cloudsim.{Cloudlet, CloudletScheduler, CloudletSchedulerTimeShared, DatacenterBroker, UtilizationModel, UtilizationModelFull, Vm}
import org.cloudbus.cloudsim.core.CloudSim
import scala.util.Random

class job {
//needs to have a ref to current broker object
  //needs vm and cloudlet creating methods

  private var broker: DatacenterBroker = _
  private var index: Int = _
  //private var config: JobConfig = _

  //do you need sim reference?

/*
  def loadJob: job = {
    //initialises job according to configuration
    config = //load info from conf file


  }
*/

  def setJob(jobIndex: Int, db: DatacenterBroker): Unit = {

    this.broker = db
    this.index = jobIndex


  }

  def createCLoudletList(IdtoSet : Int):   List[Cloudlet] = {
    //for now make 10 cloudlets with a given configuration


    //val cloudletLength = 10000
    val pesNumber = 1
    val cloudletFileSize = 300
    val cloudletOutputSize = 400



    (1 to 100).map { i =>
      val r = new scala.util.Random
      val cloudletLength = 10000 + r.nextInt(( 100000 - 10000) + 1)
      val newCloudlet = new Cloudlet(i, cloudletLength, pesNumber, cloudletFileSize, cloudletOutputSize,
        new UtilizationModelFull(), new UtilizationModelFull(), new UtilizationModelFull())
      newCloudlet.setUserId(IdtoSet)
      newCloudlet
    }.toList

  }

  def createVMList: List[Vm] = {

    //for now create 5 vms with given configuration

    val userID = 1
    val mips: Double = 1000
    val diskSize: Long = 20000
    val bandwidth = 1000
    val ram = 2000
    val vcpu = 1
    val vmm = "XEN"


    (1 to 10).map{ i =>
      new Vm(i, broker.getId(), mips, vcpu, ram, bandwidth, diskSize, vmm, new CloudletSchedulerTimeShared())
    }.toList


  }

}
