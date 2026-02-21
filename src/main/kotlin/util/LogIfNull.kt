
package moe.nea.notfirmament.util


fun runNull(block: () -> Unit): Nothing? {
    block()
    return null
}
