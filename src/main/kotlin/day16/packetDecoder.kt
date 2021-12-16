package day16

import com.sun.javafx.scene.control.skin.IntegerFieldSkin
import java.io.File

fun main() {
    val input = File("src/main/kotlin/day16/input.txt")
        .readText()
        .map { Integer.toBinaryString(Integer.parseInt(it.toString(), 16)) }.joinToString("") { it.padStart(4, '0') }

    val (packet, remaining) = Packet.parse(input)
    println("Part One: ${packet.getVersionTotals()}")
}

data class Metadata(val version: Int, val type: Int)

fun getMetadata(binary: String): Pair<Metadata, String>{
    val version = Integer.parseInt(binary.take(3), 2)
    val type = Integer.parseInt(binary.drop(3).take(3), 2)
    return Metadata(version, type) to binary.drop(6)
}

abstract class Packet(val version: Int) {
    abstract fun getVersionTotals(): Int;
    companion object {
        fun parse(binary: String) : Pair<Packet, String>{
            val (metadata, remaining) = getMetadata(binary)
                return when (metadata.type) {
                    4 -> LiteralPacket.create(metadata.version, remaining)
                    else -> OperatorPacket.create(metadata.version, remaining)
                }
        }
    }
}

class LiteralPacket(version: Int, val value: Long) : Packet(version) {
    companion object {
        fun create(version: Int, binary: String): Pair<LiteralPacket, String>{
            val (value, remaining) = getValue(binary, "")
            return LiteralPacket(version, value) to remaining
        }
        fun getValue(binary: String, cumulativeValueString: String): Pair<Long, String>{
            val valuePart = binary.drop(1).take(4)
            return if (binary.first() == '0')
                (cumulativeValueString + valuePart).toLong(2) to binary.drop(5)
            else
                getValue(binary.drop(5), cumulativeValueString + valuePart)
        }
    }

    override fun getVersionTotals(): Int {
        return version
    }
}

class OperatorPacket(version: Int, private val contents: List<Packet>) : Packet(version) {

    companion object{
        fun create(version: Int, binary: String): Pair<OperatorPacket, String>{
            val (packets, remaining) = getContents(binary)
            return OperatorPacket(version, packets) to remaining
        }
        private fun getContents(binary: String): Pair<List<Packet>, String>{
            return when(binary.first()){
                '0' -> getPacketsByLength(binary.drop(1))
                else -> getPacketsByCount(binary.drop(1))
            }
        }
        private fun getPacketsByLength(binary: String): Pair<List<Packet>, String>{
            val packets = mutableListOf<Packet>()
            val length = Integer.parseInt(binary.take(15), 2)
            var remaining = binary.drop(15).take(length)
            while(remaining.isNotEmpty()){
                val (packet, next) = Packet.parse(remaining)
                packets.add(packet)
                remaining = next
            }
            return packets to binary.drop(15).drop(length)
        }
        private fun getPacketsByCount(binary: String): Pair<List<Packet>, String>{
            val packets = mutableListOf<Packet>()
            val count = Integer.parseInt(binary.take(11), 2)
            var remaining = binary.drop(11)
            while (packets.size < count){
                val (packet, next) = Packet.parse(remaining)
                packets.add(packet)
                remaining = next
            }
            return packets to remaining
        }
    }

    override fun getVersionTotals(): Int {
        return version + contents.sumOf { it.getVersionTotals() }
    }
}