package kingmc.util.exception

/**
 * A shortcut to send specified [throwable] to receiver
 *
 * @param throwable the exception to send
 * @param target the target to throw exception to
 */
fun <TReceiver : Any> TReceiver.sendException(handler: ExceptionHandler<TReceiver>, throwable: Throwable) {
    handler.sendException(throwable, this)
}