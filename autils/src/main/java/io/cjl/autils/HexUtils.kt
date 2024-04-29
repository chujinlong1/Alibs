package io.cjl.autils

import java.math.BigInteger
import java.util.Locale

object HexUtils {

    /**
     * 计算CRC16校验码
     * @param bytes 字节数组
     * @return  校验码
     */
    fun getCRC16(bytes: ByteArray): String? {
        // CRC寄存器全为1
        var CRC = 0x0000ffff
        // 多项式校验值
        val POLYNOMIAL = 0x0000a001
        var i: Int
        var j: Int
        i = 0
        while (i < bytes.size) {
            CRC = CRC xor (bytes[i].toInt() and 0x000000ff)
            j = 0
            while (j < 8) {
                if (CRC and 0x00000001 != 0) {
                    CRC = CRC shr 1
                    CRC = CRC xor POLYNOMIAL
                } else {
                    CRC = CRC shr 1
                }
                j++
            }
            i++
        }
        // 结果转换为16进制
        var result = Integer.toHexString(CRC).uppercase(Locale.getDefault())
        if (result.length != 4) {
            val sb = StringBuffer("0000")
            result = sb.replace(4 - result.length, 4, result).toString()
        }
        return strFormatHex(result)
    }

    /**
     * 计算CRC16/Modbus校验码  低位在前,高位在后
     * @param str 十六进制字符串
     * @return
     */
    fun getCRC(str: String?): String? {
        val bytes: ByteArray = toBytes(str)
        var CRC = 0x0000ffff
        val POLYNOMIAL = 0x0000a001
        var i: Int
        var j: Int
        i = 0
        while (i < bytes.size) {
            CRC = CRC xor (bytes[i].toInt() and 0x000000ff)
            j = 0
            while (j < 8) {
                if (CRC and 0x00000001 != 0) {
                    CRC = CRC shr 1
                    CRC = CRC xor POLYNOMIAL
                } else {
                    CRC = CRC shr 1
                }
                j++
            }
            i++
        }
        var crc = Integer.toHexString(CRC)
        if (crc.length == 2) {
            crc = "00$crc"
        } else if (crc.length == 3) {
            crc = "0$crc"
        }
        return strFormatHex(crc.uppercase(Locale.getDefault()))
    }

    fun toBytes(str: String?): ByteArray {
        return BigInteger(str, 16).toByteArray()
    }

    /**
     * @param str
     * @return
     */
    fun strFormatHex(str: String):String?{
        try {
            return str.replace("(.{2})", "$1 ")
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }

    fun bytesToHex(bytes: ByteArray?): String? {
        return bytes?.let { bytesToHex(it, true) }
    }

    fun bytesToHex(bytes: ByteArray, withSpaces: Boolean): String? {
        return bytesToHex(bytes, withSpaces, 0, bytes.size)
    }

    fun bytesToHex(bytes: ByteArray?, withSpaces: Boolean, begin: Int, end: Int): String? {
        val bs: ByteArray = subarray(bytes, begin, end)!!
        val sb = StringBuilder()
        for (b in bs) {
            val hex = Integer.toHexString(b.toInt() and 0xFF).uppercase(Locale.getDefault())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
            if (withSpaces) {
                sb.append(" ")
            }
        }
        return sb.toString().trim { it <= ' ' }
    }

    val EMPTY_BYTE_ARRAY = ByteArray(0)

    fun subarray(array: ByteArray?, startIndexInclusive: Int, endIndexExclusive: Int): ByteArray? {
        var startIndexInclusive = startIndexInclusive
        var endIndexExclusive = endIndexExclusive
        if (array == null) {
            return null
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0
        }
        if (endIndexExclusive > array.size) {
            endIndexExclusive = array.size
        }
        val newSize = endIndexExclusive - startIndexInclusive
        if (newSize <= 0) {
            return EMPTY_BYTE_ARRAY
        }
        val subarray = ByteArray(newSize)
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize)
        return subarray
    }

    fun intToHex(n: Int): String? {
        var n = n
        var sb = java.lang.StringBuilder(8)
        var a: String
        val b = charArrayOf(
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F'
        )
        while (n != 0) {
            sb = sb.append(b[n % 16])
            n = n / 16
        }
        a = sb.reverse().toString()
        if (a.length % 2 != 0) {
            a = "0$a"
        }
        return a
    }
}