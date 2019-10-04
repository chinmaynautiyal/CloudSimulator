
import java.util.Calendar

import com.typesafe.config.{Config, ConfigBeanFactory, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

import scala.jdk.CollectionConverters._
import java.util.Calendar

import org.cloudbus.cloudsim.{Cloudlet, DatacenterBroker, Host, Pe, UtilizationModelFull, Vm, VmScheduler, VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.lists.PeList
import org.cloudbus.cloudsim.network.datacenter.{AggregateSwitch, EdgeSwitch, NetDatacenterBroker, NetworkCloudlet, NetworkConstants, NetworkDatacenter, NetworkHost, RootSwitch}
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple
import config.ConfigHost
import config.ConfigDataCenter
import config.ConfigSwitch
import MyDataCenter.{DataCenterLib, MyDataCenterBroker}
import MyJobs.job
import MyUtils.simResults


object driver extends LazyLogging {

  def main(args: Array[String]): Unit = {

    //loading configuration from conf files
    val conf = ConfigFactory.load()
    val cloudArch = conf.getConfigList("architectures").asScala
    val num_user = 1 //conf.getString("numUser").toInt


    //Step 1: initialise simulation object
    val calendar = Calendar.getInstance()
    val traceFlag = false
    val cloudSim = CloudSim.init(num_user, calendar, traceFlag) //notes to self: can I run it for one sim object and multiple architectures or do I need multiple sim objects?


    //Step 2: creating datacenters for each datacenter config in cloud arch

    cloudArch.foreach { i =>
      val archName = i.getString("name")
      logger.info(s"Starting simulation for architecture $archName")
      val loadBalancer = i.getString("loadBalancer")
      logger.info(s"Load Balancing Heuristic: $loadBalancer")

      val dataCenterConfig = DataCenterLib.loadDataCenterConfig(i) //returns list of datacenter configurations


      //Step 2 contd.: create data Centers ---- control goes to createDataCenters in DataCenterLib
      val dataCenterList = DataCenterLib.createDataCenters(dataCenterConfig) //returns list of datacenter objects with given configurations



      //create jobs

      val alljobs: List[(Int, job)] = (0 until 5).map{ i =>
        (i, new job())
      }.toList


      //Step 4: Create Brokers -- each job will have to have it's own broker(each job has a bunch of vms and cloudlets to be submitted
      val dcBrokerList = alljobs.map {i =>
        fetchBroker(i, loadBalancer)
      } //should there be one broker or many? (?they should be based on scheduling and allocation policies th










      logger.info(s"Starting simulation")
      CloudSim.startSimulation()

      val resultList = printSimResults(dcBrokerList)
      printAllStats(resultList)

      //val dataCenterbroker = createBroker();

    }
  }


//returns appropriate broker
  def fetchBroker(i: (Int, job), lb: String): DatacenterBroker   = {

    //returns broker list for each job in accordance with job ids, allocation policy and loadBalancer
    //create normal datacenter for now then work with network datacenter
    val name: String = i.getClass.getSimpleName
    val broker = new MyDataCenterBroker()

    val brokerId = broker.getId
    //initialise job

    val jobRef = i._2
    val ind = i._1

    //bind job to broker
    jobRef.setJob(ind, broker)
    //create job vms and cloudletlist

    val vList = jobRef.createVMList
    val cList = jobRef.createCLoudletList(brokerId)


    broker.submitVmList(vList.asJava)
    broker.submitCloudletList(cList.asJava)
    //return broker
    broker



  }






  def createJobs(numJobs: Int) : List[Cloudlet] = {

    //create a stream of network cloudlets


    //instantiating required fields for network cloudlet constructor

    val fulllUtil = new UtilizationModelFull()
    val cloudletLen = 1000
    val pesNumber = 2
    val fileSize = 10
    val outputSize = 10
    val memory = 1000
    (1 to numJobs).map { i =>
      val cloudlet = new NetworkCloudlet(i , cloudletLen, pesNumber, fileSize, outputSize, memory, fulllUtil, fulllUtil, fulllUtil)
      cloudlet
    }.toList
  }


  //returns list of result objects for individual brokers
  def printSimResults (bList: List[DatacenterBroker]): List[simResults] = {
    bList.map { i =>
      logger.info(s"Printing results for broker")
      val indent = "        "
      println("=====================OUTPUT==========================")
      println("Cloudlet ID" + indent + "STATUS" + indent + "Data center ID" + indent + "VM ID"
        + indent + "Time" + indent + "Start Time" + indent + "Finish Time")
      println()
      val brokerCompletedCloudlets = i.getCloudletReceivedList().asInstanceOf[java.util.List[Cloudlet]].asScala
      brokerCompletedCloudlets.foreach { i =>

        print(indent + i.getCloudletId() + indent)
        if (i.getCloudletStatus == Cloudlet.SUCCESS) {
          print("SUCCESS")
          println(indent + i.getResourceId() + indent + indent
            + i.getVmId() + indent + indent + i.getActualCPUTime()
            + indent + indent + i.getExecStartTime() + indent + indent
            + i.getFinishTime())


        }

      }
      //collect into result list
      //get cloudlet performance measures
      val size = brokerCompletedCloudlets.size
      val result = MyUtils.simResults(brokerCompletedCloudlets.map(i => i.getCloudletTotalLength).sum / size,
        brokerCompletedCloudlets.map(i => i.getFinishTime - i.getExecStartTime).sum / size,
        brokerCompletedCloudlets.map(i => i.getActualCPUTime * i.getCostPerSec).sum / size,
        brokerCompletedCloudlets.map(i => i.getAccumulatedBwCost).sum / size)
      result
    }




  }

  //print average results for all brokers over iterations of the simulation
def printAllStats(resultList: List[simResults]): Unit = {
  //prints all stats for one architecture over given number of iterations

  logger.info("Printing results of the simulation")
  println("*****************SIMULATION RESULTS*******************")

  val size = resultList.size
  println("Result for " + size + " brokers ")

  resultList.foreach { i =>
    val indent = "    "
    println()
    println("Avg Execution Time" + indent + "Avg CPU Cost" + indent + indent + "Avg Bandwidth Cost")
    println(i.avgExecutionTime + indent + indent +  i.avgCPUcost + indent + indent +  i.avgBandwidthCost)
  }

}



/*
  def createBroker(): Unit = {

  }

  def createVmScheduler(str : String): VmScheduler =  {
    str match {
      case "SpaceShared" => new VmSchedulerSpaceShared()
      case "TimeShared"  => new VmSchedulerTimeShared()
      case _ => new VmSchedulerTimeShared()
    }

  }
*/
/*
  def createVms(): List[Vm] = {

    //default config for network vms
    val userId = 1
    val mips = 2000
    val pes = 100
    val ram = 4096
    val bw  = 1
    val

  }
*/


}

