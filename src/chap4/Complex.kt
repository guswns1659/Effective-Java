package chap4

class Complex private constructor(private val re: Double, private val im: Double) {

    companion object Factory {
        fun valueOf(re: Double, im: Double): Complex {
            return Complex(re, im)
        }
    }
}