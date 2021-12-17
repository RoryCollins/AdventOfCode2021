package day16

import java.io.File

fun main() {
    val input = File("src/main/kotlin/day16/input.txt")
        .readText()
        .map { Integer.toBinaryString(Integer.parseInt(it.toString(), 16)) }.joinToString("") { it.padStart(4, '0') }

    val (packet, _) = Packet.parse(input)
    println("Part One: ${packet.getVersionTotals()}")
    println("Part Two: ${packet.getValue()}")
}

data class Metadata(val version: Int, val type: Int)

fun getMetadata(binary: String): Pair<Metadata, String>{
    val version = Integer.parseInt(binary.take(3), 2)
    val type = Integer.parseInt(binary.drop(3).take(3), 2)
    return Metadata(version, type) to binary.drop(6)
}

abstract class Packet(val metadata: Metadata) {
    abstract fun getVersionTotals(): Int
    abstract fun getValue(): Long
    companion object {
        fun parse(binary: String) : Pair<Packet, String>{
            val (metadata, remaining) = getMetadata(binary)
                return when (metadata.type) {
                    4 -> LiteralPacket.create(metadata, remaining)
                    else -> OperatorPacket.create(metadata, remaining)
                }
        }
    }
}

class LiteralPacket(metadata: Metadata, private val value: Long) : Packet(metadata) {
    companion object {
        fun create(metadata: Metadata, binary: String): Pair<LiteralPacket, String>{
            val (value, remaining) = getValue(binary, "")
            return LiteralPacket(metadata, value) to remaining
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
        return metadata.version
    }

    override fun getValue(): Long {
        return value
    }
}

class OperatorPacket(metadata: Metadata, private val contents: List<Packet>) : Packet(metadata) {
    companion object{
        fun create(metadata: Metadata, binary: String): Pair<OperatorPacket, String>{
            val (packets, remaining) = getContents(binary)
            return OperatorPacket(metadata, packets) to remaining
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
                val (packet, next) = parse(remaining)
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
                val (packet, next) = parse(remaining)
                packets.add(packet)
                remaining = next
            }
            return packets to remaining
        }
    }

    override fun getVersionTotals(): Int {
        return metadata.version + contents.sumOf { it.getVersionTotals() }
    }

    override fun getValue(): Long {
        return when(metadata.type){
            0 -> contents.sumOf { it.getValue() }
            1 -> contents.fold(1L){acc, packet -> packet.getValue() * acc}
            2 -> contents.minOf { it.getValue() }
            3 -> contents.maxOf { it.getValue() }
            5 -> if(contents.first().getValue()>contents.last().getValue()) 1 else 0
            6 -> if(contents.first().getValue()<contents.last().getValue()) 1 else 0
            else -> if(contents.first().getValue()==contents.last().getValue()) 1 else 0
        }
    }
}