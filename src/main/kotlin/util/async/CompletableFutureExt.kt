package moe.nea.notfirmament.util.async

import java.util.concurrent.CompletableFuture


fun CompletableFuture<*>.discard(): CompletableFuture<Void?> = thenRun { }
