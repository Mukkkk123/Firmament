package moe.nea.notfirmament.util

import java.util.Optional

fun <T : Any> T?.intoOptional(): Optional<T> = Optional.ofNullable(this)
