package dam.a51728.vecLib

import kotlin.math.sqrt

data class Vec2(val x: Double, val y: Double): Comparable<Vec2>{

    fun magnitude(): Double{return sqrt(x*x + y*y)}
    fun dot(other: Vec2): Double {return x*other.x + y*other.y}
    fun normalized(): Vec2{
        check(x != 0.0 && y != 0.0)
        return this/magnitude()
    }

    operator fun div(scalar: Double) = Vec2(x/scalar, y/scalar)

    operator fun plus(b: Vec2): Vec2 = Vec2(x + b.x, y + b.y)

    operator fun minus(b: Vec2) = Vec2(x - b.x, y - b.y)

    operator fun times(b: Double) = Vec2(x * b, y * b)

    operator fun unaryMinus() = Vec2(-x, -y)

    override fun compareTo(other: Vec2): Int {
        return if(magnitude() == other.magnitude()) 0 else (
                if(magnitude() > other.magnitude()) 1 else -1
                )
    }

    operator fun get(idx: Int): Double = if(idx == 0) x else (if(idx == 1) y else throw IndexOutOfBoundsException())

}
