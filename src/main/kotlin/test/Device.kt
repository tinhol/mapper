package test

class Device () {
    lateinit var name: String
    lateinit var serialNumber: String
    lateinit var ipAddress: String

    override fun toString(): String {
        return "Device(name='$name', serialNumber='$serialNumber', ipAddress='$ipAddress')"
    }
}