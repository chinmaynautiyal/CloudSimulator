package MyDataCenter

import com.typesafe.config.{Config, ConfigBeanFactory}
import com.typesafe.scalalogging.LazyLogging
import config.{ConfigDataCenter, ConfigHost}
import org.cloudbus.cloudsim.{Datacenter, DatacenterCharacteristics, Host, Pe, Storage, VmAllocationPolicySimple, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.network.datacenter.{NetworkDatacenter, NetworkHost}
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple

import scala.jdk.CollectionConverters._


//put datacenter/network creation stuff here
object DataCenterLib extends LazyLogging {
  def loadDataCenterConfig(config: Config): List[ConfigDataCenter] = {

    //insert logger trace
    logger.info("Loading data center configurations from conf file. ")
    config.getConfigList("dataCenters").asScala.map(ConfigBeanFactory.create(_, classOf[ConfigDataCenter])).toList


  }


  //create Network?


  //Step 3a: create CPUs/PEs  (called by createHosts)
  def createCPU(numCPU: Int, coreCapacity: Double): List[Pe] = {
    logger.info(s"Creating $numCPU CPUs with $coreCapacity core capacity")
    (1 to numCPU).map { i =>
      new Pe(i, new PeProvisionerSimple(coreCapacity))
    }.toList


  }


  //Step 3b: create Host List

  def createHosts(hostConfig: List[ConfigHost]): List[Host] = {
    val hostTypes = hostConfig.size
    logger.info(s"Creating hosts of $hostTypes")
    hostConfig.flatMap { configIter =>
      (1 to configIter.number ).map { i =>

        val storage = configIter.storage
        val bw = configIter.bandwidth
        val mips = configIter.mips
        val peList = createCPU(configIter.cores, configIter.mips)
        val host = new Host(i, new RamProvisionerSimple(configIter.ram.toInt), new BwProvisionerSimple(configIter.bandwidth), configIter.storage, peList.asJava, new VmSchedulerTimeShared(peList.asJava))
        host
        //host.setVmScheduler(createVmScheduler(configIter.vmScheduler))

      }
    }
  }

  //Step 3c: create Data Center Characteristics


  //Step 3d: Create Data Center objects and return as a list of Data Centers

  def createDataCenters(config: List[ConfigDataCenter]): List[Datacenter] = {


    //proceed to create data center charactristics


    //initialise data center characteristic objects
    config.map { i =>
      //create host list to store machines
      val hostList = createHosts(i.hosts)

      //create datacenter characteristics object
      val dCharacteristics = new DatacenterCharacteristics(i.architecture, i.os, i.vmm, hostList.asJava, i.timeZone, i.cost_second, i.cost_memory, i.cost_storage, i.cost_bandwidth)

      val SANStorage: List[Storage] = Nil

      val datacenter = new Datacenter(i.dcName, dCharacteristics, new VmAllocationPolicySimple(hostList.asJava), SANStorage.asJava, i.schedulingInterval)


      datacenter

    }
  }
/*
  def createNetwork()
    //not using for now
*/



}