package MyUtils

case class simResults(aCLen : Double, aCCost : Double, aExecTime: Double, aBwCost: Double) {
  //encapsulates results from 1 single simulation based on cloudlet performance values
  //a collection of these objects can be used to compute the statistics when iterating

  var avgCloudletLen : Double = aCLen
  var avgCPUcost : Double = aCCost
  var avgExecutionTime: Double = aExecTime
  var avgBandwidthCost: Double = aBwCost


}


