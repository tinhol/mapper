package test

class Printer () {
    lateinit var name: String
    lateinit var serialNumber: String
    lateinit var ipAddress: String

    override fun toString(): String {
        return "Printer(name='$name', serialNumber='$serialNumber', ipAddress='$ipAddress')"
    }
}