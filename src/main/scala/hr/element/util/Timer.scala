package hr.element.util

trait Timer {
  // ms/count
  def timeOnce(f: => Unit): Long = {
    val start = System.currentTimeMillis
    f
    val end = System.currentTimeMillis
    end-start
  }

  def time(initCount: Int = 0, repeatCount: Int = 1)(f: => Unit): Float = {
    var totalTime: Long = 0

    print("Initializing timer")
    for (i <- 1 to initCount) {
      print(".")
      f
    }
    println(" done!")

    print("Running %s repetitions".format(repeatCount))
    for (i <- 1 to repeatCount) {
      print(".")
      totalTime += timeOnce(f)
    }
    println(" done!")

    totalTime.toFloat / repeatCount.toFloat
  }
}


trait Meter extends Timer {
  def meter(size: Long, initCount: Int = 0, repeatCount: Int = 1)(f: => Unit): Float =
    size.toFloat / time(initCount, repeatCount)(f)   // b/ms === kb/s
}