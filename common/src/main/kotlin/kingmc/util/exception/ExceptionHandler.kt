package kingmc.util.exception

/**
 * An interface is commonly used to handle exceptions in kingmc framework
 *
 * @param TReceiver the receiver receives exceptions
 * @since 0.0.9
 * @author kingsthere
 */
interface ExceptionHandler<TReceiver : Any> {
    /**
     * Send specified [throwable] to receiver
     *
     * @param throwable the exception to send
     * @param target the target to throw exception to
     */
    fun sendException(throwable: Throwable, target: TReceiver)
}