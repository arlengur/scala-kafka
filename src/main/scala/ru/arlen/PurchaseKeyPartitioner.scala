package ru.arlen
import java.util.Date

import org.apache.kafka.clients.producer.internals.DefaultPartitioner
import org.apache.kafka.common.Cluster

class PurchaseKeyPartitioner extends DefaultPartitioner {
  override def partition(
      topic: String,
      key: Object,
      keyBytes: Array[Byte],
      value: Object,
      valueBytes: Array[Byte],
      cluster: Cluster
  ): Int = {
    var newKey: String           = ""
    var newKeyBytes: Array[Byte] = Array()
    if (key != null) {
      val purchaseKey = key.asInstanceOf[PurchaseKey]
      newKey = purchaseKey.customerId
      newKeyBytes = newKey.getBytes
    }
    super.partition(topic, newKey, newKeyBytes, value, valueBytes, cluster)
  }
}

case class PurchaseKey(customerId: String, transactionDate: Date)
